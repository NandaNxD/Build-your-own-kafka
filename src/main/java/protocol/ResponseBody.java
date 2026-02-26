package protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public interface ResponseBody {
    public byte[] encode() throws Exception;
}
