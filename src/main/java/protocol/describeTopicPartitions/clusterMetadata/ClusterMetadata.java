package protocol.describeTopicPartitions.clusterMetadata;

import java.io.FileInputStream;
import java.util.List;

public class ClusterMetadata {
    List<RecordBatch> recordBatchList;

    ClusterMetadata decode(FileInputStream fileInputStream, long offset) throws Exception {
        throw new Exception("Function not implemented");
    }
}
