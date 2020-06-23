package client;

import static session.LineType.NO_FULL_LINES;

import config.Cfg;
import decoder.DataHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import print.ConsolePrint;
import session.Cell;
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
            log.info("Введите сообщение серверу: ");

            //+++++ Ход клиента начало
            //TODO: 23.06.2020 тут ходит CLIENT
            cin.readLine();
            //+++++ Ход клиента конец

            if (isWin()) {
                break;
            }
            gameBoard.setWhoseMove(WhoseMove.SERVER);
            pushData();
            log.info("Клиент отправил " + i++ + "ход");
            pullData();

            if (isLose()) {
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
            gameBoard.setWinner(WhoseMove.CLIENT);
            ConsolePrint.printActiveWinner(gameBoard, fullLine);
            pushData();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isLose() {
        if (gameBoard.getWinner() != null) {
            log.info("Ты просрал игру!!!");
            log.info("Победитель ---> " + gameBoard.getWinner() + "!!!");
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
