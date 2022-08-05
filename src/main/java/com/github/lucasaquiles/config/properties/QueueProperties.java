package com.github.lucasaquiles.config.properties;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

import java.util.List;

@ConfigurationProperties("rabbitmq")
public class QueueProperties {

    List<Binding> bindings;

    public List<Binding> getBindings() {
        return bindings;
    }

    public void setBindings(List<Binding> bindings) {
        this.bindings = bindings;
    }

    @EachProperty("bindings")
    public static class Binding {
        private String name;

        private String exchange;

        private String queue;

        private Boolean dlq;

        private Boolean retry;

        public Binding(@Parameter String name) {
            this.name = name;
        }

        public Boolean getDlq() {
            return dlq;
        }

        public void setDlq(Boolean dlq) {
            this.dlq = dlq;
        }

        public Boolean getRetry() {
            return retry;
        }

        public void setRetry(Boolean retry) {
            this.retry = retry;
        }

        public String getExchange() {
            return exchange+".exchange";
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getQueue() {
            return queue;
        }

        public void setQueue(String queue) {
            this.queue = queue;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRetryQueueName() {
            return getQueue()+".retry";
        }

        public String getDlqName() {
            return getQueue()+".dql";
        }

        @Override
        public String toString() {
            return "Binding{" +
                    "name='" + name + '\'' +
                    ", exchange='" + exchange + '\'' +
                    ", queue='" + queue + '\'' +
                    ", dlq=" + dlq +
                    ", retry=" + retry +
                    '}';
        }
    }
}
