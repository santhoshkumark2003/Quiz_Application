package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class add extends JFrame implements ActionListener {
    JLabel titleLabel, categoryLabel, questionLabel, option1Label, option2Label, option3Label, option4Label, correctLabel;
    JTextField idField, questionField, option1Field, option2Field, option3Field, option4Field;
    JComboBox<String> categoryDropdown, correctDropdown;
    JButton addButton, backButton, viewButton;
    JRadioButton techRadio, aptRadio;
    ButtonGroup categoryGroup;
    DefaultComboBoxModel<String> categoryModel;
    
    public add() {
        setTitle("Admin - Add Questions");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        titleLabel = new JLabel("Add Questions", JLabel.CENTER);
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
        
        // Question ID Field
        idField = new JTextField(20);
        gbc.gridy = 4;
        add(new JLabel("Question ID:"), gbc);
        gbc.gridy = 5;
        add(idField, gbc);
        
        // Question and Options
        questionField = new JTextField(20);
        gbc.gridy = 6;
        add(new JLabel("Question:"), gbc);
        gbc.gridy = 7;
        add(questionField, gbc);
        
        option1Field = new JTextField(20);
        gbc.gridy = 8;
        add(new JLabel("Option 1:"), gbc);
        gbc.gridy = 9;
        add(option1Field, gbc);

        option2Field = new JTextField(20);
        gbc.gridy = 10;
        add(new JLabel("Option 2:"), gbc);
        gbc.gridy = 11;
        add(option2Field, gbc);
        
        option3Field = new JTextField(20);
        gbc.gridy = 12;
        add(new JLabel("Option 3:"), gbc);
        gbc.gridy = 13;
        add(option3Field, gbc);
        
        option4Field = new JTextField(20);
        gbc.gridy = 14;
        add(new JLabel("Option 4:"), gbc);
        gbc.gridy = 15;
        add(option4Field, gbc);
        
        correctLabel = new JLabel("Correct Option:");
        correctDropdown = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3", "Option 4"});
        gbc.gridy = 16;
        add(correctLabel, gbc);
        gbc.gridy = 17;
        add(correctDropdown, gbc);

        // Buttons
        addButton = new JButton("Add Question");
        backButton = new JButton("Back");
        viewButton = new JButton("View Questions");
        gbc.gridy = 18;
        add(addButton, gbc);
        gbc.gridy = 19;
        add(backButton, gbc);
        gbc.gridy = 20;
        add(viewButton, gbc);

        addButton.addActionListener(this);
        backButton.addActionListener(this);
        viewButton.addActionListener(this);
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
        if (ae.getSource() == addButton) {
            addQuestion();
        } else if (ae.getSource() == backButton) {
            new adqs().setVisible(true);
            setVisible(false);
        } else if (ae.getSource() == techRadio) {
            loadCategories("tech");
        } else if (ae.getSource() == aptRadio) {
            loadCategories("apt");
        } else if (ae.getSource() == viewButton) {
            viewQuestions();
        }
    }

    private void viewQuestions() {
        String category = (String) categoryDropdown.getSelectedItem();
        if (category == null) {
            JOptionPane.showMessageDialog(this, "Please select a category!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        StringBuilder questions = new StringBuilder();
        try {
            conn connection = new conn();
            String query = "SELECT id, question FROM " + category;
            ResultSet rs = connection.s.executeQuery(query);
            
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "No questions found for this category.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            while (rs.next()) {
                questions.append("ID: ").append(rs.getInt("id"))
                        .append(" - ")
                        .append(rs.getString("question"))
                        .append("\n");
            }
            connection.c.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving questions.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextArea textArea = new JTextArea(questions.toString(), 15, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Available Questions", JOptionPane.INFORMATION_MESSAGE);
    }
private void addQuestion() {
        String category = (String) categoryDropdown.getSelectedItem();
        if (category == null) {
            JOptionPane.showMessageDialog(this, "Please select a category!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String id = idField.getText();
        
        String question = questionField.getText();
        String option1 = option1Field.getText();
        String option2 = option2Field.getText();
        String option3 = option3Field.getText();
        String option4 = option4Field.getText();
        String correctOption = (String) correctDropdown.getSelectedItem();
        
        try {
            conn connection = new conn();
            String query = "INSERT INTO " + category + " (id, question, option1, option2, option3, option4, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.c.prepareStatement(query);
            pstmt.setString(1, id);
           
            pstmt.setString(2, question);
            pstmt.setString(3, option1);
            pstmt.setString(4, option2);
            pstmt.setString(5, option3);
            pstmt.setString(6, option4);
            pstmt.setString(7, correctOption);
            pstmt.executeUpdate();
            connection.c.close();
            JOptionPane.showMessageDialog(this, "Question added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding question.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        new add();
    }
}
