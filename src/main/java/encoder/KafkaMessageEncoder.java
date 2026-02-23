package encoder;

import message.Message;
import message.MessageHeader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.System.out;

public class KafkaMessageEncoder {
    public byte[] encode(Message message) throws IOException {
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        buffer.write(encodeMessageSize(message));
        buffer.write(encodeMessageHeader(message));
        buffer.write(message.getMessageBody());
        return buffer.toByteArray();
    }

    private byte[] encodeMessageSize(Message message){
        byte[] result=new byte[4];
        for(int i=0;i<4;i++){
            result[4-i-1]= (byte) ((message.getMessageSize()>>(i*8)) & 0xFF);
        }
        return result;
    }

    private byte[] encodeMessageHeader(Message message) throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();


        if(message.getMessageHeader().getRequestApiVersion()!=-1){
            byte[] encodedRequestApiKeyInBytes=new byte[2];
            for(int i=0;i<2;i++){
                encodedRequestApiKeyInBytes[2-i-1]= (byte) ((message.getMessageHeader().getRequestApiKey()>>(i*8)) & 0xFF);
            }
            outputStream.write(encodedRequestApiKeyInBytes);
        }

        if(message.getMessageHeader().getRequestApiKey()!=-1){
            byte[] encodedRequestApiVersionInBytes=new byte[2];
            for(int i=0;i<2;i++){
                encodedRequestApiVersionInBytes[2-i-1]= (byte) ((message.getMessageHeader().getRequestApiVersion()>>(i*8)) & 0xFF);
            }
            outputStream.write(encodedRequestApiVersionInBytes);
        }

        byte[] encodedCorrelationIdInBytes=new byte[4];
        for(int i=0;i<4;i++){
            encodedCorrelationIdInBytes[4-i-1]= (byte) ((message.getMessageHeader().getCorrelationId()>>(i*8)) & 0xFF);
        }
        outputStream.write(encodedCorrelationIdInBytes);

        return outputStream.toByteArray();
    }
}
