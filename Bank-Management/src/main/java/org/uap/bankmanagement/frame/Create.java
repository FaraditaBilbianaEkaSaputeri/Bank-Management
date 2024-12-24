package org.uap.bankmanagement.frame;


import org.uap.bankmanagement.utils.JsonHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Create implements ActionListener {
	
	JsonHelper jsonHelper;
	JFrame frame;
    JTextField txt_userid = new JTextField();
    JPasswordField txt_password = new JPasswordField();
    JButton btn_login;
    JButton btn_cancel;

    Create() {
    	jsonHelper = new JsonHelper();
        frame = new JFrame();
        frame.setSize(500, 310);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        JPanel p5 = new JPanel();
        	
        ImageIcon img = new ImageIcon("C:\\Users\\My Computer\\Documents\\UAP\\Bank-Management\\Bank-Management\\src\\main\\resources\\pic\\pic3.png");
        JLabel label = new JLabel();

        label.setIcon(img);
        label.setText("Create an Account");
        label.setHorizontalTextPosition(label.CENTER);
        label.setVerticalTextPosition(label.BOTTOM);
        label.setFont(new Font("Helvetica", Font.BOLD, 20));

        p1.setBounds(0, 20, 500, 150);
        p1.add(label);

        JLabel lbl_userid = new JLabel("Enter Your Email:  ");
        txt_userid = new JTextField();
        txt_userid.setPreferredSize(new Dimension(250, 20));

        JLabel lbl_password = new JLabel("Enter a Password:");
        txt_password = new JPasswordField();
        txt_password.setPreferredSize(new Dimension(250, 20));
        txt_password.setEchoChar('*');

        btn_login = new JButton("Sign Up");
        btn_login.setBackground(Color.BLUE);
        btn_login.setForeground(Color.WHITE);
        btn_login.addActionListener(this);
        
        btn_cancel = new JButton("Cancel");
        btn_cancel.setBackground(Color.RED);
        btn_cancel.setForeground(Color.WHITE);
        btn_cancel.addActionListener(this);
        
        p2.setBounds(0, 170, 500, 30);
        p3.setBounds(0, 200, 500, 30);
        p4.setBounds(30, 230, 500, 40);
        p2.add(lbl_userid);
        p2.add(txt_userid);
        p3.add(lbl_password);
        p3.add(txt_password);
        p4.add(btn_login);
        p4.add(btn_cancel);
        
        frame.add(p1);
        frame.add(p2);
        frame.add(p3);
        frame.add(p4);
        frame.add(p5);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_login) {
            String email = txt_userid.getText();
            String password = new String(txt_password.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Save user data to JSON file
                jsonHelper.createUser(email, password);
                JOptionPane.showMessageDialog(null, "Your Account has been created!", "Success", JOptionPane.PLAIN_MESSAGE);
                LoginFrame l = new LoginFrame();
                frame.dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving account data!", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }else if(e.getSource() == btn_cancel){
        	LoginFrame l = new LoginFrame();
        	frame.dispose();
        }
    }

}
