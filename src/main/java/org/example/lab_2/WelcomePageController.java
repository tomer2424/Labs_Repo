
package org.example.lab_2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


// Controller class for the Welcome Page.
// This class handles the UI logic after a successful login,
// primarily allowing the user to navigate back to the login screen.
public class WelcomePageController
{

    private ArrayList<User> allUsers;
    private int maxAttempts;
    private int blockTime;

    public void setData(ArrayList<User> users, int maxAttempts, int blockTime)
    {
        this.allUsers = users;
        this.maxAttempts = maxAttempts;
        this.blockTime = blockTime;
    }


    @FXML
    private Label Welcome_Label;

    // Handles the action of the "Back" button.
    // This method loads the login-page FXML, initializes its controller
    // with the user list, and switches the current scene.
    @FXML
    public void handleBackToLoginPage()
    {
        try
        {
            // Load the FXML file for the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-page.fxml"));
            Parent root = loader.load();

            // Retrieve the current stage using the Welcome_Label's scene
            Stage stage = (Stage) Welcome_Label.getScene().getWindow();

            // Access the LoginPageController to pass the necessary user data
            LoginPageController controller = loader.getController();

            controller.setUsers(this.allUsers);
            controller.setMaxAttempts(this.maxAttempts);
            controller.setBlockTime(this.blockTime);

            // Set the new scene and update the stage title
            stage.setScene(new Scene(root));
            stage.setTitle("Login page");
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
