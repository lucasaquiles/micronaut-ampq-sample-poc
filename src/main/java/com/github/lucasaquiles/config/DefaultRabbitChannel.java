package com.github.lucasaquiles.config;


import com.github.lucasaquiles.config.properties.QueueProperties;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class DefaultRabbitChannel extends ChannelInitializer {

    private final Logger log = LoggerFactory.getLogger(DefaultRabbitChannel.class);

    @Inject
    private QueueProperties queueProperties;

    @Override
    public void initialize(Channel channel) throws IOException {
        log.info("M=initialize, I=creating queue, channel={},", channel);

        new QueueInitializerConfig()
                 .applyQueueConfig(channel, queueProperties);
    }
}
