package protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Request {
    private int messageSize;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

}
