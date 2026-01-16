package service.broker.impl;

import domain.Field;
import domain.Offset;
import domain.request.RequestHeaderV2;
import domain.request.body.ProduceRequestV11;
import domain.response.body.ProduceResponseV11;
import enums.ApiKey;
import enums.FieldType;
import service.broker.BaseBrokerService;
import service.broker.BrokerService;
import utils.BrokerUtil;
import utils.ByteUtil;

import java.util.LinkedList;

public class ProduceImpl extends BaseBrokerService<ProduceRequestV11, ProduceResponseV11> {

    @Override
    public void registerHandler() {
        BrokerService.STORE.put(ApiKey.PRODUCE, this);
    }

    @Override
    public ProduceRequestV11 parseRequestBody(byte[] bytes, Offset offset) {
        ProduceRequestV11 produceRequestV11 = new ProduceRequestV11();
        produceRequestV11.setTransactionalId(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        produceRequestV11.setRequiredAcks(BrokerUtil.wrapField(bytes, offset, FieldType.SHORT));
        produceRequestV11.setTimeout(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        produceRequestV11.setTopicArrayLength(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        int topicArrayLength = ByteUtil.convertStreamToByte(produceRequestV11.getTopicArrayLength().getData()) - FieldType.BYTE.getByteSize();
        ProduceRequestV11.TopicItem[] topicItemArray = new ProduceRequestV11.TopicItem[topicArrayLength];
        for (int i=0; i<topicArrayLength; i++) {
            ProduceRequestV11.TopicItem topicItem = parseProduceRequestTopicItem(bytes, offset);
            topicItemArray[i] = topicItem;
        }
        produceRequestV11.setTopicArray(topicItemArray);
        produceRequestV11.setTagBuffer(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        return produceRequestV11;
    }

    private ProduceRequestV11.TopicItem parseProduceRequestTopicItem(byte[] bytes, Offset offset) {
        ProduceRequestV11.TopicItem topicItem = new ProduceRequestV11.TopicItem();
        topicItem.setTopicNameLength(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        int topicNameLength = ByteUtil.convertStreamToByte(topicItem.getTopicNameLength().getData()) - FieldType.BYTE.getByteSize();
        topicItem.setTopicName(BrokerUtil.wrapField(bytes, offset, FieldType.STRING, topicNameLength));
        topicItem.setPartitionArrayLength(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        int partitionArrayLength = ByteUtil.convertStreamToByte(topicItem.getPartitionArrayLength().getData()) - FieldType.BYTE.getByteSize();
        ProduceRequestV11.PartitionItem[] partitionItemArray = new ProduceRequestV11.PartitionItem[partitionArrayLength];
        for (int i=0; i<partitionArrayLength; i++) {
            ProduceRequestV11.PartitionItem partitionItem = parseProduceRequestPartitionItem(bytes, offset);
            partitionItemArray[i] = partitionItem;
        }
        topicItem.setPartitionArray(partitionItemArray);
        topicItem.setTagBuffer(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        return topicItem;
    }

    private ProduceRequestV11.PartitionItem parseProduceRequestPartitionItem(byte[] bytes, Offset offset) {
        ProduceRequestV11.PartitionItem partitionItem = new ProduceRequestV11.PartitionItem();
        partitionItem.setPartitionIndex(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        partitionItem.setRecordBatchArrayLength(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        int recordBatchArrayLength = ByteUtil.convertStreamToByte(partitionItem.getRecordBatchArrayLength().getData()) - FieldType.BYTE.getByteSize();
        ProduceRequestV11.BatchRecordItem[] batchRecordArray = new ProduceRequestV11.BatchRecordItem[recordBatchArrayLength];
        for (int i=0; i<recordBatchArrayLength; i++) {
            ProduceRequestV11.BatchRecordItem batchRecordItem = parseProduceRequestBatchRecordItem(bytes, offset);
            batchRecordArray[i] = batchRecordItem;
        }
        partitionItem.setRecordBatchArray(batchRecordArray);
        partitionItem.setTagBuffer(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        return partitionItem;
    }

    private ProduceRequestV11.BatchRecordItem parseProduceRequestBatchRecordItem(byte[] bytes, Offset offset) {
        ProduceRequestV11.BatchRecordItem batchRecordItem = new ProduceRequestV11.BatchRecordItem();
        batchRecordItem.setBaseOffset(BrokerUtil.wrapField(bytes, offset, FieldType.BIG_INTEGER));
        return batchRecordItem;
    }

    private ProduceRequestV11.RecordItem parseProduceRequestRecordItem(byte[] bytes, Offset offset) {
        ProduceRequestV11.RecordItem recordItem = new ProduceRequestV11.RecordItem();
        recordItem.setRecordSize(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        return recordItem;
    }

    @Override
    public ProduceResponseV11 convertToResponseBody(ProduceRequestV11 request) {
        return null;
    }

    @Override
    public LinkedList<Field> flattenResponse(ProduceResponseV11 responseBody, RequestHeaderV2 requestHeader) {
        return null;
    }
}
