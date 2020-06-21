package server;

import config.ServerConfig;
import lombok.SneakyThrows;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class XOServer extends Thread {

    private DatagramSocket socket;

    @SneakyThrows
    public void run() {
        socket = new DatagramSocket(ServerConfig.GAME_PORT);
        byte[] buffer = new byte[ServerConfig.BUFFER_SIZE];
        DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
        System.out.println("Ожидаем данные...");
        while (true) {
            socket.receive(incoming);
            byte[] data = incoming.getData();
            String s = new String(data, 0, incoming.getLength());
            System.out.println("Сервер получил: " + s);
            DatagramPacket dp = new DatagramPacket(s.getBytes(), s.getBytes().length, incoming.getAddress(), incoming.getPort());
            socket.send(dp);
        }
    }
}