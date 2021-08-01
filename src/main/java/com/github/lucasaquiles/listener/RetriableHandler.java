package com.github.lucasaquiles.listener;

import com.github.lucasaquiles.config.QueueDeclaration;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.bind.RabbitConsumerState;
import io.micronaut.rabbitmq.exception.RabbitListenerException;
import io.micronaut.rabbitmq.exception.RabbitListenerExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class RetriableHandler implements RabbitListenerExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(RetriableHandler.class);

    private final String X_DEATH = "x-death";
    private final String TTL_COUNT = "count";

    abstract public QueueDeclaration queue();

    public void sendTo(final Optional<RabbitConsumerState> rabbitConsumerState, String queuename, String exchange, byte[] message, AMQP.BasicProperties properties) {

        rabbitConsumerState.ifPresent(state -> {
            try {

                final Channel channel = state.getChannel().getConnection().createChannel();
                log.info("M=sendTo, I=should move to, queue={}", queuename);
                channel.basicPublish(exchange, queuename, properties, message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void handle(RabbitListenerException exception) {
        log.error("M=handle, E=handling, listener={}, exception={}", exception.getListener(), exception.getMessage());

        long count = getRetryCount(exception.getMessageState());
        if (count < queue().getMaxRetry()) {

            long interval = Duration.of(queue().getInterval() * count, ChronoUnit.SECONDS).toMillis();
            log.info("M=handle, I=should retry, ttl={}", interval);
            sendTo(
                    exception.getMessageState(),
                    queue().getRetryName(),
                    queue().getExchangeName(),
                    exception.getMessageState().get().getBody(),
                    updateMessageProperties(exception.getMessageState(), interval)
            );
        } else {

            sendToDLQ(exception.getMessageState());
        }

    }

    private void sendToDLQ(Optional<RabbitConsumerState> messageState) {

        final RabbitConsumerState rabbitConsumerState = messageState.get();
        rabbitConsumerState.getProperties().getHeaders().remove(X_DEATH);
        sendTo(
                messageState,
                queue().getDLQName(),
                queue().getExchangeName(),
                rabbitConsumerState.getBody(),
                rabbitConsumerState.getProperties()
        );
    }

    private AMQP.BasicProperties updateMessageProperties(Optional<RabbitConsumerState> messageState, final long ttl) {
        final AMQP.BasicProperties old = messageState.get().getProperties();
        return new AMQP.BasicProperties("application/json",
                "UTF-8",
                old.getHeaders(),
                old.getDeliveryMode(),
                old.getPriority(),
                old.getCorrelationId(),
                old.getReplyTo(),
                String.valueOf(ttl),
                old.getMessageId(),
                old.getTimestamp(),
                old.getType(),
                old.getUserId(),
                old.getAppId(),
                old.getClusterId()
        );
    }

    private long getRetryCount(final Optional<RabbitConsumerState> messageState) {
        long retryCount = 0L;
        final RabbitConsumerState rabbitConsumerState = messageState.get();

        final Map<String, Object> headers = rabbitConsumerState.getProperties().getHeaders();
        if (headers.containsKey(X_DEATH)) {

            final List list = (List) Collections.singletonList(headers.get(X_DEATH)).get(0);
            retryCount = Long.parseLong(((Map) list.get(0)).get(TTL_COUNT).toString());
        }

        log.info("M=getRetryCount, I=get current header count, retryCount={}", retryCount);
        return retryCount;
    }
}
