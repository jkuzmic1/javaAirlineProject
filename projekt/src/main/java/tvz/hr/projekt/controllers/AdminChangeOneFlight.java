package tvz.hr.projekt.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tvz.hr.projekt.HelloApplication;
import tvz.hr.projekt.changes.Changes;
import tvz.hr.projekt.changes.PossibleChanges;
import tvz.hr.projekt.entiteti.Flight;
import tvz.hr.projekt.iznimke.NedozvoljenaPromjenaException;
import tvz.hr.projekt.iznimke.DataSourceException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import static tvz.hr.projekt.entiteti.Flight.logger;

public class AdminChangeOneFlight {
    public static final DateTimeFormatter formater = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

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
    @FXML
    private TableView<Flight> tableFlightView;
    @FXML
    private TableColumn<Flight, String> IDofFlight;
    @FXML
    private TableColumn<Flight, String> From;
    @FXML
    private TableColumn<Flight, String> To;
    @FXML
    private TableColumn<Flight, LocalDateTime> DepartureDate;
    @FXML
    private TableColumn<Flight, LocalDateTime> ArriveDate;
    @FXML
    private TableColumn<Flight, Integer> Business;
    @FXML
    private TableColumn<Flight, Integer> Economy;
    @FXML
    private TableColumn<Flight, Integer> FirstClass;
    private static int selectedFlightId;
    private static String selectedFlightNumber;
    private static String selectedFrom;
    private static String selectedTo;
    private static int selectedBusiness;
    private static int selectedEconomy;
    private static int selectedFirstClass;
    private static LocalDateTime selectedDepartureDate;
    private static LocalDateTime selectedArriveDate;

