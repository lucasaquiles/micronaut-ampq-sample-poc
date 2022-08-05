package com.github.lucasaquiles.listener;

import com.github.lucasaquiles.config.annotation.RetriableQueue;
import com.github.lucasaquiles.domain.Event;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Requires(notEnv = Environment.TEST)
@RabbitListener
public class RetriableListener {

    private final Logger log = LoggerFactory.getLogger(RetriableListener.class);

    @RetriableQueue(value = "retriable-queue", maxRetry = 3, interval = 10)
    @Queue(value = "retriable-queue")
    public void read(final Event event) {

        log.info("M=read, I=receiving event. event={}", event);
        throw new RuntimeException("no sense runtime exception!!");
    }
}
