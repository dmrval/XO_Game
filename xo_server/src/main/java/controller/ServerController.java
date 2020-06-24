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
    @FXML
    Button cell10;
    @FXML
    Button cell11;
    @FXML
    Button cell12;
    @FXML
    Button cell20;
    @FXML
    Button cell21;
    @FXML
    Button cell22;

    public ServerController() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        xoServer = new XOServer();
        executorService.execute(xoServer);
    }

    private boolean yourStep() {
        if (xoServer.getGameBoard() != null) {
            if (xoServer.getGameBoard().getWhoseMove() != WhoseMove.SERVER) {
                // TODO: 24.06.2020 тут не твой ход
                log.info("НЕ ТВОЙ ХОД ШАКАЛ");
            }
            return xoServer.getGameBoard().getWhoseMove() == WhoseMove.SERVER;
        } else {
            return false;
        }
    }

    private void responseStep() {
        xoServer.getGameBoard().getCells()[0][1].setStatus(Status.X);
        xoServer.getGameBoard().setWhoseMove(WhoseMove.CLIENT);
    }


    public void setField00(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell00.setText("X");
            responseStep();
        }
    }

    public void setField01(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell01.setText("X");
            responseStep();
        }
    }

    public void setField02(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell02.setText("X");
            responseStep();
        }
    }

    public void setField10(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell10.setText("X");
            responseStep();
        }
    }

    public void setField11(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell11.setText("X");
            responseStep();
        }
    }

    public void setField12(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell12.setText("X");
            responseStep();
        }
    }

    public void setField20(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell20.setText("X");
            responseStep();
        }
    }

    public void setField21(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell21.setText("X");
            responseStep();
        }
    }

    public void setField22(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell22.setText("X");
            responseStep();
        }
    }
}
