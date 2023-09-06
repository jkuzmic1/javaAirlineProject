package tvz.hr.projekt.entiteti;

public enum TicketType  {

    ECONOMY("Economy"),
    BUSINESS("Business"),
    FIRST_CLASS("FirstClass");

    private String typeOfSeat;


    TicketType(String typeOfSeat) {
        this.typeOfSeat = typeOfSeat;
    }

    public String getTypeOfSeat() {
        return typeOfSeat;
    }

    private String naziv;
    private Integer numberOfEconomySeats;
    private Integer numberOfBusinessSeats;
    private Integer numberOfFirstClassSeats;

    TicketType(String naziv, Integer numberOfEconomySeats, Integer numberOfBusinessSeats, Integer numberOfFirstClassSeats) {
        this.naziv = naziv;
        this.numberOfEconomySeats = numberOfEconomySeats;
        this.numberOfBusinessSeats = numberOfBusinessSeats;
        this.numberOfFirstClassSeats = numberOfFirstClassSeats;
    }

    public String getNaziv() {
        return naziv;
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

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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
}

