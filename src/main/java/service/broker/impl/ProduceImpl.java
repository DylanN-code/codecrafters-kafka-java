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
import utils.FieldUtil;

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
        batchRecordItem.setBatchSize(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        batchRecordItem.setPartitionLeaderEpoch(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        batchRecordItem.setMagicByte(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        batchRecordItem.setCrc32(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        batchRecordItem.setAttributes(BrokerUtil.wrapField(bytes, offset, FieldType.SHORT));
        batchRecordItem.setLastOffsetDelta(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        batchRecordItem.setFirstTimestamp(BrokerUtil.wrapField(bytes, offset, FieldType.BIG_INTEGER));
        batchRecordItem.setLastTimestamp(BrokerUtil.wrapField(bytes, offset, FieldType.BIG_INTEGER));
        batchRecordItem.setProducerId(BrokerUtil.wrapField(bytes, offset, FieldType.BIG_INTEGER));
        batchRecordItem.setProducerEpoch(BrokerUtil.wrapField(bytes, offset, FieldType.SHORT));
        batchRecordItem.setBaseSequence(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        batchRecordItem.setRecordArrayLength(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        int recordArrayLength = ByteUtil.convertStreamToInt(batchRecordItem.getRecordArrayLength().getData()) - FieldType.INTEGER.getByteSize();
        ProduceRequestV11.RecordItem[] recordItemArray = new ProduceRequestV11.RecordItem[recordArrayLength];
        for (int i=0; i<recordArrayLength; i++) {
            ProduceRequestV11.RecordItem recordItem = parseProduceRequestRecordItem(bytes, offset);
            recordItemArray[i] = recordItem;
        }
        batchRecordItem.setRecordArray(recordItemArray);
        return batchRecordItem;
    }

    private ProduceRequestV11.RecordItem parseProduceRequestRecordItem(byte[] bytes, Offset offset) {
        ProduceRequestV11.RecordItem recordItem = new ProduceRequestV11.RecordItem();
        recordItem.setRecordSize(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        recordItem.setAttributes(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        recordItem.setTimestampDelta(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        recordItem.setOffsetDelta(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        recordItem.setKeyLength(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        recordItem.setValueLength(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        int valueLength = ByteUtil.convertStreamToByte(recordItem.getValueLength().getData());
        recordItem.setValue(BrokerUtil.wrapField(bytes, offset, FieldType.STRING, valueLength));
        recordItem.setHeadersCount(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        return recordItem;
    }

    @Override
    public ProduceResponseV11 convertToResponseBody(ProduceRequestV11 request) {
        ProduceResponseV11 produceResponseV11 = new ProduceResponseV11();
        produceResponseV11.setResponseLength(request.getTopicArrayLength());
        int topicLength = ByteUtil.convertStreamToByte(request.getTopicArrayLength().getData()) - FieldType.BYTE.getByteSize();
        ProduceResponseV11.Response[] responseArray = new ProduceResponseV11.Response[topicLength];
        for (int i=0; i<topicLength; i++) {
            responseArray[i] = getProduceResponse(request.getTopicArray()[i]);
        }
        produceResponseV11.setResponseArray(responseArray);
        produceResponseV11.setThrottleTimeMs(FieldUtil.getThrottleTimeMS());
        produceResponseV11.setTagBuffer(FieldUtil.getDefaultTaggedFieldSize());
        return produceResponseV11;
    }

    private ProduceResponseV11.Response getProduceResponse(ProduceRequestV11.TopicItem topicItem) {
        ProduceResponseV11.Response response = new ProduceResponseV11.Response();
        response.setTopicNameLength(topicItem.getTopicNameLength());
        response.setTopicName(topicItem.getTopicName());
        response.setPartitionArrayLength(topicItem.getPartitionArrayLength());
        int partitionLength = ByteUtil.convertStreamToByte(topicItem.getPartitionArrayLength().getData()) - FieldType.BYTE.getByteSize();
        ProduceResponseV11.PartitionItem[] partitionArray = new ProduceResponseV11.PartitionItem[partitionLength];
        for (int i=0; i<partitionLength; i++) {
            partitionArray[i] = getProducePartition(topicItem.getPartitionArray()[i], i);
        }
        response.setPartitionArray(partitionArray);
        response.setTagBuffer(FieldUtil.getDefaultTaggedFieldSize());
        return response;
    }

    private ProduceResponseV11.PartitionItem getProducePartition(ProduceRequestV11.PartitionItem partitionItem, int i) {
        ProduceResponseV11.PartitionItem responsePartitionItem = new ProduceResponseV11.PartitionItem();
        responsePartitionItem.setPartitionIndex(BrokerUtil.wrapField(ByteUtil.convertIntToStream(i), FieldType.INTEGER));
        responsePartitionItem.setErrorCode(FieldUtil.getErrorCodeUnknownTopicOrPartition());
        responsePartitionItem.setBaseOffset(FieldUtil.getErrorPartitionItemBaseOffset());
        responsePartitionItem.setLogAppendTime(FieldUtil.getErrorPartitionItemLogAppendTime());
        responsePartitionItem.setLogStartOffset(FieldUtil.getErrorPartitionItemLogStartOffset());
        responsePartitionItem.setRecordErrorArrayLength(FieldUtil.getErrorPartitionArrayLength());
        responsePartitionItem.setErrorMessage(FieldUtil.getErrorPartitionMessage());
        responsePartitionItem.setTagBuffer(FieldUtil.getDefaultTaggedFieldSize());
        return responsePartitionItem;
    }

    @Override
    public LinkedList<Field> flattenResponse(ProduceResponseV11 responseBody, RequestHeaderV2 requestHeader) {
        return null;
    }
}
