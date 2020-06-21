package session;

import lombok.Data;
import util.WhoseMove;

import java.io.Serializable;

@Data
public class GameBoard implements Serializable {
    private Cell[][] cells;
    private WhoseMove whoseMove;

    public GameBoard() {
        whoseMove = WhoseMove.getRandomPlayer();
        cells = new Cell[3][3];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = Cell.builder()
                                  .status(Status.NONE)
                                  .build();
            }
        }
    }


}
