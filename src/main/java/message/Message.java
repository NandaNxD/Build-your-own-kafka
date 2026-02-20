package message;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;

//@Getter
//@Setter
//@AllArgsConstructor
public class Message {
    private int messageSize;

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }

    private MessageHeader messageHeader;
    private byte[] messageBody;

    public Message(int messageSize, MessageHeader messageHeader, byte[] messageBody) {
        this.messageSize = messageSize;
        this.messageHeader = messageHeader;
        this.messageBody = messageBody;
    }
}
