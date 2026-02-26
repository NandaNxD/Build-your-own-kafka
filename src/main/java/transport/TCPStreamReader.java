package transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TCPStreamReader {

    private void readFully(InputStream in, byte[] buffer) throws IOException {
        int offset = 0;
        while (offset < buffer.length) {
            int n = in.read(buffer, offset, buffer.length - offset);
            offset += n;
            if (n == -1) {
                throw new IOException("Stream closed before reading fully");
            }
        }
    }

    public byte[] readMessage(Socket clientSocket) throws IOException {
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();

        InputStream inputStream=clientSocket.getInputStream();

        /**
         * Message size is 4 bytes long field
         */
        byte[] messageSizeInBytes=new byte[4];
        readFully(inputStream,messageSizeInBytes);

        buffer.write(messageSizeInBytes);

        int messageSize=ByteBuffer.wrap(messageSizeInBytes).getInt();

        byte[] message = new byte[messageSize];

        readFully(inputStream,message);

        buffer.write(message);

       return buffer.toByteArray();
    }
}
