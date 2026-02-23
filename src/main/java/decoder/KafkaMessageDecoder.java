package decoder;

import message.Message;
import message.MessageHeader;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class KafkaMessageDecoder {

    public Message decodeMessage(byte[] data){

        int messageSize=ByteBuffer.wrap(Arrays.copyOfRange(data,0,0+4)).getInt();

        int requestApiKey=ByteBuffer.wrap(Arrays.copyOfRange(data,4,4+2)).getShort();

        int requestApiVersion=ByteBuffer.wrap(Arrays.copyOfRange(data,6,6+2)).getShort();

        int correlationId=ByteBuffer.wrap(Arrays.copyOfRange(data,8,8+4)).getInt();

        return new Message(messageSize,new MessageHeader(requestApiKey,requestApiVersion,correlationId),null);
    }

}
