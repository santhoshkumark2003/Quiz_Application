package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class type extends JFrame implements ActionListener {

    // Components
    JLabel welcomeLabel;
    JButton aptitudeButton, technicalButton, backButton;

    type() {
        // Set frame properties for larger window size
        setSize(1200, 700);  // Adjusting window size to be larger
        setLocation(200, 100);
        setVisible(true);

        // Set up the frame
        setTitle("Select Quiz Type");
        getContentPane().setBackground(Color.CYAN);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased padding for better spacing

        // Welcome Label
        welcomeLabel = new JLabel("Welcome, ");
        welcomeLabel.setFont(new Font("Viner Hand ITC", Font.BOLD, 40));
        welcomeLabel.setForeground(new Color(30, 144, 254));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(welcomeLabel, gbc);

        // Aptitude Button
        aptitudeButton = new JButton("Aptitude Quiz");
        aptitudeButton.setBackground(new Color(30, 144, 254));
        aptitudeButton.setForeground(Color.WHITE);
        aptitudeButton.addActionListener(this);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        aptitudeButton.setPreferredSize(new Dimension(400, 60));  // Larger button size
        add(aptitudeButton, gbc);

        // Technical Button
        technicalButton = new JButton("Technical Quiz");
        technicalButton.setBackground(new Color(30, 144, 254));
        technicalButton.setForeground(Color.WHITE);
        technicalButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        technicalButton.setPreferredSize(new Dimension(400, 60));  // Larger button size
        add(technicalButton, gbc);

        // Back Button (centered)
        backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 69, 0));  // Red color for 'Back'
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);

        // Set constraints for the Back button
        gbc.gridx = 0;                // Center horizontally
        gbc.gridy = 7;                // Position vertically
        gbc.gridwidth = 2;            // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Align to center
        gbc.fill = GridBagConstraints.NONE;     // No stretching
        add(backButton, gbc);

        // Set default close operation and other properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window on the screen
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // If Aptitude button is clicked
        if (ae.getSource() == aptitudeButton) {
            new apt().setVisible(true);
            setVisible(false);
        }
        // If Technical button is clicked
        else if (ae.getSource() == technicalButton) {
            new tech().setVisible(true);
            setVisible(false);
        }
        // If Back button is clicked (go back to login)
        else if (ae.getSource() == backButton) {
            new Login().setVisible(true);
            setVisible(false);  // Close the current window
        }
    }

    public static void main(String[] args) {
        // Simulate a username being passed after login (this would typically come from the login system)
        new type();
    }
}
