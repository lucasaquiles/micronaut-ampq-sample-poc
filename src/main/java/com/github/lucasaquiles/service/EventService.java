package com.github.lucasaquiles.service;

import com.github.lucasaquiles.config.annotation.RetriableQueue;
import com.github.lucasaquiles.producer.EventProducer;
import com.github.lucasaquiles.domain.Event;
import com.github.lucasaquiles.producer.NoRetriableProducer;
import com.github.lucasaquiles.producer.RetriableProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class EventService {

    private final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventProducer eventProducer;
    private final NoRetriableProducer noRetriableProducer;
    private final RetriableProducer retriableProducer;

    public EventService(EventProducer analyticsRabbitClient, NoRetriableProducer noRetriableProducer, RetriableProducer retriableProducer) {
        this.eventProducer = analyticsRabbitClient;
        this.noRetriableProducer = noRetriableProducer;
        this.retriableProducer = retriableProducer;
    }

    public void send(final Event event) {
        log.info("M=send, I=sending, event={}", event);
        eventProducer.send(new HashMap<>(), event);
        log.info("M=send, I=sent, event={}", event);
    }

    public void sendToNoRetriableQueue(final Event event) {
        log.info("M=sendToNoRetriableQueue, I=sending, event={}", event);
        noRetriableProducer.send(new HashMap<>(), event);
        log.info("M=sendToNoRetriableQueue, I=sent, event={}", event);
    }

    public void sendToRetriableQueue(final Event event) {
        log.info("M=sendToRetriableQueue, I=sending, event={}", event);
        retriableProducer.send(new HashMap<>(), event);
        log.info("M=sendToRetriableQueue, I=sent, event={}", event);
    }


}
