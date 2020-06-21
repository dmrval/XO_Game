package server;

import config.Cfg;
import decoder.DataHelper;
import lombok.SneakyThrows;
import print.ConsolePrint;
import session.GameBoard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class XOServer extends Thread {

    private DatagramSocket socket;

    BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

    @SneakyThrows
    public void run() {
        System.out.println("Старт сервера --->");
        socket = new DatagramSocket(Cfg.PORT);
        byte[] buffer = new byte[Cfg.BUFFER];
        DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
        System.out.println("Ожидаем данные...");
        while (true) {
            socket.receive(incoming);
            byte[] data = incoming.getData();
            GameBoard gameBoard = (GameBoard) DataHelper.deserialize(data);
            System.out.println("Сервер получил: ");
            ConsolePrint.printToConsole(gameBoard);
            DatagramPacket dp = new DatagramPacket(data, data.length, incoming.getAddress(), incoming.getPort());
            socket.send(dp);
        }
    }


}