package protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Data
@AllArgsConstructor
public class Response {
    /**
     * Computed on calling encode, set it as null in beginning
     */
    Integer messageSize;
    ResponseHeader responseHeader;
    ResponseBody responseBody;

    public byte[] encode() throws Exception {
        ByteArrayOutputStream result=new ByteArrayOutputStream();
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();

        buffer.write(encodeResponseHeader());
        buffer.write(encodeResponseBody());

        result.write(encodeResponseMessageSize(buffer.size()));
        result.write(buffer.toByteArray());

        this.setMessageSize(buffer.size());

        return result.toByteArray();
    }

    private byte[] encodeResponseMessageSize(Integer size){
        byte[] result=new byte[4];
        for(int i=0;i<4;i++){
            result[4-i-1]= (byte) ((size>>(i*8)) & 0xFF);
        }
        return result;
    }

    private byte[] encodeResponseHeader() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();

        byte[] encodedCorrelationIdInBytes=new byte[4];
        for(int i=0;i<4;i++){
            encodedCorrelationIdInBytes[4-i-1]= (byte) ((getResponseHeader().getCorrelationId()>>(i*8)) & 0xFF);
        }
        outputStream.write(encodedCorrelationIdInBytes);

        return outputStream.toByteArray();
    }

    private byte[] encodeResponseBody() throws Exception {
        return getResponseBody().encode();
    }
}
