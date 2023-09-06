package tvz.hr.projekt.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import tvz.hr.projekt.HelloApplication;

import java.util.Optional;

public class GuestViewController {



    public void logout(ActionEvent actionEvent) {
        String contextText = "Are you sure you want to logout?";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contextText, ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.YES) {
            HelloApplication.showWindow("Registration.fxml");
        }
    }

    public void BuyTicket() {
        HelloApplication.showWindow("BuyTicket-view.fxml");
    }

    public void FindTicket() {
        HelloApplication.showWindow("FindFlight-view.fxml");
    }


    public void PurchasedTicket() {
        HelloApplication.showWindow("PurchasedTickets.fxml");
    }

}
