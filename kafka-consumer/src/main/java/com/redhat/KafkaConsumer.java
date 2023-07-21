package com.redhat;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.kafka.IncomingKafkaRecord;
import jakarta.enterprise.context.ApplicationScoped;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class KafkaConsumer {
    private static final Logger LOGGER = Logger.getLogger(KafkaConsumer.class.getName());

    @Incoming("kafka-in")
    public void consume(ConsumerRecord<String, String> record) {
        String key = record.key(); // Can be `null` if the incoming record has no key
        String value = record.value(); // Can be `null` if the incoming record has no value
        String topic = record.topic();
        int partition = record.partition();
        LOGGER.infof("Received from Kafka: Key = %s, Value = %s, Topic = %s, Partition = %d", key, value, topic, partition);
        
    }

    // @Incoming("kafka-in")
    // @Blocking
    // public void onMessage(IncomingKafkaRecord<String, String> record) {
    //     LOGGER.infof("Received from Kafka: Key = %s, Value = %s", record.getKey(), record.getPayload());
    // }
}
