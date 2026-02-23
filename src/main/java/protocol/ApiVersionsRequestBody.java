package protocol;

import lombok.Data;

@Data
public class ApiVersionsRequestBody extends RequestBody{
    private short clientIdLength;
    private byte[] clientId;
}
