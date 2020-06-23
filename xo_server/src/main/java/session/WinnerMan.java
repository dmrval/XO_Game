package session;

import lombok.Data;
import util.WhoseMove;

import java.io.Serializable;

@Data
public class WinnerMan implements Serializable {
    private WhoseMove winner = null;
    private Status winnerMark = Status.NONE;
}
