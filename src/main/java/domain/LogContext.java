package domain;

import enums.ValueType;

import java.io.ByteArrayInputStream;

public class LogContext {
    private String filePath;
    private ByteArrayInputStream is;
    private ValueType valueType;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ByteArrayInputStream getIs() {
        return is;
    }

    public void setIs(ByteArrayInputStream is) {
        this.is = is;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }
}
