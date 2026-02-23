package protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    int messageSize;
    ResponseHeader responseHeader;
    ResponseBody responseBody;
}
