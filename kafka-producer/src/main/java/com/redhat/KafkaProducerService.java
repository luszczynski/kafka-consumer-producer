package com.redhat;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

public class KafkaProducerService {
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

    @ConfigProperty(name = "kafka.compression.type", defaultValue = "lz4")
    String compressionType;


    @Produces
    @ApplicationScoped
    public Producer<String, String> createProducer() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        
        return new KafkaProducer<>(props);
    }
}
