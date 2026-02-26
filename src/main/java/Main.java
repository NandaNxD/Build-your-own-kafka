import datatypes.CompactArray;
import decoder.KafkaRequestDecoder;
import encoder.KafkaResponseEncoder;
import protocol.Request;
import protocol.Response;
import protocol.ResponseBody;
import protocol.ResponseHeader;
import protocol.apiversions.ApiKey;
import protocol.apiversions.ApiVersionsResponseBody;
import transport.TCPStreamReader;
import transport.TCPStreamWriter;
import util.Util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
  public static void main(String[] args){

     ServerSocket serverSocket = null;
     Socket clientSocket = null;
     int port = 9092;
     try {
       serverSocket = new ServerSocket(port);

       serverSocket.setReuseAddress(true);

       clientSocket = serverSocket.accept();

       TCPStreamReader tcpStreamReader=new TCPStreamReader();

       byte[] data=tcpStreamReader.readBytes(clientSocket);

       KafkaRequestDecoder kafkaMessageDecoder=new KafkaRequestDecoder();

       Request request=kafkaMessageDecoder.decodeRequest(data);

       ArrayList<ApiKey> apiKeyArrayList=new ArrayList<>();
       apiKeyArrayList.add(new ApiKey(18,0,4,null));

       CompactArray<ApiKey> apiKeysCompactArray= new CompactArray<>(apiKeyArrayList, ApiKey.class);

       Response response=new Response(null,
               new ResponseHeader(request.getRequestHeader().getCorrelationId())
               ,new ApiVersionsResponseBody(0,apiKeysCompactArray,0,null));

       KafkaResponseEncoder kafkaMessageEncoder=new KafkaResponseEncoder();

       byte[] encodedResponse=kafkaMessageEncoder.encode(response);

       TCPStreamWriter tcpStreamWriter=new TCPStreamWriter();

       tcpStreamWriter.writeBytes(clientSocket,encodedResponse);

     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     } catch (Exception e) {
         throw new RuntimeException(e);
     } finally {
       try {
         if (clientSocket != null) {
           clientSocket.close();
         }
       } catch (IOException e) {
         System.out.println("IOException: " + e.getMessage());
       }
     }
  }
}
