package utils;

import domain.LogContext;
import domain.metadata.Batch;
import domain.metadata.Log;
import domain.metadata.Record;
import domain.metadata.Value;
import enums.FieldType;
import enums.ValueType;
import service.log.BaseLogValueService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class LogUtil {

    public static Log getLog(LogContext logContext) throws IOException {
        if (Objects.isNull(logContext) || Objects.isNull(logContext.getFilePath()) || logContext.getFilePath().isBlank()) {
            return null;
        }
        String filePath = logContext.getFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("failed to load due to file not found, logContext=" + logContext);
            return null;
        }
        FileInputStream fileIS = new FileInputStream(file);
        byte[] allBytes = fileIS.readAllBytes();
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(allBytes);
        logContext.setIs(byteArrayIS);
        Log log = new Log();
        while (byteArrayIS.available() != 0) {
            Batch batch = getLogBatch(logContext);
            log.addBatch(batch);
        }
        log.setTotalByteRead(allBytes.length);
        return log;
    }

    private static Batch getLogBatch(LogContext logContext) throws IOException {
        ByteArrayInputStream is = logContext.getIs();
        Batch batch = new Batch();
        batch.setBaseOffset(BrokerUtil.wrapField(is, FieldType.BIG_INTEGER));
        batch.setBatchLength(BrokerUtil.wrapField(is, FieldType.INTEGER));
        batch.setPartitionLeaderEpoch(BrokerUtil.wrapField(is, FieldType.INTEGER));
        batch.setMagicByte(BrokerUtil.wrapField(is, FieldType.BYTE));
        batch.setCrc(BrokerUtil.wrapField(is, FieldType.INTEGER));
        batch.setAttributes(BrokerUtil.wrapField(is, FieldType.SHORT));
        batch.setLastOffsetDelta(BrokerUtil.wrapField(is, FieldType.INTEGER));
        batch.setBaseTimestamp(BrokerUtil.wrapField(is, FieldType.BIG_INTEGER));
        batch.setMaxTimestamp(BrokerUtil.wrapField(is, FieldType.BIG_INTEGER));
        batch.setProducerId(BrokerUtil.wrapField(is, FieldType.BIG_INTEGER));
        batch.setProducerEpoch(BrokerUtil.wrapField(is, FieldType.SHORT));
        batch.setBaseSequence(BrokerUtil.wrapField(is, FieldType.INTEGER));
        batch.setRecordLength(BrokerUtil.wrapField(is, FieldType.INTEGER));
        batch.setRecords(new LinkedList<>());
        int numRecords = ByteUtil.convertStreamToInt(batch.getRecordLength().getData());
        for (int i = 0; i < numRecords; i++) {
            Record record = getLogRecord(i, logContext);
            batch.getRecords().add(record);
        }
        return batch;
    }

    private static Record getLogRecord(int index, LogContext logContext) throws IOException {
        ByteArrayInputStream is = logContext.getIs();
        Record record = new Record();
        if (index == 0) {
            record.setLength(BrokerUtil.wrapField(is, FieldType.BYTE));
        } else {
            record.setLength(BrokerUtil.wrapField(is, FieldType.SHORT));
        }
        record.setAttributes(BrokerUtil.wrapField(is, FieldType.BYTE));
        record.setTimestampDelta(BrokerUtil.wrapField(is, FieldType.BYTE));
        record.setOffsetDelta(BrokerUtil.wrapField(is, FieldType.BYTE));
        record.setKeyLength(BrokerUtil.wrapField(is, FieldType.BYTE));
        record.setKey(null);
        if (index == 0) {
            record.setValueLength(BrokerUtil.wrapField(is, FieldType.BYTE));
        } else {
            record.setValueLength(BrokerUtil.wrapField(is, FieldType.SHORT));
        }
        boolean isPartitionLogRecord = Objects.equals(logContext.getValueType(), ValueType.PARTITION);
        if (isPartitionLogRecord) {
            int valueLength;
            if (index == 0) {
                valueLength = ByteUtil.convertStreamToByte(record.getValueLength().getData());
            } else {
                valueLength = ByteUtil.convertStreamToShort(record.getValueLength().getData());
            }
            record.setValueStream(BrokerUtil.wrapField(is, FieldType.COMPACT_RECORD, valueLength / 2));
        } else {
            record.setValue(getLogValue(logContext));
        }
        record.setHeaderArrayCount(BrokerUtil.wrapField(is, FieldType.BYTE));
        return record;
    }

    private static Value getLogValue(LogContext logContext) throws IOException {
        return BaseLogValueService.getLogValue(logContext);
    }
}
