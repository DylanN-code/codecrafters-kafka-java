package domain.request.body;

import domain.Field;

public class ProduceRequestV11 extends BaseRequestBody {

    public static class TopicItem {
        private Field topicNameLength;
        private Field topicName;
        private Field partitionArrayLength;
        private PartitionItem[] partitionArray;
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

        public PartitionItem[] getPartitionArray() {
            return partitionArray;
        }

        public void setPartitionArray(PartitionItem[] partitionArray) {
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
        private Field recordBatchArrayLength;
        private Field recordBatchData;
        private Field tagBuffer;

        public Field getPartitionIndex() {
            return partitionIndex;
        }

        public void setPartitionIndex(Field partitionIndex) {
            this.partitionIndex = partitionIndex;
        }

        public Field getRecordBatchArrayLength() {
            return recordBatchArrayLength;
        }

        public void setRecordBatchArrayLength(Field recordBatchArrayLength) {
            this.recordBatchArrayLength = recordBatchArrayLength;
        }

        public Field getRecordBatchData() {
            return recordBatchData;
        }

        public void setRecordBatchData(Field recordBatchData) {
            this.recordBatchData = recordBatchData;
        }

        public Field getTagBuffer() {
            return tagBuffer;
        }

        public void setTagBuffer(Field tagBuffer) {
            this.tagBuffer = tagBuffer;
        }
    }

    private Field transactionalId;
    private Field requiredAcks;
    private Field timeout;
    private Field topicArrayLength;
    private TopicItem[] topicArray;
    private Field tagBuffer;

    public Field getTransactionalId() {
        return transactionalId;
    }

    public void setTransactionalId(Field transactionalId) {
        this.transactionalId = transactionalId;
    }

    public Field getRequiredAcks() {
        return requiredAcks;
    }

    public void setRequiredAcks(Field requiredAcks) {
        this.requiredAcks = requiredAcks;
    }

    public Field getTimeout() {
        return timeout;
    }

    public void setTimeout(Field timeout) {
        this.timeout = timeout;
    }

    public Field getTopicArrayLength() {
        return topicArrayLength;
    }

    public void setTopicArrayLength(Field topicArrayLength) {
        this.topicArrayLength = topicArrayLength;
    }

    public TopicItem[] getTopicArray() {
        return topicArray;
    }

    public void setTopicArray(TopicItem[] topicArray) {
        this.topicArray = topicArray;
    }

    public Field getTagBuffer() {
        return tagBuffer;
    }

    public void setTagBuffer(Field tagBuffer) {
        this.tagBuffer = tagBuffer;
    }
}
