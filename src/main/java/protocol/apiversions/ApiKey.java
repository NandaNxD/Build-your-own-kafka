package protocol.apiversions;

import lombok.AllArgsConstructor;
import lombok.Data;
import util.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Data
@AllArgsConstructor
public class ApiKey {
    /**
     * api_key	INT16	The API identifier
     * min_version	INT16	Minimum supported version
     * max_version	INT16	Maximum supported version
     * TAG_BUFFER	TAGGED_FIELDS	Optional tagged fields
     */

    Integer apiKey;
    Integer minVersion;
    Integer maxVersion;
    Object tagBuffer;

    public byte[] encode() throws Exception {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(Util.encodeINT16(apiKey));
        outputStream.write(Util.encodeINT16(minVersion));
        outputStream.write(Util.encodeINT16(maxVersion));
        /**
         * Tag buffer
         */
        if(tagBuffer!=null){
            throw new Exception("Implement tag buffer encoding in ApiKey");
        }
        else{
            outputStream.write(0);
        }

        return outputStream.toByteArray();
    }
}
