package tvz.hr.projekt.login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Login {


    private static final String USER_FILE = "users\\users.txt";
    private static final String ADMIN_FILE = "users\\admins.txt";


    public static boolean loginUser(String username, String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(password.getBytes("UTF-8"));
            String hashedPassword = Base64.getEncoder().encodeToString(hash);
            return checkUserFile(username, hashedPassword);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean loginAdmin(String username, String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(password.getBytes("UTF-8"));
            String hashedPassword = Base64.getEncoder().encodeToString(hash);
            return checkAdminFile(username, hashedPassword);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean checkUserFile(String username, String password) throws IOException {
        return checkFile(USER_FILE, username, password);
    }

    private static boolean checkAdminFile(String username, String password) throws IOException {
        return checkFile(ADMIN_FILE, username, password);
    }

    private static boolean checkFile(String file, String username, String password) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts[0].equals(username) && parts[1].equals(password)) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }
}
