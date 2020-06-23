package client;

import config.Cfg;
import decoder.DataHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import print.ConsolePrint;
import session.GameBoard;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Slf4j
public class XOClient extends Thread {
    private DatagramSocket socket;
    private GameBoard gameBoard;
    private int gameBoardSize = 3;

    @SneakyThrows
    @Override
    public void run() {
        log.info("Старт клиента");
        socket = new DatagramSocket();
        log.info("Старт сессии клиента: ");
        gameBoard = new GameBoard(gameBoardSize);
        log.info("Отправка новой доски серверу: ");
        pushData(socket, gameBoard);
        log.info("Доска отправлена: ");
        GameBoard gameBoard1 = pullData(socket);
        ConsolePrint.printToConsole(gameBoard1);
    }

    @SneakyThrows
    private GameBoard pullData(DatagramSocket socket) {
        byte[] buffer = new byte[Cfg.BUFFER];
        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
        socket.receive(reply);
        byte[] dataResult = reply.getData();
        return (GameBoard) DataHelper.deserialize(dataResult);
    }

    @SneakyThrows
    private void pushData(DatagramSocket socket, GameBoard gameBoard) {
        byte[] data = DataHelper.serialize(gameBoard);
        DatagramPacket datagramPacket =
                new DatagramPacket(data, data.length, InetAddress.getByName(Cfg.HOST), Cfg.PORT);
        socket.send(datagramPacket);
    }
}
