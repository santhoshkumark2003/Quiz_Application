package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {

    JButton login, signup, adminLogin;  // Added Admin Login button
    JTextField tfUsername;
    JPasswordField pfPassword;

    Login() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Adding Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/login.jpeg"));
        JLabel image = new JLabel(i1);
        image.setBounds(0, 0, 600, 500);
        add(image);

        // Heading
        JLabel heading = new JLabel("User Login");
        heading.setBounds(750, 60, 300, 45);
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD, 40));
        heading.setForeground(new Color(30, 144, 254));
        add(heading);

        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(810, 150, 300, 20);
        usernameLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        usernameLabel.setForeground(new Color(30, 144, 254));
        add(usernameLabel);

        // Username Field
        tfUsername = new JTextField();
        tfUsername.setBounds(735, 180, 300, 25);
        tfUsername.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(tfUsername);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(810, 220, 300, 20);
        passwordLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        passwordLabel.setForeground(new Color(30, 144, 254));
        add(passwordLabel);

        // Password Field
        pfPassword = new JPasswordField();
        pfPassword.setBounds(735, 250, 300, 25);
        pfPassword.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(pfPassword);

        // Login Button
        login = new JButton("Login");
        login.setBounds(735, 300, 120, 25);
        login.setBackground(new Color(30, 144, 254));
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        // Sign Up Button
        signup = new JButton("Sign Up");
        signup.setBounds(915, 300, 120, 25);
        signup.setBackground(new Color(30, 144, 254));
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        add(signup);

        // Admin Login Button (New)
        adminLogin = new JButton("Admin Login");
        adminLogin.setBounds(820, 340, 150, 25);
        adminLogin.setBackground(new Color(255, 69, 0)); // Red for distinction
        adminLogin.setForeground(Color.WHITE);
        adminLogin.addActionListener(this);
        add(adminLogin);

        setSize(1200, 500);
        setLocation(200, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == login) {
                conn c1 = new conn();
                String uname = tfUsername.getText();
                String pass = pfPassword.getText();
                String q = "SELECT * FROM signup WHERE uname = '" + uname + "' AND pass = '" + pass + "'";

                ResultSet rs = c1.s.executeQuery(q);
                if (rs.next()) {
                    setVisible(false);
                    new type().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or Password");
                }
            } 
            else if (ae.getSource() == signup) {
                setVisible(false);
                new Signup().setVisible(true);
            } 
            else if (ae.getSource() == adminLogin) {  // Admin Login Action
                setVisible(false);
                new adminlogin().setVisible(true);  // Redirects to Admin Login Page
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
