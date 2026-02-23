package encoder;

import protocol.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class KafkaResponseEncoder {
    public byte[] encode(Response response) throws IOException {
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        buffer.write(encodeResponseMessageSize(response));
        buffer.write(encodeResponseHeader(response));
        buffer.write(encodeResponseBody(response));
        return buffer.toByteArray();
    }

    private byte[] encodeResponseMessageSize(Response response){
        byte[] result=new byte[4];
        for(int i=0;i<4;i++){
            result[4-i-1]= (byte) ((response.getMessageSize()>>(i*8)) & 0xFF);
        }
        return result;
    }

    private byte[] encodeResponseHeader(Response response) throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();

        byte[] encodedCorrelationIdInBytes=new byte[4];
        for(int i=0;i<4;i++){
            encodedCorrelationIdInBytes[4-i-1]= (byte) ((response.getResponseHeader().getCorrelationId()>>(i*8)) & 0xFF);
        }
        outputStream.write(encodedCorrelationIdInBytes);

        return outputStream.toByteArray();
    }

    private byte[] encodeResponseBody(Response response){
        return response.getResponseBody().getBody();
    }
}
