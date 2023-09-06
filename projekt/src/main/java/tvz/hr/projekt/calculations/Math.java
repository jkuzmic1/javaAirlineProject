//package tvz.hr.projekt.calculations;
//
////import tvz.hr.projekt.entiteti.Airplane;
////import tvz.hr.projekt.entiteti.Airplane;
//import tvz.hr.projekt.entiteti.TicketType;
//import tvz.hr.projekt.iznimke.TicketNotAvailableException;
//
//public non-sealed class Math implements Calculus {
//
//
//
////    @Override
////    public void ReservationOfSeats(Integer numberOfSeats, TicketType type, Airplane a) throws Exception {
////        if (type.getTypeOfSeat().contains("Business")) {
////            if (a.getNumberOfBusinessSeats() > 0 && a.getNumberOfBusinessSeats() >= numberOfSeats) {
////                a.setNumberOfBusinessSeats(a.getNumberOfBusinessSeats() - numberOfSeats);
////            } else {
////                throw new TicketNotAvailableException("Not enough business seats!");
////            }
////        } else if (type.getTypeOfSeat().contains("Economy")) {
////            if (a.getNumberOfEconomySeats() > 0 && a.getNumberOfEconomySeats() >= numberOfSeats) {
////                a.setNumberOfEconomySeats(a.getNumberOfEconomySeats() - numberOfSeats);
////            } else {
////                throw new TicketNotAvailableException("Not enough economy seats!");
////            }
////        } else if (type.getTypeOfSeat().contains("FirstClass")) {
////            if (a.getNumberOfFirstClassSeats() > 0 && a.getNumberOfFirstClassSeats() >= numberOfSeats) {
////                a.setNumberOfFirstClassSeats(a.getNumberOfFirstClassSeats() - numberOfSeats);
////            } else {
////                throw new TicketNotAvailableException("Not enough first-class seats!");
////            }
////        }
////    }
//
//
//
//    @Override
//    public void howManyKm() {
//
//    }
//
//    @Override
//    public void price() {
//
//    }
//
//
//}
