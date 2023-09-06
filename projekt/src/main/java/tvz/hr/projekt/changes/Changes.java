package tvz.hr.projekt.changes;

import tvz.hr.projekt.iznimke.NedozvoljenaPromjenaException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Changes implements Serializable {

    public static final DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private String user;
    private String idOfFlight;
    private PossibleChanges DesChange;
    private String deletedFlight;
    private String from;
    private String to;
    private String addedFlight;
    private String fromAdded;
    private String toAdded;
    private String timeOfChange;
    private String buyingTicket;
    private String changingTicket;
    private String oldValue;
    private String newValue;
    private int changingSeats;
    private int oldValueSeats;
    private int newValueSeats;
    private LocalDateTime changingTime;
    private LocalDateTime oldTime;
    private LocalDateTime newTime;

    private static Map<String, String> changeDetails;
    private Changes() {
    }
    public Map<String, String> getChangeDetails() {
        return changeDetails;
    }

    public void setChangeDetails(Map<String, String> changeDetails) {
        this.changeDetails = changeDetails;
    }
    public LocalDateTime getChangingTime() {
        return changingTime;
    }

    public LocalDateTime getOldTime() {
        return oldTime;
    }

    public LocalDateTime getNewTime() {
        return newTime;
    }

    public int getChangingSeats() {
        return changingSeats;
    }

    public int getOldValueSeats() {
        return oldValueSeats;
    }

    public int getNewValueSeats() {
        return newValueSeats;
    }

    public String getUser() {
        return user;
    }

    public String getIdOfFlight() {
        return idOfFlight;
    }

    public PossibleChanges getDesChange() {
        return DesChange;
    }

    public String getDeletedFlight() {
        return deletedFlight;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getAddedFlight() {
        return addedFlight;
    }

    public String getFromAdded() {
        return fromAdded;
    }

    public String getToAdded() {
        return toAdded;
    }

    public String getBuyingTicket() {
        return buyingTicket;
    }

    public String getChangingTicket() {
        return changingTicket;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public String getTimeOfChange() {
        return timeOfChange;
    }


    public static class Builder{

        private String user;
        private String idOfFlight;
        private PossibleChanges DesChange;
        private String deletedFlight;
        private String from;
        private String to;
        private String addedFlight;
        private String fromAdded;
        private String toAdded;
        private String timeOfChange;
        private String buyingTicket;
        private String changingTicket;
        private String oldValue;
        private String newValue;
        private int oldValueSeats;
        private int newValueSeats;
        private int changingSeats;
        private LocalDateTime changingTime;
        private LocalDateTime oldTime;
        private LocalDateTime newTime;
        public Builder(String idOfFlight, PossibleChanges DesChange, LocalDateTime timeOfChange, String user) throws NedozvoljenaPromjenaException {

            this.idOfFlight = idOfFlight;
            this.DesChange = DesChange;
            this.timeOfChange = timeOfChange.format(formater);
            this.user = user;
        }

        public Builder deletingFlight(String deletedFlight){

            this.deletedFlight = deletedFlight;

            return this;
        }

        public Builder addingFlight(String addedFlight){

            this. addedFlight = addedFlight;

            return this;
        }

        public Builder buyingTicket(String buyingTicket){

            this.buyingTicket = buyingTicket;

            return this;
        }

        public Builder changingTicket(String oldValue, String newValue) {
            this.oldValue = oldValue;
            this.newValue = newValue;
            return this;
        }

        public Builder changingTime(LocalDateTime oldTime, LocalDateTime newTime) {
            this.oldTime = oldTime;
            this.newTime = newTime;
            return this;
        }

        public Builder changingSeats(Integer oldValueSeats, Integer newValueSeats) {
            this.oldValueSeats = oldValueSeats;
            this.newValueSeats = newValueSeats;
            return this;
        }



        public Changes build(){
            Changes change = new Changes();
            change.idOfFlight = this.idOfFlight;
            change.DesChange = this.DesChange;
            change.deletedFlight = this.deletedFlight;
            change.from = this.from;
            change.to = this.to;
            change.addedFlight = this.addedFlight;
            change.fromAdded = this.fromAdded;
            change.toAdded = this.toAdded;
            change.timeOfChange = this.timeOfChange;
            change.user = this.user;
            change.buyingTicket = this.buyingTicket;
            change.changingTicket = this.changingTicket;
            change.changingSeats = this.changingSeats;
            change.changingTime = this.changingTime;
            change.oldValue = this.oldValue;
            change.newValue = this.newValue;
            change.oldValueSeats = this.oldValueSeats;
            change.newValueSeats = this.newValueSeats;
            return change;
        }
    }

    @Override
    public String toString() {
        return "Changes{" +
                "user='" + user + '\'' +
                ", idOfFlight='" + idOfFlight + '\'' +
                ", DesChange=" + DesChange +
                ", deletedFlight='" + deletedFlight + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", addedFlight='" + addedFlight + '\'' +
                ", fromAdded='" + fromAdded + '\'' +
                ", toAdded='" + toAdded + '\'' +
                ", timeOfChange='" + timeOfChange + '\'' +
                ", buyingTicket='" + buyingTicket + '\'' +
                ", changingTicket='" + changingTicket + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", newValue='" + newValue + '\'' +
                ", changingSeats=" + changingSeats +
                ", oldValueSeats=" + oldValueSeats +
                ", newValueSeats=" + newValueSeats +
                ", changingTime=" + changingTime +
                ", oldTime=" + oldTime +
                ", newTime=" + newTime +
                '}';
    }
}
