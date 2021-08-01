package com.github.lucasaquiles.config;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class QueueInitializerConfig {

    private final Logger log = LoggerFactory.getLogger(QueueInitializerConfig.class);

    public void applyQueueConfig(final Channel channel) {
        Stream.of(QueueDeclaration.values())
                .forEach(queue -> declareQueueWith(channel, queue));
    }

    private void declareQueueWith(Channel channel, QueueDeclaration queue) {
        try{

            final String exchangeName = queue.getExchangeName();
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);

            declareQueue(channel, queue.getQueueName(), exchangeName, queue.getQueueName(), false);

            if(queue.WithDLQ()) {
                declareQueue(channel, queue.getDLQName(), exchangeName, queue.getQueueName(), false);
            }

            if(queue.withRetry()) {
                declareQueue(channel, queue.getRetryName(), exchangeName, queue.getQueueName(), true);
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void declareQueue(Channel channel, String queue, String exchangeName, String routingKey, boolean withDLQ) throws IOException {
        final Map<String, Object> queueArgs = new HashMap<>();

        if(withDLQ) {
            queueArgs.put("x-dead-letter-exchange", exchangeName);
            queueArgs.put("x-dead-letter-routing-key", routingKey);
        }

        channel.queueDeclare(queue, true, false, false, queueArgs);
        channel.queueBind(queue, exchangeName, queue);

        log.info("M=declareQueue, I=declaring queue. queue={}", queue);
    }
}
