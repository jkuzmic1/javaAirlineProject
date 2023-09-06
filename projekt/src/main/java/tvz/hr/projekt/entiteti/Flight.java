package tvz.hr.projekt.entiteti;

//import tvz.hr.projekt.calculations.Math;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tvz.hr.projekt.HelloApplication;
import tvz.hr.projekt.iznimke.TicketNotAvailableException;
import tvz.hr.projekt.iznimke.DataSourceException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Flight {
    public static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    private LocalDateTime departureTime;
    private LocalDateTime arriveTime;
    private String departureCity;
    private String arriveCity;
    private Integer id;
    private String flightNumber;
    private int bookedBusinessSeats;
    private int bookedEconomySeats;
    private int bookedFirstClassSeats;
    private int numberOfBusinessSeats;
    private int numberOfEconomySeats;
    private int numberOfFirstClassSeats;
    private Integer bussiness;
    private Integer economy;
    private Integer firstClass;
    private double price;

    private Map<TicketType, Integer> bookedSeatsMap = new HashMap<>();

    public Flight(Integer id, String flightNumber, LocalDateTime departureTime, LocalDateTime arriveTime,
                  String departureCity, String arriveCity,Integer bussiness,Integer economy,Integer firstClass) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arriveTime = arriveTime;
        this.departureCity = departureCity;
        this.arriveCity = arriveCity;
        this.bussiness = bussiness;
        this.economy = economy;
        this.firstClass = firstClass;

        bookedSeatsMap.put(TicketType.BUSINESS, bussiness);
        bookedSeatsMap.put(TicketType.ECONOMY, economy);
        bookedSeatsMap.put(TicketType.FIRST_CLASS, firstClass);

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalDateTime arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getDepartureAirport() {
        return departureCity;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureCity = departureAirport;
    }

    public String getArriveAirport() {
        return arriveCity;
    }

    public void setArriveAirport(String arriveAirport) {
        this.arriveCity = arriveAirport;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getNumberOfBusinessSeats() {
        return numberOfBusinessSeats;
    }

    public void setNumberOfBusinessSeats(int numberOfBusinessSeats) {
        this.numberOfBusinessSeats = numberOfBusinessSeats;
    }

    public int getNumberOfEconomySeats() {
        return numberOfEconomySeats;
    }

    public void setNumberOfEconomySeats(int numberOfEconomySeats) {
        this.numberOfEconomySeats = numberOfEconomySeats;
    }

    public int getNumberOfFirstClassSeats() {
        return numberOfFirstClassSeats;
    }

    public void setNumberOfFirstClassSeats(int numberOfFirstClassSeats) {
        this.numberOfFirstClassSeats = numberOfFirstClassSeats;
    }

    public Integer getBussiness() {
        return bussiness;
    }

    public void setBussiness(Integer bussiness) {
        this.bussiness = bussiness;
    }

    public Integer getEconomy() {
        return economy;
    }

    public void setEconomy(Integer economy) {
        this.economy = economy;
    }

    public Integer getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(Integer firstClass) {
        this.firstClass = firstClass;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", departureTime=" + departureTime +
                ", arriveTime=" + arriveTime +
                ", departureAirport='" + departureCity + '\'' +
                ", arriveAirport='" + arriveCity + '\'' +
                '}' + "\n";
    }

    public void ReservationOfSeats(int numberOfSeats, TicketType classType) throws Exception {
        if (classType == TicketType.ECONOMY) {
            if (economy >= numberOfSeats) {
                economy -= numberOfSeats;
                bookedEconomySeats += numberOfSeats;
                updateSeatNumbersInDatabase();

            } else {
                throw new TicketNotAvailableException("Not enough economy seats!");
            }
        } else if (classType == TicketType.BUSINESS) {
            if (bussiness >= numberOfSeats) {
                bussiness -= numberOfSeats;
                bookedBusinessSeats += numberOfSeats;
                updateSeatNumbersInDatabase();

            } else {
                throw new TicketNotAvailableException("Not enough business seats!");
            }
        } else if (classType == TicketType.FIRST_CLASS) {
            if (firstClass >= numberOfSeats) {
                firstClass -= numberOfSeats;
                bookedFirstClassSeats  += numberOfSeats;
                updateSeatNumbersInDatabase();

            } else {
                throw new TicketNotAvailableException("Not enough first-class seats!");
            }
        } else {
            throw new IllegalArgumentException("Invalid class type!");
        }
    }
    private void updateSeatNumbersInDatabase() {
        try {
            HelloApplication.getDataSource().updateFlightSeats(this);
        } catch (DataSourceException e) {
        }
    }

    public void displayAvailableSeats() {
        System.out.println("Available Seats:");
        System.out.println("Economy: " + getAvailableSeats(TicketType.ECONOMY));
        System.out.println("Business: " + getAvailableSeats(TicketType.BUSINESS));
        System.out.println("First Class: " + getAvailableSeats(TicketType.FIRST_CLASS));
    }


    public int getAvailableSeats(TicketType classType) {
        int availableSeats = 0;

            if (classType == TicketType.BUSINESS) {
                availableSeats += getNumberOfBusinessSeats() - bookedBusinessSeats;
            } else if (classType == TicketType.ECONOMY) {
                availableSeats += getNumberOfEconomySeats() - bookedEconomySeats;
            } else if (classType == TicketType.FIRST_CLASS) {
                availableSeats += getNumberOfFirstClassSeats() - bookedFirstClassSeats;
            } else {
                throw new IllegalArgumentException("Invalid class type!");
            }


        return availableSeats;
    }


    public static double calculateSeatValue(TicketType ticketType, GoogleApi api) {
        double basePrice;

        if (ticketType == TicketType.BUSINESS) {
            basePrice = 500.0;
        } else if (ticketType == TicketType.ECONOMY) {
            basePrice = 300.0;
        } else if (ticketType == TicketType.FIRST_CLASS) {
            basePrice = 800.0;
        } else {
            throw new IllegalArgumentException("Invalid seat class");
        }

        double distance = api.calculateDistance();

        return basePrice + distance;
    }
}
