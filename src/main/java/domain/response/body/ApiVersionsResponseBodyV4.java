package domain.response.body;

import domain.Field;

import java.util.List;

public class ApiVersionsResponseBodyV4 extends BaseResponseBody {
    private Field errorCode;
    private Field apiKeyCounts;
    private List<Item> apiVersionList;
    private Field throttleTimeMS;
    private Field taggedFieldSize;

    public Field getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Field errorCode) {
        this.errorCode = errorCode;
    }

    public Field getApiKeyCounts() {
        return apiKeyCounts;
    }

    public void setApiKeyCounts(Field apiKeyCounts) {
        this.apiKeyCounts = apiKeyCounts;
    }

    public List<Item> getApiVersionList() {
        return apiVersionList;
    }

    public void setApiVersionList(List<Item> apiVersionList) {
        this.apiVersionList = apiVersionList;
    }

    public Field getThrottleTimeMS() {
        return throttleTimeMS;
    }

    public void setThrottleTimeMS(Field throttleTimeMS) {
        this.throttleTimeMS = throttleTimeMS;
    }

    public Field getTaggedFieldSize() {
        return taggedFieldSize;
    }

    public void setTaggedFieldSize(Field taggedFieldSize) {
        this.taggedFieldSize = taggedFieldSize;
    }

    public static class Item {
        private Field apiKey;
        private Field apiMinVersion;
        private Field apiMaxVersion;
        private Field taggedFieldSize;

        public Field getApiKey() {
            return apiKey;
        }

        public void setApiKey(Field apiKey) {
            this.apiKey = apiKey;
        }

        public Field getApiMinVersion() {
            return apiMinVersion;
        }

        public void setApiMinVersion(Field apiMinVersion) {
            this.apiMinVersion = apiMinVersion;
        }

        public Field getApiMaxVersion() {
            return apiMaxVersion;
        }

        public void setApiMaxVersion(Field apiMaxVersion) {
            this.apiMaxVersion = apiMaxVersion;
        }

        public Field getTaggedFieldSize() {
            return taggedFieldSize;
        }

        public void setTaggedFieldSize(Field taggedFieldSize) {
            this.taggedFieldSize = taggedFieldSize;
        }
    }
}
