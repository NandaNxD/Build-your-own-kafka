package transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TCPStreamWriter {
    public void writeBytes(Socket clientSocket,byte[] data) throws IOException {
        clientSocket.getOutputStream().write(data);
    }
}
