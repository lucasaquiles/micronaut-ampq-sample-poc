package com.github.lucasaquiles.config.annotation;

import com.github.lucasaquiles.config.RabbitListenerMethodInterceptor;
import io.micronaut.aop.Around;
import io.micronaut.context.annotation.Type;
import io.micronaut.core.annotation.Internal;

@Around
@Internal
@Type(RabbitListenerMethodInterceptor.class)
public @interface RabbitListenerAdvice {
}
