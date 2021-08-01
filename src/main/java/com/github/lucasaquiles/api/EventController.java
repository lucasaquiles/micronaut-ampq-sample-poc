package com.github.lucasaquiles.api;

import com.github.lucasaquiles.domain.Event;
import com.github.lucasaquiles.service.EventService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Event send(@Body Event event) {

        eventService.send(event);
        return event;
    }
}
