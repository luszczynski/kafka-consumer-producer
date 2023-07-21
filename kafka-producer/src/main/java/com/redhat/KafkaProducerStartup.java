package com.redhat;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Startup
@Singleton
public class KafkaProducerStartup {

    @Inject
    Producer<String, String> producer;

    @ConfigProperty(name = "kafka.bootstrap.servers", defaultValue = "localhost:9092")
    String bootstrapServers;

    @ConfigProperty(name = "kafka.acks", defaultValue = "all")
    String acks;

    @ConfigProperty(name = "kafka.retries", defaultValue = "0")
    int retries;

    @ConfigProperty(name = "kafka.batch.size", defaultValue = "16384")
    int batchSize;

    @ConfigProperty(name = "kafka.linger.ms", defaultValue = "1")
    long lingerMs;

    @ConfigProperty(name = "kafka.buffer.memory", defaultValue = "33554432")
    long bufferMemory;

    @ConfigProperty(name = "kafka.topic", defaultValue = "my-topic")
    String topic;

    @ConfigProperty(name = "kafka.num.records", defaultValue = "512")
    long numRecords;

    @ConfigProperty(name = "kafka.record.size", defaultValue = "4096")
    long recordSize;

    @ConfigProperty(name = "kafka.throughput", defaultValue = "1000")
    long throughput;

    @ConfigProperty(name = "kafka.compression.type", defaultValue = "lz4")
    String compressionType;

    @ConfigProperty(name = "kafka.message.key")
    String messageKey;

    @ConfigProperty(name = "departamento")
    String departamento;

    private final Random random = new Random();

    @PostConstruct
    void produceRecords() {
        ObjectMapper objectMapper = new ObjectMapper();
        IntStream.range(0, (int)numRecords).forEach(i -> {
            String key = messageKey != null ? messageKey : "key-" + i;
            KafkaMessage message = generateMessage();
            String value;
            try {
                value = objectMapper.writeValueAsString(message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            producer.send(new ProducerRecord<>(topic, key, value));

            if (throughput > 0) {
                long sleepTime = 1000 / throughput;
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.flush();
        producer.close();
    }

    private KafkaMessage generateMessage() {
        KafkaMessage message = new KafkaMessage();
        message.payload = "my data " + random.nextInt();
        message.departamento = departamento;
        return message;
    }
}