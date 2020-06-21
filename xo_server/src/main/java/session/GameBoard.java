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
        for (Cell[] c : cells) {
            for (Cell curr : c) {
                curr = new Cell(Status.X);
            }
        }
    }
}
