import decoder.KafkaRequestDecoder;
import encoder.KafkaResponseEncoder;
import protocol.Request;
import protocol.Response;
import protocol.ResponseBody;
import protocol.ResponseHeader;
import transport.TCPStreamReader;
import transport.TCPStreamWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args){

     ServerSocket serverSocket = null;
     Socket clientSocket = null;
     int port = 9092;
     try {
       serverSocket = new ServerSocket(port);
       // Since the tester restarts your program quite often, setting SO_REUSEADDR
       // ensures that we don't run into 'Address already in use' errors
       serverSocket.setReuseAddress(true);
       // Wait for connection from client.
       clientSocket = serverSocket.accept();

       TCPStreamReader tcpStreamReader=new TCPStreamReader();

       byte[] data=tcpStreamReader.readBytes(clientSocket);

       KafkaRequestDecoder kafkaMessageDecoder=new KafkaRequestDecoder();

       Request request=kafkaMessageDecoder.decodeRequest(data);

       Response response=new Response(0,
               new ResponseHeader(request.getRequestHeader().getCorrelationId())
               ,new ResponseBody(new byte[]{0,0x23}));

       KafkaResponseEncoder kafkaMessageEncoder=new KafkaResponseEncoder();

       byte[] encodedResponse=kafkaMessageEncoder.encode(response);

       TCPStreamWriter tcpStreamWriter=new TCPStreamWriter();

       tcpStreamWriter.writeBytes(clientSocket,encodedResponse);

     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
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
