package domain.metadata.record;

import domain.Field;
import domain.metadata.Value;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * - Partition ID
 * - Topic UUID
 * - Length of replica array
 * - Replica Array
 * - Length of In Sync Replica array
 * - In Sync Replica Array
 * - Length of Removing Replicas array
 * - Length of Adding Replicas array
 * - Leader
 * - Leader Epoch
 * - Partition Epoch
 * - Length of Directories array
 * - Directories Array
 */

public class PartitionValue extends Value implements Serializable {
    @Serial
    private static final long serialVersionUID = 1920980239L;
    private Field recordId;
    private Field partitionId;
    private Field topicUUID;
    private Field replicaArrayLength;
    private LinkedList<Field> replicaArray;
    private Field inSyncReplicaArrayLength;
    private LinkedList<Field> inSyncReplicaArray;
    private Field removeReplicaArrayLength;
    private Field addReplicaArrayLength;
    private Field leader;
    private Field leaderEpoch;
    private Field partitionEpoch;
    private Field directoryArrayLength;
    private LinkedList<Field> directoryArray;

    public Field getRecordId() {
        return recordId;
    }

    public void setRecordId(Field recordId) {
        this.recordId = recordId;
    }

    public Field getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(Field partitionId) {
        this.partitionId = partitionId;
    }

    public Field getTopicUUID() {
        return topicUUID;
    }

    public void setTopicUUID(Field topicUUID) {
        this.topicUUID = topicUUID;
    }

    public Field getReplicaArrayLength() {
        return replicaArrayLength;
    }

    public void setReplicaArrayLength(Field replicaArrayLength) {
        this.replicaArrayLength = replicaArrayLength;
    }

    public LinkedList<Field> getReplicaArray() {
        return replicaArray;
    }

    public void setReplicaArray(LinkedList<Field> replicaArray) {
        this.replicaArray = replicaArray;
    }

    public Field getInSyncReplicaArrayLength() {
        return inSyncReplicaArrayLength;
    }

    public void setInSyncReplicaArrayLength(Field inSyncReplicaArrayLength) {
        this.inSyncReplicaArrayLength = inSyncReplicaArrayLength;
    }

    public LinkedList<Field> getInSyncReplicaArray() {
        return inSyncReplicaArray;
    }

    public void setInSyncReplicaArray(LinkedList<Field> inSyncReplicaArray) {
        this.inSyncReplicaArray = inSyncReplicaArray;
    }

    public Field getRemoveReplicaArrayLength() {
        return removeReplicaArrayLength;
    }

    public void setRemoveReplicaArrayLength(Field removeReplicaArrayLength) {
        this.removeReplicaArrayLength = removeReplicaArrayLength;
    }

    public Field getAddReplicaArrayLength() {
        return addReplicaArrayLength;
    }

    public void setAddReplicaArrayLength(Field addReplicaArrayLength) {
        this.addReplicaArrayLength = addReplicaArrayLength;
    }

    public Field getLeader() {
        return leader;
    }

    public void setLeader(Field leader) {
        this.leader = leader;
    }

    public Field getLeaderEpoch() {
        return leaderEpoch;
    }

    public void setLeaderEpoch(Field leaderEpoch) {
        this.leaderEpoch = leaderEpoch;
    }

    public Field getPartitionEpoch() {
        return partitionEpoch;
    }

    public void setPartitionEpoch(Field partitionEpoch) {
        this.partitionEpoch = partitionEpoch;
    }

    public Field getDirectoryArrayLength() {
        return directoryArrayLength;
    }

    public void setDirectoryArrayLength(Field directoryArrayLength) {
        this.directoryArrayLength = directoryArrayLength;
    }

    public LinkedList<Field> getDirectoryArray() {
        return directoryArray;
    }

    public void setDirectoryArray(LinkedList<Field> directoryArray) {
        this.directoryArray = directoryArray;
    }
}
