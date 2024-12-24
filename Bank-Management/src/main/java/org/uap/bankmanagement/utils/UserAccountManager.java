package org.uap.bankmanagement.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.uap.bankmanagement.exception.BankAccountException;
import org.uap.bankmanagement.model.User;

import java.io.IOException;
	
	public class UserAccountManager {
		JsonHelper jsonHelper = new JsonHelper();
		
		 public UserAccountManager() {}
		 
		 public UserAccountManager(JsonHelper jsonHelper) {
		        this.jsonHelper = jsonHelper;
		 }
	    /**
	     * Log in with the user's email and password.
	     * This sets the current user's details if the credentials are correct.
	     * 
	     * @param email The user's email to log in.
	     * @param password The user's password to log in.
	     * @return boolean Returns true if the login is successful, false otherwise.
	     */
	    public boolean login(String email, String password) throws IOException {
	        // Load all users
	        JSONArray users = jsonHelper.loadUsers("users.json");
	
	        for (int i = 0; i < users.length(); i++) {
	            JSONObject userJson = users.getJSONObject(i);
	            if (userJson.getString("email").equals(email) && userJson.getString("password").equals(password)) {
	                // Set the current user data
	                User.setEmail(userJson.getString("email"));
	                User.setPassword(userJson.getString("password"));
	                User.setBalance(userJson.getDouble("balance"));
	                User.setAccountNumber(userJson.getString("account_number"));
	                return true;
	            }
	        }
	        return false;  // User not found or incorrect credentials
	    }
	
	    /**
	     * Deposit a specified amount into the user's bank account.
	     * Updates the balance and saves the updated user data.
	     * 
	     * @param amount The amount to be deposited.
	     */
	    public void depositToAccount(double amount) throws IOException {
	        double newBalance = User.getBalance() + amount;
	        User.setBalance(newBalance);  // Update the balance
	
	        // Update the user data in the JSON file
	        jsonHelper.updateUserBalance(User.getAccountNumber(), newBalance);
	    }
	
	    /**
	     * Withdraw a specified amount from the user's bank account.
	     * Ensures sufficient balance exists for the withdrawal.
	     * 
	     * @param amount The amount to be withdrawn.
	     * @throws BankAccountException if the withdrawal amount exceeds the available balance.
	     */
	    public void withdrawFromAccount(double amount) throws BankAccountException, IOException {
	        if (User.getBalance() >= amount) {
	            User.setBalance(User.getBalance()-amount);  // Update the balance
	            // Save the updated user data to JSON file
	            jsonHelper.updateUserBalance(User.getAccountNumber(), amount*-1);
	        } else {
	            throw new BankAccountException("Insufficient funds for withdrawal.");
	        }
	    }
	    
	    /**
	     * Transfer a specified amount into the another user's bank account.
	     * Updates the balance and saves the updated user data.
	     * 
	     * @param amount The amount to be deposited.
	     */
	    public void transferToAnAccount(String account_number, double amount) throws BankAccountException, IOException {
	    	if (User.getBalance() >= amount) {
	    		User.setBalance(User.getBalance()-amount);  
	      
	    		jsonHelper.updateUserBalance(User.getAccountNumber(), amount*-1);
	    		jsonHelper.updateUserBalance(account_number, amount);
	    	} else {
	    		throw new BankAccountException("Insufficient funds for transfer.");
	    	}
	    }
	
	    /**
	     * Delete the current user's account, including removing their data from the JSON file.
	     */
	    public void deleteAccount() throws IOException {
	        // Delete user from JSON file
	        jsonHelper.deleteUser(User.getEmail());
	        System.out.println("User account has been deleted.");
	    }
	
	    /**
	     * Get the current user's balance.
	     * 
	     * @return The current balance of the user.
	     */
	    public double getAccountBalance() {
	        return User.getBalance();
	    }
	}
