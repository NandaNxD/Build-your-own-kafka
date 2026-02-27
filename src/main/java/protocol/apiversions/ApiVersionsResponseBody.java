package protocol.apiversions;

import datatypes.CompactArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import protocol.response.ResponseBody;
import util.Util;

import java.io.ByteArrayOutputStream;

@Data
@AllArgsConstructor
public class ApiVersionsResponseBody implements ResponseBody {
    /**
     * Field	Data type	Description
     * error_code	INT16	Error code (0 for successful requests)
     * api_keys	COMPACT_ARRAY	Array of supported APIs with version ranges
     * throttle_time_ms	INT32	Throttle time in milliseconds
     * TAG_BUFFER	TAGGED_FIELDS	Optional tagged fields
     */

    // INT16
    int errorCode;

    // COMPACT_ARRAY
    CompactArray<ApiKey> apiKeys;

    // INT32
    int throttleTime_ms;

    Object tagBuffer;


    @Override
    public byte[] encode() throws Exception {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(Util.encodeINT16(errorCode));
        if(errorCode==0){
            outputStream.write(apiKeys.encode());
            outputStream.write(Util.encodeINT32(throttleTime_ms));
            if(tagBuffer!=null){
                throw new Exception("Tag buffer encoding not implemented in ApiVersionsResponseBody");
            }
            else{
                outputStream.write(0);
            }
        }

        return outputStream.toByteArray();
    }


}
