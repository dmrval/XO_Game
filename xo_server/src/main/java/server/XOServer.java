package server;

import config.Cfg;
import decoder.DataHelper;
import lombok.Data;
import lombok.SneakyThrows;
import print.ConsolePrint;
import session.Cell;
import session.GameBoard;
import session.Status;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class XOServer extends Thread {

    private DatagramSocket socket;

    @SneakyThrows
    public void run() {
        System.out.println("Старт сервера --->");
        socket = new DatagramSocket(Cfg.PORT);
        byte[] buffer = new byte[Cfg.BUFFER];
        DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
        System.out.println("Ожидаем данные...");
            socket.receive(incoming);
            byte[] data = incoming.getData();
            GameBoard gameBoard = (GameBoard) DataHelper.deserialize(data);
            System.out.println("Сервер получил: ");
            ConsolePrint.printToConsole(gameBoard);
            testStep(gameBoard);
            data = DataHelper.serialize(gameBoard);
            DatagramPacket dp = new DatagramPacket(data, data.length, incoming.getAddress(), incoming.getPort());
            socket.send(dp);
    }

    public static void testStep(GameBoard gameBoard) {
        for (Cell[] cellArr: gameBoard.getCells()) {
            for (Cell curr: cellArr) {
                curr.setStatus(Status.O);
            }
        }
    }
}