package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import server.XOServer;
import session.Cell;
import session.Status;
import util.WhoseMove;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
public class ServerController {

    private XOServer xoServer;

    @FXML
    private Button cell00;
    @FXML
    private Button cell01;
    @FXML
    private Button cell02;
    @FXML
    private Button cell10;
    @FXML
    private Button cell11;
    @FXML
    private Button cell12;
    @FXML
    private Button cell20;
    @FXML
    private Button cell21;
    @FXML
    private Button cell22;

    public ServerController() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        xoServer = new XOServer();
        executorService.execute(xoServer);
    }

    private boolean yourStep() {
        if (xoServer.getGameBoard() != null) {
            repaintGameBoard();
            if (xoServer.getGameBoard().getWhoseMove() != WhoseMove.SERVER) {
                // TODO: 24.06.2020 тут не твой ход
                log.info("НЕ ТВОЙ ХОД ШАКАЛ");
            }
            return xoServer.getGameBoard().getWhoseMove() == WhoseMove.SERVER;
        } else {
            return false;
        }
    }

    private void repaintGameBoard() {
        Cell[][] cells = xoServer.getGameBoard().getCells();
        cell00.setText(cells[0][0].getStatus().name());
        cell01.setText(cells[0][1].getStatus().name());
        cell02.setText(cells[0][2].getStatus().name());
        cell10.setText(cells[1][0].getStatus().name());
        cell11.setText(cells[1][1].getStatus().name());
        cell12.setText(cells[1][2].getStatus().name());
        cell20.setText(cells[2][0].getStatus().name());
        cell21.setText(cells[2][1].getStatus().name());
        cell22.setText(cells[2][2].getStatus().name());
    }

    private void responseStep() {
        xoServer.getGameBoard().setWhoseMove(WhoseMove.CLIENT);
    }

    public void setField00(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell00.setText("X");
            xoServer.getGameBoard().getCells()[0][0].setStatus(Status.X);
            responseStep();
        }
    }

    public void setField01(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell01.setText("X");
            xoServer.getGameBoard().getCells()[0][1].setStatus(Status.X);
            responseStep();
        }
    }

    public void setField02(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell02.setText("X");
            xoServer.getGameBoard().getCells()[0][2].setStatus(Status.X);
            responseStep();
        }
    }

    public void setField10(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell10.setText("X");
            xoServer.getGameBoard().getCells()[1][0].setStatus(Status.X);
            responseStep();
        }
    }

    public void setField11(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell11.setText("X");
            xoServer.getGameBoard().getCells()[1][1].setStatus(Status.X);
            responseStep();
        }
    }

    public void setField12(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell12.setText("X");
            xoServer.getGameBoard().getCells()[1][2].setStatus(Status.X);
            responseStep();
        }
    }

    public void setField20(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell20.setText("X");
            xoServer.getGameBoard().getCells()[2][0].setStatus(Status.X);
            responseStep();
        }
    }

    public void setField21(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell21.setText("X");
            xoServer.getGameBoard().getCells()[2][1].setStatus(Status.X);
            responseStep();
        }
    }

    public void setField22(MouseEvent mouseEvent) {
        if (yourStep()) {
            cell22.setText("X");
            xoServer.getGameBoard().getCells()[2][2].setStatus(Status.X);
            responseStep();
        }
    }
}
