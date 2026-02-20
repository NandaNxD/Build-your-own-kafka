package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {
    private int messageSize;
    private MessageHeader messageHeader;
    private byte[] messageBody;

}
