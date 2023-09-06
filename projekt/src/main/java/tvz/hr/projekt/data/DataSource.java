package tvz.hr.projekt.data;

import org.h2.store.Data;
import tvz.hr.projekt.entiteti.Flight;
import tvz.hr.projekt.entiteti.PurchasedFlight;
import tvz.hr.projekt.iznimke.DataSourceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface DataSource {

    List<Flight> readFlights() throws DataSourceException, SQLException;
    void updateFlightSeats(Flight flight) throws DataSourceException;
    void addFlight(Flight flight) throws DataSourceException;
    void deleteFlight(Flight flight) throws DataSourceException;
    public void addUser(String username, String password) throws DataSourceException;
    public boolean checkUserExists(String username) throws DataSourceException;
    public void buyTicket(int userId, String flightId, String departureCity, String arriveCity, LocalDateTime departureDate, LocalDateTime arriveDate, double price) throws SQLException;
    public int fetchUserIdByUsername(String username) throws DataSourceException;
//    public int fetchFlightIdByFlightNumber(String flightNumber) throws DataSourceException;
    public List<PurchasedFlight> fetchPurchasedFlights(int userId) throws DataSourceException;
    public List<PurchasedFlight> updatePurchasedFlights(int userId) throws DataSourceException;
    public List<PurchasedFlight> readPurchasedFlights() throws DataSourceException;
    public String fetchFlightWithId(int id) throws SQLException, DataSourceException;


    }
