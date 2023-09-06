package tvz.hr.projekt.niti;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import tvz.hr.projekt.changes.Changes;
import tvz.hr.projekt.data.SerDesChanges;
import tvz.hr.projekt.entiteti.User;
import tvz.hr.projekt.entiteti.UserManager;
import tvz.hr.projekt.iznimke.DeserijalizacijaEOFException;

import java.util.ArrayList;
import java.util.List;

public class ReadingChanges implements Runnable{
    @Override
    public void run() {
        if (checkIfUser()) {
            List<Changes> listChanges = new ArrayList<>();

            try {
                listChanges = SerDesChanges.readChanges();

            } catch (DeserijalizacijaEOFException e) {
                System.out.println(e.getMessage());
            }


        try {
                Changes lastChange = listChanges.get(listChanges.size() - 1);

                String contextText = "Last change was made '" + lastChange.getDesChange().getOpis() + "' at " + lastChange.getTimeOfChange() + " by: " + lastChange.getUser();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, contextText, ButtonType.OK);
                alert.setTitle("Last change");
                alert.show();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No changes were made");
            }
        }
    }
    private boolean checkIfUser(){
        User isUser = UserManager.getInstance().getLoggedInUser();

        return isUser.getUsername().equals("admin");
    }
}
