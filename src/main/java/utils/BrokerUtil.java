package utils;

import domain.Field;
import domain.Offset;
import enums.ApiKey;
import enums.FieldType;
import service.broker.BrokerService;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.IntStream;

public class BrokerUtil {

    public static Field wrapField(byte[] data, Offset offset, FieldType fieldType) {
        return wrapField(data, offset, fieldType, fieldType.getByteSize());
    }

    public static Field wrapField(byte[] data, Offset offset, FieldType fieldType, int length) {
        byte[] fieldData = ByteUtil.copyOfRangeByOffset(data, offset, length);
        return new Field(fieldData, fieldType, length);
    }

    public static Field wrapField(byte[] data, FieldType fieldType) {
        return wrapField(data, fieldType, fieldType.getByteSize());
    }

    public static Field wrapField(byte[] data, FieldType fieldType, int length) {
        return new Field(deepCopy(data), fieldType, length);
    }

    public static Field wrapField(FileInputStream fileIS, FieldType fieldType) throws IOException {
        return wrapField(fileIS, fieldType, fieldType.getByteSize());
    }

    public static Field wrapField(FileInputStream fileIS, FieldType fieldType, int length) throws IOException {
        return wrapField(fileIS.readNBytes(length), fieldType, length);
    }

    public static Field wrapField(ByteArrayInputStream byteArrayIS, FieldType fieldType) throws IOException {
        return wrapField(byteArrayIS, fieldType, fieldType.getByteSize());
    }

    public static Field wrapField(ByteArrayInputStream byteArrayIS, FieldType fieldType, int length) throws IOException {
        return wrapField(byteArrayIS.readNBytes(length), fieldType, length);
    }

    private static byte[] deepCopy(byte[] input) {
        int l = input.length;
        byte[] output = new byte[l];
        for (int i=0; i<l; i++) {
            output[i] = input[i];;
        }
        return output;
    }

    public static BrokerService getInstance(Short key) {
        ApiKey apiKey = ApiKey.fromKey(key);
        if (Objects.isNull(apiKey)) {
            throw new RuntimeException("Invalid API KEY");
        }
        BrokerService<?, ?> handler = BrokerService.STORE.getOrDefault(apiKey, null);
        if (Objects.isNull(handler)) {
            throw new RuntimeException("API not registered yet");
        }
        return handler;
    }

    public static void handle(Socket clientSocket, LinkedList<Field> fieldLinkedList) throws IOException {
        int messageSize = getMessageSizeFromFieldList(fieldLinkedList);
        byte[] messageSizeStream = ByteUtil.convertIntToStream(messageSize);
        fieldLinkedList.addFirst(wrapField(messageSizeStream, FieldType.INTEGER));
        byte[] bytes = convertToStream(fieldLinkedList);
        SocketUtil.writeThenFlushSocket(clientSocket, bytes);
    }

    private static int getMessageSizeFromFieldList(LinkedList<Field> fieldLinkedList) {
        return fieldLinkedList.stream()
                .flatMapToInt(e -> IntStream.builder().add(e.getSize()).build())
                .sum();
    }

    private static byte[] convertToStream(LinkedList<Field> fieldLinkedList) {
        int allocationSize = getMessageSizeFromFieldList(fieldLinkedList);
        ByteBuffer byteBuffer =  ByteBuffer.allocate(allocationSize).order(ByteOrder.BIG_ENDIAN);
        fieldLinkedList.forEach(f -> byteBuffer.put(f.getData()));
        return byteBuffer.array();
    }
}
