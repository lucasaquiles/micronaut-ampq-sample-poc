package com.github.lucasaquiles.config.properties;

public class QueuePropertyMap {
    private QueueProperties.Binding binding;
    private int maxRetry;
    private int interval;

    public QueuePropertyMap(QueueProperties.Binding binding, int maxRetry, int interval) {
        this.binding = binding;
        this.maxRetry = maxRetry;
        this.interval = interval;
    }

    public QueueProperties.Binding getBinding() {
        return binding;
    }

    public void setBinding(QueueProperties.Binding binding) {
        this.binding = binding;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
