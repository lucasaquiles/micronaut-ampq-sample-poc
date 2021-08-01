package com.github.lucasaquiles.api;

import com.github.lucasaquiles.domain.Event;
import com.github.lucasaquiles.service.EventService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/event")
public class EventController {

    private final Logger log = LoggerFactory.getLogger(EventController.class);
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Flowable<Event> send(@Body Event event) {

        return Flowable.fromCallable(() -> {
            eventService.send(event);
            return event;
        }).doOnNext(ev -> log.info("M=send, I=sending, event={}", ev));
    }
}
