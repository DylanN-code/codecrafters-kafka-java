package service.broker.impl;

import constants.Constant;
import domain.Field;
import domain.LogContext;
import domain.Offset;
import domain.metadata.record.PartitionValue;
import domain.metadata.record.TopicValue;
import domain.request.RequestHeaderV2;
import domain.request.body.ProduceRequestV11;
import domain.response.body.ProduceResponseV11;
import enums.ApiKey;
import enums.FieldType;
import enums.ValueType;
import service.broker.BaseBrokerService;
import service.broker.BrokerService;
import service.log.BaseLogValueService;
import utils.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Objects;

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
        int recordsLength = ByteUtil.convertStreamToByte(partitionItem.getRecordBatchArrayLength().getData()) - FieldType.BYTE.getByteSize();
        if (recordsLength > 0) {
            Field recordBatchData = BrokerUtil.wrapField(bytes, offset, FieldType.STRING, recordsLength);
            partitionItem.setRecordBatchData(recordBatchData);
        } else {
            partitionItem.setRecordBatchData(new Field(new byte[0], FieldType.STRING, 0));
        }
        partitionItem.setTagBuffer(BrokerUtil.wrapField(bytes, offset, FieldType.BYTE));
        return partitionItem;
    }

    @Override
    public ProduceResponseV11 convertToResponseBody(ProduceRequestV11 request) {
        ProduceResponseV11 produceResponseV11 = new ProduceResponseV11();
        produceResponseV11.setResponseLength(request.getTopicArrayLength());
        int topicLength = ByteUtil.convertStreamToByte(request.getTopicArrayLength().getData()) - FieldType.BYTE.getByteSize();
        ProduceResponseV11.Response[] responseArray = new ProduceResponseV11.Response[topicLength];
        for (int i=0; i<topicLength; i++) {
            responseArray[i] = handleThenGetProduceResponse(request.getTopicArray()[i]);
        }
        produceResponseV11.setResponseArray(responseArray);
        produceResponseV11.setThrottleTimeMs(FieldUtil.getThrottleTimeMS());
        produceResponseV11.setTagBuffer(FieldUtil.getDefaultTaggedFieldSize());
        return produceResponseV11;
    }

    private ProduceResponseV11.Response handleThenGetProduceResponse(ProduceRequestV11.TopicItem topicItem) {
        TopicValue topicValue = BaseLogValueService.getTopicByNameField(topicItem.getTopicName());
        ProduceResponseV11.Response response = new ProduceResponseV11.Response();
        response.setTopicNameLength(topicItem.getTopicNameLength());
        response.setTopicName(topicItem.getTopicName());
        response.setPartitionArrayLength(topicItem.getPartitionArrayLength());
        int partitionLength = ByteUtil.convertStreamToByte(topicItem.getPartitionArrayLength().getData()) - FieldType.BYTE.getByteSize();
        ProduceResponseV11.PartitionItem[] partitionArray = new ProduceResponseV11.PartitionItem[partitionLength];
        for (int i=0; i<partitionLength; i++) {
            partitionArray[i] = handleThenGetProducePartition(topicValue, topicItem.getPartitionArray()[i]);
        }
        response.setPartitionArray(partitionArray);
        response.setTagBuffer(FieldUtil.getDefaultTaggedFieldSize());
        return response;
    }

    private ProduceResponseV11.PartitionItem handleThenGetProducePartition(TopicValue topicValue, ProduceRequestV11.PartitionItem partitionItem) {
        Field errorCode = FieldUtil.getErrorCodeNone();
        if (!Objects.nonNull(topicValue)) {
            errorCode = FieldUtil.getErrorCodeUnknownTopicOrPartition();
        } else {
            PartitionValue partitionValue = BaseLogValueService.isPartitionExistByTopicUUIDAndPartitionIdField(topicValue.getTopicUUID(), partitionItem.getPartitionIndex());
            errorCode = Objects.isNull(partitionValue) ? FieldUtil.getErrorCodeUnknownTopicOrPartition() : errorCode;
        }

        if (Objects.equals(errorCode, FieldUtil.getErrorCodeNone())) {
            handlePartition(topicValue, partitionItem);
        }

        ProduceResponseV11.PartitionItem responsePartitionItem = new ProduceResponseV11.PartitionItem();
        responsePartitionItem.setPartitionIndex(partitionItem.getPartitionIndex());
        responsePartitionItem.setErrorCode(errorCode);
        if (Objects.equals(errorCode, FieldUtil.getErrorCodeNone())) {
            responsePartitionItem.setBaseOffset(FieldUtil.getPartitionItemBaseOffset());
        } else if (Objects.equals(errorCode, FieldUtil.getErrorCodeUnknownTopicOrPartition())) {
            responsePartitionItem.setBaseOffset(FieldUtil.getErrorPartitionItemBaseOffset());
        }
        responsePartitionItem.setLogAppendTime(FieldUtil.getErrorPartitionItemLogAppendTime());
        if (Objects.equals(errorCode, FieldUtil.getErrorCodeNone())) {
            responsePartitionItem.setLogStartOffset(FieldUtil.getPartitionItemLogStartOffset());
        } else if (Objects.equals(errorCode, FieldUtil.getErrorCodeUnknownTopicOrPartition())) {
            responsePartitionItem.setLogStartOffset(FieldUtil.getErrorPartitionItemLogStartOffset());
        }
        responsePartitionItem.setRecordErrorArrayLength(FieldUtil.getErrorPartitionArrayLength());
        responsePartitionItem.setErrorMessage(FieldUtil.getErrorPartitionMessage());
        responsePartitionItem.setTagBuffer(FieldUtil.getDefaultTaggedFieldSize());
        return responsePartitionItem;
    }

    private void handlePartition(TopicValue topicValue, ProduceRequestV11.PartitionItem partitionItem) {
        String logFolderDefaultPath = PropertyUtil.getProperty(Constant.COMBINED_LOG_FOLDER_DEFAULT_PATH);
        String topicName = new String(topicValue.getTopicName().getData());
        String partitionIndex = String.valueOf(ByteUtil.convertStreamToInt(partitionItem.getPartitionIndex().getData()));
        String batchRecordLogFolderName = String.format(Constant.BATCH_RECORD_LOG_DATA_FOLDER_NAME, topicName, partitionIndex);
        Path batchRecordLogFileName = Path.of(logFolderDefaultPath, batchRecordLogFolderName, Constant.FIRST_LOG_FILE_NAME);
        System.out.println(batchRecordLogFileName);
        try {
            LogContext logContext = new LogContext();
            logContext.setFilePath(batchRecordLogFileName.toString());
            logContext.setValueType(ValueType.PARTITION);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.writeBytes(partitionItem.getRecordBatchData().getData());
            logContext.setOs(byteArrayOutputStream);
            LogUtil.saveLog(logContext);
        } catch (IOException e) {
            System.out.println("failed to handle batch record from partition log due to error=" + e.getMessage());
        }
    }

    @Override
    public LinkedList<Field> flattenResponse(ProduceResponseV11 responseBody, RequestHeaderV2 requestHeader) {
        LinkedList<Field> fieldLinkedList = new LinkedList<>();
        // Add response header fields
        fieldLinkedList.add(requestHeader.getCorrelationId());
        fieldLinkedList.add(FieldUtil.getDefaultTaggedFieldSize());
        // Add response body fields
        fieldLinkedList.add(responseBody.getResponseLength());
        for (ProduceResponseV11.Response response: responseBody.getResponseArray()) {
            fieldLinkedList.add(response.getTopicNameLength());
            fieldLinkedList.add(response.getTopicName());
            fieldLinkedList.add(response.getPartitionArrayLength());
            for (ProduceResponseV11.PartitionItem partitionItem: response.getPartitionArray()) {
                fieldLinkedList.add(partitionItem.getPartitionIndex());
                fieldLinkedList.add(partitionItem.getErrorCode());
                fieldLinkedList.add(partitionItem.getBaseOffset());
                fieldLinkedList.add(partitionItem.getLogAppendTime());
                fieldLinkedList.add(partitionItem.getLogStartOffset());
                fieldLinkedList.add(partitionItem.getRecordErrorArrayLength());
                fieldLinkedList.add(partitionItem.getErrorMessage());
                fieldLinkedList.add(partitionItem.getTagBuffer());
            }
            fieldLinkedList.add(response.getTagBuffer());
        }
        fieldLinkedList.add(responseBody.getThrottleTimeMs());
        fieldLinkedList.add(responseBody.getTagBuffer());
        return fieldLinkedList;
    }
}
