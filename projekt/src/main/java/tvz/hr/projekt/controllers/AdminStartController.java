package tvz.hr.projekt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import tvz.hr.projekt.HelloApplication;

import java.util.Optional;

import static java.lang.System.exit;

public class AdminStartController {
    @FXML
    MenuItem findTicketsView = new MenuItem();
    @FXML
    MenuItem soldTicketsView = new MenuItem();
    @FXML
    MenuItem addTicketsView = new MenuItem();
    @FXML
    MenuItem changesView = new MenuItem();
    @FXML
    MenuItem logutView = new MenuItem();

    public void deleteTicket() {
        HelloApplication.showWindow("AdminDeleteTickets.fxml");
    }
    public void SoldTickets(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminSoldTickets.fxml");
    }

    public void AddTickets(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminAddFlights-view.fxml");

    }

    public void logout(ActionEvent actionEvent) {
        String contextText = "Are you sure you want to logout?";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contextText, ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.YES) {
            HelloApplication.showWindow("Registration.fxml");
        }
    }

    public void changes(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminChanges-view.fxml");
    }

    public void changeFlight(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminChangeFlight-view.fxml");

    }
}
