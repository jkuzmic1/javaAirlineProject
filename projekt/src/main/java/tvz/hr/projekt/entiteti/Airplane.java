package tvz.hr.projekt.entiteti;

public class Airplane  {

    private Integer numberOfEconomySeats;
    private Integer numberOfBusinessSeats;
    private Integer numberOfFirstClassSeats;

    public Airplane(Integer numberOfEconomySeats, Integer numberOfBusinessSeats, Integer numberOfFirstClassSeats) {
        this.numberOfEconomySeats = numberOfEconomySeats;
        this.numberOfBusinessSeats = numberOfBusinessSeats;
        this.numberOfFirstClassSeats = numberOfFirstClassSeats;
    }

    public Integer getNumberOfEconomySeats() {
        return numberOfEconomySeats;
    }

    public Integer getNumberOfBusinessSeats() {
        return numberOfBusinessSeats;
    }

    public Integer getNumberOfFirstClassSeats() {
        return numberOfFirstClassSeats;
    }

    public void setNumberOfEconomySeats(Integer numberOfEconomySeats) {
        this.numberOfEconomySeats = numberOfEconomySeats;
    }

    public void setNumberOfBusinessSeats(Integer numberOfBusinessSeats) {
        this.numberOfBusinessSeats = numberOfBusinessSeats;
    }

    public void setNumberOfFirstClassSeats(Integer numberOfFirstClassSeats) {
        this.numberOfFirstClassSeats = numberOfFirstClassSeats;
    }

    public void ReservationOfSeats(int numberOfSeats, TicketType business, Flight flight) {
    }
}
