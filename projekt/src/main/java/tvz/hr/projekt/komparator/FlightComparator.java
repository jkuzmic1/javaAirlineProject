package tvz.hr.projekt.komparator;

import tvz.hr.projekt.entiteti.Flight;

import java.util.Comparator;

public class FlightComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight o1, Flight o2) {
        return o1.getFlightNumber().compareTo(o2.getFlightNumber());
    }
}
