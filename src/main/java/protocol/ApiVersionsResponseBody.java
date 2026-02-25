package protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Data
@AllArgsConstructor
public class ApiVersionsResponseBody extends ResponseBody{
    // 16 bits | 2 bytes
    int errorCode;

    // COMPACT_ARRAY
    Object apiKeys;

    // 32 bits | 4 bytes
    int throttleTime_ms;

    Object tagBuffer;

    public ApiVersionsResponseBody(byte[] body) {
        super(body);
    }

    public byte[] encode() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(encodeInteger(2,errorCode));
        outputStream.write(encodeInteger(4,throttleTime_ms));
        return null;
    }

    public byte[] encodeInteger(int numberOfBytes,int value){
        byte[] result=new byte[numberOfBytes];
        for(int i=0;i<numberOfBytes;i++){
            result[numberOfBytes-i-1]= (byte)((value)>>(i*8) & 0xFF);
        }
        return result;
    }
}
