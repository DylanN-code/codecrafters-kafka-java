package service.log.impl;

import constants.Constant;
import domain.Field;
import domain.metadata.record.TopicValue;
import enums.FieldType;
import enums.ValueType;
import service.log.BaseLogValueService;
import service.log.LogValueService;
import utils.BrokerUtil;
import utils.ByteUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

public class TopicValueImpl extends BaseLogValueService<TopicValue> {
    @Override
    public TopicValue createValue() {
        return new TopicValue();
    }

    @Override
    public void load(ByteArrayInputStream is, TopicValue value) throws IOException {
        value.setNameLength(BrokerUtil.wrapField(is, FieldType.BYTE));
        int nameLength = ByteUtil.convertStreamToByte(value.getNameLength().getData()) - FieldType.BYTE.getByteSize();
        value.setTopicName(BrokerUtil.wrapField(is, FieldType.STRING, nameLength));
        value.setTopicUUID(BrokerUtil.wrapField(is, FieldType.STRING, Constant.TOPIC_ID_LENGTH));
    }

    @Override
    public void map(TopicValue value) {
        TopicValue copiedTopicValue = ByteUtil.deepCopy(value);
        if (Objects.isNull(copiedTopicValue)) {
            return;
        }
        byte[] topicIdStream = copiedTopicValue.getTopicUUID().getData();
        Field topicId = BrokerUtil.wrapField(topicIdStream, FieldType.STRING, Constant.TOPIC_ID_LENGTH);
        METADATA_CLUSTER_TOPIC_VALUE_MAP.put(topicId, copiedTopicValue);
    }

    @Override
    public void registerHandler() {
        LogValueService.STORE.put(ValueType.TOPIC, this);
    }
}
