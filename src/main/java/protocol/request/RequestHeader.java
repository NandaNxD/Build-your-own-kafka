package protocol.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RequestHeader {
    // INT16
    int requestApiKey;

    // INT16
    int requestApiVersion;

    // INT32
    int correlationId;

    byte[] clientId;

    Object tagBuffer;
}