    public void initialize()throws DataSourceException, SQLException {
        IDofFlight.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFlightNumber()));
        From.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartureAirport()));
        To.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getArriveAirport()));
        DepartureDate.setCellFactory(column -> {
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
        DepartureDate.setCellValueFactory(data -> {
            LocalDateTime departureTime = data.getValue().getDepartureTime();
            return new SimpleObjectProperty<>(departureTime);
        });
        ArriveDate.setCellFactory(column -> {
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
        ArriveDate.setCellValueFactory(data -> {
            LocalDateTime departureTime = data.getValue().getArriveTime();
            return new SimpleObjectProperty<>(departureTime);
        });

        Business.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBussiness()));
        Economy.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getEconomy()));
        FirstClass.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFirstClass()));

        tableFlightView.setItems(FXCollections.observableList(HelloApplication.getDataSource().readFlights()));
    }

    public void discard(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminChangeFlight-view.fxml");
    }


    public void setSelectedFlightId(int selectedFlightId) {
        this.selectedFlightId = selectedFlightId;
        //System.out.println(selectedFlightId);
    }

    public static int getSelectedFlightId() {
        return selectedFlightId;
    }

    public static String getSelectedFlightNumber() {
        return selectedFlightNumber;
    }

    public  void setSelectedFlightNumber(String selectedFlightNumber) {
        AdminChangeOneFlight.selectedFlightNumber = selectedFlightNumber;
    }

    public static String getSelectedFrom() {
        return selectedFrom;
    }

    public  void setSelectedFrom(String selectedFrom) {
        AdminChangeOneFlight.selectedFrom = selectedFrom;
    }

    public static String getSelectedTo() {
        return selectedTo;
    }

    public  void setSelectedTo(String selectedTo) {
        AdminChangeOneFlight.selectedTo = selectedTo;
    }

    public static int getSelectedBusiness() {
        return selectedBusiness;
    }

    public  void setSelectedBusiness(int selectedBusiness) {
        AdminChangeOneFlight.selectedBusiness = selectedBusiness;
    }

    public static int getSelectedEconomy() {
        return selectedEconomy;
    }

    public  void setSelectedEconomy(int selectedEconomy) {
        AdminChangeOneFlight.selectedEconomy = selectedEconomy;
    }

    public static int getSelectedFirstClass() {
        return selectedFirstClass;
    }

    public  void setSelectedFirstClass(int selectedFirstClass) {
        AdminChangeOneFlight.selectedFirstClass = selectedFirstClass;
    }

    public static LocalDateTime getSelectedDepartureDate() {
        return selectedDepartureDate;
    }

    public void setSelectedDepartureDate(LocalDateTime selectedDepartureDate) {
        AdminChangeOneFlight.selectedDepartureDate = selectedDepartureDate;
    }

    public static LocalDateTime getSelectedArriveDate() {
        return selectedArriveDate;
    }

    public void setSelectedArriveDate(LocalDateTime selectedArriveDate) {
        AdminChangeOneFlight.selectedArriveDate = selectedArriveDate;
    }

    public void editFlight(ActionEvent actionEvent) throws DataSourceException, SQLException, NedozvoljenaPromjenaException {
        String idFlight = flightId.getText();
        String fromCity = from.getText();
        String toCity = to.getText();
        String departureDateStr = departureDate.getText();
        String departureTimeStr = departureTime.getText();
        String arriveDateStr = arriveDate.getText();
        String arriveTimeStr = arriveTime.getText();
        String numberOfBusinessStr = numberOfBusiness.getText();
        String numberOfEconomyStr = numberOfEconomy.getText();
        String numberOfFirstClassStr = numberOfFirstClass.getText();

        LocalDate departureLocalDate = parseDate(departureDateStr, "dd-MM-yyyy");
        LocalDate arriveLocalDate = parseDate(arriveDateStr, "dd-MM-yyyy");

        LocalDateTime departureDateTime = LocalDateTime.of(departureLocalDate, parseTime(departureTimeStr));
        LocalDateTime arriveDateTime = LocalDateTime.of(arriveLocalDate, parseTime(arriveTimeStr));


        Integer numberOfBusiness = Integer.parseInt(numberOfBusinessStr);
        Integer numberOfEconomy = Integer.parseInt(numberOfEconomyStr);
        Integer numberOfFirstClass = Integer.parseInt(numberOfFirstClassStr);


        Integer a = AdminChangeOneFlight.getSelectedFlightId();
        String flightNumber = AdminChangeOneFlight.getSelectedFlightNumber();
        String From = AdminChangeOneFlight.getSelectedFrom();
        String To = AdminChangeOneFlight.getSelectedTo();
        Integer businessSeats = AdminChangeOneFlight.getSelectedBusiness();
        Integer economySeats = AdminChangeOneFlight.getSelectedEconomy();
        Integer firstClassSeats = AdminChangeOneFlight.getSelectedFirstClass();
        LocalDateTime departureTime = AdminChangeOneFlight.getSelectedDepartureDate();
        LocalDateTime arriveTime = AdminChangeOneFlight.getSelectedArriveDate();


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change ticket?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.YES) {

            Flight updatedFlight = new Flight(a, idFlight, departureDateTime, arriveDateTime, fromCity, toCity, numberOfBusiness, numberOfEconomy, numberOfFirstClass);
            HelloApplication.getDataSource().updateFlight(updatedFlight);

            tableFlightView.getItems().setAll(HelloApplication.getDataSource().readFlights());

            boolean happened = false;

            if(!flightNumber.equals( flightId.getText())){
                if(!happened) {
                    Changes changes = new Changes.Builder(updatedFlight.getFlightNumber(), PossibleChanges.CHANGING_TICKET, LocalDateTime.now(), HelloApplication.userAdmin)
                            .changingTicket(flightNumber, String.valueOf(flightId.getText()))
                            .build();
                    HelloApplication.listaPromjena.add(changes);
                    logger.info("Ticket changed successfully!");
                    happened = true;
                }
            }
            happened = false;
            if(!From.equals(from.getText())){
                if(!happened) {
                    Changes changes = new Changes.Builder(updatedFlight.getFlightNumber(), PossibleChanges.CHANGING_TICKET, LocalDateTime.now(), HelloApplication.userAdmin)
                        .changingTicket(From, fromCity)
                        .build();
                HelloApplication.listaPromjena.add(changes);
                logger.info("Ticket changed successfully!");
                    happened = true;
                }
            }
            happened = false;
            if(!To.equals(to.getText())){
                if(!happened) {
                Changes changes = new Changes.Builder(updatedFlight.getFlightNumber(), PossibleChanges.CHANGING_TICKET, LocalDateTime.now(), HelloApplication.userAdmin)
                        .changingTicket(To, toCity)
                        .build();
                HelloApplication.listaPromjena.add(changes);
                logger.info("Ticket changed successfully!");
                    happened = true;
                }
            }
            happened = false;
            if(businessSeats != numberOfBusiness){
                if(!happened) {
                Changes changes = new Changes.Builder(updatedFlight.getFlightNumber(), PossibleChanges.CHANGING_SEATS, LocalDateTime.now(), HelloApplication.userAdmin)
                        .changingSeats(businessSeats, numberOfBusiness)
                        .build();
                HelloApplication.listaPromjena.add(changes);
                    logger.info("Seats changed successfully!");
                    happened = true;
                }
            }
            happened = false;
            if(!economySeats.equals(numberOfEconomy)){
                if(!happened) {
                Changes changes = new Changes.Builder(updatedFlight.getFlightNumber(), PossibleChanges.CHANGING_SEATS, LocalDateTime.now(), HelloApplication.userAdmin)
                        .changingSeats(economySeats, numberOfEconomy)
                        .build();
                HelloApplication.listaPromjena.add(changes);
                    logger.info("Seats changed successfully!");
                    happened = true;
                }
            }
            happened = false;
            if(firstClassSeats != numberOfFirstClass){
                if(!happened) {
                Changes changes = new Changes.Builder(updatedFlight.getFlightNumber(), PossibleChanges.CHANGING_SEATS, LocalDateTime.now(), HelloApplication.userAdmin)
                        .changingSeats(economySeats, numberOfFirstClass)
                        .build();
                HelloApplication.listaPromjena.add(changes);
                logger.info("Seats changed successfully!");
                    happened = true;
                }
            }
            happened = false;
            if(!departureTime.equals(departureDateTime)){
                if(!happened) {
                    Changes changes = new Changes.Builder(updatedFlight.getFlightNumber(), PossibleChanges.CHANGING_TIME, LocalDateTime.now(), HelloApplication.userAdmin)
                        .changingTime(departureTime, departureDateTime)
                        .build();
                HelloApplication.listaPromjena.add(changes);
                logger.info("Time changed successfully!");
                    happened = true;
                }
            }
            happened = false;
            if(!arriveTime.equals(arriveDateTime)){
                if(!happened) {
                Changes changes = new Changes.Builder(updatedFlight.getFlightNumber(), PossibleChanges.CHANGING_TIME, LocalDateTime.now(), HelloApplication.userAdmin)
                        .changingTime(arriveTime, arriveDateTime)
                        .build();
                HelloApplication.listaPromjena.add(changes);
                logger.info("Time changed successfully!");
                    happened = true;
                }
            }

            happened = false;


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
    public void openEditWindow() {
        HelloApplication.showWindow("AdminChangeOneFlight.fxml");

    }

}
