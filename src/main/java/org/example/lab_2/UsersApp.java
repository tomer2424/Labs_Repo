package org.example.lab_2;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

public class UsersApp
{
    // Reads the "Users.txt" file and parses each line into a User object.
    // Valid users are added to an ArrayList, while invalid entries are skipped.
    public static ArrayList<User> CreateUserList()
    {

        ArrayList<User> userList = new ArrayList<>();
        File inputFile = new File("Users.txt");

        // check to see if the file opened successfully
        try (Scanner reader = new Scanner(inputFile))
        {
            while (reader.hasNextLine())
            {
                String line = reader.nextLine();
                String[] arr = line.trim().split("\\s+");
                try
                {
                    // Attempt to create a new User object from the split strings
                    User user = new User(arr[0], arr[1]);
                    userList.add(user);
                }
                catch (IllegalArgumentException e)
                {
                    // Handle cases where user data doesn't meet validation rules
                    System.out.println(line + " - " + e.getMessage());
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());

        }
        return userList;
    }
}
