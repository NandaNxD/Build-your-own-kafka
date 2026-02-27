package encoder;

import protocol.response.Response;

public class KafkaResponseEncoder {
    public byte[] encode(Response response) throws Exception {
        return response.encode();
    }
}
