package utils;

import domain.Offset;
import enums.FieldType;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteUtil {

    public static byte convertStreamToByte(byte[] input) {
        return getByteBuffer(input, FieldType.BYTE.getByteSize()).get();
    }

    public static short convertStreamToShort(byte[] input) {
        return getByteBuffer(input, FieldType.SHORT.getByteSize()).getShort();
    }

    public static int convertStreamToInt(byte[] input) {
        return getByteBuffer(input, FieldType.INTEGER.getByteSize()).getInt();
    }

    public static String convertStreamToString(byte[] input) {
        return new String(input, StandardCharsets.UTF_8);
    }

    public static byte[] convertLongToStream(long input) {
        return getByteBuffer(FieldType.BIG_INTEGER.getByteSize()).putLong(input).clear().array();
    }

    public static byte[] convertIntToStream(int input) {
        return getByteBuffer(FieldType.INTEGER.getByteSize()).putInt(input).clear().array();
    }

    public static byte[] convertShortToStream(short input) {
        return getByteBuffer(FieldType.SHORT.getByteSize()).putShort(input).clear().array();
    }

    public static byte[] convertByteToStream(byte input) {
        return getByteBuffer(FieldType.BYTE.getByteSize()).put(input).clear().array();
    }

    public static byte[] copyOfRangeByOffset(byte[] input, Offset offset, int length) {
        return Arrays.copyOfRange(input, offset.getOffset(), offset.addByThenReturn(length));
    }

    private static ByteBuffer getByteBuffer(byte[] input, int length) {
        assert (input.length == length);
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        byteBuffer.put(input);
        byteBuffer.clear();
        return byteBuffer;
    }

    private static ByteBuffer getByteBuffer(int length) {
        return ByteBuffer.allocate(length);
    }

    public static <T extends Serializable> T deepCopy(T input) {
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
        ) {
            objectOutputStream.writeObject(input);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            @SuppressWarnings("unchecked")
            T output = (T) objectInputStream.readObject();
            return output;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("failed to deep copy object due to error=" + e.getMessage());
        }
        return null;
    }
}
