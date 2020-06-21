package session;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Cell implements Serializable {
    private Status status;
}
