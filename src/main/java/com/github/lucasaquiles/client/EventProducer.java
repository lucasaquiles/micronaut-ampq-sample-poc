package com.github.lucasaquiles.client;

import com.github.lucasaquiles.domain.Event;
import io.micronaut.messaging.annotation.Header;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.reactivex.Maybe;

import java.util.Map;

@RabbitClient
public interface EventProducer {

    @Binding("simple-queue")
//    @RabbitProperty(name = "contentType", value = "application/json")
//    @RabbitProperty(name = "contentEncoding", value = "UTF-8")
    Maybe<Void> send(@Header Map<String, Object> headers, Event event);
}
