package org.uap.bankmanagement.frame;

import org.uap.bankmanagement.utils.JsonHelper;
import org.uap.bankmanagement.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DeleteAccount implements ActionListener {
	
	JsonHelper jsonHelper;
    JPasswordField txt_password = new JPasswordField();
    JButton btn_delete;
    JButton btn_cancel;
    JFrame frame;

    public DeleteAccount() {
    	jsonHelper = new JsonHelper();
        frame = new JFrame();
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();

        JLabel label = new JLabel("Delete Your Account");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.RED);
        label.setFont(new Font("Helvetica", Font.BOLD, 20));
        p1.add(label);

        JLabel lbl_password = new JLabel("Enter Your Password: ");
        txt_password.setPreferredSize(new Dimension(250, 20));

        btn_delete = new JButton("Delete");
        btn_cancel = new JButton("Cancel");

        btn_delete.setBackground(Color.RED);
        btn_delete.setForeground(Color.WHITE);
        btn_cancel.setBackground(Color.BLUE);
        btn_cancel.setForeground(Color.WHITE);

        btn_delete.addActionListener(this);
        btn_cancel.addActionListener(this);

        p2.add(lbl_password);
        p2.add(txt_password);
        p3.add(btn_delete);
        p3.add(btn_cancel);

        frame.add(p1, BorderLayout.NORTH);
        frame.add(p2, BorderLayout.CENTER);
        frame.add(p3, BorderLayout.SOUTH);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_delete) {
            String enteredPassword = new String(txt_password.getPassword());

            try {
                // Verify password
                if (!User.getPassword().equals(enteredPassword)) {
                    JOptionPane.showMessageDialog(frame, "Incorrect password!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check account balance
                double balance = User.getBalance();
                if (balance > 0) {
                    int choice = JOptionPane.showOptionDialog(
                        frame,
                        "Your account has a balance of $" + balance + 
                        ". Do you want to withdraw or transfer your balance before deletion?",
                        "Account Balance Detected",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[] { "Withdraw or Transfer", "Delete Anyway" },
                        "Withdraw"
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        // Withdraw option
                        JOptionPane.showMessageDialog(frame, "Please proceed to withdraw or transfer your funds.");
                        Dashboard d = new Dashboard();
                        frame.dispose();
                        return;
                    } else if (choice == JOptionPane.NO_OPTION) {
                        // Confirm deletion without handling balance
                        int confirmDelete = JOptionPane.showConfirmDialog(
                            frame,
                            "Are you sure you want to delete your account? Your remaining balance will be lost.",
                            "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                        );

                        if (confirmDelete != JOptionPane.YES_OPTION) {
                            return;
                        }
                    }
                } else {
                    // If no balance, confirm deletion
                    int confirmDelete = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to delete your account? This action cannot be undone.",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );

                    if (confirmDelete != JOptionPane.YES_OPTION) {
                        return;
                    }
                }

                // Delete account
                jsonHelper.deleteUser(User.getEmail());
                JOptionPane.showMessageDialog(frame, "Your account has been successfully deleted.", "Account Deleted", JOptionPane.INFORMATION_MESSAGE);
                LoginFrame l = new LoginFrame();
                frame.dispose(); // Close the delete account window
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "An error occurred while deleting the account.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else if (e.getSource() == btn_cancel) {
            frame.dispose(); // Close the window if the user cancels
        }
    }


}
