package tvz.hr.projekt.entiteti;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class Airport extends Entitet implements Comparable<Airport> {

    private String name;
    private String city;
    private String country;

    private Airport(Builder builder) {
        super(builder.id, builder.flightNumber);
        this.name = builder.name;
        this.city = builder.city;
        this.country = builder.country;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public int compareTo(Airport other) {
        return name.compareTo(other.getName());
    }

    public static class Builder {
        private Integer id;
        private String flightNumber;
        private String name;
        private String city;
        private String country;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder flightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Airport build() {
            return new Airport(this);
        }


    }
}
