package com.github.lucasaquiles.config.factory;

import com.github.javafaker.Faker;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

@Factory
public class FakerFactory {

    @Bean
    Faker faker() {
        return new Faker();
    }
}
