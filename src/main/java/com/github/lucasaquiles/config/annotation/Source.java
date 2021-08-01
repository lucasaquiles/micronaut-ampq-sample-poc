package com.github.lucasaquiles.config.annotation;

import io.micronaut.core.bind.annotation.Bindable;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.PARAMETER
})
@Bindable
public @interface Source {
}
