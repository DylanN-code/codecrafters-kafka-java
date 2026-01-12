package domain;

import enums.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Field implements Serializable {
    @Serial
    private static final long serialVersionUID = 130740929309L;
    private byte[] data;
    private FieldType fieldType;
    private int size;

    public Field(byte[] data, FieldType fieldType, int size) {
        this.data = data;
        this.fieldType = fieldType;
        this.size = size;
    }

    public static boolean equalsByData(Field f1, Field f2) {
        if (Objects.isNull(f1) || Objects.isNull(f2)) {
            return false;
        }
        return Arrays.equals(f1.getData(), f2.getData());
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Field field)) return false;
        return Arrays.equals(getData(), field.getData());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getData());
    }

    @Override
    public String toString() {
        return "Field{" +
                "data=" + Arrays.toString(data) +
                ", fieldType=" + fieldType +
                ", size=" + size +
                '}';
    }
}
