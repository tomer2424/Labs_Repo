
package org.example.lab_2;

import javafx.fxml.FXML;
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

    // Getting the users from the HelloApplication class after it read them from the file
    public void setUsers(ArrayList<User> users)
    {
        this.allUsers = users;
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
            Error_Label.setText("user or password do not match");
        }
        else
        {
            try
            {
                // Load the Welcome Page FXML
                // and get the current window (Stage) to switch scenes
                FXMLLoader WelcomePage = new FXMLLoader(LoginPageController.class.getResource("Welcome-Page.fxml"));
                Parent root = WelcomePage.load();
                Stage stage = (Stage) Username_TextField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Welcome!");
                stage.show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
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
}
