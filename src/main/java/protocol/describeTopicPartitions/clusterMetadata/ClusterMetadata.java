package protocol.describeTopicPartitions.clusterMetadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import models.DecodedResponse;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ClusterMetadata {
    List<RecordBatch> recordBatchList;

    public static ClusterMetadata decode(byte[] data, long offset) throws Exception {
        List<RecordBatch> recordBatches=new ArrayList<>();

        while(offset<data.length){
            DecodedResponse<RecordBatch> recordBatch=RecordBatch.decode(data,offset);
            offset+=recordBatch.getBytesRead();

            recordBatches.add(recordBatch.getContent());
        }
        return new ClusterMetadata(recordBatches);
    }
}
