package session;

import lombok.Data;
import util.WhoseMove;

import java.io.Serializable;
//CHANGED: размер поля задаётся при инициализации
//ADD: метод проверки игрового поля на выигрыш
@Data
public class GameBoard implements Serializable {
    private Cell[][] cells;
    private WhoseMove whoseMove;

    public GameBoard(int size) {
        whoseMove = WhoseMove.getRandomPlayer();
        cells = new Cell[size][size];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = Cell.builder()
                                  .status(Status.NONE)
                                  .build();
            }
        }
    }

    public FullLine checkField() {
        FullLine row = checkRows();
        if (!row.getLineType().equals(LineType.NO_FULL_LINES)) {
            return row;
        }
        FullLine col = checkCols();
        if (!col.getLineType().equals(LineType.NO_FULL_LINES)) {
            return col;
        }
        return checkDiagonals();
    }

    private FullLine checkRows() {
        outerLoop:
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length - 1; j++) {
                if (cells[i][j].getStatus().equals(Status.NONE)) {
                    continue outerLoop;
                }
                if (!cells[i][j].equals(cells[i][j + 1])) {
                    continue outerLoop;
                }
            }
            return new FullLine(LineType.ROW, i);
        }
        return new FullLine();
    }

    private FullLine checkCols() {
        outerLoop:
        for (int j = 0; j < cells.length; j++) {
            for (int i = 0; i < cells.length - 1; i++) {
                if (cells[i][j].getStatus().equals(Status.NONE)) {
                    continue outerLoop;
                }
                if (!cells[i][j].equals(cells[i + 1][j])) {
                    continue outerLoop;
                }
            }
            return new FullLine(LineType.COL, j);
        }
        return new FullLine();
    }

    private FullLine checkDiagonals() {
        for (int i = 0, j = 0; i < cells.length - 1; i++, j++) {
            if (cells[i][j].getStatus().equals(Status.NONE)) {
                break;
            }
            if (!cells[i][j].equals(cells[i + 1][j + 1])) {
                break;
            }
            if(i==cells.length-2){
                return new FullLine(LineType.DIAG, 1);
            }
        }

        for (int i = cells.length - 1, j = 0; i >= 1; i--, j++) {
            if (cells[i][j].getStatus().equals(Status.NONE)) {
                break;
            }
            if (!cells[i][j].equals(cells[i - 1][j + 1])) {
                break;
            }
            if(i==1){
                return new FullLine(LineType.DIAG, 2);
            }
        }
        return new FullLine();
    }
}
