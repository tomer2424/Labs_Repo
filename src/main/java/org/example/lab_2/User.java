package org.example.lab_2;

public class User
{
    private String userName;
    private String password;

    // Constructor for the User object.
    // It runs validation checks on both parameters before assignment.
    User(String _userName, String _password)
    {
        validateUserName(_userName);
        validatePassword(_password);
        this.userName = _userName;
        this.password = _password;
    }

    // Validates the username format and length.
    private void validateUserName(String userName)
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






















}
