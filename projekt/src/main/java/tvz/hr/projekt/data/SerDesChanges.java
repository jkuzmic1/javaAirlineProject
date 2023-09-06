package tvz.hr.projekt.data;

import tvz.hr.projekt.changes.Changes;
import tvz.hr.projekt.iznimke.DeserijalizacijaEOFException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerDesChanges {
    public static final String SERIALIZATION_FILE_NAME = "promjene/promjene.dat";
    private static final Object fileLock = new Object();

    public static void writeChangesInDat(List<Changes> listChanges) {
        synchronized (fileLock) {
            try (ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(SERIALIZATION_FILE_NAME))) {
                out.writeObject(listChanges);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Changes> readChanges() throws DeserijalizacijaEOFException {
        List<Changes> res = new ArrayList<>();
        synchronized (fileLock) {
            try (ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(SERIALIZATION_FILE_NAME))) {
                while (true) {
                    try {
                        List<Changes> changesList = (List<Changes>) in.readObject();
                        res.addAll(changesList);
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

//    public SerDesChanges() throws IOException {
//    }
}


//    Object obj = in.readObject();
//    Changes change = (Changes) obj;
//                    res.add(change);


//    public synchronized static List<Changes> readChanges() throws DeserijalizacijaEOFException, FileNotFoundException {
//        List<Changes> res = new ArrayList<>();
//
//        try (ObjectInputStream in = new ObjectInputStream(
//                new FileInputStream(SERIALIZATION_FILE_NAME)
//        )) {
//            while (true) {
//                try {
//                    Object obj = in.readObject();
//                    Changes change = (Changes) obj;
//                    res.add(change);
//                } catch (EOFException e) {
//                    break;
//                }
//            }
//        } catch (IOException exception) {
//            System.out.println("No changes were made!");
//        } catch (ClassNotFoundException e) {
//            throw new DeserijalizacijaEOFException(e.getMessage());
//        }
//        return res;
//    }
//    public synchronized static void writeChangesInDat(List<Changes> listChanges){
//        try(ObjectOutputStream out = new ObjectOutputStream(
//                new FileOutputStream(SERIALIZATION_FILE_NAME, true))) {
//            for(Changes c : listChanges){
//                out.writeObject(c);
//                out.flush();
//            }
//        } catch (IOException e) {
//            System.err.println(e);
//        }
//    }
//
//


