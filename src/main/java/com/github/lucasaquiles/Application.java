package com.github.lucasaquiles;

import com.github.lucasaquiles.domain.Event;
import com.github.lucasaquiles.service.EventService;
import io.micronaut.runtime.Micronaut;
import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class Application {


    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }


}
