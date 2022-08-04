package com.github.lucasaquiles.config;

public enum QueueDeclaration {

    SIMPLE_QUEUE("queue-name-a", true,  5, 5);

    private String queueName;
    private boolean withDLQ;
    private int maxRetry;
    private int interval;

    QueueDeclaration(String queuename, boolean withDLQ, int maxRetry, int interval) {
        this.queueName = queuename;
        this.withDLQ = withDLQ;
        this.maxRetry = maxRetry;
        this.interval = interval;
    }

    public String getQueueName() {
        return queueName;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public int getInterval() {
        return interval;
    }

    public boolean withDLQ() {
        return withDLQ;
    }

    public String getExchangeName() {
        return this.queueName + ".exchange";
    }

    public String getDLQName() {
        return this.queueName + ".dlq";
    }

    public String getRetryName() {
        return this.queueName + ".retry";
    }


    @Override
    public String toString() {
        return "QueueDeclaration{" +
                "queueName='" + queueName + '\'' +
                ", withDLQ=" + withDLQ +
                ", maxRetry=" + maxRetry +
                ", interval=" + interval +
                '}';
    }

    public boolean withRetry() {
        return maxRetry > 0;
    }
}
