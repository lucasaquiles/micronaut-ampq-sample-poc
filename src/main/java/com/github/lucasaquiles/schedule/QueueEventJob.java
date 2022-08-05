package com.github.lucasaquiles.schedule;

import com.github.javafaker.Faker;
import com.github.lucasaquiles.domain.Event;
import com.github.lucasaquiles.service.EventService;
import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class QueueEventJob {

    private static final Logger log = LoggerFactory.getLogger(QueueEventJob.class);

    @Inject
    private EventService eventService;

    @Inject
    private Faker faker;

    @Scheduled(fixedDelay = "30s")
    void executeNoRetriableEvent() {
        log.info("M=executeNoRetriableEvent, I=execute new event");

        final Event event = new Event();
        event.setKey(faker.starTrek().character());
        event.setValue(faker.starTrek().location());

        eventService.sendToNoRetriableQueue(event);
    }

    @Scheduled(fixedDelay = "10s")
    void executeToRetriableQueue() {
        log.info("M=executeToRetriableQueue, I=execute new event");

        final Event event = new Event();

        event.setKey("retriable: "+ faker.starTrek().character());
        event.setValue(faker.starTrek().location());

        eventService.sendToRetriableQueue(event);
    }
}
