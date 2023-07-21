package com.redhat;

import jakarta.enterprise.context.ApplicationScoped;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class KafkaConsumer {
    private static final Logger LOGGER = Logger.getLogger(KafkaConsumer.class.getName());

    @Incoming("kafka-in")
    public void consume(ConsumerRecord<String, KafkaMessage> record) {
        String key = record.key();
        KafkaMessage value = record.value(); 
        String topic = record.topic();
        int partition = record.partition();
        LOGGER.infof("==========\nKey = %s\nPayload = %s\nDepartamento = %s\nTopic = %s\nPartition = %d\n==========\n\n", key, value.payload, value.departamento, topic, partition);
        
    }
}
