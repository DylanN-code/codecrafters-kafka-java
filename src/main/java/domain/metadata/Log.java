package domain.metadata;

import java.util.LinkedList;

public class Log {
    private int totalByteRead;
    private LinkedList<Batch> batches;

    public Log() {
        this.batches = new LinkedList<>();
    }

    public void addBatch(Batch batch) {
        batches.add(batch);
    }

    public int getTotalByteRead() {
        return totalByteRead;
    }

    public void setTotalByteRead(int totalByteRead) {
        this.totalByteRead = totalByteRead;
    }

    public LinkedList<Batch> getBatches() {
        return batches;
    }

    public void setBatches(LinkedList<Batch> batches) {
        this.batches = batches;
    }
}
