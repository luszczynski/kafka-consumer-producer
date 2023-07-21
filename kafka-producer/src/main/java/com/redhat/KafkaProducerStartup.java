package com.redhat;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Startup
@Singleton
public class KafkaProducerStartup {
    @Inject
    Producer<String, String> producer;

    @Inject
    KafkaProducerConfig config;

    private final Random random = new Random();

    @PostConstruct
    void produceRecords() {
        IntStream.range(0, (int)config.getNumRecords()).forEach(i -> {
            String key = config.getMessageKey() != null ? config.getMessageKey() : "key-" + i;
            String value = generateMessage((int)config.getRecordSize());
            producer.send(new ProducerRecord<>(config.getTopic(), key, value));

            if (config.getThroughput() > 0) {
                long sleepTime = 1000 / config.getThroughput();
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

    private String generateMessage(int size) {
        byte[] array = new byte[size];
        random.nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
    
}
