package service.broker;

import domain.Field;
import domain.Offset;
import domain.request.RequestHeaderV2;
import domain.request.body.BaseRequestBody;
import domain.response.body.BaseResponseBody;
import enums.ApiKey;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public interface BrokerService<T extends BaseRequestBody, R extends BaseResponseBody> {
    Map<ApiKey, BrokerService<?, ?>> STORE = new HashMap<>();

    /**
     * register a handler to local map
     */
    void registerHandler();

    /**
     * parse the byte stream into request body obj
     * @param bytes request byte stream
     * @param offset current offset
     * @return request body
     */
    T parseRequestBody(byte[] bytes, Offset offset);

    /**
     * load info from request obj to response obj
     * @param request request body
     * @return response body
     */
    R convertToResponseBody(T request);

    /**
     * flatten response obj to list of fields
     * @param responseBody response body
     * @param requestHeader request header V2
     * @return list of field items
     */
    LinkedList<Field> flattenResponse(R responseBody, RequestHeaderV2 requestHeader);
}
