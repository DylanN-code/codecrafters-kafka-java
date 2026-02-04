package domain.request.body;

import domain.Field;

import java.util.List;

public class DescribeTopicPartitionsRequestBodyV0 extends BaseRequestBody {
    private Field topicListLength;
    private List<Item> topicList;
    private Field responsePartitionLimit;
    private Field cursor;
    private Field tagBuffer;

    public Field getTopicListLength() {
        return topicListLength;
    }

    public void setTopicListLength(Field topicListLength) {
        this.topicListLength = topicListLength;
    }

    public List<Item> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Item> topicList) {
        this.topicList = topicList;
    }

    public Field getResponsePartitionLimit() {
        return responsePartitionLimit;
    }

    public void setResponsePartitionLimit(Field responsePartitionLimit) {
        this.responsePartitionLimit = responsePartitionLimit;
    }

    public Field getCursor() {
        return cursor;
    }

    public void setCursor(Field cursor) {
        this.cursor = cursor;
    }

    public Field getTagBuffer() {
        return tagBuffer;
    }

    public void setTagBuffer(Field tagBuffer) {
        this.tagBuffer = tagBuffer;
    }

    public static class Item {
        private Field topicName;
        private Field topicNameLength;
        private Field tagBuffer;

        public Field getTopicName() {
            return topicName;
        }

        public void setTopicName(Field topicName) {
            this.topicName = topicName;
        }

        public Field getTopicNameLength() {
            return topicNameLength;
        }

        public void setTopicNameLength(Field topicNameLength) {
            this.topicNameLength = topicNameLength;
        }

        public Field getTagBuffer() {
            return tagBuffer;
        }

        public void setTagBuffer(Field tagBuffer) {
            this.tagBuffer = tagBuffer;
        }
    }
}
