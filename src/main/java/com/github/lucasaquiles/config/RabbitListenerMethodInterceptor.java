package com.github.lucasaquiles.config;

import com.github.lucasaquiles.config.annotation.RetriableQueue;
import com.github.lucasaquiles.config.properties.QueueProperties;
import com.github.lucasaquiles.config.properties.QueuePropertyMap;
import io.micronaut.aop.InterceptPhase;
import io.micronaut.aop.InvocationContext;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.context.BeanContext;
import io.micronaut.context.BeanLocator;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.rabbitmq.bind.RabbitBinderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class RabbitListenerMethodInterceptor implements MethodInterceptor<Object, Object>{

    private static final Logger log = LoggerFactory.getLogger(RabbitListenerMethodInterceptor.class);

    private final QueueProperties queueProperties;
    private final BeanContext beanContext;
    private final RabbitBinderRegistry binderRegistry;
    private final BeanLocator beanLocator;

    public RabbitListenerMethodInterceptor(QueueProperties queueProperties, BeanContext beanContext, RabbitBinderRegistry binderRegistry, BeanLocator beanLocator) {
        this.queueProperties = queueProperties;
        this.beanContext = beanContext;
        this.binderRegistry = binderRegistry;
        this.beanLocator = beanLocator;
    }

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        log.info("M=intercept, I=processando...");

        Optional<AnnotationValue<RetriableQueue>> opt = context.findAnnotation(RetriableQueue.class);

        if (!opt.isPresent()) {
            return context.proceed();
        }

        AnnotationValue<RetriableQueue> retriableMethod = opt.get();
        String queue = retriableMethod.getRequiredValue(String.class);
        int maxRetries = retriableMethod.getRequiredValue("maxRetry", Integer.class);
        int interval = retriableMethod.getRequiredValue("interval", Integer.class);

        final QueueProperties.Binding binding = queueProperties.getBindings().stream()
                .filter(f -> f.getName().equals(queue))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nao encontrou a binding " + queue));

        final QueuePropertyMap queuePropertyMap = new QueuePropertyMap(binding, maxRetries, interval);
        final RetriableHandler retriableHandler = beanLocator.getBean(RetriableHandler.class);
        retriableHandler.setQueuePropertyMap(queuePropertyMap);

       // beanContext.registerSingleton(queuePropertyMap);

        return context.getExecutableMethod().invoke(queuePropertyMap, context.getParameterValues());
    }

    @Override
    public Object intercept(InvocationContext<Object, Object> context) {
        log.info("M=aqui é antes ou depois");
        return null;
    }

    @Override
    public int getOrder() {
        return InterceptPhase.RETRY.getPosition();
    }
}
