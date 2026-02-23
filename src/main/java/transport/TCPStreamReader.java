package transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TCPStreamReader {
    public byte[] readBytes(Socket clientSocket) throws IOException {
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();

        InputStream inputStream=clientSocket.getInputStream();

        /**
         * Message size is 4 bytes long field
         */
        byte[] messageSizeInBytes=new byte[4];
        inputStream.read(messageSizeInBytes);

        buffer.write(messageSizeInBytes);

        int messageSize=ByteBuffer.wrap(messageSizeInBytes).getInt();

        byte[] temp = new byte[messageSize];
        int bytesRead=0;

        while (bytesRead<messageSize && ((bytesRead = inputStream.read(temp)) != -1)) {
            buffer.write(temp, 0, bytesRead);
            messageSize-=bytesRead;
        }

       return buffer.toByteArray();
    }
}
