package protocol.describeTopicPartitions;

import datatypes.CompactString;
import lombok.AllArgsConstructor;
import lombok.Data;
import models.DecodedResponse;

import java.io.ByteArrayOutputStream;

@Data
@AllArgsConstructor
public class TopicRequest {
    CompactString topicName;
    Object tagBuffer;

    public byte[] encode() throws Exception {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(topicName.encode());

        if(tagBuffer==null){
            throw new Exception("Tag buffer not null condition not implemented in topic encode");
        }
        else{
            outputStream.write(0);
        }

        return outputStream.toByteArray();
    }

    public static DecodedResponse<TopicRequest> decode(byte[] data, int offset){
        int offsetBeforeReading=offset;

        DecodedResponse<CompactString> decodedTopicName=CompactString.decode(data,offset);
        offset+= (int) decodedTopicName.getBytesRead();

        /**
         * Skip tagBuffer
         */
        offset++;

        int bytesRead=offset-offsetBeforeReading;

        return new DecodedResponse<>(new TopicRequest(decodedTopicName.getContent(),null),bytesRead);
    }
}
