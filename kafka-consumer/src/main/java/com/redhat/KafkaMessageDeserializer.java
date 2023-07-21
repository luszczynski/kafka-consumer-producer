package com.redhat;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaMessageDeserializer implements Deserializer<KafkaMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public KafkaMessage deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, KafkaMessage.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing JSON message", e);
        }
    }
}