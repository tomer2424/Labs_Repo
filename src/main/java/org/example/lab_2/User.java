package org.example.lab_2;

public class User
{
    // The username and password
    private String userName;
    private String password;

    // The amount of failed login attempts and the time of the block
    private int failedAttempts;
    private long timeOfBlock;

    // Constructor for the User object.
    // It runs validation checks on both parameters before assignment.
    User(String _userName, String _password)
    {
        validateUserName(_userName);
        validatePassword(_password);
        this.userName = _userName;
        this.password = _password;

        failedAttempts = 0;
        timeOfBlock = 0;
    }

    // Validates the username format and length.
    private  void validateUserName(String userName)
    {
        if (userName.length() > 50)
        {
            throw new IllegalArgumentException("Username is too long, try something shorter");
        }

        String userName_regex_pattern = "^([a-zA-Z0-9\\-+%._]+)@([a-zA-Z0-9][a-zA-Z0-9.\\-]*)\\.([a-zA-Z]{2,})$";
        if (!(userName.matches(userName_regex_pattern)))
        {
            throw new IllegalArgumentException("Please enter a valid Email as username");
        }
    }

    // Validates password complexity and length
    private void validatePassword(String password)
    {
        if (password.length() < 8)
        {
            throw new IllegalArgumentException("Your password is too short, add more characters");
        }
        else if (password.length() > 12)
        {
            throw new IllegalArgumentException("Your password is too long, try a shorter one");
        }

        String password_regex_pattern = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=])[a-zA-Z0-9!@#$%^&*()_+\\-=]+$";
        if (!(password.matches(password_regex_pattern)))
        {
            throw new IllegalArgumentException("Please enter a valid password");
        }
    }

    // Getters for the userName and Password
    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }


    // Increasing the failed attempts counter by 1
    public synchronized void increaseFailedAttempts()
    {
        this.failedAttempts += 1;
    }

    // Resetting the failed attempts counter and the time for the block
    public synchronized void resetFailedAttempts()
    {
        this.failedAttempts = 0;
        this.timeOfBlock = 0;
    }

    // Saving the time of the block
    public synchronized void saveTimeOfBlock()
    {
        this.timeOfBlock = System.currentTimeMillis();
    }

    // Getters for the failed attempts and the time of block
    public int getFailedAttempts()
    {
        return this.failedAttempts;
    }

    public synchronized long getTimeOfBlock()
    {
        return this.timeOfBlock;
    }

}
