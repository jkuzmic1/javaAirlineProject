package tvz.hr.projekt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tvz.hr.projekt.HelloApplication;
import tvz.hr.projekt.changes.Changes;
import tvz.hr.projekt.changes.PossibleChanges;
import tvz.hr.projekt.entiteti.Flight;
import tvz.hr.projekt.iznimke.DataSourceException;
import tvz.hr.projekt.iznimke.NedozvoljenaPromjenaException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import static tvz.hr.projekt.entiteti.Flight.logger;

public class AdminAddFlights {

    @FXML
    private TextField flightId;
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private TextField departureDate;
    @FXML
    private TextField departureTime;
    @FXML
    private TextField arriveDate;
    @FXML
    private TextField arriveTime;
    @FXML
    private TextField numberOfBusiness;
    @FXML
    private TextField numberOfEconomy;
    @FXML
    private TextField numberOfFirstClass;


    public void SoldTickets(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminSoldTickets.fxml");
    }

    public void AddTickets(ActionEvent actionEvent) {
    }

    public void logout(ActionEvent actionEvent) {
        String contextText = "Are you sure you want to logout?";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contextText, ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.YES) {
            HelloApplication.showWindow("Registration.fxml");
        }
    }

    public void addFlight(ActionEvent actionEvent) throws DataSourceException, NedozvoljenaPromjenaException {

        String flightNumber = flightId.getText();
        String fromCity = from.getText();
        String toCity = to.getText();
        String departureDateStr = departureDate.getText();
        String departureTimeStr = departureTime.getText();
        String arriveDateStr = arriveDate.getText();
        String arriveTimeStr = arriveTime.getText();
        String businessSeats = numberOfBusiness.getText();
        String economySeats = numberOfEconomy.getText();
        String firstClassSeats = numberOfFirstClass.getText();

        LocalDate departureLocalDate = parseDate(departureDateStr, "dd-MM-yyyy");
        LocalDate arriveLocalDate = parseDate(arriveDateStr, "dd-MM-yyyy");

        if (flightNumber.isBlank() || fromCity.isBlank() || toCity.isBlank() || departureDateStr.isBlank() ||
                departureTimeStr.isBlank() || arriveDateStr.isBlank() || arriveTimeStr.isBlank() || businessSeats.isBlank() || economySeats.isBlank() || firstClassSeats.isBlank()) {
            logger.error("You need to fill up everything");
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to fill up everything", ButtonType.OK);
            alert.show();
            System.out.println("You need to fill up everything");
            return;
        }
        LocalDateTime departureDateTime = LocalDateTime.of(departureLocalDate, parseTime(departureTimeStr));
        LocalDateTime arriveDateTime = LocalDateTime.of(arriveLocalDate, parseTime(departureTimeStr));

        Integer business = Integer.valueOf(businessSeats);
        Integer economy = Integer.valueOf(economySeats);
        Integer firstClass = Integer.valueOf(firstClassSeats);

        Flight newFlight = new Flight(null,flightNumber, departureDateTime,arriveDateTime, fromCity, toCity, business, economy, firstClass);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add new model!", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.YES && newFlight != null) {
            HelloApplication.getDataSource().addFlight(newFlight);

            Changes changes = new Changes.Builder(newFlight.getFlightNumber(), PossibleChanges.ADDING_FLIGHT, LocalDateTime.now(), HelloApplication.userAdmin).addingFlight(flightNumber).build();
            HelloApplication.listaPromjena.add(changes);
            logger.info("New flight added " + flightNumber);
        }
        else{
            logger.error("New flight couldnt be added");
        }

    }
    private LocalDate parseDate(String dateStr, String pattern) {
        if (dateStr.isBlank()) {
            logger.error("Date is required.");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Date is required.", ButtonType.OK);
            alert.show();
            return null;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        try {
            return LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format. Use " + pattern);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid date format. Use " + pattern, ButtonType.OK);
            alert.show();
            return null;
        }
    }

    private LocalTime parseTime(String timeStr) {
        if (timeStr.isBlank()) {
            logger.error("Time is required.");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Time is required.", ButtonType.OK);
            alert.show();
            return null;
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(timeStr, timeFormatter);
        } catch (DateTimeParseException e) {
            logger.error("Invalid time format. Use HH:mm");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid time format. Use HH:mm", ButtonType.OK);
            alert.show();
            return null;
        }
    }
    public void discard(ActionEvent actionEvent) {
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
