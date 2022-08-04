package com.github.lucasaquiles.listener;

import com.github.lucasaquiles.config.QueueDeclaration;
import com.github.lucasaquiles.domain.Event;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.messaging.annotation.MessageListener;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Requires(notEnv = Environment.TEST)
@RabbitListener
public class EventListener extends RetriableHandler {

    private final Logger log = LoggerFactory.getLogger(MessageListener.class);

    @Queue("queue-name-a")
    public void read(final Event event) {
        log.info("M=read, I=receiving event. event={}", event);
        throw new RuntimeException("no sense runtime exception!!");
    }

    @Override
    public QueueDeclaration queue() {
        return QueueDeclaration.SIMPLE_QUEUE;
    }
}
