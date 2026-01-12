package domain.metadata;

import domain.Field;

import java.io.Serial;
import java.io.Serializable;

/**
 * Value (Topic CompactRecord)
 * - Frame Version
 * - Type
 * - Version
 * - Tagged Fields Count
 */

public class Value implements Serializable {
    @Serial
    private static final long serialVersionUID = 13097439202L;
    private Field frameVersion;
    private Field type;
    private Field version;
    private Field taggedFieldCount;

    public Field getFrameVersion() {
        return frameVersion;
    }

    public void setFrameVersion(Field frameVersion) {
        this.frameVersion = frameVersion;
    }

    public Field getType() {
        return type;
    }

    public void setType(Field type) {
        this.type = type;
    }

    public Field getVersion() {
        return version;
    }

    public void setVersion(Field version) {
        this.version = version;
    }

    public Field getTaggedFieldCount() {
        return taggedFieldCount;
    }

    public void setTaggedFieldCount(Field taggedFieldCount) {
        this.taggedFieldCount = taggedFieldCount;
    }
}
