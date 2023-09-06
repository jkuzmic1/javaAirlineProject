package tvz.hr.projekt.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tvz.hr.projekt.HelloApplication;
import tvz.hr.projekt.entiteti.Flight;
import tvz.hr.projekt.iznimke.DataSourceException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static tvz.hr.projekt.entiteti.Flight.logger;

public class AdminChangeFlightController {
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


    public void setAdminChangeOneFlightController(AdminChangeOneFlight controller) {
        this.adminChangeOneFlightController = controller;
    }

    private AdminChangeOneFlight adminChangeOneFlightController = new AdminChangeOneFlight();

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

        flightTableView.setRowFactory(tv -> {
            TableRow<Flight> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && !row.isEmpty()){
                    Flight selectedFlight = row.getItem();
                    int selectedFlightId = selectedFlight.getId();
                    String selectedFlightNumber = selectedFlight.getFlightNumber();
                    String from = selectedFlight.getDepartureAirport();
                    String to = selectedFlight.getArriveAirport();
                    int business = selectedFlight.getBussiness();
                    int economy = selectedFlight.getEconomy();
                    int firstClass = selectedFlight.getFirstClass();
                    LocalDateTime departureDate = selectedFlight.getDepartureTime();
                    LocalDateTime arriveDate = selectedFlight.getArriveTime();


                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit this flight?", ButtonType.YES, ButtonType.NO);

                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get() == ButtonType.YES) {
                        adminChangeOneFlightController.setSelectedFlightId(selectedFlightId);
                        adminChangeOneFlightController.setSelectedFrom(from);
                        adminChangeOneFlightController.setSelectedFlightNumber(selectedFlightNumber);
                        adminChangeOneFlightController.setSelectedTo(to);
                        adminChangeOneFlightController.setSelectedBusiness(business);
                        adminChangeOneFlightController.setSelectedEconomy(economy);
                        adminChangeOneFlightController.setSelectedFirstClass(firstClass);
                        adminChangeOneFlightController.setSelectedDepartureDate(departureDate);
                        adminChangeOneFlightController.setSelectedArriveDate(arriveDate);
                        adminChangeOneFlightController.openEditWindow();
                    }
                }
            });
            return row;
        });

        flightTableView.setItems(FXCollections.observableList(HelloApplication.getDataSource().readFlights()));


    }
    public void deleteTicket(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminDeleteTickets.fxml");

    }

    public void AddTickets(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminAddFlights-view.fxml");
    }

    public void changeFlight(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminChangeFlight-view.fxml");

    }

    public void SoldTickets(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminSoldTickets.fxml");
    }


    public void logout(ActionEvent actionEvent) {
        String contextText = "Are you sure you want to logout?";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contextText, ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.YES) {
            HelloApplication.showWindow("Registration.fxml");
        }
    }

    public void changes(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminChanges-view.fxml");

    }

    public void pretrazi() throws DataSourceException, SQLException {
        var filtrirano = HelloApplication.getDataSource().readFlights().stream()
                .filter(p -> p.getDepartureAirport().contains(fromFlight.getText()))
                .toList();

        flightTableView.setItems(FXCollections.observableList(filtrirano));
        logger.info("Pretrazeno!");
    }
}
