package enums;

public enum ValueType {
    TOPIC((byte) 2),
    PARTITION((byte) 3),
    FEATURE_LEVEL((byte) 12);

    private final byte type;

    ValueType(byte type) {
        this.type = type;
    }

    public static ValueType ofType(byte b) {
        for (ValueType v : values()) {
            if (b == v.type) {
                return v;
            }
        }
        throw new RuntimeException("invalid value type");
    }

    public byte getType() {
        return type;
    }
}
