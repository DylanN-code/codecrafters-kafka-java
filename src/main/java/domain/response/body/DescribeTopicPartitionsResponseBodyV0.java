package domain.response.body;

import domain.Field;

import java.util.List;

public class DescribeTopicPartitionsResponseBodyV0 extends BaseResponseBody {
    public static class TopicItem {
        private Field errorCode;
        private Field topicNameLength;
        private Field topicName;
        private Field topicId;
        private Field isInternal;
        private Field partitionArrayLength;
        private List<PartitionItem> partitionItemList;
        private Field topicAuthorizedOperations;
        private Field taggedFieldSize;

        public Field getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Field errorCode) {
            this.errorCode = errorCode;
        }

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

        public Field getTopicId() {
            return topicId;
        }

        public void setTopicId(Field topicId) {
            this.topicId = topicId;
        }

        public Field getIsInternal() {
            return isInternal;
        }

        public void setIsInternal(Field isInternal) {
            this.isInternal = isInternal;
        }

        public List<PartitionItem> getPartitionItemList() {
            return partitionItemList;
        }

        public void setPartitionItemList(List<PartitionItem> partitionItemList) {
            this.partitionItemList = partitionItemList;
        }

        public Field getPartitionArrayLength() {
            return partitionArrayLength;
        }

        public void setPartitionArrayLength(Field partitionArrayLength) {
            this.partitionArrayLength = partitionArrayLength;
        }

        public Field getTopicAuthorizedOperations() {
            return topicAuthorizedOperations;
        }

        public void setTopicAuthorizedOperations(Field topicAuthorizedOperations) {
            this.topicAuthorizedOperations = topicAuthorizedOperations;
        }

        public Field getTaggedFieldSize() {
            return taggedFieldSize;
        }

        public void setTaggedFieldSize(Field taggedFieldSize) {
            this.taggedFieldSize = taggedFieldSize;
        }
    }

    public static class PartitionItem {
        private Field errorCode;
        private Field partitionIndex;
        private Field leaderId;
        private Field leaderEpoch;
        private Field replicaNodeLength;
        private List<Field> replicaNodeArray;
        private Field isrNodeLength;
        private List<Field> isrNodeArray;
        private Field eligibleLeaderReplicaLength;
        private Field lastKnownELRLength;
        private Field offlineReplicaLength;
        private Field taggedFieldSize;

        public Field getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Field errorCode) {
            this.errorCode = errorCode;
        }

        public Field getPartitionIndex() {
            return partitionIndex;
        }

        public void setPartitionIndex(Field partitionIndex) {
            this.partitionIndex = partitionIndex;
        }

        public Field getLeaderId() {
            return leaderId;
        }

        public void setLeaderId(Field leaderId) {
            this.leaderId = leaderId;
        }

        public Field getLeaderEpoch() {
            return leaderEpoch;
        }

        public void setLeaderEpoch(Field leaderEpoch) {
            this.leaderEpoch = leaderEpoch;
        }

        public Field getReplicaNodeLength() {
            return replicaNodeLength;
        }

        public void setReplicaNodeLength(Field replicaNodeLength) {
            this.replicaNodeLength = replicaNodeLength;
        }

        public List<Field> getReplicaNodeArray() {
            return replicaNodeArray;
        }

        public void setReplicaNodeArray(List<Field> replicaNodeArray) {
            this.replicaNodeArray = replicaNodeArray;
        }

        public Field getIsrNodeLength() {
            return isrNodeLength;
        }

        public void setIsrNodeLength(Field isrNodeLength) {
            this.isrNodeLength = isrNodeLength;
        }

        public List<Field> getIsrNodeArray() {
            return isrNodeArray;
        }

        public void setIsrNodeArray(List<Field> isrNodeArray) {
            this.isrNodeArray = isrNodeArray;
        }

        public Field getEligibleLeaderReplicaLength() {
            return eligibleLeaderReplicaLength;
        }

        public void setEligibleLeaderReplicaLength(Field eligibleLeaderReplicaLength) {
            this.eligibleLeaderReplicaLength = eligibleLeaderReplicaLength;
        }

        public Field getLastKnownELRLength() {
            return lastKnownELRLength;
        }

        public void setLastKnownELRLength(Field lastKnownELRLength) {
            this.lastKnownELRLength = lastKnownELRLength;
        }

        public Field getOfflineReplicaLength() {
            return offlineReplicaLength;
        }

        public void setOfflineReplicaLength(Field offlineReplicaLength) {
            this.offlineReplicaLength = offlineReplicaLength;
        }

        public Field getTaggedFieldSize() {
            return taggedFieldSize;
        }

        public void setTaggedFieldSize(Field taggedFieldSize) {
            this.taggedFieldSize = taggedFieldSize;
        }
    }

    private Field throttleTimeMS;
    private Field topicArrayLength;
    private List<TopicItem> topicList;
    private Field nextCursor;
    private Field taggedFieldSize;

    public Field getThrottleTimeMS() {
        return throttleTimeMS;
    }

    public void setThrottleTimeMS(Field throttleTimeMS) {
        this.throttleTimeMS = throttleTimeMS;
    }

    public Field getTopicArrayLength() {
        return topicArrayLength;
    }

    public void setTopicArrayLength(Field topicArrayLength) {
        this.topicArrayLength = topicArrayLength;
    }

    public List<TopicItem> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<TopicItem> topicList) {
        this.topicList = topicList;
    }

    public Field getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(Field nextCursor) {
        this.nextCursor = nextCursor;
    }

    public Field getTaggedFieldSize() {
        return taggedFieldSize;
    }

    public void setTaggedFieldSize(Field taggedFieldSize) {
        this.taggedFieldSize = taggedFieldSize;
    }
}
