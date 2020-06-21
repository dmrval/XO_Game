package client;

import config.Cfg;
import decoder.DataHelper;
import lombok.Data;
import lombok.SneakyThrows;
import print.ConsolePrint;
import session.GameBoard;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class XOClient extends Thread {
    private DatagramSocket socket;

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Старт клиента --->");
        socket = new DatagramSocket();
            System.out.println("Старт сессии клиента: ");
            GameBoard gameBoard = new GameBoard();
            byte[] boardByteArray = DataHelper.serialize(gameBoard);
            DatagramPacket dp = new DatagramPacket(boardByteArray, boardByteArray.length, InetAddress.getByName(Cfg.HOST), Cfg.PORT);
            socket.send(dp);
            byte[] buffer = new byte[Cfg.BUFFER];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);
            byte[] data = reply.getData();
        GameBoard gameBoard1 = (GameBoard) DataHelper.deserialize(data);
        ConsolePrint.printToConsole(gameBoard1);
//            System.out.println("Сервер: " + reply.getAddress().getHostAddress() + ", порт: " + reply.getPort() + ", получил: " + s);

    }
}
