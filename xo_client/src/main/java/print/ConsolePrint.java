package print;

import lombok.extern.slf4j.Slf4j;
import session.Cell;
import session.FullLine;
import session.GameBoard;

@Slf4j
public class ConsolePrint {
    public static void printGameBoard(GameBoard gameBoard) {
        log.debug("Ходит ---> " + gameBoard.getWhoseMove());
        Cell[][] cells = gameBoard.getCells();
        for (Cell[] cellArray : cells) {
            for (Cell curr : cellArray) {
                System.out.print(curr.getStatus().getCurrentStatus() + " ");
            }
            System.out.println();
        }
    }

    public static void printActiveWinner(GameBoard gameBoard, FullLine fullLine) {
        log.info("Победитель ---> " + gameBoard.getWinnerMan().getWinner() + "!!!");
        log.info("Выйграл на " + fullLine.getLineType() + " " + fullLine.getPosition());
    }
}
