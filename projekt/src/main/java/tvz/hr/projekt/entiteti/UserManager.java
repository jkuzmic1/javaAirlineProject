package tvz.hr.projekt.entiteti;

public class UserManager {

    private static UserManager instance;

    private User loggedInUser;

    private UserManager() {
        // Singleton pattern
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}

