package server;

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

@Slf4j
@Getter
public class XOServer extends Thread implements Winnable {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private byte[] buf;
    private GameBoard gameBoard;
    private FullLine fullLine;
    private BufferedReader cin;


    @SneakyThrows
    public XOServer() {
        buf = new byte[Cfg.BUFFER];
        cin = new BufferedReader(new InputStreamReader(System.in));
        fullLine = new FullLine();
    }

    @SneakyThrows
    public void run() {
        socket = new DatagramSocket(Cfg.PORT);
        byte[] buffer = new byte[Cfg.BUFFER];
        packet = new DatagramPacket(buffer, buffer.length);
        log.info("Ожидаем данные...");
        int i = 0;
        while (true) {
            pullData();
            log.info("Сервер получил " + i++ + " ход");
            ConsolePrint.printGameBoard(gameBoard);
            if (isWin()) {
                log.info("Нажать любую клавишу для выхода");
                cin.readLine();
                break;
            }

            //+++++ Ход сервера начало
            boolean correctInput;
            do {
                switch (cin.readLine()) {
                    case "1":
                        if (gameBoard.getCells()[0][0].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[0][0].setStatus(Status.X);
                        correctInput = true;
                        break;
                    case "2":
                        if (gameBoard.getCells()[0][1].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[0][1].setStatus(Status.X);
                        correctInput = true;
                        break;
                    case "3":
                        if (gameBoard.getCells()[0][2].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[0][2].setStatus(Status.X);
                        correctInput = true;
                        break;
                    case "4":
                        if (gameBoard.getCells()[1][0].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[1][0].setStatus(Status.X);
                        correctInput = true;
                        break;
                    case "5":
                        if (gameBoard.getCells()[1][1].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[1][1].setStatus(Status.X);
                        correctInput = true;
                        break;
                    case "6":
                        if (gameBoard.getCells()[1][2].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[1][2].setStatus(Status.X);
                        correctInput = true;
                        break;
                    case "7":
                        if (gameBoard.getCells()[2][0].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[2][0].setStatus(Status.X);
                        correctInput = true;
                        break;
                    case "8":
                        if (gameBoard.getCells()[2][1].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[2][1].setStatus(Status.X);
                        correctInput = true;
                        break;
                    case "9":
                        if (gameBoard.getCells()[2][2].getStatus() != Status.NONE) {
                            log.info("КЛЕТКА УЖЕ ЗАНЯТА");
                            correctInput = false;
                            break;
                        }
                        gameBoard.getCells()[2][2].setStatus(Status.X);
                        correctInput = true;
                        break;
                    default:
                        correctInput = false;
                }
            } while (!correctInput);
            //+++++ Ход сервера конец

            gameBoard.setWhoseMove(WhoseMove.CLIENT);
            pushData();
            if (isWin()) {
                log.info("Нажать любую клавишу для выхода");
                cin.readLine();
                break;
            }
            log.info("Сервер отправил " + i++ + "ход");
        }
    }

    private void checkWinnerInGameBoard() {
        fullLine = gameBoard.checkField();
    }

    @Override
    public boolean isWin() {
        checkWinnerInGameBoard();
        if (fullLine.getLineType() != NO_FULL_LINES) {
            // TODO: 24.06.2020 тут надо продумать как записывать знак победителя
            gameBoard.getWinnerMan().setWinnerMark(fullLine.getWinMark());
            gameBoard.getWinnerMan().setWinner(gameBoard.getWinnerMan().getWinnerMark().equals(Status.O) ? WhoseMove.CLIENT : WhoseMove.SERVER);
            ConsolePrint.printActiveWinner(gameBoard, fullLine);
            pushData();
            return true;
        } else {
            return false;
        }
    }

    @SneakyThrows
    private void pullData() {
        socket.receive(packet);
        gameBoard = (GameBoard) DataHelper.deserialize(packet.getData());
    }

    @SneakyThrows
    private void pushData() {
        buf = DataHelper.serialize(gameBoard);
        packet = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());
        socket.send(packet);
    }
}