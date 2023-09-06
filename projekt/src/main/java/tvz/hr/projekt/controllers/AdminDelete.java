package tvz.hr.projekt.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tvz.hr.projekt.HelloApplication;
import tvz.hr.projekt.changes.Changes;
import tvz.hr.projekt.changes.PossibleChanges;
import tvz.hr.projekt.entiteti.Flight;
import tvz.hr.projekt.entiteti.TicketType;
import tvz.hr.projekt.iznimke.NedozvoljenaPromjenaException;
import tvz.hr.projekt.iznimke.DataSourceException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static tvz.hr.projekt.entiteti.Flight.logger;

public class AdminDelete {
    public static final DateTimeFormatter formater = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    @FXML
    private TextField fromFlight;
    @FXML
    private TextField toFlight;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Flight> flightTableView;
    @FXML
    private TableColumn<Flight, String> flightIdColumn;
    @FXML
    private TableColumn<Flight, String> fromTableColumn;
    @FXML
    private TableColumn<Flight, String> toTableColumn;
    @FXML
    private TableColumn<Flight, LocalDateTime> departureDateColumn;

    @FXML
    private TableColumn<Flight, LocalDateTime> arriveDateColumn;

    @FXML
    private ChoiceBox<String> ticketTypeChoiceBox;


    public void initialize() throws DataSourceException, SQLException {
        flightIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFlightNumber()));
        fromTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartureAirport()));
        toTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getArriveAirport()));
        departureDateColumn.setCellFactory(column -> {
            return new TableCell<Flight, LocalDateTime>() {
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
        departureDateColumn.setCellValueFactory(data -> {
            LocalDateTime departureTime = data.getValue().getDepartureTime();
            return new SimpleObjectProperty<>(departureTime);
        });
        arriveDateColumn.setCellFactory(column -> {
            return new TableCell<Flight, LocalDateTime>() {
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

        arriveDateColumn.setCellValueFactory(data -> {
            LocalDateTime departureTime = data.getValue().getArriveTime();
            return new SimpleObjectProperty<>(departureTime);
        });

        flightTableView.setItems(FXCollections.observableList(HelloApplication.getDataSource().readFlights()));

        ObservableList<String> ticketTypes = FXCollections.observableArrayList(
                "Economy",
                "Business",
                "First Class"
        );

        ticketTypeChoiceBox.setItems(ticketTypes);
        ticketTypeChoiceBox.setOnAction(event -> {
            String selectedType = ticketTypeChoiceBox.getValue();
            TicketType ticketType;

            switch (selectedType) {
                case "Economy":
                    ticketType = TicketType.ECONOMY;
                    break;
                case "Business":
                    ticketType = TicketType.BUSINESS;
                    break;
                case "First Class":
                    ticketType = TicketType.FIRST_CLASS;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid ticket type: " + selectedType);
            }
        });

    }
    public void pretrazi() throws DataSourceException, SQLException {
        var filtrirano = HelloApplication.getDataSource().readFlights().stream()
                .filter(p -> p.getDepartureAirport().contains(toFlight.getText()))
                .toList();

        flightTableView.setItems(FXCollections.observableList(filtrirano));
        logger.info("Pretrazeno!");
    }

    public void DeleteFlights(ActionEvent actionEvent) {
    }

    public void AddTickets(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminAddFlights-view.fxml");
    }

    public void SoldTickets(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminSoldTickets.fxml");
    }

    public void deleteTicket(ActionEvent actionEvent) throws DataSourceException, NedozvoljenaPromjenaException {
        var all = flightTableView.getItems();
        var flightDelete = flightTableView.getSelectionModel().getSelectedItem();



        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this flight?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.YES) {
            flightTableView.getItems().remove(flightDelete);

            HelloApplication.getDataSource().deleteFlight(flightDelete);

            Changes changes = new Changes.Builder(flightDelete.getFlightNumber(), PossibleChanges.DELETING_FLIGHT, LocalDateTime.now(), HelloApplication.userAdmin).deletingFlight(flightDelete.getFlightNumber()).build();
            HelloApplication.listaPromjena.add(changes);
            logger.info("Flight deleted " + flightDelete.getFlightNumber());
        }
        else{
            logger.error("New flight couldnt be deleted");
        }


    }

    public void changes(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminChanges-view.fxml");
    }

    public void logout(ActionEvent actionEvent) {
        String contextText = "Are you sure you want to logout?";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contextText, ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.YES) {
            HelloApplication.showWindow("Registration.fxml");
        }
    }

    public void changeFlight(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminChangeFlight-view.fxml");

    }
}
