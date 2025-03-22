package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class tech extends JFrame implements ActionListener {
    
    JLabel titleLabel;
    JPanel buttonPanel;
    JButton backButton;
    ArrayList<JButton> categoryButtons = new ArrayList<>();
    
    tech() {
        // Set frame properties
        setSize(1200, 700);
        setLocation(200, 100);
        setTitle("Technical Quiz");
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title Label
        titleLabel = new JLabel("Technical Quiz");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);
        
        // Retrieve categories from database and create buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 2, 10, 10));
        String[] categories = getTechnicalCategories();
        for (String category : categories) {
            JButton categoryButton = new JButton(category);
            categoryButton.setPreferredSize(new Dimension(400, 60));
            categoryButton.setBackground(new Color(0, 102, 204));
            categoryButton.setForeground(Color.WHITE);
            categoryButton.addActionListener(this);
            buttonPanel.add(categoryButton);
            categoryButtons.add(categoryButton);
        }
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(buttonPanel, gbc);
        
        // Back Button
        backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 69, 0));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(backButton, gbc);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private String[] getTechnicalCategories() {
        ArrayList<String> categories = new ArrayList<>();
        try {
            conn connection = new conn();
            Statement stmt = connection.s;
            ResultSet rs = stmt.executeQuery("SELECT * FROM tech");
            
            while (rs.next()) {
                categories.add(rs.getString("cat"));
            }
            
            connection.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories.toArray(new String[0]);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backButton) {
            new type().setVisible(true);
            setVisible(false);
        } else {
            // Loop through the buttons to check which category is selected
            for (JButton button : categoryButtons) {
                if (ae.getSource() == button) {
                    String selectedCategory = button.getText();  // Get the category name
                    new Quiz(selectedCategory).setVisible(true);  // Pass the selected category to Quiz
                    setVisible(false);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new tech();
    }
}
