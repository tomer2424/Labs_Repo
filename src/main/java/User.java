
import java.util.regex.*;

public class User {
    private String userName;
    private String password;

    public User(String userName, String password)
    {
        // Validate username length and email format
        if (userName.length() > 50)
        {
            throw new IllegalArgumentException("Username is too long, try something shorter");
        }
        else if (isValidUserName(userName) == false)
        {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }

        // Validate password length and complexity
        if (password.length() > 12)
        {
            throw new IllegalArgumentException("Your password is too long, try a shorter one");
        }
        else if (password.length() < 8) {
            throw new IllegalArgumentException("Your password is too short, add more characters");
        }
        else if (isValidPassword(password) == false)
        {
            throw new IllegalArgumentException("Please enter a valid password");
        }

        this.userName = userName;
        this.password = password;
    }

    // Check if username is valid
    private boolean isValidUserName(String userName)
    {
        return userName.matches("^([a-zA-Z0-9\\-+%._]+)@([a-zA-Z0-9][a-zA-Z0-9\\-.-]*)\\.([a-zA-Z]{2,})$");
    }

    // Ensure password contains letters, digits, and special characters
    private boolean isValidPassword(String password)
    {
        return password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+-=]).+$");
    }
    
    public String getUsername() {
        return userName;
    }

    @Override
    public String toString() {
        return userName + " " + password;
    }
}