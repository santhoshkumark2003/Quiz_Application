package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class adminlogin extends JFrame implements ActionListener {

    JButton login, signup;
    JTextField tfUsername;
    JPasswordField pfPassword;

    adminlogin() {
        setTitle("Admin Login");
        setSize(700, 700);
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Heading
        JLabel heading = new JLabel("Admin Login");
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
        heading.setForeground(new Color(30, 144, 254));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(heading, gbc);
        
        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        usernameLabel.setForeground(new Color(30, 144, 254));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, gbc);

        // Username Field
        tfUsername = new JTextField(15);
        tfUsername.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(tfUsername, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        passwordLabel.setForeground(new Color(30, 144, 254));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, gbc);

        // Password Field
        pfPassword = new JPasswordField(15);
        pfPassword.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(pfPassword, gbc);

        // Login Button
        login = new JButton("Login");
        login.setBackground(new Color(30, 144, 254));
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(login, gbc);

        // Sign Up Button
        signup = new JButton("User Page");
        signup.setBackground(new Color(30, 144, 254));
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        gbc.gridx = 1;
        panel.add(signup, gbc);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        try{        
            if(ae.getSource() == login){
                conn c1 = new conn();
                String uname  = tfUsername.getText();
                String pass  = new String(pfPassword.getPassword());
                String q  = "SELECT * FROM admin WHERE uname = '" + uname + "' AND pass = '" + pass + "'";
                
                ResultSet rs = c1.s.executeQuery(q);
                if(rs.next()){
                    setVisible(false);
                    new managead().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password");
                }
            }
            else if(ae.getSource() == signup){
                setVisible(false);
                new Login().setVisible(true);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new adminlogin();
    }
}
