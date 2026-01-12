package domain;

public class Offset {
    private int offset;

    public int addByThenReturn(int increment) {
        setOffset(offset + increment);
        return offset;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
