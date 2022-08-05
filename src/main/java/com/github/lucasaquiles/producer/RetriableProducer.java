package com.github.lucasaquiles.producer;

import com.github.lucasaquiles.domain.Event;
import io.micronaut.messaging.annotation.Header;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

import java.util.Map;

@RabbitClient
public interface RetriableProducer {

    @Binding("retriable-queue")
    void send(@Header Map<String, Object> headers, Event event);
}
