package tvz.hr.projekt.entiteti;

import java.time.LocalDateTime;

public class PurchasedFlight {
    private int id;
    private int userId;
    private String flightId;
    private String departureCity;
    private String arriveCity;
    private LocalDateTime departureDate;
    private LocalDateTime arriveDate;
    private double price;
    private LocalDateTime purchaseDate;

    public PurchasedFlight(String flightId, LocalDateTime purchasedDate, String departureCity, String arriveCity, LocalDateTime departureDate, LocalDateTime arriveDate, double price) {
        this.flightId = flightId;
        this.purchaseDate = purchasedDate;
        this.departureCity = departureCity;
        this.arriveCity = arriveCity;
        this.departureDate = departureDate;
        this.arriveDate = arriveDate;
        this.price = price;
    }


    public PurchasedFlight() {

    }



    public PurchasedFlight(int userId, String flightId, LocalDateTime purchasedDate, String departureCity, String arriveCity, LocalDateTime departureDate, LocalDateTime arriveDate, double price) {
        this.userId = userId;
        this.flightId = flightId;
        this.purchaseDate = purchasedDate;
        this.departureCity = departureCity;
        this.arriveCity = arriveCity;
        this.departureDate = departureDate;
        this.arriveDate = arriveDate;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(LocalDateTime arriveDate) {
        this.arriveDate = arriveDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return "PurchasedFlight{" +
                "id=" + id +
                ", userId=" + userId +
                ", flightId='" + flightId + '\'' +
                ", departureCity='" + departureCity + '\'' +
                ", arriveCity='" + arriveCity + '\'' +
                ", departureDate=" + departureDate +
                ", arriveDate=" + arriveDate +
                ", price=" + price +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
