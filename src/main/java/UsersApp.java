import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.PrintWriter;

public class UsersApp {
    public static void main(String[] args) {
        // creating a new array list and opening a file
        ArrayList<User> arr = new ArrayList<>();
        File file = new File("Users.txt");

        // check to see if the file opened successfully
        try
        {
            // if so, creating a new scanner to read from the file,
            // and getting the username and the password from the file
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine())
            {
                String line = reader.nextLine().trim();
                String[] parts = line.split("\\s+");

                String username = parts[0];
                String password = parts[1];

                // tyring to create a new user
                try
                {
                    // if we succeed, add it to the array list
                    User newUser = new User(username, password);
                    arr.add(newUser);
                }
                // if we fail, print an error
                catch (Exception e)
                {
                    // printing the line with the matching error message.
                    System.out.println(line + " - " + e.getMessage());
                }
            }
        }
        // if we failed to open the file, print an error
        catch (Exception e)
        {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Sort users alphabetically by username
        Collections.sort(arr, (u1, u2) -> u1.getUsername().compareTo(u2.getUsername()));

        // Write sorted list to output file
        try (PrintWriter writer = new PrintWriter("out2.txt")) {
            for (User u : arr) {
                writer.println(u.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}









