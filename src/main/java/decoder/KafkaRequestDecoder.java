package decoder;

import protocol.Request;
import protocol.RequestHeader;
import protocol.RequestHeader;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class KafkaRequestDecoder {

    public Request decodeRequest(byte[] data){

        int messageSize=ByteBuffer.wrap(Arrays.copyOfRange(data,0,0+4)).getInt();

        int requestApiKey=ByteBuffer.wrap(Arrays.copyOfRange(data,4,4+2)).getShort();

        int requestApiVersion=ByteBuffer.wrap(Arrays.copyOfRange(data,6,6+2)).getShort();

        int correlationId=ByteBuffer.wrap(Arrays.copyOfRange(data,8,8+4)).getInt();

        return new Request(messageSize,new RequestHeader(requestApiKey,requestApiVersion,correlationId),null);
    }

}
