package decoder;

import constant.ApiKeyConstants;
import protocol.describeTopicPartitions.DescribeTopicPartitionsRequestBody;
import protocol.request.Request;
import protocol.request.RequestBody;
import protocol.request.RequestHeader;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class KafkaRequestDecoder {

    public Request decodeRequest(byte[] data) throws Exception {

        int messageSize=ByteBuffer.wrap(Arrays.copyOfRange(data,0,0+4)).getInt();

        int requestApiKey=ByteBuffer.wrap(Arrays.copyOfRange(data,4,4+2)).getShort();

        int requestApiVersion=ByteBuffer.wrap(Arrays.copyOfRange(data,6,6+2)).getShort();

        int correlationId=ByteBuffer.wrap(Arrays.copyOfRange(data,8,8+4)).getInt();

        /**
         * Decode client ID length
         */

        int clientIdLength=ByteBuffer.wrap(Arrays.copyOfRange(data,12,12+2)).getShort();
        byte[] clientId=Arrays.copyOfRange(data,14,14+clientIdLength);

        int offset=14+clientIdLength;

        /**
         * Skip tag buffer
         */
        offset++;

        RequestBody requestBody;

        if(requestApiKey==ApiKeyConstants.ApiVersions){
            requestBody=null;
        }
        else if(requestApiKey==ApiKeyConstants.DescribeApiPartitions){
            requestBody=DescribeTopicPartitionsRequestBody.decode(data,offset).getContent();
        }
        else{
            throw new IllegalArgumentException("Request api key not supported");
        }

        return new Request(messageSize,new RequestHeader(requestApiKey,requestApiVersion,correlationId,clientId,null),requestBody);
    }

}
