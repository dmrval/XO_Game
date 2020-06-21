package client;


import config.ClientConfig;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class XOClient extends Thread {
    private DatagramSocket socket;
    BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

    @SneakyThrows
    @Override
    public void run() {
        socket = new DatagramSocket();
        while (true) {
            System.out.println("Введите сообщение серверу: ");
            String s = (String) cin.readLine();
            byte[] b = s.getBytes();
            DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName(ClientConfig.ADDRESS), ClientConfig.GAME_PORT);
            socket.send(dp);
            byte[] buffer = new byte[ClientConfig.BUFFER_SIZE];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);
            byte[] data = reply.getData();
            s = new String(data, 0, reply.getLength());
            System.out.println("Сервер: " + reply.getAddress().getHostAddress() + ", порт: " + reply.getPort() + ", получил: " + s);
        }
    }
}
