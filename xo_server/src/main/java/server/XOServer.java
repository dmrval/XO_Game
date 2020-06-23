package server;

import config.Cfg;
import decoder.DataHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import print.ConsolePrint;
import session.GameBoard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

@Slf4j
public class XOServer extends Thread {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private byte[] buf;
    private GameBoard gameBoard;

    private BufferedReader cin;

    @SneakyThrows
    public XOServer() {
        buf = new byte[Cfg.BUFFER];
        cin = new BufferedReader(new InputStreamReader(System.in));

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
            ConsolePrint.printToConsole(gameBoard);
            // TODO: 23.06.2020 тут ходит SERVER
            cin.readLine();
            //
            pushData();
            log.info("Сервер отправил " + i++ + "ход");
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