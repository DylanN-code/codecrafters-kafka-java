package service.broker.impl;

import constants.Constant;
import domain.Field;
import domain.Offset;
import domain.metadata.Batch;
import domain.metadata.Log;
import domain.metadata.Record;
import domain.metadata.record.TopicValue;
import domain.request.RequestHeaderV2;
import domain.request.body.FetchRequestBodyV16;
import domain.response.body.FetchResponseBodyV16;
import enums.ApiKey;
import enums.FieldType;
import service.broker.BaseBrokerService;
import service.broker.BrokerService;
import service.log.BaseLogValueService;
import utils.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FetchImpl extends BaseBrokerService<FetchRequestBodyV16, FetchResponseBodyV16> {

    @Override
    public void registerHandler() {
        BrokerService.STORE.put(ApiKey.FETCH, this);
    }

    @Override
    public FetchRequestBodyV16 parseRequestBody(byte[] bytes, Offset offset) {
        FetchRequestBodyV16 requestBodyV16 = new FetchRequestBodyV16();
        requestBodyV16.setMaxWaitMS(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        requestBodyV16.setMinBytes(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        requestBodyV16.setMaxBytes(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        requestBodyV16.setIsolationLevel(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        requestBodyV16.setSessionId(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        requestBodyV16.setSessionEpoch(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        requestBodyV16.setTopicLength(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        int topicLength = ByteUtil.convertStreamToByte(requestBodyV16.getTopicLength().getData()) - FieldType.BYTE.getByteSize();
        List<FetchRequestBodyV16.TopicItem> topicItemList = new ArrayList<>();
        for (int i=0; i<topicLength; i++) {
            FetchRequestBodyV16.TopicItem topicItem = getFetchRequestBodyTopicItem(bytes, offset);
            topicItemList.add(topicItem);
        }
        requestBodyV16.setTopicItemList(topicItemList);
        requestBodyV16.setForgottenTopicDataLength(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        int forgottenTopicDataLength = ByteUtil.convertStreamToByte(requestBodyV16.getForgottenTopicDataLength().getData()) - FieldType.BYTE.getByteSize();
        List<FetchRequestBodyV16.ForgottenTopic> forgottenTopicDataList = new ArrayList<>();
        for (int i=0; i<forgottenTopicDataLength; i++) {
            FetchRequestBodyV16.ForgottenTopic forgottenTopicData = getFetchRequestBodyForgottenTopicData(bytes, offset);
            forgottenTopicDataList.add(forgottenTopicData);
        }
        requestBodyV16.setForgottenTopicList(forgottenTopicDataList);
        requestBodyV16.setRackIdLength(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        int rackIdLength = ByteUtil.convertStreamToByte(requestBodyV16.getRackIdLength().getData()) - FieldType.BYTE.getByteSize();
        requestBodyV16.setRackId(BrokerUtil.wrapField(bytes, offset, FieldType.STRING, rackIdLength));
        requestBodyV16.setTagBuffer(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        return requestBodyV16;
    }

    private FetchRequestBodyV16.TopicItem getFetchRequestBodyTopicItem(byte[] bytes, Offset offset) {
        FetchRequestBodyV16.TopicItem topicItem = new FetchRequestBodyV16.TopicItem();
        topicItem.setTopicId(BrokerUtil.wrapField(bytes, offset, FieldType.STRING, Constant.TOPIC_ID_LENGTH));
        topicItem.setPartitionLength(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        int partitionLength = ByteUtil.convertStreamToByte(topicItem.getPartitionLength().getData()) - FieldType.BYTE.getByteSize();
        List<FetchRequestBodyV16.PartitionItem> partitionItemList = new ArrayList<>();
        for (int i=0; i<partitionLength; i++) {
            FetchRequestBodyV16.PartitionItem partitionItem = getFetchRequestBodyV16PartitionItem(bytes, offset);
            partitionItemList.add(partitionItem);
        }
        topicItem.setPartitionItemList(partitionItemList);
        topicItem.setTagBuffer(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        return topicItem;
    }

    private FetchRequestBodyV16.ForgottenTopic getFetchRequestBodyForgottenTopicData(byte[] bytes, Offset offset) {
        FetchRequestBodyV16.ForgottenTopic forgottenTopicData = new FetchRequestBodyV16.ForgottenTopic();
        forgottenTopicData.setTopicId(BrokerUtil.wrapField(bytes, offset, FieldType.STRING, Constant.TOPIC_ID_LENGTH));
        forgottenTopicData.setPartitionLength(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        int partitionLength = ByteUtil.convertStreamToByte(forgottenTopicData.getPartitionLength().getData()) - FieldType.BYTE.getByteSize();
        List<FetchRequestBodyV16.PartitionItem> partitionItemList = new ArrayList<>();
        for (int i=0; i<partitionLength; i++) {
            FetchRequestBodyV16.PartitionItem partitionItem = getFetchRequestBodyV16PartitionItem(bytes, offset);
            partitionItemList.add(partitionItem);
        }
        forgottenTopicData.setPartitionItemList(partitionItemList);
        forgottenTopicData.setTagBuffer(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        return forgottenTopicData;
    }

    private FetchRequestBodyV16.PartitionItem getFetchRequestBodyV16PartitionItem(byte[] bytes, Offset offset) {
        FetchRequestBodyV16.PartitionItem partitionItem = new FetchRequestBodyV16.PartitionItem();
        partitionItem.setPartitionId(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        partitionItem.setCurrentLeaderEpoch(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        partitionItem.setFetchOffset(BrokerUtil.wrapField(bytes, offset, FieldType.BIG_INTEGER));
        partitionItem.setLastFetchEpoch(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        partitionItem.setLogStartOffset(BrokerUtil.wrapField(bytes, offset, FieldType.BIG_INTEGER));
        partitionItem.setPartitionMaxBytes(BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER));
        partitionItem.setTagBuffer(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        return partitionItem;
    }

    @Override
    public FetchResponseBodyV16 convertToResponseBody(FetchRequestBodyV16 request) {
        FetchResponseBodyV16 responseBodyV16 = new FetchResponseBodyV16();
        responseBodyV16.setThrottleTimeMs(FieldUtil.getThrottleTimeMS());
        responseBodyV16.setErrorCode(FieldUtil.getErrorCodeNone());
        responseBodyV16.setSessionId(request.getSessionId());
        responseBodyV16.setResponseLength(request.getTopicLength());
        int topicLength = ByteUtil.convertStreamToByte(request.getTopicLength().getData());
        if (topicLength > 0) {
            List<FetchResponseBodyV16.Response> responseList = new ArrayList<>();
            for (int i=0; i<topicLength-1; i++) {
                FetchRequestBodyV16.TopicItem topicItem = request.getTopicItemList().get(i);
                FetchResponseBodyV16.Response response = getFetchResponseBodyResponse(topicItem);
                responseList.add(response);
            }
            responseBodyV16.setResponseList(responseList);
        } else {
            responseBodyV16.setResponseList(new ArrayList<>());
        }
        responseBodyV16.setTagBuffer(FieldUtil.getDefaultTaggedFieldSize());
        return responseBodyV16;
    }

    private FetchResponseBodyV16.Response getFetchResponseBodyResponse(FetchRequestBodyV16.TopicItem topicItem) {
        FetchResponseBodyV16.Response response = new FetchResponseBodyV16.Response();
        response.setTopicId(topicItem.getTopicId());
        response.setPartitionLength(topicItem.getPartitionLength());
        TopicValue topicValue = BaseLogValueService.METADATA_CLUSTER_TOPIC_VALUE_MAP.get(topicItem.getTopicId());
        int partitionLength = ByteUtil.convertStreamToByte(response.getPartitionLength().getData());
        if (partitionLength > 0) {
            List<FetchResponseBodyV16.PartitionItem> partitionItemList = new ArrayList<>();
            for (int i=0; i<partitionLength-1; i++) {
                FetchResponseBodyV16.PartitionItem responsePartitionItem = getFetchResponseBodyPartitionItem(topicValue, i);
                partitionItemList.add(responsePartitionItem);
            }
            response.setPartitionItemList(partitionItemList);
        } else {
            response.setPartitionItemList(new ArrayList<>());
        }
        response.setTagBuffer(FieldUtil.getDefaultTaggedFieldSize());
        return response;
    }

    private FetchResponseBodyV16.PartitionItem getFetchResponseBodyPartitionItem(TopicValue topicValue, int index) {
        FetchResponseBodyV16.PartitionItem partitionItem = new FetchResponseBodyV16.PartitionItem();
        partitionItem.setPartitionIndex(BrokerUtil.wrapField(ByteUtil.convertIntToStream(index), FieldType.INTEGER));
        boolean isTopicExist = Objects.nonNull(topicValue);
        if (isTopicExist) {
            partitionItem.setErrorCode(FieldUtil.getErrorCodeNone());
        } else {
            partitionItem.setErrorCode(FieldUtil.getErrorCodeUnknownTopicId());
        }
        partitionItem.setHighWaterMark(FieldUtil.getDefaultFetchResponseHighWaterMark());
        partitionItem.setLastStableOffset(FieldUtil.getDefaultFetchResponseLastStableOffset());
        partitionItem.setLogStartOffset(FieldUtil.getDefaultFetchResponseLogStartOffset());
        partitionItem.setAbortedTransactionLength(FieldUtil.getDefaultFetchResponseAbortedTransactionsLength());
        partitionItem.setPreferredReadReplica(FieldUtil.getDefaultFetchResponsePreferredReadReplica());
        if (isTopicExist) {
            String topicName = ByteUtil.convertStreamToString(topicValue.getTopicName().getData());
            int partitionIndex = ByteUtil.convertStreamToInt(partitionItem.getPartitionIndex().getData());
            Log log = FileUtil.loadThenGetPartitionLog(topicName, String.valueOf(partitionIndex));
            if (Objects.isNull(log) || log.getBatches().isEmpty()) {
                partitionItem.setBatchRecordLength(FieldUtil.getDefaultFetchResponseRecordLength());
                partitionItem.setBatchRecordList(new ArrayList<>());
            } else {
                byte[] unsignedVarIntBytes = ByteUtil.encodeUnsignedVarInt(log.getTotalByteRead() + 1);
                FieldType unsignedVarIntFieldType = FieldType.UNSIGNED_VARINT;
                unsignedVarIntFieldType.setByteSize(unsignedVarIntBytes.length);
                partitionItem.setBatchRecordLength(BrokerUtil.wrapField(unsignedVarIntBytes, unsignedVarIntFieldType));
                partitionItem.setBatchRecordList(log.getBatches());
            }
        } else {
            partitionItem.setBatchRecordLength(FieldUtil.getDefaultFetchResponseRecordLength());
            partitionItem.setBatchRecordList(new ArrayList<>());
        }
        partitionItem.setTagBuffer(FieldUtil.getDefaultTaggedFieldSize());
        return partitionItem;
    }

    @Override
    public LinkedList<Field> flattenResponse(FetchResponseBodyV16 responseBody, RequestHeaderV2 requestHeader) {
        LinkedList<Field> fieldLinkedList = new LinkedList<>();
        fieldLinkedList.add(requestHeader.getCorrelationId());
        fieldLinkedList.add(FieldUtil.getDefaultTaggedFieldSize());
        fieldLinkedList.add(responseBody.getThrottleTimeMs());
        fieldLinkedList.add(responseBody.getErrorCode());
        fieldLinkedList.add(responseBody.getSessionId());
        fieldLinkedList.add(responseBody.getResponseLength());
        for (FetchResponseBodyV16.Response response: responseBody.getResponseList()) {
            fieldLinkedList.add(response.getTopicId());
            fieldLinkedList.add(response.getPartitionLength());
            for (FetchResponseBodyV16.PartitionItem partitionItem: response.getPartitionItemList()) {
                fieldLinkedList.add(partitionItem.getPartitionIndex());
                fieldLinkedList.add(partitionItem.getErrorCode());
                fieldLinkedList.add(partitionItem.getHighWaterMark());
                fieldLinkedList.add(partitionItem.getLastStableOffset());
                fieldLinkedList.add(partitionItem.getLogStartOffset());
                fieldLinkedList.add(partitionItem.getAbortedTransactionLength());
                fieldLinkedList.add(partitionItem.getPreferredReadReplica());
                fieldLinkedList.add(partitionItem.getBatchRecordLength());
                for (Batch batch: partitionItem.getBatchRecordList()) {
                    fieldLinkedList.add(batch.getBaseOffset());
                    fieldLinkedList.add(batch.getBatchLength());
                    fieldLinkedList.add(batch.getPartitionLeaderEpoch());
                    fieldLinkedList.add(batch.getMagicByte());
                    fieldLinkedList.add(batch.getCrc());
                    fieldLinkedList.add(batch.getAttributes());
                    fieldLinkedList.add(batch.getLastOffsetDelta());
                    fieldLinkedList.add(batch.getBaseTimestamp());
                    fieldLinkedList.add(batch.getMaxTimestamp());
                    fieldLinkedList.add(batch.getProducerId());
                    fieldLinkedList.add(batch.getProducerEpoch());
                    fieldLinkedList.add(batch.getBaseSequence());
                    fieldLinkedList.add(batch.getRecordLength());
                    for (Record record: batch.getRecords()) {
                        fieldLinkedList.add(record.getLength());
                        fieldLinkedList.add(record.getAttributes());
                        fieldLinkedList.add(record.getTimestampDelta());
                        fieldLinkedList.add(record.getOffsetDelta());
                        fieldLinkedList.add(record.getKeyLength());
                        // fieldLinkedList.add(record.getKey());
                        fieldLinkedList.add(record.getValueLength());
                        fieldLinkedList.add(record.getValueStream());
                        fieldLinkedList.add(record.getHeaderArrayCount());
                    }
                }
                fieldLinkedList.add(partitionItem.getTagBuffer());
            }
            fieldLinkedList.add(response.getTagBuffer());
        }
        fieldLinkedList.add(responseBody.getTagBuffer());
        return fieldLinkedList;
    }

}
