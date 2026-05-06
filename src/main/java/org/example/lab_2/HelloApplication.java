package org.example.lab_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        int maxAttempts;
        int blockTime;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter max attempts for login: ");
        maxAttempts = scanner.nextInt();
        System.out.print("Enter block time in seconds: ");
        blockTime = scanner.nextInt();
        scanner.close();

        // Calling the UsersApp class and getting an arrayList of users.
        // the CreateUserList() method is static.
        ArrayList<User> userList = UsersApp.CreateUserList();

        // Creating the login page to enter username and password.
        FXMLLoader loginPage = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(loginPage.load());

        // Getting a reference to the LoginPageController class,
        // and passing it the arrayList of users.
        LoginPageController controller = loginPage.getController();
        controller.setUsers(userList);

        // Passing the maxAttempts and the blockTime variables to the LoginPageController,
        controller.setMaxAttempts(maxAttempts);
        controller.setBlockTime(blockTime);

        stage.setTitle("Login page");
        stage.setScene(scene);
        stage.show();
    }
}
