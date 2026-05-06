
package org.example.lab_2;

import  javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


// Controller for the Login Page.
// This class manages user authentication by comparing input credentials
// against a list of authorized users loaded from a data source.
public class LoginPageController
{

    private ArrayList<User> allUsers;
    private int maxAttempts;
    private int blockTime;

    // Getting the users from the HelloApplication class after it read them from the file
    public void setUsers(ArrayList<User> users)
    {
        this.allUsers = users;
    }

    // Setters for the maxAttempts and the blockTime
    public void setMaxAttempts(int attempts)
    {
        this.maxAttempts = attempts;
    }

    public void setBlockTime(int time)
    {
        this.blockTime = time;
    }

    @FXML
    private Label Username_Label;
    @FXML
    private TextField Username_TextField;
    @FXML
    private Label Password_Label;
    @FXML
    private PasswordField Password_TextField;
    @FXML
    private Label Error_Label;

    //Triggered when the user clicks the Login button.
    //It validates the credentials and either displays an error message
    //or navigates the user to the Welcome Page.
    @FXML
    public void handleCheckCredentials()
    {
        if (!checkCredentials())
        {
            // If the checkCredentials returned false, we first print an error message
            Error_Label.setText("user or password do not match");

            // Then we try to find the user that typed the wrong password
            String Username_input = Username_TextField.getText();
            for (User user : allUsers)
            {
                if (user.getUserName().equals(Username_input))
                {
                    // If we found the user, we create a new LoginAttemptsTask object that will increase the
                    // login attempts counter of the user, and check if we passed the max attempts
                    UpdateLoginAttempts LoginAttemptsTask = new UpdateLoginAttempts(user);
                    // Then we create a new thread, pass the object to it and start the thread
                    Thread LoginAttemptsThread = new Thread(LoginAttemptsTask);
                    LoginAttemptsThread.start();
                    break;
                }

            }
        }
        else
        {
            // If the checkCredentials returned true
            // We find the user
            String Username_input = Username_TextField.getText();
            for (User user : allUsers)
            {
                if (user.getUserName().equals(Username_input))
                {
                    // We create a new CheckBlock object that will check if the user is blocked or not
                    CheckBlocked blockTask = new CheckBlocked(user);
                    // Then we create a new thread, pass the object to it and start the thread
                    Thread blockThread = new Thread(blockTask);
                    blockThread.start();
                    break;
                }
            }
        }
    }

    // Function to verify if the entered username and password exist in the database.
    public boolean checkCredentials()
    {
        String Username_input = Username_TextField.getText();
        String Password_input = Password_TextField.getText();

        for (User user : allUsers)
        {
            if ( (user.getUserName().equals(Username_input)) && (user.getPassword().equals(Password_input)) )
            {
                return true;
            }
        }
        return false;
    }



    // We implement the threads in separate classes to ensure that each task is isolated.
    // Using separate inner classes (instead of making the LoginPageController implement Runnable),
    // allows us to avoid messy conditional logic in a single 'run()' method,
    // and allows each background task to be managed independently.

    // A class to increments the failed login counter, and timestamps the block time if the limit is reached.
    public class UpdateLoginAttempts implements Runnable
    {
        private User user;
        UpdateLoginAttempts(User _user)
        {
            this.user = _user;
        }

        @Override
        public void run()
        {
            // Check to see if the user is blocked or not
            // if user.getTimeOfBlock was > 0 then the user was already blocked,
            // and we don't want to reset the block time
            if (user.getTimeOfBlock() == 0)
            {
                // If the user wasn't blocked, we up the attempts counter
                // and check if we exceeded the max attempts
                user.increaseFailedAttempts();
                if (user.getFailedAttempts() >= maxAttempts)
                {
                    user.saveTimeOfBlock();
                }
            }
        }
    }

    // A class that checks the user's block status, and handles UI navigation or cooldown messages.
    public class CheckBlocked implements Runnable
    {
        private User user;
        CheckBlocked(User _user)
        {
            this.user = _user;
        }

        @Override
        public void run()
        {
            boolean canLogin = false;
            // Check if the user is blocked
            if (user.getTimeOfBlock() > 0)
            {
                // If he is blocked, check if enough time has passed to unblock him
                long timePassed = System.currentTimeMillis() - user.getTimeOfBlock();
                if (timePassed >= blockTime * 1000)
                {
                    // If enough time has passed, we will unblock the user
                    user.resetFailedAttempts();
                    canLogin = true;
                }
                else
                {
                    // If not enough time has passed, calculate how much time he has left
                    // and show an error message
                    long secondsLeft = blockTime - (timePassed / 1000);
                    javafx.application.Platform.runLater(() -> {Error_Label.setText("You are blocked! Try again in " + secondsLeft + " seconds.");});
                }
            }
            else
            {
                // If the user isn't blocked, we reset his failed attempts and allow him to login
                user.resetFailedAttempts();
               canLogin = true;
            }

            // If the user is not blocked, we change the scene to the welcome page
            if (canLogin)
            {
                javafx.application.Platform.runLater(() -> {
                    try
                    {
                        // Load the Welcome Page FXML
                        // and get the current window (Stage) to switch scenes
                        FXMLLoader welcomePageLoader = new FXMLLoader(getClass().getResource("Welcome-Page.fxml"));
                        Parent root = welcomePageLoader.load();

                        WelcomePageController welcomeController = welcomePageLoader.getController();
                        welcomeController.setData(allUsers, maxAttempts, blockTime);

                        Stage stage = (Stage) Username_TextField.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Welcome!");
                        stage.show();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });
            }
        }
    }





}
