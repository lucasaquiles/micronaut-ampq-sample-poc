package com.github.lucasaquiles.domain;

import io.micronaut.core.annotation.Introspected;

import java.util.Objects;

@Introspected
public class Event {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(key, event.key) &&
                Objects.equals(value, event.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "Event{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
