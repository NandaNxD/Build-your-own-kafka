package transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TCPStreamReader {
    public byte[] readBytes(Socket clientSocket) throws IOException {
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();

        InputStream inputStream=clientSocket.getInputStream();

        byte[] temp = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(temp)) != -1) {
            buffer.write(temp, 0, bytesRead);
            if(bytesRead>=16){
                break;
            }
        }

       return buffer.toByteArray();
    }
}
