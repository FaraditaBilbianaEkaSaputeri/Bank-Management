package org.uap.bankmanagement.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Utility class for handling JSON operations related to user data management.
 * Provides functionalities to create, read, update, and delete user data.
 */
public class JsonHelper {

    /**
     * Creates a new user and stores their information in the JSON file.
     *
     * @param email    the email address of the user
     * @param password the password for the user
     * @throws IOException if the email already exists or a file operation fails
     */
	
    public void createUser(String email, String password) throws IOException {
        String filePath = "users.json";
        JSONArray users = loadUsers(filePath);

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("email").equals(email)) {
                throw new IOException("Email already exists!");
            }
        }

        JSONObject newUser = new JSONObject();
        newUser.put("email", email);
        newUser.put("password", password);
        newUser.put("balance", 0.0);
        newUser.put("account_number", generateAccountNumber(12));
        users.put(newUser);

        writeUsersToFile(filePath, users);
    }

    /**
     * Loads the users from the specified JSON file.
     *
     * @param filePath the path to the JSON file
     * @return a JSONArray of users
     * @throws IOException if a file operation fails
     */
    public JSONArray loadUsers(String filePath) throws IOException {
        JSONArray users;
        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            users = new JSONArray(tokener);
        } catch (IOException e) {
            users = new JSONArray();
        }
        return users;
    }

    /**
     * Updates the balance of a user identified by their account number.
     *
     * @param account_number the account number of the user
     * @param newBalance     the amount to add to the user's balance
     * @throws IOException if the user is not found or a file operation fails
     */
    public void updateUserBalance(String account_number, double newBalance) throws IOException {
        String filePath = "users.json";
        JSONArray users = loadUsers(filePath);
        
        if (account_number == null) {
        	throw new IOException(" ");
        }
        
        boolean userFound = false;
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("account_number").equals(account_number)) {
                user.put("balance", user.getDouble("balance") + newBalance);
                userFound = true;
                break;
            }
        }

        if (!userFound) {
            throw new IOException("User not found!");
        }

        writeUsersToFile(filePath, users);
    }

    /**
     * Deletes a user identified by their email address.
     *
     * @param email the email address of the user to delete
     * @throws IOException if the user is not found or a file operation fails
     */
    public void deleteUser(String email) throws IOException {
        String filePath = "users.json";
        JSONArray users = loadUsers(filePath);

        boolean userFound = false;
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("email").equals(email)) {
                users.remove(i);
                userFound = true;
                break;
            }
        }

        if (!userFound) {
            throw new IOException("User not found!");
        }

        writeUsersToFile(filePath, users);
    }

    /**
     * Writes the user data to the specified JSON file.
     *
     * @param filePath the path to the JSON file
     * @param users    the JSONArray of users to write
     * @throws IOException if a file operation fails
     */
    private void writeUsersToFile(String filePath, JSONArray users) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(users.toString(4));
        }
    }

    /**
     * Generates a random account number of the specified length.
     *
     * @param length the length of the account number
     * @return a randomly generated account number
     * @throws IllegalArgumentException if the length is less than or equal to zero
     */
    private String generateAccountNumber(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();

        accountNumber.append(random.nextInt(9) + 1);

        for (int i = 1; i < length; i++) {
            accountNumber.append(random.nextInt(10));
        }

        return accountNumber.toString();
    }
    
    private static boolean isNumeric(String str) {
        return str != null && str.matches("[0-9]+");  // Only digits (no negative or decimal)
    }
}