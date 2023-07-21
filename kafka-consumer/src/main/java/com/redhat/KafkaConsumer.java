package com.redhat;

import jakarta.enterprise.context.ApplicationScoped;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class KafkaConsumer {
    private static final Logger LOGGER = Logger.getLogger(KafkaConsumer.class.getName());

    @ConfigProperty(name = "departamento")
    String departamento;

    @Incoming("kafka-in")
    public void consume(ConsumerRecord<String, KafkaMessage> record) {
        KafkaMessage value = record.value();

        if(value.departamento.equalsIgnoreCase(departamento)) {
            String key = record.key();
            String topic = record.topic();
            int partition = record.partition();
            LOGGER.infof("==> Mensagem: Key = %s, Payload = %s, Departamento = %s, Topic = %s, Partition = %d", key, value.payload, value.departamento, topic, partition);
        }
    }
}
