package domain.metadata;

import domain.Field;

import java.util.LinkedList;

/**
 * - CompactRecord Batch #1
 * - Base Offset
 * - Batch Length
 * - Partition Leader Epoch
 * - Magic Byte
 * - CRC
 * - Attributes
 * - Last Offset Delta
 * - Base Timestamp
 * - Max Timestamp
 * - Producer ID
 * - Producer Epoch
 * - Base Sequence
 * - Records Length
 * → CompactRecord #1
 */

public class Batch {
    private Field baseOffset;
    private Field batchLength;
    private Field partitionLeaderEpoch;
    private Field magicByte;
    private Field crc;
    private Field attributes;
    private Field lastOffsetDelta;
    private Field baseTimestamp;
    private Field maxTimestamp;
    private Field producerId;
    private Field producerEpoch;
    private Field baseSequence;
    private Field recordLength;
    private LinkedList<Record> records;

    public Field getBaseOffset() {
        return baseOffset;
    }

    public void setBaseOffset(Field baseOffset) {
        this.baseOffset = baseOffset;
    }

    public Field getBatchLength() {
        return batchLength;
    }

    public void setBatchLength(Field batchLength) {
        this.batchLength = batchLength;
    }

    public Field getPartitionLeaderEpoch() {
        return partitionLeaderEpoch;
    }

    public void setPartitionLeaderEpoch(Field partitionLeaderEpoch) {
        this.partitionLeaderEpoch = partitionLeaderEpoch;
    }

    public Field getMagicByte() {
        return magicByte;
    }

    public void setMagicByte(Field magicByte) {
        this.magicByte = magicByte;
    }

    public Field getCrc() {
        return crc;
    }

    public void setCrc(Field crc) {
        this.crc = crc;
    }

    public Field getAttributes() {
        return attributes;
    }

    public void setAttributes(Field attributes) {
        this.attributes = attributes;
    }

    public Field getLastOffsetDelta() {
        return lastOffsetDelta;
    }

    public void setLastOffsetDelta(Field lastOffsetDelta) {
        this.lastOffsetDelta = lastOffsetDelta;
    }

    public Field getBaseTimestamp() {
        return baseTimestamp;
    }

    public void setBaseTimestamp(Field baseTimestamp) {
        this.baseTimestamp = baseTimestamp;
    }

    public Field getMaxTimestamp() {
        return maxTimestamp;
    }

    public void setMaxTimestamp(Field maxTimestamp) {
        this.maxTimestamp = maxTimestamp;
    }

    public Field getProducerId() {
        return producerId;
    }

    public void setProducerId(Field producerId) {
        this.producerId = producerId;
    }

    public Field getProducerEpoch() {
        return producerEpoch;
    }

    public void setProducerEpoch(Field producerEpoch) {
        this.producerEpoch = producerEpoch;
    }

    public Field getBaseSequence() {
        return baseSequence;
    }

    public void setBaseSequence(Field baseSequence) {
        this.baseSequence = baseSequence;
    }

    public Field getRecordLength() {
        return recordLength;
    }

    public void setRecordLength(Field recordLength) {
        this.recordLength = recordLength;
    }

    public LinkedList<Record> getRecords() {
        return records;
    }

    public void setRecords(LinkedList<Record> records) {
        this.records = records;
    }
}
