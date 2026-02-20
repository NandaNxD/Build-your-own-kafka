package message;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;

//@Setter
//@Getter
//@AllArgsConstructor
public class MessageHeader {
    public int getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
    }

    int correlationId;

    public MessageHeader(int correlationId) {
        this.correlationId=correlationId;
    }
}
