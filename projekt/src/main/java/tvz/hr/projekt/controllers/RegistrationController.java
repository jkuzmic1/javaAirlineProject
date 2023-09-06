package tvz.hr.projekt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import tvz.hr.projekt.entiteti.User;
import tvz.hr.projekt.HelloApplication;
import tvz.hr.projekt.entiteti.UserManager;
import tvz.hr.projekt.iznimke.DataSourceException;
import tvz.hr.projekt.login.Login;

import static tvz.hr.projekt.entiteti.Flight.logger;

public class RegistrationController {

    @FXML
    TextField usernameTextField = new TextField();

    @FXML
    TextField passwordTextField = new TextField();
    static User loggedInUser;

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }
    public void login(ActionEvent actionEvent) throws DataSourceException {

        var username = usernameTextField.getText();
        var password = passwordTextField.getText();

        if(Login.loginUser(username, password)) {
            boolean userExists = HelloApplication.getDataSource().checkUserExists(username);
            int userId = HelloApplication.getDataSource().fetchUserIdByUsername(username);

            if(!userExists){
                HelloApplication.getDataSource().addUser(username, password);
            }
            //System.out.println(userId + username);
            User loggedInUser = new User(userId, username, password);
            UserManager.getInstance().setLoggedInUser(loggedInUser);

            HelloApplication.showWindow("Guest-view.fxml");
        } else if(Login.loginAdmin(username, password)) {
            User loggedInUser = new User(username, password);
            UserManager.getInstance().setLoggedInUser(loggedInUser);
            HelloApplication.showWindow("AdminStart.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Wrong password or username!", ButtonType.OK);
            alert.show();
            logger.warn("Wrong password or username!");
        }
    }
}
