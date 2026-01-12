package utils;

import constants.Constant;
import domain.LogContext;
import domain.metadata.Log;
import enums.ValueType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class FileUtil {

    public static void loadConfigs() {
        Properties props = new Properties();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(Constant.CONFIG_FILE_NAME);
        try {
            props.load(inputStream);
            for (Map.Entry<Object, Object> entry : props.entrySet()) {
                PropertyUtil.saveProperty((String) entry.getKey(), (String) entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("failed to load properties file, error=" + e.getMessage());
        }
    }

    public static void loadClusterMetadataLog() {
        String logFolderDefaultPath = PropertyUtil.getProperty(Constant.COMBINED_LOG_FOLDER_DEFAULT_PATH);
        Path clusterMetadataDefaultPath = Path.of(logFolderDefaultPath, Constant.CLUSTER_METADATA_LOG_FOLDER_NAME, Constant.FIRST_LOG_FILE_NAME);
        try {
            LogContext logContext = new LogContext();
            logContext.setFilePath(clusterMetadataDefaultPath.toString());
            Log log = LogUtil.getLog(logContext);
            if (Objects.nonNull(log)) {
                System.out.println("successful to load log size = " + log.getBatches().size() + " batches for cluster metadata.");
            }
        } catch (IOException e) {
            System.out.println("failed to load cluster metadata log due to error=" + e.getMessage());
        }
    }

    public static Log loadThenGetPartitionLog(String topicName, String partitionIndex) {
        String logFolderDefaultPath = PropertyUtil.getProperty(Constant.COMBINED_LOG_FOLDER_DEFAULT_PATH);
        String partitionDirectory = String.format("%s-%s", topicName, partitionIndex);
        Path partitionLogDefaultPath = Path.of(logFolderDefaultPath, partitionDirectory, Constant.FIRST_LOG_FILE_NAME);
        try {
            LogContext logContext = new LogContext();
            logContext.setFilePath(partitionLogDefaultPath.toString());
            logContext.setValueType(ValueType.PARTITION);
            return LogUtil.getLog(logContext);
        } catch (IOException e) {
            System.out.println("failed to get partition log due to error=" + e.getMessage());
            return null;
        }
    }

}
