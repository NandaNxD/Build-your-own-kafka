package protocol.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseHeaderV0 implements ResponseHeader{
    int correlationId;
}
