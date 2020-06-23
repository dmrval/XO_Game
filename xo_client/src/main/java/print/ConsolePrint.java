package print;

import lombok.extern.slf4j.Slf4j;
import session.Cell;
import session.GameBoard;

@Slf4j
public class ConsolePrint {
    public static void printToConsole(GameBoard gameBoard) {
        log.debug("ход стартовал у " + gameBoard.getWhoseMove());
        Cell[][] cells = gameBoard.getCells();
        for (Cell[] cellArray : cells) {
            for (Cell curr : cellArray) {
                System.out.print(curr.getStatus().getCurrentStatus() + " ");
            }
            System.out.println();
        }
    }
}
