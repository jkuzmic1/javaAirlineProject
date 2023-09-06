//package tvz.hr.projekt;
//
//
//import tvz.hr.projekt.entiteti.*;
//import tvz.hr.projekt.komparator.FlightComparator;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class main {
////    private static final Logger logger = LoggerFactory.getLogger(TY.class);
//
//    public static void main(String[] args) {
//
//
//        List<Flight> listaLetova = new ArrayList<>();
//
//        Airplane LightJet = new Airplane(20, 10, 5);
//        Airplane MidJet = new Airplane(20, 40, 15);
//        Airplane RegionalJet = new Airplane(40, 20, 30);
//
//
//        listaLetova.add(new Flight(10001, "A1001", LocalDateTime.of(2022, 1, 4, 12, 12),
//                LocalDateTime.of(2022, 1, 4, 12, 12), "Berlin", "Zagreb",3,1,2));
//
//        listaLetova.add(new Flight(10002, "A1002", LocalDateTime.of(2023, 5, 4, 12, 12),
//                LocalDateTime.of(2021, 1, 4, 12, 12), "Zagreb", "Beograd", 3,2,1));
//
//        listaLetova.add(new Flight(10003, "A1003", LocalDateTime.of(2024, 2, 4, 12, 12),
//                LocalDateTime.of(2021, 1, 4, 12, 12), "Zagreb", "Berlin", 2,3,4));
//
//
//        listaLetova.stream().filter(i -> (i.getDepartureTime().compareTo(LocalDateTime.of(2022, 1, 04, 12, 12))) >= 0)
//                .sorted(new FlightComparator())
//                .forEach(System.out::println);
////        logger.info("hy");
//    }}
////
////        System.out.println();
////        System.out.println("---------------------------------------------------------------------------------");
////        System.out.println();
////
////
////        int i = 0;
////        for (Flight flight : listaLetova) {
////            try {
////
////                flight.displayAvailableSeats();
////                int availableSeats = flight.getAvailableSeats(TicketType.ECONOMY);
////                System.out.println(availableSeats);
////                if (availableSeats > 0) {
////                    flight.ReservationOfSeats(1, TicketType.ECONOMY);
////                    System.out.println("Seats reserved in economy class for flight " + flight.getFlightNumber());
////                } else {
////                    System.out.println("No seats available in economy class for flight " + flight.getFlightNumber());
////                }
////            } catch (Exception e) {
////                throw new RuntimeException(e);
////            }
////        }
////
////        Airport airport = new Airport.Builder()
////                .id(1)
////                .flightNumber("A2002")
////                .name("Pleso")
////                .city("Zagreb")
////                .country("Hrv")
////                .build();
////
////        System.out.println(airport);
////
////        List<Airport> airports = Stream.of(
////                        new Airport.Builder().name("Airport C").build(),
////                        new Airport.Builder().name("Airport A").build(),
////                        new Airport.Builder().name("Airport B").build()
////                )
////                .sorted(Comparator.comparing(Airport::getName)).toList();
////
////        airports.forEach(System.out::println);
////
////
////        List<Double> distances = new ArrayList<>();
////        for (Flight flight : listaLetova) {
////
////
////            GoogleApi googleApi = new GoogleApi(flight.getDepartureAirport(), flight.getArriveAirport());
////            double distance = googleApi.calculateDistance();
////            distances.add(distance);
////        }
////
////        Flight flight;
////        for (int i = 0; i < listaLetova.size(); i++) {
////            flight = listaLetova.get(i);
////            //System.out.println(flight.getTicketType()) ;
////            double distance = distances.get(i);
////            System.out.println("Flight: " + flight.getFlightNumber());
////            System.out.println("Departure City: " + flight.getDepartureAirport());
////            System.out.println("Arrival City: " + flight.getArriveAirport());
////
////            double roundedDistance = Math.round(distance * 100.0) / 100.0;
////            System.out.println("Distance: " + roundedDistance + " km");
////
////
////            double ticketValue = calculateSeatValue(flight.getTicketType(), new GoogleApi(flight.getDepartureAirport(), flight.getArriveAirport()));
////            double roundedPrice = Math.round(ticketValue * 100.0) / 100.0;
////            System.out.println("\nPrice" + " = " + roundedPrice + "$");
////
////            System.out.println("-------------------------");
////        }
////    }
////}