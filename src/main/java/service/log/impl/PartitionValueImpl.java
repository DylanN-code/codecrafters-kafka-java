package service.log.impl;

import constants.Constant;
import domain.Field;
import domain.metadata.record.PartitionValue;
import enums.FieldType;
import enums.ValueType;
import service.log.BaseLogValueService;
import service.log.LogValueService;
import utils.BrokerUtil;
import utils.ByteUtil;
import utils.FieldUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class PartitionValueImpl extends BaseLogValueService<PartitionValue> {
    @Override
    public PartitionValue createValue() {
        return new PartitionValue();
    }

    @Override
    public void load(ByteArrayInputStream is, PartitionValue value) throws IOException {
        value.setPartitionId(BrokerUtil.wrapField(is, FieldType.INTEGER));
        value.setTopicUUID(BrokerUtil.wrapField(is, FieldType.STRING, Constant.TOPIC_ID_LENGTH));
        value.setReplicaArrayLength(BrokerUtil.wrapField(is, FieldType.BYTE));

        int replicaArrayLength = ByteUtil.convertStreamToByte(value.getReplicaArrayLength().getData()) - FieldType.BYTE.getByteSize();
        if (replicaArrayLength == -1) {
            value.setReplicaArray(null);
        } else {
            LinkedList<Field> replicaArray = new LinkedList<>();
            for (int i=0; i<replicaArrayLength; i++) {
                replicaArray.add(BrokerUtil.wrapField(is, FieldType.INTEGER));
            }
            value.setReplicaArray(replicaArray);
        }

        value.setInSyncReplicaArrayLength(BrokerUtil.wrapField(is, FieldType.BYTE));
        int inSyncReplicaArrayLength = ByteUtil.convertStreamToByte(value.getInSyncReplicaArrayLength().getData()) - FieldType.BYTE.getByteSize();
        if (inSyncReplicaArrayLength == -1) {
            value.setInSyncReplicaArray(null);
        } else {
            LinkedList<Field> inSyncReplicaArray = new LinkedList<>();
            for (int i=0; i<inSyncReplicaArrayLength; i++) {
                inSyncReplicaArray.add(BrokerUtil.wrapField(is, FieldType.INTEGER));
            }
            value.setInSyncReplicaArray(inSyncReplicaArray);
        }

        value.setRemoveReplicaArrayLength(BrokerUtil.wrapField(is, FieldType.BYTE));
        value.setAddReplicaArrayLength(BrokerUtil.wrapField(is, FieldType.BYTE));
        value.setLeader(BrokerUtil.wrapField(is, FieldType.INTEGER));
        value.setLeaderEpoch(BrokerUtil.wrapField(is, FieldType.INTEGER));
        value.setPartitionEpoch(BrokerUtil.wrapField(is, FieldType.INTEGER));

        value.setDirectoryArrayLength(BrokerUtil.wrapField(is, FieldType.BYTE));
        int directoryArrayLength = ByteUtil.convertStreamToByte(value.getDirectoryArrayLength().getData()) - FieldType.BYTE.getByteSize();
        if (directoryArrayLength == -1) {
            value.setDirectoryArray(null);
        } else {
            LinkedList<Field> directoryArray = new LinkedList<>();
            for (int i=0; i<directoryArrayLength; i++) {
                directoryArray.add(BrokerUtil.wrapField(is, FieldType.STRING, Constant.TOPIC_ID_LENGTH));
            }
            value.setDirectoryArray(directoryArray);
        }
    }

    @Override
    public void map(PartitionValue value) {
        PartitionValue copiedPartitionValue = ByteUtil.deepCopy(value);
        if (Objects.isNull(copiedPartitionValue)) {
            return;
        }
        byte[] partitionIdStream = copiedPartitionValue.getPartitionId().getData();
        byte[] topicIdStream = copiedPartitionValue.getTopicUUID().getData();
        Field recordId = FieldUtil.getRecordId(partitionIdStream, topicIdStream);
        value.setRecordId(recordId);
        METADATA_CLUSTER_PARTITION_VALUE_MAP.put(recordId, copiedPartitionValue);
    }

    @Override
    public void registerHandler() {
        LogValueService.STORE.put(ValueType.PARTITION, this);
    }
}
