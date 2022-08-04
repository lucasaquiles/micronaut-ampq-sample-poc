package com.github.lucasaquiles.config.annotation;

import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.rabbitmq.bind.RabbitAnnotatedArgumentBinder;
import io.micronaut.rabbitmq.bind.RabbitConsumerState;

import javax.inject.Singleton;

@Singleton
public class SourceAnnotationBinder implements RabbitAnnotatedArgumentBinder<Source> {
    @Override
    public Class<Source> getAnnotationType() {
        return Source.class;
    }

    @Override
    public BindingResult<Object> bind(ArgumentConversionContext<Object> context, RabbitConsumerState source) {
        return null;
    }
}
