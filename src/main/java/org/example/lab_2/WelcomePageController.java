
package org.example.lab_2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


// Controller class for the Welcome Page.
// This class handles the UI logic after a successful login,
// primarily allowing the user to navigate back to the login screen.
public class WelcomePageController
{

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

            // Re-populate the user list from the data source (as per lab requirements)
            controller.setUsers(UsersApp.CreateUserList());

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
