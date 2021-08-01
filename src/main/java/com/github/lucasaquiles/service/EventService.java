package com.github.lucasaquiles.service;

import com.github.lucasaquiles.client.EventProducer;
import com.github.lucasaquiles.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class EventService {

    private final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventProducer analyticsRabbitClient;

    public EventService(EventProducer analyticsRabbitClient) {
        this.analyticsRabbitClient = analyticsRabbitClient;
    }

    public void send(Event event) {

        log.info("M=send, I=sending, event={}", event);
        analyticsRabbitClient.send(new HashMap<>(), event);
        log.info("M=send, I=sent, event={}", event);
    }
}
