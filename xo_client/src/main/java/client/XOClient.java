package client;

import static session.LineType.NO_FULL_LINES;

import config.Cfg;
import decoder.DataHelper;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import print.ConsolePrint;
import session.FullLine;
import session.GameBoard;
import session.Status;
import util.WhoseMove;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Slf4j
@Getter
public class XOClient extends Thread implements Winnable {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private byte[] buf;
    private GameBoard gameBoard;
    private int gameBoardSize;
    private FullLine fullLine;
    private BufferedReader cin;


    @SneakyThrows
    public XOClient() {
        buf = new byte[Cfg.BUFFER];
        cin = new BufferedReader(new InputStreamReader(System.in));
        fullLine = new FullLine();
        gameBoardSize = 3;
    }

    @SneakyThrows
    @Override
    public void run() {
        socket = new DatagramSocket();
        int i = 0;
        gameBoard = new GameBoard(gameBoardSize);
        while (true) {

            //+++++ Ход клиента начало
            boolean correctInput;
            do {
                log.info("Введите сообщение серверу: ");
                switch (cin.readLine()) {
                    case "1":
                        if (gameBoard.getCells()[0][0].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[0][0].setStatus(Status.O);
                        correctInput = true;
                        break;
                    case "2":
                        if (gameBoard.getCells()[0][1].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[0][1].setStatus(Status.O);
                        correctInput = true;
                        break;
                    case "3":
                        if (gameBoard.getCells()[0][2].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[0][2].setStatus(Status.O);
                        correctInput = true;
                        break;
                    case "4":
                        if (gameBoard.getCells()[1][0].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[1][0].setStatus(Status.O);
                        correctInput = true;
                        break;
                    case "5":
                        if (gameBoard.getCells()[1][1].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[1][1].setStatus(Status.O);
                        correctInput = true;
                        break;
                    case "6":
                        if (gameBoard.getCells()[1][2].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[1][2].setStatus(Status.O);
                        correctInput = true;
                        break;
                    case "7":
                        if (gameBoard.getCells()[2][0].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[2][0].setStatus(Status.O);
                        correctInput = true;
                        break;
                    case "8":
                        if (gameBoard.getCells()[2][1].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[2][1].setStatus(Status.O);
                        correctInput = true;
                        break;
                    case "9":
                        if (gameBoard.getCells()[2][2].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[2][2].setStatus(Status.O);
                        correctInput = true;
                        break;
                    default:
                        correctInput = false;
                }
            } while (!correctInput);
            //+++++ Ход клиента конец

            gameBoard.setWhoseMove(WhoseMove.SERVER);
            pushData();
            log.info("Клиент отправил " + i++ + " ход");

            if (isWin()) {
                log.info("Нажать любую клавишу для выхода");
                cin.readLine();
                break;
            }
            pullData();

            if (isWin()) {
                log.info("Нажать любую клавишу для выхода");
                cin.readLine();
                break;
            }
            log.info("Клиент получил " + i++ + " ход");
            ConsolePrint.printGameBoard(gameBoard);
        }
    }

    @Override
    public boolean isWin() {
        checkWinnerInGameBoard();
        if (fullLine.getLineType() != NO_FULL_LINES) {
            gameBoard.getWinnerMan().setWinnerMark(fullLine.getWinMark());
            gameBoard.getWinnerMan().setWinner(gameBoard.getWinnerMan().getWinnerMark().equals(Status.O) ? WhoseMove.CLIENT : WhoseMove.SERVER);
            ConsolePrint.printActiveWinner(gameBoard, fullLine);
            pushData();
            return true;
        } else {
            return false;
        }
    }

    private void checkWinnerInGameBoard() {
        fullLine = gameBoard.checkField();
    }

    @SneakyThrows
    private void pullData() {
        buf = new byte[Cfg.BUFFER];
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        buf = packet.getData();
        gameBoard = (GameBoard) DataHelper.deserialize(packet.getData());
    }

    @SneakyThrows
    private void pushData() {
        buf = DataHelper.serialize(gameBoard);
        packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(Cfg.HOST), Cfg.PORT);
        socket.send(packet);
    }

}
