package tvz.hr.projekt.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tvz.hr.projekt.HelloApplication;
import tvz.hr.projekt.entiteti.Flight;
import tvz.hr.projekt.entiteti.PurchasedFlight;
import tvz.hr.projekt.iznimke.DataSourceException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AdminSoldTickets {

    public static final DateTimeFormatter formater = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    @FXML
    private TableView<PurchasedFlight> soldTableView;
    @FXML
    private TableColumn<PurchasedFlight,String> flightNumber;
    @FXML
    private TableColumn<PurchasedFlight,String> fromColumn;
    @FXML
    private TableColumn<PurchasedFlight,String> toColumn;
    @FXML
    private TableColumn<PurchasedFlight, LocalDateTime> departureColumn;
    @FXML
    private TableColumn<PurchasedFlight,LocalDateTime> arriveColumn;
    @FXML
    private TableColumn<PurchasedFlight,Double> priceColumn;
    @FXML
    private TableColumn<PurchasedFlight,LocalDateTime> dateColumn;

    public void initialize() throws DataSourceException, SQLException {


        flightNumber.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFlightId()));
        fromColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartureCity()));
        toColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getArriveCity()));
        departureColumn.setCellFactory(column -> {
            return new TableCell<PurchasedFlight, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        // Format LocalDateTime using the shared formatter
                        String formattedDate = item.format(formater);
                        setText(formattedDate);
                    }
                }
            };
        });
        departureColumn.setCellValueFactory(data -> {
            LocalDateTime departureTime = data.getValue().getDepartureDate();
            return new SimpleObjectProperty<>(departureTime);
        });
        arriveColumn.setCellFactory(column -> {
            return new TableCell<PurchasedFlight, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        // Format LocalDateTime using the shared formatter
                        String formattedDate = item.format(formater);
                        setText(formattedDate);
                    }
                }
            };
        });
        arriveColumn.setCellValueFactory(data -> {
            LocalDateTime departureTime = data.getValue().getArriveDate();
            return new SimpleObjectProperty<>(departureTime);
        });
        priceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        dateColumn.setCellFactory(column -> {
            return new TableCell<PurchasedFlight, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        // Format LocalDateTime using the shared formatter
                        String formattedDate = item.format(formater);
                        setText(formattedDate);
                    }
                }
            };
        });
        dateColumn.setCellValueFactory(data -> {
            LocalDateTime departureTime = data.getValue().getPurchaseDate();
            return new SimpleObjectProperty<>(departureTime);
        });

        soldTableView.setItems(FXCollections.observableList(HelloApplication.getDataSource().readPurchasedFlights()));
    }

    public void AddTickets(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminAddFlights-view.fxml");
    }


    public void SoldTickets(ActionEvent actionEvent) {
    }



    public void logout(ActionEvent actionEvent) {
        String contextText = "Are you sure you want to logout?";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contextText, ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.YES) {
            HelloApplication.showWindow("Registration.fxml");
        }
    }

    public void deleteTicket(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminDeleteTickets.fxml");
    }

    public void Change(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminChanges-view.fxml");

    }

    public void changeFlight(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminChangeFlight-view.fxml");

    }
}
