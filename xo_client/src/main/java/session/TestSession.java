package session;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestSession implements Serializable {
    private boolean whoseStep;

    public TestSession() {
        whoseStep = true;
    }
}
