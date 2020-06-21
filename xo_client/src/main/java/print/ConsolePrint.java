package print;

import session.Cell;
import session.GameBoard;

public class ConsolePrint {
    public static void printToConsole(GameBoard gameBoard) {
        System.out.println("Первый ход у " + gameBoard.getWhoseMove());
        Cell[][] cells = gameBoard.getCells();
        for (Cell[] cellArray: cells) {
            for (Cell curr: cellArray) {
                System.out.print(curr.getStatus().getCurrentStatus() + " ");
            }
            System.out.println();
        }
    }
}
