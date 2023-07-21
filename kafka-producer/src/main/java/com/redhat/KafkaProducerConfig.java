package com.redhat;

import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "kafka")
public class KafkaProducerConfig {

    String bootstrapServers = "localhost:9092";
    String acks = "all";
    int retries = 0;
    long batchSize = 16384;
    long lingerMs = 1;
    long bufferMemory = 33554432;
    String topic = "my-topic";
    long numRecords = 512;
    long recordSize = 4096;
    long throughput = 1000;
    String compressionType = "lz4";
    String messageKey;

    

    public void setTopic(String topic) {
        this.topic = topic;
    }
    public void setNumRecords(long numRecords) {
        this.numRecords = numRecords;
    }
    public void setRecordSize(long recordSize) {
        this.recordSize = recordSize;
    }
    public void setThroughput(long throughput) {
        this.throughput = throughput;
    }
    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }
    public String getTopic() {
        return topic;
    }
    public long getNumRecords() {
        return numRecords;
    }
    public long getRecordSize() {
        return recordSize;
    }
    public long getThroughput() {
        return throughput;
    }
    public String getCompressionType() {
        return compressionType;
    }
    public String getBootstrapServers() {
        return bootstrapServers;
    }
    public String getAcks() {
        return acks;
    }
    public int getRetries() {
        return retries;
    }
    public long getBatchSize() {
        return batchSize;
    }
    public long getLingerMs() {
        return lingerMs;
    }
    public long getBufferMemory() {
        return bufferMemory;
    }
    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }
    public void setAcks(String acks) {
        this.acks = acks;
    }
    public void setRetries(int retries) {
        this.retries = retries;
    }
    public void setBatchSize(long batchSize) {
        this.batchSize = batchSize;
    }
    public void setLingerMs(long lingerMs) {
        this.lingerMs = lingerMs;
    }
    public void setBufferMemory(long bufferMemory) {
        this.bufferMemory = bufferMemory;
    }
    public String getMessageKey() {
        return messageKey;
    }
    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    

    
}
