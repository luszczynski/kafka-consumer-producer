package com.redhat;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.kafka.IncomingKafkaRecord;
import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class KafkaConsumer {
    private static final Logger LOGGER = Logger.getLogger(KafkaConsumer.class.getName());

    @Incoming("kafka-in")
    @Blocking
    public void onMessage(IncomingKafkaRecord<String, String> record) {
        LOGGER.infof("Received from Kafka: Key = %s, Value = %s", record.getKey(), record.getPayload());
    }
}
