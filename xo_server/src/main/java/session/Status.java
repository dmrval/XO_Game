package session;

import lombok.Getter;

@Getter
public enum Status {
    X("X"),
    O("O"),
    NONE("-");

    String currentStatus;

    Status(String st) {
        currentStatus = st;
    }
}
