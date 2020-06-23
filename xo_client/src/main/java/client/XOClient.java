package client;

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
import java.net.InetAddress;

@Slf4j
public class XOClient extends Thread {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private byte[] buf;
    private GameBoard gameBoard;

    private BufferedReader cin;



    @SneakyThrows
    public XOClient (){
        buf = new byte[Cfg.BUFFER];
        cin = new BufferedReader(new InputStreamReader(System.in));
    }

    @SneakyThrows
    @Override
    public void run() {
        socket = new DatagramSocket();
        int i = 0;
        gameBoard = new GameBoard();
        while (true) {
            log.info("Введите сообщение серверу: ");
            //TODO: 23.06.2020 тут ходит CLIENT
            String s = cin.readLine();
            //
            pushData();
            log.info("Клиент отправил " + i++ + "ход");
            pullData();
            log.info("Клиент получил " + i++ + " ход");
            ConsolePrint.printToConsole(gameBoard);
        }
    }



    @SneakyThrows
    private void pullData() {
        buf = new byte[Cfg.BUFFER];
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        buf = packet.getData();
        gameBoard = (GameBoard) DataHelper.deserialize(packet.getData());
    }

    @SneakyThrows
    private void pushData() {
        buf = DataHelper.serialize(gameBoard);
        packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(Cfg.HOST), Cfg.PORT);
        socket.send(packet);
    }
}
