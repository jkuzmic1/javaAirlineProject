package tvz.hr.projekt.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tvz.hr.projekt.HelloApplication;
import tvz.hr.projekt.entiteti.Flight;
import tvz.hr.projekt.entiteti.TicketType;
import tvz.hr.projekt.iznimke.DataSourceException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static tvz.hr.projekt.entiteti.Flight.logger;

public class FindFlightController {

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


    public FindFlightController() {
    }

    public void initialize() throws DataSourceException, SQLException {
        flightIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFlightNumber()));
        fromTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartureAirport()));
        toTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getArriveAirport()));
        departureDateColumn.setCellValueFactory(data -> {
            LocalDateTime departureTime = data.getValue().getDepartureTime();
            return new SimpleObjectProperty<>(departureTime);
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
    public void pretrazi() throws DataSourceException, SQLException{
        var filtrirano = HelloApplication.getDataSource().readFlights().stream()

                .filter(p -> p.getArriveAirport().contains(toFlight.getText()))

                .toList();

        flightTableView.setItems(FXCollections.observableList(filtrirano));
        logger.info("Pretrazeno!");
    }

//    private List<Flight> retrieveFlights(String from, String to, LocalDate date) {
//        // Implement the logic to retrieve flights based on the input criteria
//        // You can query the database or use any other data source
//        // Here, I'm returning a dummy list of flights for demonstration purposes
////        List<Flight> flights = new ArrayList<>();
////        flights.add(new Flight(1, "Airport A", LocalDateTime.now(), LocalDateTime.now(), "City B", "City B",11,12,12));
////        flights.add(new Flight(2, "Airport B", LocalDateTime.now(), LocalDateTime.now(), "City C", "City C", 11,12,12));
////        flights.add(new Flight(3, "Airport C", LocalDateTime.now(), LocalDateTime.now(), "City A", "City A", 11,12,12));
//
////        return flights;
//    }
//    public void findFlights() throws DataSourceException {
//        String fromText = fromFlight.getText();
//        String toText = toFlight.getText();
//        LocalDate selectedDate = datePicker.getValue();
//
//        List<Flight> flights = retrieveFlights(fromText, toText, selectedDate);
//
//        ObservableList<Flight> flightList = FXCollections.observableList(flights);
//        flightTableView.setItems(flightList);
//    }
//



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
