package server;

import config.Cfg;
import decoder.DataHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import print.ConsolePrint;
import session.Cell;
import session.GameBoard;
import session.Status;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Slf4j
public class XOServer extends Thread {

    private DatagramSocket socket;
    private GameBoard gameBoard;

    @SneakyThrows
    public XOServer() {
        socket = new DatagramSocket(Cfg.PORT);
    }

    public void run() {
        log.info("Старт сервера");
        log.info("Ожидаем данные...");
        gameBoard = pullData(socket);
        log.info("Сервер получил !");
        log.info("Проверка доски:");
        log.info(gameBoard.checkField().getLineType().toString()+gameBoard.checkField().getPosition());
        ConsolePrint.printToConsole(gameBoard);
        testStep(gameBoard);
        log.info("Доска изменена");
        log.info("Отправка данных клиенту");
        pushData(socket, gameBoard);
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

    public static void testStep(GameBoard gameBoard) {
        for (Cell[] cellArr : gameBoard.getCells()) {
            for (Cell curr : cellArr) {
                curr.setStatus(Status.O);
            }
        }
    }
}