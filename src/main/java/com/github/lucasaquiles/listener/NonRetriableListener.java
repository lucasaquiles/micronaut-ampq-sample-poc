package com.github.lucasaquiles.listener;

import com.github.lucasaquiles.domain.Event;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Requires(notEnv = Environment.TEST)
@RabbitListener
public class NonRetriableListener {

    private final Logger log = LoggerFactory.getLogger(NonRetriableListener.class);

    @Queue(value = "non-retriable-queue")
    public void read(final Event event) {
        log.info("M=read, I=receiving event. event={}", event);
    }
}
