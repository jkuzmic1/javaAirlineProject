package tvz.hr.projekt.data;

//import tvz.hr.projekt.entiteti.Airplane;
import tvz.hr.projekt.entiteti.Flight;
import tvz.hr.projekt.entiteti.PurchasedFlight;
import tvz.hr.projekt.entiteti.TicketType;
import tvz.hr.projekt.iznimke.DataSourceException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class BazaPodataka implements DataSource {

    private final Connection connection;


    public BazaPodataka() throws DataSourceException {
        try {
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/javaProjekt", "student", "student");
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public List<Flight> readFlights() throws DataSourceException {
        String query = "SELECT * FROM FLIGHTS";

        List<Flight> items = new ArrayList<>();

        try (var s = connection.createStatement();
             var resultSet = s.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String flightNumber = resultSet.getString("FLIGHTNUMBER");
                LocalDateTime departureTime = resultSet.getTimestamp("DEPARTURETIME").toLocalDateTime();
                LocalDateTime arriveTime = resultSet.getTimestamp("ARRIVETIME").toLocalDateTime();
                String departureCity = resultSet.getString("DEPARTURECITY");
                String arriveCity = resultSet.getString("ARRIVECITY");
                Integer bussiness = resultSet.getInt("BUSSINESS");
                Integer economy = resultSet.getInt("ECONOMY");
                Integer firstClass = resultSet.getInt("FIRSTCLASS");


                List<TicketType> ticketTypes = new ArrayList<>();


                Flight flight = new Flight(id, flightNumber, departureTime, arriveTime, departureCity, arriveCity, bussiness, economy, firstClass);
                items.add(flight);
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return items;
    }

    public void updateFlightSeats(Flight flight) throws DataSourceException {

        String query = "UPDATE flights SET ECONOMY = ?, BUSSINESS = ?, FIRSTCLASS = ? WHERE ID = ?";
        try (var s = connection.prepareStatement(query)) {
            s.setInt(1, flight.getEconomy());
            s.setInt(2, flight.getBussiness());
            s.setInt(3, flight.getFirstClass());
            s.setInt(4, flight.getId());

            s.executeUpdate();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    public void addFlight(Flight flight) throws DataSourceException {

        String query = "INSERT INTO FLIGHTS (FLIGHTNUMBER, DEPARTURETIME, ARRIVETIME, DEPARTURECITY, ARRIVECITY, BUSSINESS, ECONOMY, FIRSTCLASS) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (var s = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, flight.getFlightNumber());
            s.setTimestamp(2, Timestamp.valueOf(flight.getDepartureTime()));
            s.setTimestamp(3, Timestamp.valueOf(flight.getArriveTime()));
            s.setString(4, flight.getDepartureAirport());
            s.setString(5, flight.getArriveAirport());
            s.setInt(6, flight.getBussiness());
            s.setInt(7, flight.getEconomy());
            s.setInt(8, flight.getFirstClass());

            s.executeUpdate();

            try (ResultSet generatedKeys = s.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    flight.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    public void deleteFlight(Flight flight) throws DataSourceException{
        String query = "DELETE FROM FLIGHTS WHERE ID = ?";

        try (var s = connection.prepareStatement(query)) {
            s.setInt(1, flight.getId());
            s.executeUpdate();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    public void updateFlight(Flight flight) throws DataSourceException, SQLException {
        String query = "UPDATE FLIGHTS SET FLIGHTNUMBER = ?, DEPARTURETIME = ?, ARRIVETIME = ?, DEPARTURECITY = ?, ARRIVECITY = ?, BUSSINESS = ?, ECONOMY = ?, FIRSTCLASS = ? WHERE ID = ?";

        try (var s = connection.prepareStatement(query)) {
            s.setString(1, flight.getFlightNumber());
            s.setTimestamp(2, Timestamp.valueOf(flight.getDepartureTime()));
            s.setTimestamp(3, Timestamp.valueOf(flight.getArriveTime()));
            s.setString(4, flight.getDepartureAirport());
            s.setString(5, flight.getArriveAirport());
            s.setInt(6, flight.getBussiness());
            s.setInt(7, flight.getEconomy());
            s.setInt(8, flight.getFirstClass());
            s.setInt(9, flight.getId());

            s.executeUpdate();
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    public void addUser(String username, String password) throws DataSourceException {
        String insertQuery = "INSERT INTO Users (username, password) VALUES (?, ?)";

        try (var s = connection.prepareStatement(insertQuery)) {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(password.getBytes());
            String hashedPassword = Base64.getEncoder().encodeToString(hash);

            s.setString(1, username);
            s.setString(2, password);

            s.executeUpdate();
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new DataSourceException(e.getMessage());
        }
    }


    public boolean checkUserExists(String username) throws DataSourceException {
        String query = "SELECT COUNT(*) FROM Users WHERE username = ?";

        try (var s = connection.prepareStatement(query)) {
            s.setString(1, username);
            try (var resultSet = s.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
    }

    public int fetchUserIdByUsername(String username) throws DataSourceException {
        int userId = -1;
        String query = "SELECT ID FROM users WHERE username = ?";

        try (var s = connection.prepareStatement(query)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("ID");
                }
            }
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }

        return userId;
    }

    public List<PurchasedFlight> updatePurchasedFlights(int userId) throws DataSourceException {
        List<PurchasedFlight> purchasedFlights = new ArrayList<>();

        String query = "SELECT * FROM purchased_flights WHERE user_id = ?";

        try (var s = connection.prepareStatement(query)) {
            s.setInt(1, userId);

            try (var resultSet = s.executeQuery()) {
                while (resultSet.next()) {
                    PurchasedFlight purchasedFlight = new PurchasedFlight();
                    purchasedFlight.setId(resultSet.getInt("id"));
                    purchasedFlight.setUserId(resultSet.getInt("user_id"));
                    purchasedFlight.setFlightId(String.valueOf(resultSet.getInt("flight_id")));
                    purchasedFlight.setPurchaseDate(resultSet.getTimestamp("purchase_date").toLocalDateTime());

                    purchasedFlights.add(purchasedFlight);
                }
            }
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }

        return purchasedFlights;
    }

    public void buyTicket(int userId, String flightId, String departureCity, String arriveCity, LocalDateTime departureDate, LocalDateTime arriveDate, double price) throws SQLException {

        String query = "INSERT INTO purchased_flights (USER_ID, FLIGHT_ID, PURCHASED_DATE, DEPARTURE_CITY, ARRIVE_CITY, DEPARTURE_DATE, ARRIVE_DATE, PRICE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (var s = connection.prepareStatement(query)) {
            s.setInt(1, userId);
            s.setString(2, flightId);
            s.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            s.setString(4, departureCity);
            s.setString(5, arriveCity);
            s.setTimestamp(6, java.sql.Timestamp.valueOf(departureDate));
            s.setTimestamp(7, java.sql.Timestamp.valueOf(arriveDate));
            s.setDouble(8, price);

            s.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }
//    public int fetchFlightIdByFlightNumber(String flightNumber) throws DataSourceException {
//        int flightId = -1;
//        String query = "SELECT ID FROM flights WHERE FLIGHTNUMBER = ?";
//
//        try (var s = connection.prepareStatement(query)) {
//            s.setString(1, flightNumber);
//
//            try (ResultSet resultSet = s.executeQuery()) {
//                if (resultSet.next()) {
//                    flightId = resultSet.getInt("ID");
//                }
//            }
//        } catch (SQLException e) {
//            throw new DataSourceException(e.getMessage());
//        }
//
//        return flightId;
//    }


    public List<PurchasedFlight> fetchPurchasedFlights(int userId) throws DataSourceException {
        String query = "SELECT * FROM PURCHASED_FLIGHTS WHERE USER_ID = ?";


        List<PurchasedFlight> purchasedFlights = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String flightId = resultSet.getString("FLIGHT_ID");
                    LocalDateTime purchasedDate = resultSet.getTimestamp("PURCHASED_DATE").toLocalDateTime();
                    String departureCity = resultSet.getString("DEPARTURE_CITY");
                    String arriveCity = resultSet.getString("ARRIVE_CITY");
                    LocalDateTime departureDate = resultSet.getTimestamp("DEPARTURE_DATE").toLocalDateTime();
                    LocalDateTime arriveDate = resultSet.getTimestamp("ARRIVE_DATE").toLocalDateTime();
                    double price = resultSet.getDouble("PRICE");

//                    System.out.println("Flight ID: " + flightId);
//                    System.out.println("Purchased Date: " + purchasedDate);
//                    System.out.println("Price: " + price);

                    PurchasedFlight purchasedFlight = new PurchasedFlight(
                            userId, flightId, purchasedDate, departureCity, arriveCity,
                            departureDate, arriveDate, price
                    );
                    purchasedFlights.add(purchasedFlight);
                }
            }
        } catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }

        return purchasedFlights;
    }

    public List<PurchasedFlight> readPurchasedFlights() throws DataSourceException {
        String query = "SELECT * FROM PURCHASED_FLIGHTS";

        List<PurchasedFlight> items = new ArrayList<>();

        try (var s = connection.createStatement();
             var resultSet = s.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int userId = resultSet.getInt("USER_ID");
                String flightId = resultSet.getString("FLIGHT_ID");
                LocalDateTime purchasedTime = resultSet.getTimestamp("PURCHASED_DATE").toLocalDateTime();
                String departureCity = resultSet.getString("DEPARTURE_CITY");
                String arriveCity = resultSet.getString("ARRIVE_CITY");
                LocalDateTime departureTime = resultSet.getTimestamp("DEPARTURE_DATE").toLocalDateTime();
                LocalDateTime arriveTIme = resultSet.getTimestamp("ARRIVE_DATE").toLocalDateTime();
                double price = resultSet.getDouble("PRICE");

                PurchasedFlight purchasedFlight = new PurchasedFlight(
                        userId, flightId, purchasedTime, departureCity, arriveCity,
                        departureTime, arriveTIme, price
                );
                items.add(purchasedFlight);
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return items;
    }

    public String fetchFlightWithId(int id) throws SQLException, DataSourceException {
        String flightnumber = null;
        String query = "SELECT * FROM FLIGHTS WHERE ID = ?";

        try(var s = connection.prepareStatement(query)){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    flightnumber = resultSet.getString("FLIGHTNUMBER");
                }
            }
        }catch (SQLException e) {
            throw new DataSourceException(e.getMessage());
        }
        return flightnumber;
    }
}
