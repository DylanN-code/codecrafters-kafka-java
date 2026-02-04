package domain.request.body;

import domain.Field;

import java.util.List;

public class FetchRequestBodyV16 extends BaseRequestBody {
    private Field maxWaitMS;
    private Field minBytes;
    private Field maxBytes;
    private Field isolationLevel;
    private Field sessionId;
    private Field sessionEpoch;
    private Field topicLength;
    private List<TopicItem> topicItemList;
    private Field forgottenTopicDataLength;
    private List<ForgottenTopic> forgottenTopicList;
    private Field rackIdLength;
    private Field rackId;
    private Field tagBuffer;

    public Field getMaxWaitMS() {
        return maxWaitMS;
    }

    public void setMaxWaitMS(Field maxWaitMS) {
        this.maxWaitMS = maxWaitMS;
    }

    public Field getMinBytes() {
        return minBytes;
    }

    public void setMinBytes(Field minBytes) {
        this.minBytes = minBytes;
    }

    public Field getMaxBytes() {
        return maxBytes;
    }

    public void setMaxBytes(Field maxBytes) {
        this.maxBytes = maxBytes;
    }

    public Field getIsolationLevel() {
        return isolationLevel;
    }

    public void setIsolationLevel(Field isolationLevel) {
        this.isolationLevel = isolationLevel;
    }

    public Field getSessionId() {
        return sessionId;
    }

    public void setSessionId(Field sessionId) {
        this.sessionId = sessionId;
    }

    public Field getSessionEpoch() {
        return sessionEpoch;
    }

    public void setSessionEpoch(Field sessionEpoch) {
        this.sessionEpoch = sessionEpoch;
    }

    public Field getTopicLength() {
        return topicLength;
    }

    public void setTopicLength(Field topicLength) {
        this.topicLength = topicLength;
    }

    public List<TopicItem> getTopicItemList() {
        return topicItemList;
    }

    public void setTopicItemList(List<TopicItem> topicItemList) {
        this.topicItemList = topicItemList;
    }

    public Field getForgottenTopicDataLength() {
        return forgottenTopicDataLength;
    }

    public void setForgottenTopicDataLength(Field forgottenTopicDataLength) {
        this.forgottenTopicDataLength = forgottenTopicDataLength;
    }

    public List<ForgottenTopic> getForgottenTopicList() {
        return forgottenTopicList;
    }

    public void setForgottenTopicList(List<ForgottenTopic> forgottenTopicList) {
        this.forgottenTopicList = forgottenTopicList;
    }

    public Field getRackIdLength() {
        return rackIdLength;
    }

    public void setRackIdLength(Field rackIdLength) {
        this.rackIdLength = rackIdLength;
    }

    public Field getRackId() {
        return rackId;
    }

    public void setRackId(Field rackId) {
        this.rackId = rackId;
    }

    public Field getTagBuffer() {
        return tagBuffer;
    }

    public void setTagBuffer(Field tagBuffer) {
        this.tagBuffer = tagBuffer;
    }

    public static class TopicItem {
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
        private Field partitionId;
        private Field currentLeaderEpoch;
        private Field fetchOffset;
        private Field lastFetchEpoch;
        private Field logStartOffset;
        private Field partitionMaxBytes;
        private Field tagBuffer;

        public Field getPartitionId() {
            return partitionId;
        }

        public void setPartitionId(Field partitionId) {
            this.partitionId = partitionId;
        }

        public Field getCurrentLeaderEpoch() {
            return currentLeaderEpoch;
        }

        public void setCurrentLeaderEpoch(Field currentLeaderEpoch) {
            this.currentLeaderEpoch = currentLeaderEpoch;
        }

        public Field getFetchOffset() {
            return fetchOffset;
        }

        public void setFetchOffset(Field fetchOffset) {
            this.fetchOffset = fetchOffset;
        }

        public Field getLastFetchEpoch() {
            return lastFetchEpoch;
        }

        public void setLastFetchEpoch(Field lastFetchEpoch) {
            this.lastFetchEpoch = lastFetchEpoch;
        }

        public Field getLogStartOffset() {
            return logStartOffset;
        }

        public void setLogStartOffset(Field logStartOffset) {
            this.logStartOffset = logStartOffset;
        }

        public Field getPartitionMaxBytes() {
            return partitionMaxBytes;
        }

        public void setPartitionMaxBytes(Field partitionMaxBytes) {
            this.partitionMaxBytes = partitionMaxBytes;
        }

        public Field getTagBuffer() {
            return tagBuffer;
        }

        public void setTagBuffer(Field tagBuffer) {
            this.tagBuffer = tagBuffer;
        }
    }

    public static class ForgottenTopic {
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
}
