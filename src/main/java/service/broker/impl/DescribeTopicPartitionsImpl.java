package service.broker.impl;

import constants.Constant;
import domain.Field;
import domain.Offset;
import domain.metadata.record.PartitionValue;
import domain.metadata.record.TopicValue;
import domain.request.RequestHeaderV2;
import domain.request.body.DescribeTopicPartitionsRequestBodyV0;
import domain.response.body.DescribeTopicPartitionsResponseBodyV0;
import enums.ApiKey;
import enums.FieldType;
import service.broker.BaseBrokerService;
import service.broker.BrokerService;
import service.log.BaseLogValueService;
import utils.BrokerUtil;
import utils.ByteUtil;
import utils.FieldUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DescribeTopicPartitionsImpl extends BaseBrokerService<DescribeTopicPartitionsRequestBodyV0, DescribeTopicPartitionsResponseBodyV0> {

    @Override
    public void registerHandler() {
        BrokerService.STORE.put(ApiKey.DESCRIBE_TOPIC_PARTITIONS, this);
    }

    @Override
    public DescribeTopicPartitionsRequestBodyV0 parseRequestBody(byte[] bytes, Offset offset) {
        DescribeTopicPartitionsRequestBodyV0 requestBodyV0 = new DescribeTopicPartitionsRequestBodyV0();

        Field topicLength = BrokerUtil.wrapField(bytes, offset, FieldType.BYTE);
        requestBodyV0.setTopicListLength(topicLength);

        byte topicLengthVal = ByteUtil.convertStreamToByte(topicLength.getData());
        if (topicLengthVal == 0) {
            requestBodyV0.setTopicList(null);
        } else if (topicLengthVal == 1) {
            requestBodyV0.setTopicList(new ArrayList<>());
        } else {
            List<DescribeTopicPartitionsRequestBodyV0.Item> itemList = new ArrayList<>();
            for (int i = 0; i < topicLengthVal - 1; i++) {
                DescribeTopicPartitionsRequestBodyV0.Item requestItem = new DescribeTopicPartitionsRequestBodyV0.Item();
                Field topicNameLength = BrokerUtil.wrapField(bytes, offset, FieldType.BYTE);
                requestItem.setTopicNameLength(topicNameLength);

                int topicNameLengthVal = ByteUtil.convertStreamToByte(topicNameLength.getData()) - FieldType.BYTE.getByteSize();
                Field topicName = BrokerUtil.wrapField(bytes, offset, FieldType.STRING, topicNameLengthVal);
                requestItem.setTopicName(topicName);

                Field topicTagBuffer = BrokerUtil.wrapField(bytes, offset, FieldType.BYTE);
                requestItem.setTagBuffer(topicTagBuffer);
                itemList.add(requestItem);
            }
            requestBodyV0.setTopicList(itemList);
        }
        Field responsePartitionLimit = BrokerUtil.wrapField(bytes, offset, FieldType.INTEGER);
        requestBodyV0.setResponsePartitionLimit(responsePartitionLimit);

        Field cursor = BrokerUtil.wrapField(bytes, offset, FieldType.BYTE);
        requestBodyV0.setCursor(cursor);

        Field tagBuffer = BrokerUtil.wrapField(bytes, offset, FieldType.BYTE);
        requestBodyV0.setTagBuffer(tagBuffer);
        return requestBodyV0;
    }

    @Override
    public DescribeTopicPartitionsResponseBodyV0 convertToResponseBody(DescribeTopicPartitionsRequestBodyV0 request) {
        DescribeTopicPartitionsResponseBodyV0 responseBodyV0 = new DescribeTopicPartitionsResponseBodyV0();
        responseBodyV0.setThrottleTimeMS(FieldUtil.getThrottleTimeMS());
        responseBodyV0.setTopicArrayLength(request.getTopicListLength());

        List<DescribeTopicPartitionsResponseBodyV0.TopicItem> itemList = new ArrayList<>();
        for (DescribeTopicPartitionsRequestBodyV0.Item requestItem : request.getTopicList()) {
            DescribeTopicPartitionsResponseBodyV0.TopicItem responseBodyV0TopicItem = getResponseBodyV0TopicItem(requestItem);
            itemList.add(responseBodyV0TopicItem);
        }
        responseBodyV0.setTopicList(itemList);

        responseBodyV0.setNextCursor(request.getCursor());
        responseBodyV0.setTaggedFieldSize(FieldUtil.getDefaultTaggedFieldSize());
        return responseBodyV0;
    }

    private DescribeTopicPartitionsResponseBodyV0.TopicItem getResponseBodyV0TopicItem(DescribeTopicPartitionsRequestBodyV0.Item requestItem) {
        DescribeTopicPartitionsResponseBodyV0.TopicItem responseItem = new DescribeTopicPartitionsResponseBodyV0.TopicItem();
        responseItem.setTopicNameLength(requestItem.getTopicNameLength());
        responseItem.setTopicName(requestItem.getTopicName());

        List<TopicValue> topicValues = BaseLogValueService.METADATA_CLUSTER_TOPIC_VALUE_MAP.values().stream()
                .filter(e -> Arrays.equals(e.getTopicName().getData(), requestItem.getTopicName().getData()))
                .toList();
        if (topicValues.isEmpty()) {
            responseItem.setErrorCode(FieldUtil.getErrorCodeUnknownTopicOrPartition());
            responseItem.setTopicId(BrokerUtil.wrapField(Constant.DEFAULT_TOPIC_ID, FieldType.STRING, Constant.TOPIC_ID_LENGTH));
            responseItem.setPartitionArrayLength(FieldUtil.getDefaultPartitionArrayLength());
        } else {
            responseItem.setErrorCode(FieldUtil.getErrorCodeNone());

            TopicValue topicValue = topicValues.getFirst();
            responseItem.setTopicId(BrokerUtil.wrapField(topicValue.getTopicUUID().getData(), FieldType.STRING, Constant.TOPIC_ID_LENGTH));

            List<PartitionValue> partitionValues = BaseLogValueService.METADATA_CLUSTER_PARTITION_VALUE_MAP.values().stream()
                    .filter(e -> Arrays.equals(e.getTopicUUID().getData(), topicValue.getTopicUUID().getData()))
                    .toList();
            int partitionArrayLength = partitionValues.size();
            byte[] partitionArrayLengthStream = ByteUtil.convertByteToStream((byte) (partitionArrayLength + FieldType.BYTE.getByteSize()));
            responseItem.setPartitionArrayLength(BrokerUtil.wrapField(partitionArrayLengthStream, FieldType.BYTE));

            List<DescribeTopicPartitionsResponseBodyV0.PartitionItem> partitionItemList = new ArrayList<>();
            for (int i = 0; i < partitionArrayLength; i++) {
                DescribeTopicPartitionsResponseBodyV0.PartitionItem partitionItem = getPartitionItem(partitionValues.get(i), i);
                partitionItemList.add(partitionItem);
            }
            responseItem.setPartitionItemList(partitionItemList);
        }

        responseItem.setIsInternal(FieldUtil.getDefaultIsInternal());
        responseItem.setTopicAuthorizedOperations(FieldUtil.getDefaultTopicAuthorizedOperations());
        responseItem.setTaggedFieldSize(FieldUtil.getDefaultTaggedFieldSize());
        return responseItem;
    }

    private DescribeTopicPartitionsResponseBodyV0.PartitionItem getPartitionItem(PartitionValue partitionValue, int index) {
        DescribeTopicPartitionsResponseBodyV0.PartitionItem partitionItem = new DescribeTopicPartitionsResponseBodyV0.PartitionItem();
        partitionItem.setErrorCode(FieldUtil.getErrorCodeNone());
        partitionItem.setPartitionIndex(BrokerUtil.wrapField(ByteUtil.convertIntToStream(index), FieldType.INTEGER));
        partitionItem.setLeaderId(partitionValue.getLeader());
        partitionItem.setLeaderEpoch(partitionValue.getLeaderEpoch());
        partitionItem.setReplicaNodeLength(partitionValue.getReplicaArrayLength());
        partitionItem.setReplicaNodeArray(partitionValue.getReplicaArray());
        partitionItem.setIsrNodeLength(partitionValue.getInSyncReplicaArrayLength());
        partitionItem.setIsrNodeArray(partitionValue.getInSyncReplicaArray());
        partitionItem.setEligibleLeaderReplicaLength(FieldUtil.getDefaultEligibleLeaderReplicasLength());
        partitionItem.setLastKnownELRLength(FieldUtil.getDefaultLastKnownELRLength());
        partitionItem.setOfflineReplicaLength(FieldUtil.getDefaultOfflineReplicasLength());
        partitionItem.setTaggedFieldSize(FieldUtil.getDefaultTaggedFieldSize());
        return partitionItem;
    }

    @Override
    public LinkedList<Field> flattenResponse(DescribeTopicPartitionsResponseBodyV0 responseBody, RequestHeaderV2 requestHeader) {
        LinkedList<Field> fieldLinkedList = new LinkedList<>();
        fieldLinkedList.add(requestHeader.getCorrelationId());
        fieldLinkedList.add(FieldUtil.getDefaultTaggedFieldSize());
        fieldLinkedList.add(responseBody.getThrottleTimeMS());
        fieldLinkedList.add(responseBody.getTopicArrayLength());
        for (DescribeTopicPartitionsResponseBodyV0.TopicItem item : responseBody.getTopicList()) {
            fieldLinkedList.add(item.getErrorCode());
            fieldLinkedList.add(item.getTopicNameLength());
            fieldLinkedList.add(item.getTopicName());
            fieldLinkedList.add(item.getTopicId());
            fieldLinkedList.add(item.getIsInternal());
            fieldLinkedList.add(item.getPartitionArrayLength());
            byte partitionArrayLength = ByteUtil.convertStreamToByte(item.getPartitionArrayLength().getData());
            for (int i = 0; i < partitionArrayLength - 1; i++) {
                DescribeTopicPartitionsResponseBodyV0.PartitionItem partitionItem = item.getPartitionItemList().get(i);
                fieldLinkedList.add(partitionItem.getErrorCode());
                fieldLinkedList.add(partitionItem.getPartitionIndex());
                fieldLinkedList.add(partitionItem.getLeaderId());
                fieldLinkedList.add(partitionItem.getLeaderEpoch());
                fieldLinkedList.add(partitionItem.getReplicaNodeLength());
                fieldLinkedList.addAll(partitionItem.getReplicaNodeArray());
                fieldLinkedList.add(partitionItem.getIsrNodeLength());
                fieldLinkedList.addAll(partitionItem.getIsrNodeArray());
                fieldLinkedList.add(partitionItem.getEligibleLeaderReplicaLength());
                fieldLinkedList.add(partitionItem.getLastKnownELRLength());
                fieldLinkedList.add(partitionItem.getOfflineReplicaLength());
                fieldLinkedList.add(partitionItem.getTaggedFieldSize());
            }
            fieldLinkedList.add(item.getTopicAuthorizedOperations());
            fieldLinkedList.add(FieldUtil.getDefaultTaggedFieldSize());
        }
        fieldLinkedList.add(responseBody.getNextCursor());
        fieldLinkedList.add(FieldUtil.getDefaultTaggedFieldSize());
        return fieldLinkedList;
    }
}
