package bg.mycompany.eventbuddy.web.exception;

public class UserNotFoundException extends RuntimeException {
    private final String username;

    public UserNotFoundException(String username) {
        super("User " + username + " not found!");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
