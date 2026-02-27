package protocol.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseHeaderV1 implements ResponseHeader{
    int correlationId;
    Object tagBuffer;
}
