package domain.request;

import domain.Field;

public class RequestHeaderV2 {
    private Field requestApiKey;
    private Field requestApiVersion;
    private Field correlationId;
    private Field clientIdLength;
    private Field clientId;
    private Field tagBuffer;

    public Field getRequestApiKey() {
        return requestApiKey;
    }

    public void setRequestApiKey(Field requestApiKey) {
        this.requestApiKey = requestApiKey;
    }

    public Field getRequestApiVersion() {
        return requestApiVersion;
    }

    public void setRequestApiVersion(Field requestApiVersion) {
        this.requestApiVersion = requestApiVersion;
    }

    public Field getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(Field correlationId) {
        this.correlationId = correlationId;
    }

    public Field getClientIdLength() {
        return clientIdLength;
    }

    public void setClientIdLength(Field clientIdLength) {
        this.clientIdLength = clientIdLength;
    }

    public Field getClientId() {
        return clientId;
    }

    public void setClientId(Field clientId) {
        this.clientId = clientId;
    }

    public Field getTagBuffer() {
        return tagBuffer;
    }

    public void setTagBuffer(Field tagBuffer) {
        this.tagBuffer = tagBuffer;
    }
}
