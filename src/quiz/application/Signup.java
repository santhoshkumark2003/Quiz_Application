
package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Signup extends JFrame implements ActionListener {

    JTextField tfName, tfAge, tfQualifications, tfUsername;
    JPasswordField pfPassword;
    JComboBox<String> cbType;
    JButton signup, back;
 
    Signup() {
             getContentPane().setBackground(Color.CYAN);
        setLayout(new GridBagLayout()); // Use GridBagLayout for centering
     
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components
        // Heading
        JLabel heading = new JLabel("User Sign Up");
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD, 40));
        heading.setForeground(new Color(30, 144, 254));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(heading, gbc);

// Name Label
JLabel nameLabel = new JLabel("Name:");
nameLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
nameLabel.setForeground(new Color(30, 144, 254));
gbc.gridwidth = 1;      // Label takes up only one column
gbc.gridx = 0;          // Align in the first column
gbc.gridy = 1;          // Place it on the second row (same as Age label)
gbc.anchor = GridBagConstraints.WEST;  // Align the label to the left
add(nameLabel, gbc);

// Name Field
tfName = new JTextField();
tfName.setFont(new Font("Times New Roman", Font.BOLD, 20));
gbc.gridx = 1;          // Text field should be placed in the second column
gbc.gridy = 1;          // Same row as the Name label
gbc.gridwidth = 2;      // Text field should span two columns like Age
gbc.fill = GridBagConstraints.HORIZONTAL;  // Make the text field fill horizontally
add(tfName, gbc);



        // Age Label
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        ageLabel.setForeground(new Color(30, 144, 254));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(ageLabel, gbc);

        // Age Field
        tfAge = new JTextField();
        tfAge.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfAge, gbc);

        // Qualifications Label
        JLabel qualificationsLabel = new JLabel("Qualifications:");
        qualificationsLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        qualificationsLabel.setForeground(new Color(30, 144, 254));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(qualificationsLabel, gbc);

        // Qualifications Field
        tfQualifications = new JTextField();
        tfQualifications.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfQualifications, gbc);

        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        usernameLabel.setForeground(new Color(30, 144, 254));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(usernameLabel, gbc);

        // Username Field
        tfUsername = new JTextField();
        tfUsername.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfUsername, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        passwordLabel.setForeground(new Color(30, 144, 254));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(passwordLabel, gbc);

        // Password Field
        pfPassword = new JPasswordField();
        pfPassword.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(pfPassword, gbc);

        // Type Label
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        typeLabel.setForeground(new Color(30, 144, 254));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(typeLabel, gbc);

        // Type Dropdown
        cbType = new JComboBox<>(new String[] { "Student", "Professional" });
        cbType.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(cbType, gbc);

        // Sign Up Button
        signup = new JButton("Sign Up");
        signup.setBackground(new Color(30, 144, 254));
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(signup, gbc);

        // Back Button
        back = new JButton("Back");
        back.setBackground(new Color(30, 144, 254));
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(back, gbc);

        // Set JFrame properties
        setSize(1200, 700);
        setLocation(200, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == signup) {
            String name = tfName.getText();
            String age = tfAge.getText();
            String qual = tfQualifications.getText();
            String uname = tfUsername.getText();
            String pass = new String(pfPassword.getPassword());
            String type = (String) cbType.getSelectedItem();
        
            try {
                conn c1 = new conn();
                String q1 = "insert into signup values('" + name + "','" + age + "','" + qual + "','" + uname + "','" + pass + "','" + type + "')";
                c1.s.executeUpdate(q1);
                
                new Login().setVisible(true);
                setVisible(false);
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login().setVisible(true); // Redirect to login screen
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}