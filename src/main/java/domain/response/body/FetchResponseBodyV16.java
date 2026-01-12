package domain.response.body;

import domain.Field;
import domain.metadata.Batch;

import java.util.List;

public class FetchResponseBodyV16 extends BaseResponseBody {
    public static class Response {
        private Field topicId;
        private Field partitionLength;
        private List<PartitionItem> partitionItemList;
        private Field tagBuffer;

        public Field getTopicId() {
            return topicId;
        }

        public void setTopicId(Field topicId) {
            this.topicId = topicId;
        }

        public Field getPartitionLength() {
            return partitionLength;
        }

        public void setPartitionLength(Field partitionLength) {
            this.partitionLength = partitionLength;
        }

        public List<PartitionItem> getPartitionItemList() {
            return partitionItemList;
        }

        public void setPartitionItemList(List<PartitionItem> partitionItemList) {
            this.partitionItemList = partitionItemList;
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
        private Field highWaterMark;
        private Field lastStableOffset;
        private Field logStartOffset;
        private Field abortedTransactionLength;
        private List<AbortedTransaction> abortedTransactionList;
        private Field preferredReadReplica;
        private Field batchRecordLength;
        private List<Batch> batchRecordList;
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

        public Field getHighWaterMark() {
            return highWaterMark;
        }

        public void setHighWaterMark(Field highWaterMark) {
            this.highWaterMark = highWaterMark;
        }

        public Field getLastStableOffset() {
            return lastStableOffset;
        }

        public void setLastStableOffset(Field lastStableOffset) {
            this.lastStableOffset = lastStableOffset;
        }

        public Field getLogStartOffset() {
            return logStartOffset;
        }

        public void setLogStartOffset(Field logStartOffset) {
            this.logStartOffset = logStartOffset;
        }

        public Field getAbortedTransactionLength() {
            return abortedTransactionLength;
        }

        public void setAbortedTransactionLength(Field abortedTransactionLength) {
            this.abortedTransactionLength = abortedTransactionLength;
        }

        public List<AbortedTransaction> getAbortedTransactionList() {
            return abortedTransactionList;
        }

        public void setAbortedTransactionList(List<AbortedTransaction> abortedTransactionList) {
            this.abortedTransactionList = abortedTransactionList;
        }

        public Field getPreferredReadReplica() {
            return preferredReadReplica;
        }

        public void setPreferredReadReplica(Field preferredReadReplica) {
            this.preferredReadReplica = preferredReadReplica;
        }

        public Field getBatchRecordLength() {
            return batchRecordLength;
        }

        public void setBatchRecordLength(Field batchRecordLength) {
            this.batchRecordLength = batchRecordLength;
        }

        public List<Batch> getBatchRecordList() {
            return batchRecordList;
        }

        public void setBatchRecordList(List<Batch> batchRecordList) {
            this.batchRecordList = batchRecordList;
        }

        public Field getTagBuffer() {
            return tagBuffer;
        }

        public void setTagBuffer(Field tagBuffer) {
            this.tagBuffer = tagBuffer;
        }
    }

    public static class AbortedTransaction {
        private Field producerId;
        private Field firstOffset;
        private Field tagBuffer;

        public Field getProducerId() {
            return producerId;
        }

        public void setProducerId(Field producerId) {
            this.producerId = producerId;
        }

        public Field getFirstOffset() {
            return firstOffset;
        }

        public void setFirstOffset(Field firstOffset) {
            this.firstOffset = firstOffset;
        }

        public Field getTagBuffer() {
            return tagBuffer;
        }

        public void setTagBuffer(Field tagBuffer) {
            this.tagBuffer = tagBuffer;
        }
    }

    private Field throttleTimeMs;
    private Field errorCode;
    private Field sessionId;
    private Field responseLength;
    private List<Response> responseList;
    private Field tagBuffer;

    public Field getThrottleTimeMs() {
        return throttleTimeMs;
    }

    public void setThrottleTimeMs(Field throttleTimeMs) {
        this.throttleTimeMs = throttleTimeMs;
    }

    public Field getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Field errorCode) {
        this.errorCode = errorCode;
    }

    public Field getSessionId() {
        return sessionId;
    }

    public void setSessionId(Field sessionId) {
        this.sessionId = sessionId;
    }

    public Field getResponseLength() {
        return responseLength;
    }

    public void setResponseLength(Field responseLength) {
        this.responseLength = responseLength;
    }

    public List<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<Response> responseList) {
        this.responseList = responseList;
    }

    public Field getTagBuffer() {
        return tagBuffer;
    }

    public void setTagBuffer(Field tagBuffer) {
        this.tagBuffer = tagBuffer;
    }
}
