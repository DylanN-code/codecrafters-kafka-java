package service.broker;

import constants.Constant;
import domain.Field;
import domain.Offset;
import domain.request.RequestHeaderV2;
import domain.request.body.BaseRequestBody;
import domain.response.body.BaseResponseBody;
import enums.FieldType;
import utils.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.time.Duration;
import java.util.LinkedList;
import java.util.Objects;

public abstract class BaseBrokerService<T extends BaseRequestBody, R extends BaseResponseBody> implements BrokerService<T, R> {

    public static void handle(Socket clientSocket) {
        try {
            while (!clientSocket.isClosed()) {
                LinkedList<Field> fieldLinkedList = getFieldLinkedList(clientSocket);
                if (Objects.isNull(fieldLinkedList)) {
                    continue;
                }
                BrokerUtil.handle(clientSocket, fieldLinkedList);
            }
            Thread.sleep(Duration.ofMillis(Constant.GRACE_PERIOD_BETWEEN_REQUEST_HANDLING));
        } catch (Exception e) {
            System.out.println("Failed to handle client socket, error=" + e);
        } finally {
            try {
                SocketUtil.closeSocket(clientSocket);
            } catch (IOException e) {
                System.out.println("Failed to close client socket, error=" + e);
            }
        }
    }

    private static LinkedList<Field> getFieldLinkedList(Socket clientSocket) throws IOException {
        InputStream is = clientSocket.getInputStream();
        byte[] messageSizeArr = is.readNBytes(FieldType.INTEGER.getByteSize());
        int messageSize = ByteUtil.convertStreamToInt(messageSizeArr);
        if (messageSize == 0) {
            return null;
        }
        byte[] data = is.readNBytes(messageSize);

        Offset offset = new Offset();
        Field requestApiKey = BrokerUtil.wrapField(data, offset, FieldType.SHORT);
        Field requestApiVersion = BrokerUtil.wrapField(data, offset, FieldType.SHORT);
        Field correlationId = BrokerUtil.wrapField(data, offset, FieldType.INTEGER);
        Field clientIdLength = BrokerUtil.wrapField(data, offset, FieldType.SHORT);
        short clientIdLengthVal = ByteUtil.convertStreamToShort(clientIdLength.getData());
        Field clientId = BrokerUtil.wrapField(data, offset, FieldType.STRING, clientIdLengthVal);
        Field tagBuffer = BrokerUtil.wrapField(data, offset, FieldType.BYTE);

        RequestHeaderV2 requestHeader = new RequestHeaderV2();
        requestHeader.setRequestApiKey(requestApiKey);
        requestHeader.setRequestApiVersion(requestApiVersion);
        requestHeader.setCorrelationId(correlationId);
        requestHeader.setClientIdLength(clientIdLength);
        requestHeader.setClientId(clientId);
        requestHeader.setTagBuffer(tagBuffer);

        short apiVersionVal = ByteUtil.convertStreamToShort(requestApiVersion.getData());
        if (!VersionUtil.isValidApiVersion(apiVersionVal)) {
            LinkedList<Field> fieldLinkedList = new LinkedList<>();
            fieldLinkedList.add(requestHeader.getCorrelationId());
            fieldLinkedList.add(FieldUtil.getUnSupportedVersion());
            return fieldLinkedList;
        }

        short requestApiKeyVal = ByteUtil.convertStreamToShort(requestApiKey.getData());
        BrokerService handler = BrokerUtil.getInstance(requestApiKeyVal);
        BaseRequestBody requestBody = handler.parseRequestBody(data, offset);
        BaseResponseBody responseBody = handler.convertToResponseBody(requestBody);
        return handler.flattenResponse(responseBody, requestHeader);
    }
}
