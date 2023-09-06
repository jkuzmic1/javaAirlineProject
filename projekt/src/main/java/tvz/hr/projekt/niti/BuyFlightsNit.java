package tvz.hr.projekt.niti;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.h2.store.Data;
import tvz.hr.projekt.HelloApplication;
import tvz.hr.projekt.entiteti.Flight;
import tvz.hr.projekt.entiteti.User;
import tvz.hr.projekt.entiteti.UserManager;
import tvz.hr.projekt.iznimke.DataSourceException;
import tvz.hr.projekt.komparator.FlightComparator;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BuyFlightsNit implements Runnable{

    public FlightComparator f = new FlightComparator();

    @Override
    public void run() {

        if (checkIfUser()) {
            var dataSource = HelloApplication.getDataSource();

            List<Flight> letovi;

            try {
                letovi = dataSource.readFlights();
            } catch (DataSourceException e) {
                throw new RuntimeException(e);
            }


            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Check our flights!", ButtonType.OK);
            alert.setTitle("Flights for sale!");

            alert.setHeaderText("Check our flights!");
            alert.setContentText(letovi.stream()
                    .sorted(f)
                    .map(s -> "Flight number: " + s.getFlightNumber() + " - from: " + s.getDepartureAirport() + " - to: " + s.getArriveAirport())
                    .collect(Collectors.joining("\n")));
            alert.show();
        }
    }
    private boolean checkIfUser(){
        User isUser = UserManager.getInstance().getLoggedInUser();

        return isUser != null && !isUser.getUsername().equals("admin");
    }
}
