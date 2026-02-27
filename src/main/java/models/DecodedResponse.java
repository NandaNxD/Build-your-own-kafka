package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DecodedResponse<T> {
    T content;
    long bytesRead;
}
