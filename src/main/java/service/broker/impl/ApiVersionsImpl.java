package service.broker.impl;

import domain.Field;
import domain.Offset;
import domain.request.RequestHeaderV2;
import domain.request.body.ApiVersionsRequestBodyV4;
import domain.response.body.ApiVersionsResponseBodyV4;
import enums.ApiKey;
import enums.FieldType;
import service.broker.BaseBrokerService;
import utils.BrokerUtil;
import utils.ByteUtil;
import utils.FieldUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ApiVersionsImpl extends BaseBrokerService<ApiVersionsRequestBodyV4, ApiVersionsResponseBodyV4> {
    private static final List<ApiKey> SUPPORTED_APIS = List.of(
            ApiKey.API_VERSIONS,
            ApiKey.DESCRIBE_TOPIC_PARTITIONS,
            ApiKey.FETCH,
            ApiKey.PRODUCE
    );

    @Override
    public void registerHandler() {
        STORE.put(ApiKey.API_VERSIONS, this);
    }

    @Override
    public ApiVersionsRequestBodyV4 parseRequestBody(byte[] bytes, Offset offset) {
        ApiVersionsRequestBodyV4 requestBodyV4 = new ApiVersionsRequestBodyV4();
        Field clientIdLength = BrokerUtil.wrapField(bytes, offset, FieldType.BYTE);
        int clientIdLengthVal = ByteUtil.convertStreamToByte(clientIdLength.getData()) - FieldType.BYTE.getByteSize();
        Field clientId = BrokerUtil.wrapField(bytes, offset, FieldType.STRING, clientIdLengthVal);
        Field clientSoftwareVersionLength = BrokerUtil.wrapField(bytes, offset, FieldType.BYTE);
        byte clientSoftwareVersionLengthVal = ByteUtil.convertStreamToByte(clientSoftwareVersionLength.getData());
        Field clientSoftwareVersion = BrokerUtil.wrapField(bytes, offset, FieldType.STRING, clientSoftwareVersionLengthVal);
        Field tagBuffer = BrokerUtil.wrapField(bytes, offset, FieldType.BYTE);
        requestBodyV4.setClientIdLength(clientIdLength);
        requestBodyV4.setClientId(clientId);
        requestBodyV4.setClientSoftwareVersionLength(clientSoftwareVersionLength);
        requestBodyV4.setClientSoftwareVersion(clientSoftwareVersion);
        requestBodyV4.setTagBuffer(tagBuffer);
        return requestBodyV4;
    }

    @Override
    public ApiVersionsResponseBodyV4 convertToResponseBody(ApiVersionsRequestBodyV4 request) {
        ApiVersionsResponseBodyV4 responseBodyV4 = new ApiVersionsResponseBodyV4();
        responseBodyV4.setErrorCode(FieldUtil.getErrorCodeNone());
        responseBodyV4.setApiKeyCounts(FieldUtil.getApiKeyCount((byte) (SUPPORTED_APIS.size() + FieldType.BYTE.getByteSize())));

        List<ApiVersionsResponseBodyV4.Item> itemList = new ArrayList<>();
        for (ApiKey apiKey: SUPPORTED_APIS) {
            ApiVersionsResponseBodyV4.Item item = new ApiVersionsResponseBodyV4.Item();
            item.setApiKey(BrokerUtil.wrapField(ByteUtil.convertShortToStream(apiKey.getKey()), FieldType.SHORT));
            item.setApiMinVersion(BrokerUtil.wrapField(ByteUtil.convertShortToStream(apiKey.getMinVersion()), FieldType.SHORT));
            item.setApiMaxVersion(BrokerUtil.wrapField(ByteUtil.convertShortToStream(apiKey.getMaxVersion()), FieldType.SHORT));
            item.setTaggedFieldSize(FieldUtil.getDefaultTaggedFieldSize());
            itemList.add(item);
        }
        responseBodyV4.setApiVersionList(itemList);

        responseBodyV4.setThrottleTimeMS(FieldUtil.getThrottleTimeMS());
        responseBodyV4.setTaggedFieldSize(FieldUtil.getDefaultTaggedFieldSize());
        return responseBodyV4;
    }

    @Override
    public LinkedList<Field> flattenResponse(ApiVersionsResponseBodyV4 responseBody, RequestHeaderV2 requestHeader) {
        LinkedList<Field> fieldLinkedList = new LinkedList<>();
        fieldLinkedList.add(requestHeader.getCorrelationId());
        fieldLinkedList.add(responseBody.getErrorCode());
        fieldLinkedList.add(responseBody.getApiKeyCounts());
        for (ApiVersionsResponseBodyV4.Item item: responseBody.getApiVersionList()) {
            fieldLinkedList.add(item.getApiKey());
            fieldLinkedList.add(item.getApiMinVersion());
            fieldLinkedList.add(item.getApiMaxVersion());
            fieldLinkedList.add(item.getTaggedFieldSize());
        }
        fieldLinkedList.add(responseBody.getThrottleTimeMS());
        fieldLinkedList.add(responseBody.getTaggedFieldSize());
        return fieldLinkedList;
    }
}
