package service.log.impl;

import domain.metadata.record.FeatureLevelRecord;
import enums.FieldType;
import enums.ValueType;
import service.log.BaseLogValueService;
import service.log.LogValueService;
import utils.BrokerUtil;
import utils.ByteUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class FeatureLevelValueImpl extends BaseLogValueService<FeatureLevelRecord> {
    @Override
    public FeatureLevelRecord createValue() {
        return new FeatureLevelRecord();
    }

    @Override
    public void load(ByteArrayInputStream is, FeatureLevelRecord value) throws IOException {
        value.setNameLength(BrokerUtil.wrapField(is, FieldType.BYTE));
        int nameLength = ByteUtil.convertStreamToByte(value.getNameLength().getData()) - FieldType.BYTE.getByteSize();
        if (nameLength == -1) {
            value.setName(null);
        } else {
            value.setName(BrokerUtil.wrapField(is, FieldType.STRING, nameLength));
        }
        value.setFeatureLevel(BrokerUtil.wrapField(is, FieldType.SHORT));
    }

    @Override
    public void map(FeatureLevelRecord value) {
    }

    @Override
    public void registerHandler() {
        LogValueService.STORE.put(ValueType.FEATURE_LEVEL, this);
    }
}
