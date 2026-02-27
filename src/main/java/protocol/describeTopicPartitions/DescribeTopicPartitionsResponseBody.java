package protocol.describeTopicPartitions;

import datatypes.CompactArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import protocol.response.ResponseBody;
import util.Util;

import java.io.ByteArrayOutputStream;

@AllArgsConstructor
@Data
public class DescribeTopicPartitionsResponseBody implements ResponseBody {
    /**
     * throttle_time_ms	INT32	Throttle time in milliseconds (use 0)
     * topics	COMPACT_ARRAY	Array of topic metadata
     * next_cursor	NULLABLE_INT8	Pagination cursor (use -1 for null)
     * TAG_BUFFER	TAGGED_FIELDS	Tagged fields
     */

    int throttleTime_ms;
    CompactArray<TopicResponse> topics;
    Integer next_cursor;
    Object tagBuffer;

    @Override
    public byte[] encode() throws Exception {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(throttleTime_ms);
        outputStream.write(topics.encode());
        outputStream.write(Util.encodeNullableINT8(next_cursor));

        if(tagBuffer!=null){
            throw new Exception("Tag buffer not null not implemented in DescribeTopicPartitionsResponseBody encode method");
        }
        outputStream.write(0);

        return outputStream.toByteArray();
    }
}
