package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class edit extends JFrame implements ActionListener {
    JLabel titleLabel, categoryLabel, questionLabel, idLabel;
    JTextField idField, newIdField, questionField;
    JComboBox<String> categoryDropdown;
    JButton fetchButton, updateButton, backButton;
    JTextField option1Field, option2Field, option3Field, option4Field;
    JComboBox<String> correctDropdown;
    DefaultComboBoxModel<String> categoryModel;
     JRadioButton techRadio, aptRadio;
     ButtonGroup categoryGroup;

    public edit() {
        setTitle("Admin - Edit Questions");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        titleLabel = new JLabel("Edit Questions", JLabel.CENTER);
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
        gbc.gridy = 1;
        add(categoryLabel, gbc);
        gbc.gridy = 2;
        add(categoryDropdown, gbc);
        
        // Question ID Fields
        idLabel = new JLabel("Enter Question ID:");
        idField = new JTextField(10);
        fetchButton = new JButton("Fetch Question");
        gbc.gridy = 3;
        add(idLabel, gbc);
        gbc.gridy = 4;
        add(idField, gbc);
        gbc.gridy = 5;
        add(fetchButton, gbc);

        newIdField = new JTextField(10);
        gbc.gridy = 6;
        add(new JLabel("New Question ID:"), gbc);
        gbc.gridy = 7;
        add(newIdField, gbc);

        // Question and Options Fields
        questionField = new JTextField(20);
        option1Field = new JTextField(20);
        option2Field = new JTextField(20);
        option3Field = new JTextField(20);
        option4Field = new JTextField(20);
        correctDropdown = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3", "Option 4"});
        gbc.gridy = 8;
        add(new JLabel("Question:"), gbc);
        gbc.gridy = 9;
        add(questionField, gbc);
        gbc.gridy = 10;
        add(new JLabel("Option 1:"), gbc);
        gbc.gridy = 11;
        add(option1Field, gbc);
        gbc.gridy = 12;
        add(new JLabel("Option 2:"), gbc);
        gbc.gridy = 13;
        add(option2Field, gbc);
        gbc.gridy = 14;
        add(new JLabel("Option 3:"), gbc);
        gbc.gridy = 15;
        add(option3Field, gbc);
        gbc.gridy = 16;
        add(new JLabel("Option 4:"), gbc);
        gbc.gridy = 17;
        add(option4Field, gbc);
        gbc.gridy = 18;
        add(new JLabel("Correct Option:"), gbc);
        gbc.gridy = 19;
        add(correctDropdown, gbc);

        // Buttons
        updateButton = new JButton("Update Question");
        backButton = new JButton("Back");
        gbc.gridy = 20;
        add(updateButton, gbc);
        gbc.gridy = 21;
        add(backButton, gbc);

        fetchButton.addActionListener(this);
        updateButton.addActionListener(this);
        backButton.addActionListener(this);
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

    private void fetchQuestion() {
        String category = (String) categoryDropdown.getSelectedItem();
        String id = idField.getText();
        if (category == null || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a category and enter an ID!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            conn connection = new conn();
            String query = "SELECT * FROM " + category + " WHERE id=?";
            PreparedStatement pstmt = connection.c.prepareStatement(query);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                newIdField.setText(rs.getString("id"));
                questionField.setText(rs.getString("question"));
                option1Field.setText(rs.getString("option1"));
                option2Field.setText(rs.getString("option2"));
                option3Field.setText(rs.getString("option3"));
                option4Field.setText(rs.getString("option4"));
                correctDropdown.setSelectedItem(rs.getString("correct_option"));
            } else {
                JOptionPane.showMessageDialog(this, "Question not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            connection.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      private void updateQuestion() {
        String category = (String) categoryDropdown.getSelectedItem();
        String id = idField.getText();
        String newId = newIdField.getText();
        String question = questionField.getText();
        String option1 = option1Field.getText();
        String option2 = option2Field.getText();
        String option3 = option3Field.getText();
        String option4 = option4Field.getText();
        String correctOption = (String) correctDropdown.getSelectedItem();
        
        try {
            conn connection = new conn();
            String query = "UPDATE " + category + " SET id=?, question=?, option1=?, option2=?, option3=?, option4=?, correct_option=? WHERE id=?";
            PreparedStatement pstmt = connection.c.prepareStatement(query);
            pstmt.setString(1, newId);
            pstmt.setString(2, question);
            pstmt.setString(3, option1);
            pstmt.setString(4, option2);
            pstmt.setString(5, option3);
            pstmt.setString(6, option4);
            pstmt.setString(7, correctOption);
            pstmt.setString(8, id);
            pstmt.executeUpdate();
            connection.c.close();
            JOptionPane.showMessageDialog(this, "Question updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            fetchQuestion();
        } else if (ae.getSource() == updateButton) {
            updateQuestion();
        } else if (ae.getSource() == backButton) {
             new adqs().setVisible(true);
            setVisible(false);
        }else if (ae.getSource() == techRadio) {
            loadCategories("tech");
        } else if (ae.getSource() == aptRadio) {
            loadCategories("apt");
        }
    }

    public static void main(String[] args) {
        new edit();
    }
}
