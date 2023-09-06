package tvz.hr.projekt.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tvz.hr.projekt.HelloApplication;
import tvz.hr.projekt.entiteti.PurchasedFlight;
import tvz.hr.projekt.entiteti.User;
import tvz.hr.projekt.entiteti.UserManager;
import tvz.hr.projekt.iznimke.DataSourceException;
import javafx.fxml.FXML;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PurchasedController {

    @FXML
    private TableView<PurchasedFlight> purchasedTableView;
    @FXML
    private TableColumn<PurchasedFlight,String> fromColumn;
    @FXML
    private TableColumn<PurchasedFlight,String> toColumn;
    @FXML
    private TableColumn<PurchasedFlight,LocalDateTime> departureColumn;
    @FXML
    private TableColumn<PurchasedFlight,LocalDateTime> arriveColumn;
    @FXML
    private TableColumn<PurchasedFlight,Double> priceColumn;
    @FXML
    private TableColumn<PurchasedFlight,LocalDateTime> dateColumn;

    public void initialize() throws DataSourceException{
//        System.out.println("Started");

        fromColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartureCity()));
        toColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getArriveCity()));
        departureColumn.setCellValueFactory(data -> {
            LocalDateTime departureTime = data.getValue().getDepartureDate();
            return new SimpleObjectProperty<>(departureTime);
        });
        arriveColumn.setCellValueFactory(data -> {
            LocalDateTime departureTime = data.getValue().getArriveDate();
            return new SimpleObjectProperty<>(departureTime);
        });
        priceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        dateColumn.setCellValueFactory(data -> {
            LocalDateTime departureTime = data.getValue().getPurchaseDate();
            return new SimpleObjectProperty<>(departureTime);
        });
        User loggedInUser = UserManager.getInstance().getLoggedInUser();
        List<PurchasedFlight> purchasedFlights = HelloApplication.getDataSource().fetchPurchasedFlights(loggedInUser.getId());

//        for (PurchasedFlight purchasedFlight : purchasedFlights) {
//            System.out.println(purchasedFlight.toString());
//        }

        purchasedTableView.setItems(FXCollections.observableList(purchasedFlights));

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

    public void logout(ActionEvent actionEvent) {
        String contextText = "Are you sure you want to logout?";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contextText, ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.YES) {
            HelloApplication.showWindow("Registration.fxml");
        }
    }
}
