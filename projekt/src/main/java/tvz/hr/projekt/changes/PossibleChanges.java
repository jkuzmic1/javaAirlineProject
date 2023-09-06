package tvz.hr.projekt.changes;

public enum PossibleChanges {
    ADDING_FLIGHT("Adding Flight"),
    DELETING_FLIGHT("Deleting Flight"),
    BUYING_TICKET("Buying Ticket"),
    CHANGING_TICKET("Changed flight"),
    CHANGING_SEATS("Changing seats"),
    CHANGING_TIME("Changing time");
    private String opis;

    PossibleChanges(String opis){
        this.opis = opis;
    }

    public String getOpis(){
        return opis;
    }
}
