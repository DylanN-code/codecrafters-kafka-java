package service.log;

import domain.Field;
import domain.LogContext;
import domain.metadata.Value;
import domain.metadata.record.PartitionValue;
import domain.metadata.record.TopicValue;
import enums.FieldType;
import enums.ValueType;
import utils.BrokerUtil;
import utils.ByteUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

public abstract class BaseLogValueService<T extends Value> implements LogValueService<T> {
    public static final Map<Field, TopicValue> METADATA_CLUSTER_TOPIC_VALUE_MAP = new HashMap<>();
    public static final Map<Field, PartitionValue> METADATA_CLUSTER_PARTITION_VALUE_MAP = new HashMap<>();

    private static LinkedList<Field> fillPreCommonValues(ByteArrayInputStream is) throws IOException {
        LinkedList<Field> preFields = new LinkedList<>();
        preFields.add(BrokerUtil.wrapField(is, FieldType.BYTE)); // frameVersion
        preFields.add(BrokerUtil.wrapField(is, FieldType.BYTE)); // type
        preFields.add(BrokerUtil.wrapField(is, FieldType.BYTE)); // version
        return preFields;
    }

    private static LinkedList<Field> fillPostCommonValues(ByteArrayInputStream is) throws IOException {
        LinkedList<Field> postFields = new LinkedList<>();
        postFields.add(BrokerUtil.wrapField(is, FieldType.BYTE));
        return postFields;
    }

    private static LogValueService getLogValueServiceHandler(Field type) {
        byte b = ByteUtil.convertStreamToByte(type.getData());
        ValueType valueType = ValueType.ofType(b);
        return STORE.get(valueType);
    }

    public static Value getLogValue(LogContext logContext) throws IOException {
        if (Objects.isNull(logContext) || Objects.isNull(logContext.getIs())) {
            throw new RuntimeException("failed to get log value due to invalid param");
        }
        ByteArrayInputStream is = logContext.getIs();

        // get preFields
        LinkedList<Field> preFields = fillPreCommonValues(is);

        // select valueHandler
        LogValueService handler = getLogValueServiceHandler(preFields.get(1));
        if (Objects.isNull(handler)) {
            throw new RuntimeException("not found handler to load cluster metadata value");
        }

        // fill valueFields
        Value value = handler.createValue();
        handler.load(is, value);
        handler.map(value);

        // fill preFields
        value.setFrameVersion(preFields.get(0));
        value.setType(preFields.get(1));
        value.setVersion(preFields.get(2));

        // fill postFields
        LinkedList<Field> postFields = fillPostCommonValues(is);
        value.setTaggedFieldCount(postFields.get(0));

        return value;
    }
}
