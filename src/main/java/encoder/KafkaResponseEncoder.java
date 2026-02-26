package encoder;

import protocol.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class KafkaResponseEncoder {
    public byte[] encode(Response response) throws Exception {
        return response.encode();
    }
}
