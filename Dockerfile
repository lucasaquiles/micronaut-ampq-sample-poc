FROM openjdk:14-alpine
COPY build/libs/micronaut-amqp-sample-poc-*-all.jar micronaut-amqp-sample-poc.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "micronaut-amqp-sample-poc.jar"]