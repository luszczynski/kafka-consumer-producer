package com.redhat;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

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
        ObjectMapper objectMapper = new ObjectMapper();
        IntStream.range(0, (int)config.getNumRecords()).forEach(i -> {
            String key = config.getMessageKey() != null ? config.getMessageKey() : "key-" + i;
            KafkaMessage message = generateMessage();
            String value;
            try {
                value = objectMapper.writeValueAsString(message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

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

    private KafkaMessage generateMessage() {
        KafkaMessage message = new KafkaMessage();
        message.request.rawRequestHeader.accept = "application/json,application/json";
        message.response.rawResponseBody.payload = "my data " + random.nextInt();
        return message;
    }
}