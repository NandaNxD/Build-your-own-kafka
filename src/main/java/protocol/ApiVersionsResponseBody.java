package protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiVersionsResponseBody extends ResponseBody{

    public ApiVersionsResponseBody(byte[] body) {
        super(body);
    }
}
