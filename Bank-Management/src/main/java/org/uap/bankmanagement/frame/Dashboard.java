package org.uap.bankmanagement.frame;

import org.uap.bankmanagement.exception.BankAccountException;
import org.uap.bankmanagement.utils.UserAccountManager;
import org.uap.bankmanagement.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Dashboard implements ActionListener {
    
	private UserAccountManager userManager;
    private JFrame frame;
    private JButton b1_check;
    private JButton b2_withdraw;
    private JButton b3_deposit;
    private JButton b4_transfer;
    private JButton b5_delete_account;
    private JButton b7_logout;
    private JButton b6_user_info;
  	

    Dashboard() {
    	userManager = new UserAccountManager();
        frame = new JFrame();
        frame.setTitle("Dashboard");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        
        p1.setBackground(Color.red);
        p1.setBounds(0, 0, 200, 461);
        p2.setBounds(200, 40, 500, 150);
        p3.setBounds(200, 190, 500, 50);
        p4.setBounds(200, 220, 500, 50);
        
        b1_check = new JButton("Check Balance");
        b1_check.setBackground(Color.RED);
        b1_check.setForeground(Color.WHITE);
        b1_check.setFocusable(false);
        b1_check.setLayout(new FlowLayout(FlowLayout.LEFT));
        b1_check.addActionListener(this);

        b2_withdraw = new JButton("Withdraw Money");
        b2_withdraw.setBackground(Color.RED);
        b2_withdraw.setForeground(Color.WHITE);
        b2_withdraw.setFocusable(false);
        b2_withdraw.addActionListener(this);
        b2_withdraw.setLayout(new FlowLayout(FlowLayout.CENTER));

        b3_deposit = new JButton("Deposit Money");
        b3_deposit.setBackground(Color.RED);
        b3_deposit.setForeground(Color.WHITE);
        b3_deposit.setFocusable(false);
        b3_deposit.addActionListener(this);
        b3_deposit.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        b4_transfer = new JButton("Transfer Money");
        b4_transfer.setBackground(Color.RED);
        b4_transfer.setForeground(Color.WHITE);
        b4_transfer.setFocusable(false);
        b4_transfer.addActionListener(this);
        b4_transfer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        b5_delete_account = new JButton("Delete Account");
        b5_delete_account.setBackground(Color.WHITE);
        b5_delete_account.setForeground(Color.RED);
        b5_delete_account.setFocusable(false);
        b5_delete_account.addActionListener(this);
        
        b6_user_info = new JButton("User Info");
        b6_user_info.setBackground(Color.WHITE);
        b6_user_info.setForeground(Color.RED);
        b6_user_info.setFocusable(false);
        b6_user_info.addActionListener(this);

        b7_logout = new JButton("Log out");
        b7_logout.setBackground(Color.WHITE);
        b7_logout.setForeground(Color.RED);
        b7_logout.setFocusable(false);
        b7_logout.addActionListener(this);
        
        ImageIcon img = new ImageIcon("C:\\Users\\My Computer\\Documents\\UAP\\Bank-Management\\Bank-Management\\src\\main\\resources\\pic\\pic1.png");
        ImageIcon img2 = new ImageIcon("C:\\Users\\My Computer\\Documents\\UAP\\Bank-Management\\Bank-Management\\src\\main\\resources\\pic\\pic2.png");
        JLabel label = new JLabel("UMM Bank");
        JLabel label2 = new JLabel("No 1. Bank on The Planet");
        JLabel label3 = new JLabel("WELCOME TO YOUR DASHBOARD");

        label.setHorizontalTextPosition(label.CENTER);
        label.setVerticalTextPosition(label.BOTTOM);
        label3.setHorizontalTextPosition(label3.CENTER);
        label3.setVerticalTextPosition(label3.BOTTOM);
        label.setForeground(Color.WHITE);
        label2.setForeground(Color.WHITE);
        label3.setForeground(Color.RED);
        label.setFont(new Font("Helvetica", Font.BOLD, 20));
        label3.setFont(new Font("Helvetica", Font.BOLD, 20));
        label.setIcon(img);
        label3.setIcon(img2);

        p1.add(label);
        p1.add(label2);
        p1.add(b6_user_info);
        p1.add(b5_delete_account);
        p1.add(b7_logout);
        p2.add(label3);
        p3.add(b1_check);
        p3.add(b2_withdraw);
        p4.add(b3_deposit);
        p4.add(b4_transfer);

        frame.add(p2);
        frame.add(p3);
        frame.add(p4);
        frame.add(p1);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    	 if (e.getSource() == b3_deposit) {
    	        try {
    	            String strAmount = JOptionPane.showInputDialog(null, "Enter a Value To Deposit:", "Deposit Amount", JOptionPane.QUESTION_MESSAGE);

    	            if (strAmount == null) {
    	                return; 
    	            }

    	            double amount = Double.parseDouble(strAmount);
    	            userManager.depositToAccount(amount);
    	            JOptionPane.showMessageDialog(null, "Deposit successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    	        } catch (IOException | NumberFormatException ex) {
    	            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
    	        }
    	    }

    	    if (e.getSource() == b2_withdraw) {
    	        try {
    	            String strAmount = JOptionPane.showInputDialog(null, "Enter a Value To Withdraw:", "Withdraw Amount", JOptionPane.QUESTION_MESSAGE);

    	            if (strAmount == null) {
    	                return; 
    	            }

    	            double amount = Double.parseDouble(strAmount);
    	            userManager.withdrawFromAccount(amount);
    	            JOptionPane.showMessageDialog(null, "Withdraw successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    	        } catch (IOException | BankAccountException | NumberFormatException ex) {
    	            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	        }
    	    }

    	    if (e.getSource() == b4_transfer) {
    	        try {
    	            String recipientAccount = JOptionPane.showInputDialog(null, "Enter the recipient's account number:", "Transfer Funds", JOptionPane.QUESTION_MESSAGE);

    	            if (recipientAccount == null) {
    	                return;
    	            }

    	            if (recipientAccount.trim().isEmpty()) {
    	                JOptionPane.showMessageDialog(null, "Recipient account number cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
    	                return;
    	            }

    	            String strAmount = JOptionPane.showInputDialog(null, "Enter the amount to transfer:", "Transfer Amount", JOptionPane.QUESTION_MESSAGE);

    	            if (strAmount == null) {
    	                return;
    	            }

    	            double amount = Double.parseDouble(strAmount);

    	            if (amount <= 0) {
    	                JOptionPane.showMessageDialog(null, "Amount must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
    	                return;
    	            }

    	            userManager.transferToAnAccount(recipientAccount, amount);
    	            JOptionPane.showMessageDialog(null, "Transfer successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    	        } catch (IOException | BankAccountException | NumberFormatException ex) {
    	            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	        }
    	    }

        if (e.getSource() == b1_check) {
            try {
                JOptionPane.showMessageDialog(null, "Current Balance: " + userManager.getAccountBalance(), "Information", 1);
            } catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null, npe, "Error", 2);
            }
        }
        
        if (e.getSource() == b6_user_info) {
            try {
                JOptionPane.showMessageDialog(null, "Email: " + User.getEmail() + "\n" + "Account Number: " + User.getAccountNumber(), "Current Balance", 1);
            } catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null, npe, "Error", 2);
            }
        }
        
        if (e.getSource() == b5_delete_account) {
            DeleteAccount d = new DeleteAccount();
            frame.dispose();
        }
        
        if (e.getSource() == b7_logout) {
            LoginFrame d = new LoginFrame();
            frame.dispose();
        }
    }
}
