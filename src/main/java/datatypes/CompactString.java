package datatypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import models.DecodedResponse;
import util.Util;

import javax.xml.stream.events.Characters;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@Data
@AllArgsConstructor
public class CompactString{
    String value;

    public byte[] encode() throws Exception {
        if(value==null){
            throw new Exception("Compact string cant be null");
        }
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(Util.convertIntegerToVarIntBytes(value.length()+1));

        outputStream.write(value.getBytes(StandardCharsets.UTF_8));

        return outputStream.toByteArray();
    }

    public static DecodedResponse<CompactString> decode(byte[] data, int offset){
        /**
         * Decode string length in bytes
         * It is of type VarInt
         * Important: It is length+1 here
         */

        int offsetBeforeRead=offset;

        DecodedResponse<Integer> decodedCompactStringLength=Util.decodeVarInt(data,offset);
        offset+= (int) decodedCompactStringLength.getBytesRead();

        int stringLength=decodedCompactStringLength.getContent()-1;

        /**
         * Read characters from data
         */
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<stringLength;i++){
            stringBuilder.append((char)data[offset++]);
        }

        int bytesRead=offset-offsetBeforeRead;

        return new DecodedResponse<>(new CompactString(stringBuilder.toString()),bytesRead);
    }
}
