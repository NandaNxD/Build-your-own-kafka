package protocol.apiversions;

import lombok.Data;
import protocol.request.RequestBody;

@Data
public class ApiVersionsRequestBody extends RequestBody {
    private short clientIdLength;
    private byte[] clientId;
}
