package protocol.describeTopicPartitions;

import datatypes.CompactArray;
import datatypes.CompactString;
import lombok.AllArgsConstructor;
import lombok.Data;
import util.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TopicResponse {
    /**
         error_code	INT16	Error code
         topic_name	COMPACT_STRING	The topic name (from the request)
         topic_id	UUID	Topic UUID ->  UUID 4 -> 16 bytes
         is_internal	BOOLEAN	Whether topic is internal
         partitions	COMPACT_ARRAY	Array of partition metadata (empty for unknown)
         topic_authorized_operations	INT32	Authorized operations bitfield (use 0)
         TAG_BUFFER	TAGGED_FIELDS	Tagged fields
     */
    int errorCode;
    CompactString topicName;
    byte[] topicId;
    boolean isInternal;
    /**
     * Leave it empty for now
     */
    CompactArray<?> partitions;
    int topicAuthorizedOperations;

    Object tagBuffer;

    public byte[] encode() throws Exception {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(Util.encodeINT16(errorCode));
        outputStream.write(topicName.encode());
        outputStream.write(topicId);
        outputStream.write(isInternal?1:0);
        outputStream.write(partitions.encode());
        outputStream.write(Util.encodeINT32(topicAuthorizedOperations));

        if(tagBuffer!=null){
           throw new Exception("Tag buffer not null encoding not implemented in TopicResponse encode method");
        }
        else{
            outputStream.write(0);
        }

        return outputStream.toByteArray();
    }


}
