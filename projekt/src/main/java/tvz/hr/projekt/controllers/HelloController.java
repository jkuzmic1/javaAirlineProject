package tvz.hr.projekt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tvz.hr.projekt.HelloApplication;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void login() {
        HelloApplication.showWindow("BuyTicket-view.fxml");
    }

}