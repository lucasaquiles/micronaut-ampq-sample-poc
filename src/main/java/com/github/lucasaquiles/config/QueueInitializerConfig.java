package com.github.lucasaquiles.config;

import com.github.lucasaquiles.config.properties.QueueProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Singleton
public class QueueInitializerConfig {

    private final Logger log = LoggerFactory.getLogger(QueueInitializerConfig.class);

    public void applyQueueConfig(final Channel channel, final QueueProperties queueProperties) {

        log.info("configs: "+queueProperties.getBindings());

        queueProperties.getBindings()
                .stream()
                .map(parseQueueProperties())
                .peek(it -> log.info("M=getBindings, I=defining, queue={}", it))
                .forEach(it -> declareQueueWith(channel, it));

//        Stream.of(QueueDeclaration.values())
//                .forEach(queue -> declareQueueWith(channel, queue));
    }

    private Function<QueueProperties.Binding, QueueDeclarationData> parseQueueProperties() {
        return m -> new QueueDeclarationData(m);
    }

    class QueueDeclarationData {
        String exchangeName;
        String queueName;
        boolean withDQL;
        boolean withRetry;

        public QueueDeclarationData(QueueProperties.Binding binding) {
            this.exchangeName = binding.getExchange();
            this.queueName = binding.getQueue();
            this.withDQL = binding.getDlq();
            this.withRetry = binding.getRetry();
        }
    }

    private void declareQueueWith(Channel channel, QueueDeclarationData queue) {
        try{
            final String exchangeName = queue.queueName+".exchange";
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);

            declareQueue(channel, queue.queueName, exchangeName, queue.queueName, false);

            if(queue.withDQL) {
                declareQueue(channel, queue.queueName + ".dlq", exchangeName, queue.queueName, false);
            }

            if(queue.withRetry) {
                declareQueue(channel, queue.queueName+".retry", exchangeName, queue.queueName, true);
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void declareQueue(Channel channel, String queue, String exchangeName, String routingKey, boolean withDLQ) throws IOException {
        final Map<String, Object> queueArgs = new HashMap<>();

        if(withDLQ) {
            queueArgs.put("x-dead-letter-exchange", exchangeName);
            queueArgs.put("x-dead-letter-routing-key", routingKey);
        }

        channel.queueDeclare(queue, true, false, false, queueArgs);
        channel.queueBind(queue, exchangeName, queue);

        log.info("M=declareQueue, I=declaring queue. queue={}", queue);
    }
}
