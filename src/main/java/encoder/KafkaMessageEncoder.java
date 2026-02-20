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
//        out.println(Arrays.toString(encodeMessageHeader(message)));
        return buffer.toByteArray();
    }

    private byte[] encodeMessageSize(Message message){
        byte[] result=new byte[4];
        for(int i=0;i<4;i++){
            result[4-i-1]= (byte) ((message.getMessageSize()>>(i*8)) & 15);
        }
        return result;
    }

    private byte[] encodeMessageHeader(Message message){
        byte[] result=new byte[4];
        for(int i=0;i<4;i++){
            result[4-i-1]= (byte) ((message.getMessageHeader().getCorrelationId()>>(i*8)) & 15);
        }
        return result;
    }
}
