import service.broker.BaseBrokerService;
import service.broker.impl.ApiVersionsImpl;
import service.broker.impl.DescribeTopicPartitionsImpl;
import service.broker.impl.FetchImpl;
import service.log.impl.FeatureLevelValueImpl;
import service.log.impl.PartitionValueImpl;
import service.log.impl.TopicValueImpl;
import utils.FileUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        // Uncomment this block to pass the first stage

        ServerSocket serverSocket;
        Socket clientSocket = null;
        int port = 9092;
        new DescribeTopicPartitionsImpl().registerHandler();
        new ApiVersionsImpl().registerHandler();
        new FetchImpl().registerHandler();
        new FeatureLevelValueImpl().registerHandler();
        new TopicValueImpl().registerHandler();
        new PartitionValueImpl().registerHandler();
        FileUtil.loadConfigs();
        FileUtil.loadClusterMetadataLog();
        try {
            serverSocket = new ServerSocket(port);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);
            // Wait for connection from client.
            while (!serverSocket.isClosed()) {
                clientSocket = serverSocket.accept(); // wait for connection from client
                Socket finalClientSocket = clientSocket;
                new Thread(() -> BaseBrokerService.handle(finalClientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
