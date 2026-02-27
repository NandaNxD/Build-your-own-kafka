package protocol.describeTopicPartitions;

import datatypes.CompactArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import models.DecodedResponse;
import protocol.request.RequestBody;

@Data
@AllArgsConstructor
public class DescribeTopicPartitionsRequestBody extends RequestBody {
    CompactArray<TopicRequest> topics;
    Object tagBuffer;

    public static DecodedResponse<DescribeTopicPartitionsRequestBody> decode(byte[] data, int offset) throws Exception {
        int offsetBeforeRead=offset;
        DecodedResponse<CompactArray<TopicRequest>> decodedCompactArray=CompactArray.decode(data,offset,TopicRequest.class);
        offset+= (int) decodedCompactArray.getBytesRead();
        /**
         * Skip tag buffer
         */
        offset++;

        int bytesRead=offset-offsetBeforeRead;

        return new DecodedResponse<>(new DescribeTopicPartitionsRequestBody(decodedCompactArray.getContent(),null),bytesRead);
    }
}
