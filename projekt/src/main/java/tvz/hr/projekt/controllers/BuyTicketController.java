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
import tvz.hr.projekt.entiteti.*;
import tvz.hr.projekt.iznimke.DataSourceException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static tvz.hr.projekt.entiteti.Flight.logger;

public class BuyTicketController {

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
    private TableColumn<Flight, String> price;
    @FXML
    private ChoiceBox<String> ticketTypeChoiceBox;
    private User loggedInUser;

    public void initialize() throws DataSourceException, SQLException {
        flightIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFlightNumber()));
        fromTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartureAirport()));
        toTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getArriveAirport()));
        departureDateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDepartureTime()));
        arriveDateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getArriveTime()));
        price.setCellValueFactory(data -> new SimpleStringProperty(calculatePriceForFlight(data.getValue())));

        flightTableView.setItems(FXCollections.observableList(HelloApplication.getDataSource().readFlights()));

        ObservableList<String> ticketTypes = FXCollections.observableArrayList(
                "Economy",
                "Business",
                "First Class"
        );
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

            updatePriceColumn();
        });
        ticketTypeChoiceBox.setItems(ticketTypes);
        //updatePriceColumn();
    }

    private void updatePriceColumn() {
        //System.out.println("hihihi1");
        ObservableList<Flight> flights = flightTableView.getItems();
        for (Flight flight : flights) {
            String selectedTicketType = ticketTypeChoiceBox.getValue();

            if (selectedTicketType != null) {
               // System.out.println("asde");

                TicketType ticketType = getTicketTypeFromChoice(selectedTicketType);
                double price = calculatePrice(flight, ticketType);
                flight.setPrice(price);
               // System.out.println("hihihi");
            }
        }
        flightTableView.refresh();
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
    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public void buyTicket(ActionEvent event) {
        Flight selectedFlight = flightTableView.getSelectionModel().getSelectedItem();
        String selectedTicketType = ticketTypeChoiceBox.getValue();


        if (selectedTicketType == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select type of seat.", ButtonType.OK);
            alert.show();

            logger.warn("as");
            return;
        }

        TicketType ticketType = null;
        switch (selectedTicketType) {
            case "Economy":
                ticketType = TicketType.ECONOMY;
                break;
            case "Business":
                ticketType = TicketType.BUSINESS;
                break;
            case "First Class":
                ticketType = TicketType.FIRST_CLASS;
                break;
        }

        int numberOfSeatsToReserve = 1;
        try {
            double calculatedPrice = Flight.calculateSeatValue(ticketType, new GoogleApi(
                    selectedFlight.getDepartureAirport(), selectedFlight.getArriveAirport()
            ));

            User loggedInUser = UserManager.getInstance().getLoggedInUser();
            int userId = loggedInUser.getId();
            String flightId = String.valueOf(selectedFlight.getId());

            String departureCity = selectedFlight.getDepartureAirport();
            String arriveCity = selectedFlight.getArriveAirport();
            LocalDateTime departureDate = selectedFlight.getDepartureTime();
            LocalDateTime arriveDate = selectedFlight.getArriveTime();
            double ticketPrice = selectedFlight.getPrice();


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to buy ticket?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.YES && selectedFlight != null) {

                HelloApplication.getDataSource().buyTicket(userId, flightId, departureCity, arriveCity, departureDate, arriveDate, ticketPrice);

                selectedFlight.setPrice(calculatedPrice);
                selectedFlight.ReservationOfSeats(1, ticketType);
                selectedFlight.ReservationOfSeats(numberOfSeatsToReserve, ticketType);
                HelloApplication.getDataSource().updateFlightSeats(selectedFlight);

                Changes changes = new Changes.Builder(selectedFlight.getFlightNumber(), PossibleChanges.BUYING_TICKET, LocalDateTime.now(), HelloApplication.userGuest).buyingTicket(String.valueOf(selectedFlight)).build();
                HelloApplication.listaPromjena.add(changes);
                logger.info("Ticket purchased successfully!");

            }
            else  {
                logger.error("Failed to purchase ticket!");

            }
        } catch (Exception e) {
            System.out.println("Failed to purchase ticket: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING, "Failed to purchase ticket!", ButtonType.OK);
            alert.show();
        }

    }

    private String calculatePriceForFlight(Flight flight) {
        String selectedTicketType = ticketTypeChoiceBox.getValue();
        if (selectedTicketType != null) {
            TicketType ticketType = getTicketTypeFromChoice(selectedTicketType);
            double price = calculatePrice(flight, ticketType);
            return String.format("$%.2f", price);
         }
        return "/";
    }
    private TicketType getTicketTypeFromChoice(String selectedType) {
        if("Economy".equals(selectedType)){
            return  TicketType.ECONOMY;
        }else if("Business".equals(selectedType)){
            return TicketType.BUSINESS;
        } else if ("First Class".equals(selectedType)) {
            return TicketType.FIRST_CLASS;
        }else {
            throw new IllegalArgumentException("Invalid tickett type: " + selectedType);
        }
    }

    private double calculatePrice(Flight flight, TicketType ticketType) {
        return Flight.calculateSeatValue(ticketType, new GoogleApi(flight.getDepartureAirport(), flight.getArriveAirport()));
    }

    public void findTicket() throws DataSourceException {
//        var filtrirano = HelloApplication.getDataSource().readFlights().stream()
//                .filter(p -> p.getDepartureAirport().contains(fromFlight.getText()))
//                .toList();
//
//        flightTableView.setItems(FXCollections.observableList(filtrirano));
//        logger.info("Pretrazeno!");
    }
}
