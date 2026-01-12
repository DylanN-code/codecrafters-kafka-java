package domain.request.body;

import domain.Field;

public class ApiVersionsRequestBodyV4 extends BaseRequestBody {
    private Field clientIdLength;
    private Field clientId;
    private Field clientSoftwareVersionLength;
    private Field clientSoftwareVersion;
    private Field tagBuffer;

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

    public Field getClientSoftwareVersionLength() {
        return clientSoftwareVersionLength;
    }

    public void setClientSoftwareVersionLength(Field clientSoftwareVersionLength) {
        this.clientSoftwareVersionLength = clientSoftwareVersionLength;
    }

    public Field getClientSoftwareVersion() {
        return clientSoftwareVersion;
    }

    public void setClientSoftwareVersion(Field clientSoftwareVersion) {
        this.clientSoftwareVersion = clientSoftwareVersion;
    }

    public Field getTagBuffer() {
        return tagBuffer;
    }

    public void setTagBuffer(Field tagBuffer) {
        this.tagBuffer = tagBuffer;
    }
}
