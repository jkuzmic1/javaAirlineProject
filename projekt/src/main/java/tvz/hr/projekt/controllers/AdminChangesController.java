package tvz.hr.projekt.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tvz.hr.projekt.HelloApplication;
import tvz.hr.projekt.changes.Changes;
import tvz.hr.projekt.changes.PossibleChanges;
import tvz.hr.projekt.data.SerDesChanges;
import tvz.hr.projekt.iznimke.DeserijalizacijaEOFException;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static tvz.hr.projekt.entiteti.Flight.logger;

public class AdminChangesController {

    @FXML
    TableView<Changes> tableChanges = new TableView<>();
    @FXML
    TableColumn< Changes, String> description = new TableColumn<>();
    @FXML
    TableColumn<Changes, String> idOfFlight = new TableColumn<>();
    @FXML
    TableColumn< Changes, String> timeOfChange = new TableColumn<>();
    @FXML
    TableColumn<Changes, String> oldValue = new TableColumn<>();
    @FXML
    TableColumn< Changes, String> newValue = new TableColumn<>();
    @FXML
    TableColumn<Changes, String> userID = new TableColumn<>();

    private static List<Changes> allChanges = new ArrayList<>();


    public void initialize() throws DeserijalizacijaEOFException, FileNotFoundException {
//        try {
//            List<Changes> allChanges = SerDesChanges.readChanges();
//
//            if (allChanges.isEmpty()) {
//                System.out.println("No changes found in the file.");
//            } else {
//                System.out.println("Changes in the file:");
//                for (Changes change : allChanges) {
//                    System.out.println(change.toString());
//                }
//            }
//        } catch (DeserijalizacijaEOFException e) {
//            System.err.println("Error reading changes from the file: " + e.getMessage());
//        }
//        System.out.println("stvoreno");

        description.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDesChange().getOpis()));
        idOfFlight.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdOfFlight()));
        timeOfChange.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTimeOfChange()));

        oldValue.setCellValueFactory(data -> {
            Changes change = data.getValue();
            if (change.getDesChange() == PossibleChanges.CHANGING_SEATS) {
                return new SimpleStringProperty(formatSeatsChangeOld(change.getOldValueSeats()));
            }  else {
                return new SimpleStringProperty(change.getOldValue());
            }
        });

        newValue.setCellValueFactory(data -> {
            Changes change = data.getValue();
            if (change.getDesChange() == PossibleChanges.CHANGING_SEATS) {
                return new SimpleStringProperty(formatSeatsChangeNew(change.getNewValueSeats()));
            }
            else {
                return new SimpleStringProperty(change.getNewValue());
            }
        });
        userID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUser()));

        tableChanges.getItems().clear();
        List<Changes> allChanges = SerDesChanges.readChanges();
        ObservableList<Changes> observableChangesList = FXCollections.observableList(allChanges);
        tableChanges.setItems(observableChangesList);

        logger.info("TableView initialized");

    }

    private String formatSeatsChangeOld(Integer oldValueSeats) {
        return oldValueSeats + ";" + oldValueSeats + ";" + oldValueSeats;
    }
    private String formatSeatsChangeNew(Integer newValueSeats) {
        return newValueSeats + ";" + newValueSeats + ";" + newValueSeats;
    }
    private String formatTimeChange(LocalDateTime oldTime, LocalDateTime newTime) {
        return "Old Time: " + oldTime.toString() + " New Time: " + newTime.toString();
    }


    public void deleteTicket(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminDeleteTickets.fxml");

    }

    public void AddTickets(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminAddFlights-view.fxml");
    }

    public void SoldTickets(ActionEvent actionEvent) {
        HelloApplication.showWindow("AdminSoldTickets.fxml");
    }

    public void Change(ActionEvent actionEvent) {
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
