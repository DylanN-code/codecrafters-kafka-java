package domain.metadata;

import domain.Field;

/**
 * CompactRecord #1
 * - Length
 * - Attributes
 * - Timestamp Delta
 * - Offset Delta
 * - Key Length
 * - Key
 * - Value Length
 * → Value (Topic CompactRecord)
 * - Headers Array Count
 */

public class Record {
    private Field length;
    private Field attributes;
    private Field timestampDelta;
    private Field offsetDelta;
    private Field keyLength;
    private Field key;
    private Field valueLength;
    private Value value;
    private Field valueStream; // raw value as byte stream
    private Field headerArrayCount;

    public Field getLength() {
        return length;
    }

    public void setLength(Field length) {
        this.length = length;
    }

    public Field getAttributes() {
        return attributes;
    }

    public void setAttributes(Field attributes) {
        this.attributes = attributes;
    }

    public Field getTimestampDelta() {
        return timestampDelta;
    }

    public void setTimestampDelta(Field timestampDelta) {
        this.timestampDelta = timestampDelta;
    }

    public Field getOffsetDelta() {
        return offsetDelta;
    }

    public void setOffsetDelta(Field offsetDelta) {
        this.offsetDelta = offsetDelta;
    }

    public Field getKeyLength() {
        return keyLength;
    }

    public void setKeyLength(Field keyLength) {
        this.keyLength = keyLength;
    }

    public Field getKey() {
        return key;
    }

    public void setKey(Field key) {
        this.key = key;
    }

    public Field getValueLength() {
        return valueLength;
    }

    public void setValueLength(Field valueLength) {
        this.valueLength = valueLength;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Field getValueStream() {
        return valueStream;
    }

    public void setValueStream(Field valueStream) {
        this.valueStream = valueStream;
    }

    public Field getHeaderArrayCount() {
        return headerArrayCount;
    }

    public void setHeaderArrayCount(Field headerArrayCount) {
        this.headerArrayCount = headerArrayCount;
    }
}
