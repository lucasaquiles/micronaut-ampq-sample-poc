package com.github.lucasaquiles.config.annotation;

import io.micronaut.aop.Adapter;
import io.micronaut.rabbitmq.exception.DefaultRabbitListenerExceptionHandler;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Adapter(DefaultRabbitListenerExceptionHandler.class)
@RabbitListenerAdvice
public @interface RetriableQueue {

    String value() default "";
    int maxRetry() default 0;
    int interval() default 0;
}
