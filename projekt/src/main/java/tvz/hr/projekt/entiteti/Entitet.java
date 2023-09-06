package tvz.hr.projekt.entiteti;

public abstract class Entitet {

    private Integer id;
    private String flightNumber;

    public Entitet(Integer id, String flightNumber) {
        this.id = id;
        this.flightNumber = flightNumber;
    }

    public Entitet() {

    }

    public Integer getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
