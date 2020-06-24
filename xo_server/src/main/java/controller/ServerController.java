package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import server.XOServer;
import session.Status;
import util.WhoseMove;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
public class ServerController {

    private XOServer xoServer;

    @FXML
    Button cell00;
    @FXML
    Button cell01;
    @FXML
    Button cell02;

    public ServerController() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        xoServer = new XOServer();
        executorService.execute(xoServer);
    }

    public void setField00(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell00.setText("X");
            xoServer.getGameBoard().getCells()[0][0].setStatus(Status.X);
        }
    }

    private boolean yourStep() {
        if (xoServer.getGameBoard() != null) {
            if (xoServer.getGameBoard().getWhoseMove() != WhoseMove.SERVER) {
                // TODO: 24.06.2020 тут не твой ход
                log.info("НЕ ТВОЙ ХОД ШАКАЛ");
            }
            return xoServer.getGameBoard().getWhoseMove() == WhoseMove.SERVER;
        }
        else {
            return false;
        }
    }

    public void setField01(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell01.setText("X");
            xoServer.getGameBoard().getCells()[0][1].setStatus(Status.X);
        }
    }

    public void setField02(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell02.setText("X");
            xoServer.getGameBoard().getCells()[0][2].setStatus(Status.X);
        }
    }

}
