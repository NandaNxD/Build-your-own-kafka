import constant.ApiKeyConstants;
import datatypes.CompactArray;
import datatypes.CompactString;
import decoder.KafkaRequestDecoder;
import encoder.KafkaResponseEncoder;
import protocol.describeTopicPartitions.DescribeTopicPartitionsRequestBody;
import protocol.describeTopicPartitions.DescribeTopicPartitionsResponseBody;
import protocol.describeTopicPartitions.TopicRequest;
import protocol.describeTopicPartitions.TopicResponse;
import protocol.request.Request;
import protocol.response.Response;
import protocol.response.ResponseHeader;
import protocol.apiversions.ApiKey;
import protocol.apiversions.ApiVersionsResponseBody;
import protocol.response.ResponseHeaderV0;
import protocol.response.ResponseHeaderV1;
import transport.TCPStreamReader;
import transport.TCPStreamWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static util.Util.decodeVarInt;

public class Main {
    public static void main(String[] args){

        ServerSocket serverSocket = null;

        int port = 9092;
        try {

            serverSocket = new ServerSocket(port);

            serverSocket.setReuseAddress(true);

            ServerSocket finalServerSocket = serverSocket;

            while(true){
                Socket clientSocket = finalServerSocket.accept();

                Thread.startVirtualThread(()->{
                    try{
                        TCPStreamReader tcpStreamReader=new TCPStreamReader();

                        byte[] data=null;

                        while((data=tcpStreamReader.readMessage(clientSocket))!=null){
                            KafkaRequestDecoder kafkaMessageDecoder=new KafkaRequestDecoder();

                            Request request=kafkaMessageDecoder.decodeRequest(data);

                            if(request.getRequestHeader().getRequestApiKey()== ApiKeyConstants.ApiVersions){
                                int errorCode=(request.getRequestHeader().getRequestApiVersion()>=0 && request.getRequestHeader().getRequestApiVersion()<=4)?0:35;
                                ArrayList<ApiKey> apiKeyArrayList=new ArrayList<>();
                                /**
                                 * ApiVersions api
                                 */
                                apiKeyArrayList.add(new ApiKey(18,0,4,null));

                                /**
                                 * DescribeTopicPartitions
                                 */
                                apiKeyArrayList.add(new ApiKey(75,0,0,null));

                                CompactArray<ApiKey> apiKeysCompactArray= new CompactArray<>(apiKeyArrayList, ApiKey.class);

                                Response response=new Response(null,
                                        new ResponseHeaderV0(request.getRequestHeader().getCorrelationId())
                                        ,new ApiVersionsResponseBody(errorCode,apiKeysCompactArray,0,null));


                                KafkaResponseEncoder kafkaMessageEncoder=new KafkaResponseEncoder();

                                byte[] encodedResponse=kafkaMessageEncoder.encode(response);

                                TCPStreamWriter tcpStreamWriter=new TCPStreamWriter();

                                tcpStreamWriter.writeBytes(clientSocket,encodedResponse);
                            }
                            else{

                                int errorCode=3;
                                String topicName=((DescribeTopicPartitionsRequestBody)request.getRequestBody()).getTopics().getArrayList().getFirst().getTopicName().getValue();
                                CompactString topicNameCompactString=new CompactString(topicName);
                                byte[] topicId=new byte[]{0,0,0,0,0,0,0,0,
                                                            0,0,0,0,0,0,0,0};
                                boolean isInternal=false;
                                CompactArray<TopicResponse> partitions=new CompactArray<>(new ArrayList<>(),TopicResponse.class);
                                int topicAuthorizedOperations=0;
                                Object tagBuffer=null;

                                TopicResponse topicResponse=new TopicResponse(errorCode,topicNameCompactString,topicId,isInternal,partitions,topicAuthorizedOperations,tagBuffer);


                                ArrayList<TopicResponse> topicResponseArrayList=new ArrayList<>();
                                topicResponseArrayList.add(topicResponse);

                                CompactArray<TopicResponse> topicResponseCompactArray=new CompactArray<>(topicResponseArrayList, TopicResponse.class);

                                Response response=new Response(null,
                                        new ResponseHeaderV1(request.getRequestHeader().getCorrelationId(),null)
                                        ,new DescribeTopicPartitionsResponseBody(0,topicResponseCompactArray,-1,null));


                                KafkaResponseEncoder kafkaMessageEncoder=new KafkaResponseEncoder();

                                byte[] encodedResponse=kafkaMessageEncoder.encode(response);

                                TCPStreamWriter tcpStreamWriter=new TCPStreamWriter();

                                tcpStreamWriter.writeBytes(clientSocket,encodedResponse);
                            }


                        }
                    } catch (IOException e) {
                        System.out.println("Closed connection");
                    }
                    catch (Exception e){
                        throw new RuntimeException(e.getMessage());
                    }
                });
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Finished executing");
        }
    }
}
