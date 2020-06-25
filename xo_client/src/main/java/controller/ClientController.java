package controller;

import client.XOClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import session.Cell;
import session.Status;
import util.WhoseMove;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
public class ClientController {

    private XOClient xoClient;

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

    public ClientController() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        xoClient = new XOClient(this);
        executorService.execute(xoClient);
    }

    private boolean yourStep() {
        if (xoClient.getGameBoard() != null) {
            repaintGameBoard();
            if (xoClient.getGameBoard().getWhoseMove() != WhoseMove.CLIENT) {
                // TODO: 24.06.2020 тут не твой ход
                log.info("НЕ ТВОЙ ХОД ШАКАЛ");
            }
            return xoClient.getGameBoard().getWhoseMove() == WhoseMove.CLIENT;
        } else {
            return false;
        }
    }

    public void repaintGameBoard() {
        Cell[][] cells = xoClient.getGameBoard().getCells();
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
        xoClient.getGameBoard().setWhoseMove(WhoseMove.SERVER);
    }

    public void setField00(MouseEvent mouseEvent) {
        if (yourStep()) {
            if (xoClient.getGameBoard().getCells()[0][0].getStatus()==Status.X) {
                // TODO: 24.06.2020 нельзя тут сделать ход
                log.info("ТУТ СТОИТ знак противника");
                return;
            }
            cell00.setText("O");
            xoClient.getGameBoard().getCells()[0][0].setStatus(Status.O);
            responseStep();
        }
    }

    public void setField01(MouseEvent mouseEvent) {
        if (yourStep()) {
            if (xoClient.getGameBoard().getCells()[0][1].getStatus()==Status.X) {
                // TODO: 24.06.2020 нельзя тут сделать ход
                log.info("ТУТ СТОИТ знак противника");
                return;
            }
            cell01.setText("O");
            xoClient.getGameBoard().getCells()[0][1].setStatus(Status.O);
            responseStep();
        }
    }

    public void setField02(MouseEvent mouseEvent) {
        if (yourStep()) {
            if (xoClient.getGameBoard().getCells()[0][2].getStatus()==Status.X) {
                // TODO: 24.06.2020 нельзя тут сделать ход
                log.info("ТУТ СТОИТ знак противника");
                return;
            }
            cell02.setText("O");
            xoClient.getGameBoard().getCells()[0][2].setStatus(Status.O);
            responseStep();
        }
    }

    public void setField10(MouseEvent mouseEvent) {
        if (yourStep()) {
            if (xoClient.getGameBoard().getCells()[1][0].getStatus()==Status.X) {
                // TODO: 24.06.2020 нельзя тут сделать ход
                log.info("ТУТ СТОИТ знак противника");
                return;
            }
            cell10.setText("O");
            xoClient.getGameBoard().getCells()[1][0].setStatus(Status.O);
            responseStep();
        }
    }

    public void setField11(MouseEvent mouseEvent) {
        if (yourStep()) {
            if (xoClient.getGameBoard().getCells()[1][1].getStatus()==Status.X) {
                // TODO: 24.06.2020 нельзя тут сделать ход
                log.info("ТУТ СТОИТ знак противника");
                return;
            }
            cell11.setText("O");
            xoClient.getGameBoard().getCells()[1][1].setStatus(Status.O);
            responseStep();
        }
    }

    public void setField12(MouseEvent mouseEvent) {
        if (yourStep()) {
            if (xoClient.getGameBoard().getCells()[1][2].getStatus()==Status.X) {
                // TODO: 24.06.2020 нельзя тут сделать ход
                log.info("ТУТ СТОИТ знак противника");
                return;
            }
            cell12.setText("O");
            xoClient.getGameBoard().getCells()[1][2].setStatus(Status.O);
            responseStep();
        }
    }

    public void setField20(MouseEvent mouseEvent) {
        if (yourStep()) {
            if (xoClient.getGameBoard().getCells()[2][0].getStatus()==Status.X) {
                // TODO: 24.06.2020 нельзя тут сделать ход
                log.info("ТУТ СТОИТ знак противника");
                return;
            }
            cell20.setText("O");
            xoClient.getGameBoard().getCells()[2][0].setStatus(Status.O);
            responseStep();
        }
    }

    public void setField21(MouseEvent mouseEvent) {
        if (yourStep()) {
            if (xoClient.getGameBoard().getCells()[2][1].getStatus()==Status.X) {
                // TODO: 24.06.2020 нельзя тут сделать ход
                log.info("ТУТ СТОИТ знак противника");
                return;
            }
            cell21.setText("O");
            xoClient.getGameBoard().getCells()[2][1].setStatus(Status.O);
            responseStep();
        }
    }

    public void setField22(MouseEvent mouseEvent) {
        if (yourStep()) {
            if (xoClient.getGameBoard().getCells()[2][2].getStatus()==Status.X) {
                // TODO: 24.06.2020 нельзя тут сделать ход
                log.info("ТУТ СТОИТ знак противника");
                return;
            }
            cell22.setText("O");
            xoClient.getGameBoard().getCells()[2][2].setStatus(Status.O);
            responseStep();
        }
    }
}
