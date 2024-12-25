package com.uap.bankmanagement.utils;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uap.bankmanagement.exception.BankAccountException;
import com.uap.model.User;

import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

class UserAccountManagerTest {
    
    private JsonHelper mockJsonHelper;
    private UserAccountManager manager;

    @BeforeEach
    void setUp() {

        mockJsonHelper = mock(JsonHelper.class);
        
        manager = new UserAccountManager(mockJsonHelper);
    }

    @Test
    void testLogin_Success() throws IOException {

        JSONArray mockUsers = new JSONArray();
        JSONObject mockUser = new JSONObject();
        mockUser.put("email", "user@example.com");
        mockUser.put("password", "password123");
        mockUser.put("balance", 100.0);
        mockUser.put("account_number", "1234567890");
        mockUsers.put(mockUser);

        when(mockJsonHelper.loadUsers(anyString())).thenReturn(mockUsers);

        boolean loginSuccess = manager.login("user@example.com", "password123");

        assertTrue(loginSuccess);
        assertEquals("user@example.com", User.getEmail());
        assertEquals(100.0, User.getBalance());
    }

    @Test
    void testLogin_Fail() throws IOException {

        JSONArray mockUsers = new JSONArray();

        when(mockJsonHelper.loadUsers(anyString())).thenReturn(mockUsers);

        boolean loginSuccess = manager.login("user@example.com", "wrongpassword");

        assertFalse(loginSuccess);
    }
    
    @Test
    void testDepositToAccount() throws IOException {
        // Arrange
        doNothing().when(mockJsonHelper).updateUserBalance(anyString(), anyDouble());
        User.setBalance(100.0);
        String accountNumber = "expectedAccountNumber";
        User.setAccountNumber(accountNumber); // Set the account number for the test

        // Act
        manager.depositToAccount(50.0);

        // Assert
        verify(mockJsonHelper).updateUserBalance(eq(accountNumber), eq(150.0));
    }

    @Test
    void testWithdrawFromAccount_Success() throws IOException, BankAccountException {
        // Arrange
        doNothing().when(mockJsonHelper).updateUserBalance(anyString(), anyDouble());
        User.setBalance(100.0);
        String accountNumber = "expectedAccountNumber";
        User.setAccountNumber(accountNumber);

        // Act
        manager.withdrawFromAccount(50.0);

        // Assert
        verify(mockJsonHelper).updateUserBalance(eq(accountNumber), eq(-50.0));
    }

    @Test
    void testWithdrawFromAccount_InsufficientFunds() {
  
        User.setBalance(30.0);

        assertThrows(BankAccountException.class, () -> {
            manager.withdrawFromAccount(50.0);
        });
    }

    @Test
    void testTransferToAnAccount_Success() throws IOException, BankAccountException {

        doNothing().when(mockJsonHelper).updateUserBalance(anyString(), anyDouble());

        User.setBalance(100.0);

        manager.transferToAnAccount("9876543210", 50.0);

        verify(mockJsonHelper).updateUserBalance(eq(User.getAccountNumber()), eq(-50.0));
        verify(mockJsonHelper).updateUserBalance(eq("9876543210"), eq(50.0));
    }

    @Test
    void testTransferToAnAccount_InsufficientFunds() {
        User.setBalance(30.0);

        assertThrows(BankAccountException.class, () -> {
            manager.transferToAnAccount("9876543210", 50.0);
        });
    }

    @Test
    void testDeleteAccount() throws IOException {
        doNothing().when(mockJsonHelper).deleteUser(anyString());

        User.setEmail("user@example.com");

        manager.deleteAccount();

        verify(mockJsonHelper).deleteUser("user@example.com");
    }

    @Test
    void testGetAccountBalance() {

        User.setBalance(200.0);

        double balance = manager.getAccountBalance();

        assertEquals(200.0, balance);
    }
}
