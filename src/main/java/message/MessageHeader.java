package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MessageHeader {
    // Size: 2
    private int requestApiKey;

    // Size: 2
    private int requestApiVersion;

    // Size: 4
    private int correlationId;
}
