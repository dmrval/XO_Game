package server;

import static session.LineType.NO_FULL_LINES;

import config.Cfg;
import controller.GameBoardController;
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
                break;
            }
            //+++++ Ход сервера начало
            // TODO: 23.06.2020 тут ходит SERVER
            cin.readLine();

//            setTestWin(gameBoard);
            //+++++ Ход сервера конец

            gameBoard.setWhoseMove(WhoseMove.CLIENT);
            pushData();
            if (isWin()) {
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
            gameBoard.getWinnerMan().setWinnerMark(Status.X);
            gameBoard.getWinnerMan().setWinner(WhoseMove.SERVER);
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