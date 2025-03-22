package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class del extends JFrame implements ActionListener {
    JLabel titleLabel, categoryLabel, deleteLabel;
    JTextField deleteField;
    JComboBox<String> categoryDropdown;
    JButton deleteButton, backButton, viewButton;
    JRadioButton techRadio, aptRadio;
    ButtonGroup categoryGroup;
    DefaultComboBoxModel<String> categoryModel;
    
    public del() {
        setTitle("Admin - Delete Questions");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        titleLabel = new JLabel("Delete Questions", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Radio Buttons for Classification
        techRadio = new JRadioButton("Technical", true);
        aptRadio = new JRadioButton("Aptitude");
        categoryGroup = new ButtonGroup();
        categoryGroup.add(techRadio);
        categoryGroup.add(aptRadio);
        
        JPanel radioPanel = new JPanel();
        radioPanel.add(techRadio);
        radioPanel.add(aptRadio);
        gbc.gridy = 1;
        add(radioPanel, gbc);

        // Category Dropdown
        categoryLabel = new JLabel("Select Category:");
        categoryModel = new DefaultComboBoxModel<>();
        categoryDropdown = new JComboBox<>(categoryModel);
        gbc.gridy = 2;
        add(categoryLabel, gbc);
        gbc.gridy = 3;
        add(categoryDropdown, gbc);
        
        // Delete Section
        deleteLabel = new JLabel("Delete Question by ID:");
        deleteField = new JTextField(20);
        gbc.gridy = 4;
        add(deleteLabel, gbc);
        gbc.gridy = 5;
        add(deleteField, gbc);
        
        deleteButton = new JButton("Delete Question");
        viewButton = new JButton("View Questions");
        backButton = new JButton("Back");
        gbc.gridy = 6;
        add(deleteButton, gbc);
        gbc.gridy = 7;
        add(viewButton, gbc);
        gbc.gridy = 8;
        add(backButton, gbc);

        deleteButton.addActionListener(this);
        viewButton.addActionListener(this);
        backButton.addActionListener(this);
        techRadio.addActionListener(this);
        aptRadio.addActionListener(this);
        
        loadCategories("tech");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadCategories(String classification) {
        categoryModel.removeAllElements();
        try {
            conn connection = new conn();
            String query = "SELECT cat FROM " + classification;
            ResultSet rs = connection.s.executeQuery(query);
            while (rs.next()) {
                categoryModel.addElement(rs.getString("cat"));
            }
            connection.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deleteButton) {
            deleteQuestion();
        } else if (ae.getSource() == viewButton) {
            viewQuestions();
        } else if (ae.getSource() == backButton) {
            new adqs().setVisible(true);
            setVisible(false);
        } else if (ae.getSource() == techRadio) {
            loadCategories("tech");
        } else if (ae.getSource() == aptRadio) {
            loadCategories("apt");
        }
    }

    private void deleteQuestion() {
        String questionId = deleteField.getText().trim();
        String category = (String) categoryDropdown.getSelectedItem();

        if (questionId.isEmpty() || category == null) {
            JOptionPane.showMessageDialog(this, "Please enter the Question ID and select a category.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            conn connection = new conn();
            String query = "DELETE FROM " + category + " WHERE id = ?";
            PreparedStatement stmt = connection.c.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(questionId));
            int rowsAffected = stmt.executeUpdate();
            connection.c.close();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Question Deleted Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No question found with the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewQuestions() {
        String category = (String) categoryDropdown.getSelectedItem();
        if (category == null) {
            JOptionPane.showMessageDialog(this, "Please select a category.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            conn connection = new conn();
            String query = "SELECT id, question FROM " + category;
            ResultSet rs = connection.s.executeQuery(query);
            StringBuilder questions = new StringBuilder("Available Questions:\n");
            while (rs.next()) {
                questions.append("ID: ").append(rs.getInt("id")).append(" - ").append(rs.getString("question")).append("\n");
            }
            connection.c.close();
            JOptionPane.showMessageDialog(this, questions.toString(), "Questions", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new del();
    }
}
