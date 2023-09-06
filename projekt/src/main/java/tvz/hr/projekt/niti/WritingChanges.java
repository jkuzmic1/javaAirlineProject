package tvz.hr.projekt.niti;


import tvz.hr.projekt.HelloApplication;
import tvz.hr.projekt.changes.Changes;
import tvz.hr.projekt.data.SerDesChanges;
import tvz.hr.projekt.iznimke.DeserijalizacijaEOFException;

import java.util.ArrayList;
import java.util.List;

public class WritingChanges implements Runnable{

    @Override
    public void run() {
        List<Changes> staraLista = new ArrayList<>();
        try {
            staraLista = SerDesChanges.readChanges();
        } catch (DeserijalizacijaEOFException e) {
            throw new RuntimeException(e);
        }

        staraLista.addAll(HelloApplication.listaPromjena);
        HelloApplication.listaPromjena.clear();

        SerDesChanges.writeChangesInDat(staraLista);
    }
}