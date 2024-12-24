package org.uap.bankmanagement.model;

public class User {
    private static String email;
    private static String password;
    private static double balance;
    private static String accountNumber;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static double getBalance() {
        return balance;
    }

    public static void setBalance(double d) {
        User.balance = d;
    }

    public static String getAccountNumber() {
        return accountNumber;
    }

    public static void setAccountNumber(String accountNumber) {
        User.accountNumber = accountNumber;
    }
}
