package protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RequestHeader {
    // Size: 2
    private int requestApiKey;

    // Size: 2
    private int requestApiVersion;

    // Size: 4
    private int correlationId;
}
