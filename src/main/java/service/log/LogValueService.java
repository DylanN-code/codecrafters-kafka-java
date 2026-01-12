package service.log;

import domain.metadata.Value;
import enums.ValueType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface LogValueService<T extends Value> {
    Map<ValueType, LogValueService> STORE = new HashMap<>();

    T createValue();

    void load(ByteArrayInputStream is, T value) throws IOException;

    void map(T value);

    void registerHandler();
}
