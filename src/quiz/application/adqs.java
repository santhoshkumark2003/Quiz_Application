package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class adqs extends JFrame implements ActionListener {

    // Components
    JButton addButton, editButton, deleteButton, backButton;

    adqs() {
        // Set frame properties
        setSize(700, 700);
        setLocation(200, 100);
        setTitle("Manage Quiz");
        getContentPane().setBackground(Color.CYAN);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add Quiz Button
        addButton = new JButton("Add Quiz");
        addButton.setBackground(new Color(30, 144, 254));
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(200, 60));
        addButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(addButton, gbc);

        // Edit Quiz Button (New)
        editButton = new JButton("Edit Quiz");
        editButton.setBackground(new Color(255, 165, 0)); // Orange color for distinction
        editButton.setForeground(Color.WHITE);
        editButton.setPreferredSize(new Dimension(200, 60));
        editButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(editButton, gbc);

        // Delete Quiz Button
        deleteButton = new JButton("Delete Quiz");
        deleteButton.setBackground(new Color(30, 144, 254));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setPreferredSize(new Dimension(200, 60));
        deleteButton.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(deleteButton, gbc);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 69, 0));  // Red color for 'Back'
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backButton, gbc);

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addButton) {
            new add().setVisible(true);
            setVisible(false);
        } else if (ae.getSource() == editButton) {
            new edit().setVisible(true);  // Open Edit Quiz window
            setVisible(false);
        } else if (ae.getSource() == deleteButton) {
            new del().setVisible(true);
            setVisible(false);
        } else if (ae.getSource() == backButton) {
            new managead().setVisible(true);
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new adqs();
    }
}
