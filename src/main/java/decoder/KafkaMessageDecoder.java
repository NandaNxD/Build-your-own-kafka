package decoder;

import message.Message;
import message.MessageHeader;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class KafkaMessageDecoder {

    public Message decodeMessage(byte[] data){

        int messageSize=ByteBuffer.wrap(Arrays.copyOfRange(data,0,4)).getInt();

        int correlationId=ByteBuffer.wrap(Arrays.copyOfRange(data,8,8+4)).getInt();

        return new Message(messageSize,new MessageHeader(0,0,correlationId),null);
    }

}
