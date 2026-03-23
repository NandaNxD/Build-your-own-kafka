package protocol.describeTopicPartitions.clusterMetadata;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Data
@AllArgsConstructor
public class ClusterMetadataReader {
    public static ClusterMetadata readClusterMetadata(String filePath) throws Exception {
        FileInputStream fileInputStream=new FileInputStream(filePath);
        byte data[]=fileInputStream.readAllBytes();

        return  ClusterMetadata.decode(data,0);
    }
}
