package enums;

import java.util.Objects;

public enum ApiKey {
    PRODUCE("Produce", Short.valueOf("0"), Short.valueOf("0"), Short.valueOf("11")),
    FETCH("Fetch", Short.valueOf("1"), Short.valueOf("0"), Short.valueOf("16")),
    LIST_OFFSETS("ListOffsets", Short.valueOf("2"), Short.valueOf("0"), Short.valueOf("0")),
    METADATA("Metadata", Short.valueOf("3"), Short.valueOf("0"), Short.valueOf("0")),
    OFFSET_COMMIT("OffsetCommit", Short.valueOf("4"), Short.valueOf("0"), Short.valueOf("0")),
    OFFSET_FETCH("OffsetFetch", Short.valueOf("5"), Short.valueOf("0"), Short.valueOf("0")),
    FIND_COORDINATOR("FindCoordinator", Short.valueOf("6"), Short.valueOf("0"), Short.valueOf("0")),
    JOIN_GROUP("JoinGroup", Short.valueOf("11"), Short.valueOf("0"), Short.valueOf("0")),
    HEARTBEAT("Heartbeat", Short.valueOf("12"), Short.valueOf("0"), Short.valueOf("0")),
    LEAVE_GROUP("LeaveGroup", Short.valueOf("13"), Short.valueOf("0"), Short.valueOf("0")),
    SYNC_GROUP("SyncGroup", Short.valueOf("14"), Short.valueOf("0"), Short.valueOf("0")),
    DESCRIBE_GROUPS("DescribeGroups", Short.valueOf("15"), Short.valueOf("0"), Short.valueOf("0")),
    LIST_GROUPS("ListGroups", Short.valueOf("16"), Short.valueOf("0"), Short.valueOf("0")),
    SASL_HANDSHAKE("SaslHandshake", Short.valueOf("17"), Short.valueOf("0"), Short.valueOf("0")),
    API_VERSIONS("ApiVersions", Short.valueOf("18"), Short.valueOf("0"), Short.valueOf("4")),
    CREATE_TOPICS("CreateTopics", Short.valueOf("19"), Short.valueOf("0"), Short.valueOf("0")),
    DELETE_TOPICS("DeleteTopics", Short.valueOf("20"), Short.valueOf("0"), Short.valueOf("0")),
    DELETE_RECORDS("DeleteRecords", Short.valueOf("21"), Short.valueOf("0"), Short.valueOf("0")),
    INIT_PRODUCER_ID("InitProducerId", Short.valueOf("22"), Short.valueOf("0"), Short.valueOf("0")),
    OFFSET_FOR_LEADER_EPOCH("OffsetForLeaderEpoch", Short.valueOf("23"), Short.valueOf("0"), Short.valueOf("0")),
    ADD_PARTITIONS_TO_TXN("AddPartitionsToTxn", Short.valueOf("24"), Short.valueOf("0"), Short.valueOf("0")),
    ADD_OFFSETS_TO_TXN("AddOffsetsToTxn", Short.valueOf("25"), Short.valueOf("0"), Short.valueOf("0")),
    END_TXN("EndTxn", Short.valueOf("26"), Short.valueOf("0"), Short.valueOf("0")),
    WRITE_TXN_MARKERS("WriteTxnMarkers", Short.valueOf("27"), Short.valueOf("0"), Short.valueOf("0")),
    TXN_OFFSET_COMMIT("TxnOffsetCommit", Short.valueOf("28"), Short.valueOf("0"), Short.valueOf("0")),
    DESCRIBE_ACLS("DescribeAcls", Short.valueOf("29"), Short.valueOf("0"), Short.valueOf("0")),
    CREATE_ACLS("CreateAcls", Short.valueOf("30"), Short.valueOf("0"), Short.valueOf("0")),
    DELETE_ACLS("DeleteAcls", Short.valueOf("31"), Short.valueOf("0"), Short.valueOf("0")),
    DESCRIBE_CONFIGS("DescribeConfigs", Short.valueOf("32"), Short.valueOf("0"), Short.valueOf("0")),
    ALTER_CONFIGS("AlterConfigs", Short.valueOf("33"), Short.valueOf("0"), Short.valueOf("0")),
    ALTER_REPLICA_LOG_DIRS("AlterReplicaLogDirs", Short.valueOf("34"), Short.valueOf("0"), Short.valueOf("0")),
    DESCRIBE_LOG_DIRS("DescribeLogDirs", Short.valueOf("35"), Short.valueOf("0"), Short.valueOf("0")),
    SASL_AUTHENTICATE("SaslAuthenticate", Short.valueOf("36"), Short.valueOf("0"), Short.valueOf("0")),
    CREATE_PARTITIONS("CreatePartitions", Short.valueOf("37"), Short.valueOf("0"), Short.valueOf("0")),
    CREATE_DELEGATION_TOKEN("CreateDelegationToken", Short.valueOf("38"), Short.valueOf("0"), Short.valueOf("0")),
    RENEW_DELEGATION_TOKEN("RenewDelegationToken", Short.valueOf("39"), Short.valueOf("0"), Short.valueOf("0")),
    EXPIRE_DELEGATION_TOKEN("ExpireDelegationToken", Short.valueOf("40"), Short.valueOf("0"), Short.valueOf("0")),
    DESCRIBE_DELEGATION_TOKEN("DescribeDelegationToken", Short.valueOf("41"), Short.valueOf("0"), Short.valueOf("0")),
    DELETE_GROUPS("DeleteGroups", Short.valueOf("42"), Short.valueOf("0"), Short.valueOf("0")),
    ELECT_LEADERS("ElectLeaders", Short.valueOf("43"), Short.valueOf("0"), Short.valueOf("0")),
    INCREMENTAL_ALTER_CONFIGS("IncrementalAlterConfigs", Short.valueOf("44"), Short.valueOf("0"), Short.valueOf("0")),
    ALTER_PARTITION_REASSIGNMENTS("AlterPartitionReassignments", Short.valueOf("45"), Short.valueOf("0"), Short.valueOf("0")),
    LIST_PARTITION_REASSIGNMENTS("ListPartitionReassignments", Short.valueOf("46"), Short.valueOf("0"), Short.valueOf("0")),
    OFFSET_DELETE("OffsetDelete", Short.valueOf("47"), Short.valueOf("0"), Short.valueOf("0")),
    DESCRIBE_CLIENT_QUOTAS("DescribeClientQuotas", Short.valueOf("48"), Short.valueOf("0"), Short.valueOf("0")),
    ALTER_CLIENT_QUOTAS("AlterClientQuotas", Short.valueOf("49"), Short.valueOf("0"), Short.valueOf("0")),
    DESCRIBE_USER_SCRAM_CREDENTIALS("DescribeUserScramCredentials", Short.valueOf("50"), Short.valueOf("0"), Short.valueOf("0")),
    ALTER_USER_SCRAM_CREDENTIALS("AlterUserScramCredentials", Short.valueOf("51"), Short.valueOf("0"), Short.valueOf("0")),
    DESCRIBE_QUORUM("DescribeQuorum", Short.valueOf("55"), Short.valueOf("0"), Short.valueOf("0")),
    UPDATE_FEATURES("UpdateFeatures", Short.valueOf("57"), Short.valueOf("0"), Short.valueOf("0")),
    DESCRIBE_CLUSTER("DescribeCluster", Short.valueOf("60"), Short.valueOf("0"), Short.valueOf("0")),
    DESCRIBE_PRODUCERS("DescribeProducers", Short.valueOf("61"), Short.valueOf("0"), Short.valueOf("0")),
    UNREGISTER_BROKER("UnregisterBroker", Short.valueOf("64"), Short.valueOf("0"), Short.valueOf("0")),
    DESCRIBE_TRANSACTIONS("DescribeTransactions", Short.valueOf("65"), Short.valueOf("0"), Short.valueOf("0")),
    LIST_TRANSACTIONS("ListTransactions", Short.valueOf("66"), Short.valueOf("0"), Short.valueOf("0")),
    CONSUMER_GROUP_HEARTBEAT("ConsumerGroupHeartbeat", Short.valueOf("68"), Short.valueOf("0"), Short.valueOf("0")),
    CONSUMER_GROUP_DESCRIBE("ConsumerGroupDescribe", Short.valueOf("69"), Short.valueOf("0"), Short.valueOf("0")),
    GET_TELEMETRY_SUBSCRIPTIONS("GetTelemetrySubscriptions", Short.valueOf("71"), Short.valueOf("0"), Short.valueOf("0")),
    PUSH_TELEMETRY("PushTelemetry", Short.valueOf("72"), Short.valueOf("0"), Short.valueOf("0")),
    LIST_CLIENT_METRICS_RESOURCES("ListClientMetricsResources", Short.valueOf("74"), Short.valueOf("0"), Short.valueOf("0")),
    DESCRIBE_TOPIC_PARTITIONS("DescribeTopicPartitions", Short.valueOf("75"), Short.valueOf("0"), Short.valueOf("10")),
    ADD_RAFT_VOTER("AddRaftVoter", Short.valueOf("80"), Short.valueOf("0"), Short.valueOf("0")),
    REMOVE_RAFT_VOTER("RemoveRaftVoter", Short.valueOf("81"), Short.valueOf("0"), Short.valueOf("0"));

    private final String name;
    private final Short key;
    private final Short minVersion;
    private final Short maxVersion;

    ApiKey(String name, Short key, Short minVersion, Short maxVersion) {
        this.name = name;
        this.key = key;
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
    }

    public static ApiKey fromKey(Short key) {
        for (ApiKey apiKey : values()) {
            if (Objects.equals(key, apiKey.getKey())) {
                return apiKey;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Short getKey() {
        return key;
    }

    public Short getMinVersion() {
        return minVersion;
    }

    public Short getMaxVersion() {
        return maxVersion;
    }
}
