package tvz.hr.projekt.entiteti;

public class Ticket extends Entitet{
    private Flight flight;
    private TicketType ticketType;
    private double price;
    private int seatNumber;

    public Ticket(Integer id, String flightNumber, Flight flight, TicketType ticketType, double price) {
        super(id, flightNumber);
        this.flight = flight;
        this.ticketType = ticketType;
        this.price = price;
    }

    public Flight getFlight() {
        return flight;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public double getPrice() {
        return price;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

}





