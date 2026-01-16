package domain.response.body;

import domain.Field;

import java.util.LinkedList;

public class ProduceResponseV11 extends BaseResponseBody {

    public static class Response {
        private Field topicNameLength;
        private Field topicName;
        private Field partitionArrayLength;
        private LinkedList<PartitionItem> partitionArray;
        private Field tagBuffer;

        public Field getTopicNameLength() {
            return topicNameLength;
        }

        public void setTopicNameLength(Field topicNameLength) {
            this.topicNameLength = topicNameLength;
        }

        public Field getTopicName() {
            return topicName;
        }

        public void setTopicName(Field topicName) {
            this.topicName = topicName;
        }

        public Field getPartitionArrayLength() {
            return partitionArrayLength;
        }

        public void setPartitionArrayLength(Field partitionArrayLength) {
            this.partitionArrayLength = partitionArrayLength;
        }

        public LinkedList<PartitionItem> getPartitionArray() {
            return partitionArray;
        }

        public void setPartitionArray(LinkedList<PartitionItem> partitionArray) {
            this.partitionArray = partitionArray;
        }

        public Field getTagBuffer() {
            return tagBuffer;
        }

        public void setTagBuffer(Field tagBuffer) {
            this.tagBuffer = tagBuffer;
        }
    }

    public static class PartitionItem {
        private Field partitionIndex;
        private Field errorCode;
        private Field baseOffset;
        private Field logAppendTime;
        private Field logStartOffset;
        private Field recordErrorArrayLength;
        private Field errorMessage;
        private Field recordBatchArrayLength;
        private LinkedList<BatchRecordItem> recordBatchArray;
        private Field tagBuffer;

        public Field getPartitionIndex() {
            return partitionIndex;
        }

        public void setPartitionIndex(Field partitionIndex) {
            this.partitionIndex = partitionIndex;
        }

        public Field getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Field errorCode) {
            this.errorCode = errorCode;
        }

        public Field getBaseOffset() {
            return baseOffset;
        }

        public void setBaseOffset(Field baseOffset) {
            this.baseOffset = baseOffset;
        }

        public Field getLogAppendTime() {
            return logAppendTime;
        }

        public void setLogAppendTime(Field logAppendTime) {
            this.logAppendTime = logAppendTime;
        }

        public Field getLogStartOffset() {
            return logStartOffset;
        }

        public void setLogStartOffset(Field logStartOffset) {
            this.logStartOffset = logStartOffset;
        }

        public Field getRecordErrorArrayLength() {
            return recordErrorArrayLength;
        }

        public void setRecordErrorArrayLength(Field recordErrorArrayLength) {
            this.recordErrorArrayLength = recordErrorArrayLength;
        }

        public Field getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(Field errorMessage) {
            this.errorMessage = errorMessage;
        }

        public Field getRecordBatchArrayLength() {
            return recordBatchArrayLength;
        }

        public void setRecordBatchArrayLength(Field recordBatchArrayLength) {
            this.recordBatchArrayLength = recordBatchArrayLength;
        }

        public LinkedList<BatchRecordItem> getRecordBatchArray() {
            return recordBatchArray;
        }

        public void setRecordBatchArray(LinkedList<BatchRecordItem> recordBatchArray) {
            this.recordBatchArray = recordBatchArray;
        }

        public Field getTagBuffer() {
            return tagBuffer;
        }

        public void setTagBuffer(Field tagBuffer) {
            this.tagBuffer = tagBuffer;
        }
    }

    public static class BatchRecordItem {
        private Field baseOffset;
        private Field batchSize;
        private Field partitionLeaderEpoch;
        private Field magicByte;
        private Field crc32;
        private Field attributes;
        private Field lastOffsetDelta;
        private Field firstTimestamp;
        private Field lastTimestamp;
        private Field producerId;
        private Field producerEpoch;
        private Field baseSequence;
        private Field recordArrayLength;
        private LinkedList<RecordItem> recordArray;

        public Field getBaseOffset() {
            return baseOffset;
        }

        public void setBaseOffset(Field baseOffset) {
            this.baseOffset = baseOffset;
        }

        public Field getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(Field batchSize) {
            this.batchSize = batchSize;
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

        public Field getCrc32() {
            return crc32;
        }

        public void setCrc32(Field crc32) {
            this.crc32 = crc32;
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

        public Field getFirstTimestamp() {
            return firstTimestamp;
        }

        public void setFirstTimestamp(Field firstTimestamp) {
            this.firstTimestamp = firstTimestamp;
        }

        public Field getLastTimestamp() {
            return lastTimestamp;
        }

        public void setLastTimestamp(Field lastTimestamp) {
            this.lastTimestamp = lastTimestamp;
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

        public Field getRecordArrayLength() {
            return recordArrayLength;
        }

        public void setRecordArrayLength(Field recordArrayLength) {
            this.recordArrayLength = recordArrayLength;
        }

        public LinkedList<RecordItem> getRecordArray() {
            return recordArray;
        }

        public void setRecordArray(LinkedList<RecordItem> recordArray) {
            this.recordArray = recordArray;
        }
    }

    public static class RecordItem {
        private Field recordSize;
        private Field attributes;
        private Field timestampDelta;
        private Field offsetDelta;
        private Field keyLength;
        private Field valueLength;
        private Field value;
        private Field headersCount;

        public Field getRecordSize() {
            return recordSize;
        }

        public void setRecordSize(Field recordSize) {
            this.recordSize = recordSize;
        }

        public Field getAttributes() {
            return attributes;
        }

        public void setAttributes(Field attributes) {
            this.attributes = attributes;
        }

        public Field getTimestampDelta() {
            return timestampDelta;
        }

        public void setTimestampDelta(Field timestampDelta) {
            this.timestampDelta = timestampDelta;
        }

        public Field getOffsetDelta() {
            return offsetDelta;
        }

        public void setOffsetDelta(Field offsetDelta) {
            this.offsetDelta = offsetDelta;
        }

        public Field getKeyLength() {
            return keyLength;
        }

        public void setKeyLength(Field keyLength) {
            this.keyLength = keyLength;
        }

        public Field getValueLength() {
            return valueLength;
        }

        public void setValueLength(Field valueLength) {
            this.valueLength = valueLength;
        }

        public Field getValue() {
            return value;
        }

        public void setValue(Field value) {
            this.value = value;
        }

        public Field getHeadersCount() {
            return headersCount;
        }

        public void setHeadersCount(Field headersCount) {
            this.headersCount = headersCount;
        }
    }

    private Field errorCode;
    private Field responseLength;
    private LinkedList<Response> responseList;
    private Field throttleTimeMs;
    private Field tagBuffer;

    public Field getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Field errorCode) {
        this.errorCode = errorCode;
    }

    public Field getResponseLength() {
        return responseLength;
    }

    public void setResponseLength(Field responseLength) {
        this.responseLength = responseLength;
    }

    public LinkedList<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(LinkedList<Response> responseList) {
        this.responseList = responseList;
    }

    public Field getThrottleTimeMs() {
        return throttleTimeMs;
    }

    public void setThrottleTimeMs(Field throttleTimeMs) {
        this.throttleTimeMs = throttleTimeMs;
    }

    public Field getTagBuffer() {
        return tagBuffer;
    }

    public void setTagBuffer(Field tagBuffer) {
        this.tagBuffer = tagBuffer;
    }
}
