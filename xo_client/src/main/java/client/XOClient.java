package client;

import config.Cfg;
import decoder.DataHelper;
import lombok.SneakyThrows;
import session.GameBoard;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class XOClient extends Thread {
    private DatagramSocket socket;

    private byte[] buf;

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Старт клиента --->");
        socket = new DatagramSocket();
        while (true) {
            System.out.println("Старт сессии клиента: ");
            GameBoard gameBoard = new GameBoard();
            byte[] boardByteArray = DataHelper.serialize(gameBoard);
            DatagramPacket dp = new DatagramPacket(boardByteArray, boardByteArray.length, InetAddress.getByName(Cfg.HOST), Cfg.PORT);
            socket.send(dp);
            byte[] buffer = new byte[Cfg.BUFFER];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);
//            byte[] data = reply.getData();
//            s = new String(data, 0, reply.getLength());
//            System.out.println("Сервер: " + reply.getAddress().getHostAddress() + ", порт: " + reply.getPort() + ", получил: " + s);
        }

    }
}
