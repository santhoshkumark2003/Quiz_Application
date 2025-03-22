package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class arcat extends JFrame implements ActionListener {

    JLabel titleLabel, categoryLabel;
    JTextField categoryField;
    JButton addButton, deleteButton, backButton;
    JRadioButton techRadio, aptRadio;
    ButtonGroup tableGroup;
    DefaultListModel<String> techListModel, aptListModel;
    JList<String> techList, aptList;
    
    public arcat() {
        // Set frame properties for larger window
        setSize(700, 700);
        setLocationRelativeTo(null);
        setTitle("Admin Panel - Manage Categories");
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title Label
        titleLabel = new JLabel("Manage Quiz Categories", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(30, 144, 254));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Lists Panel
        JPanel listPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        listPanel.setBorder(BorderFactory.createTitledBorder("Available Categories"));
        
        techListModel = new DefaultListModel<>();
        aptListModel = new DefaultListModel<>();
        techList = new JList<>(techListModel);
        aptList = new JList<>(aptListModel);
        
        loadCategories(); // Load categories from DB

        listPanel.add(new JScrollPane(techList));
        listPanel.add(new JScrollPane(aptList));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(listPanel, gbc);
        
        // Input & Radio Button Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Category"));
        
        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.insets = new Insets(5, 5, 5, 5);
        innerGbc.anchor = GridBagConstraints.CENTER;

        categoryLabel = new JLabel("New Category:");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        categoryField = new JTextField(15);

        techRadio = new JRadioButton("Tech", true);
        aptRadio = new JRadioButton("Aptitude");
        tableGroup = new ButtonGroup();
        tableGroup.add(techRadio);
        tableGroup.add(aptRadio);

        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        inputPanel.add(categoryLabel, innerGbc);
        
        innerGbc.gridx = 1;
        inputPanel.add(categoryField, innerGbc);

        innerGbc.gridx = 0;
        innerGbc.gridy = 1;
        inputPanel.add(techRadio, innerGbc);

        innerGbc.gridx = 1;
        inputPanel.add(aptRadio, innerGbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(inputPanel, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));

        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back");

        addButton.setBackground(new Color(30, 144, 254));
        addButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(255, 69, 0));
        deleteButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        backButton.addActionListener(this);

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadCategories() {
        techListModel.clear();
        aptListModel.clear();
        try {
            conn connection = new conn();
            Statement stmt = connection.s;
            ResultSet rsTech = stmt.executeQuery("SELECT cat FROM tech");
            while (rsTech.next()) {
                techListModel.addElement(rsTech.getString("cat"));
            }
            
            ResultSet rsApt = stmt.executeQuery("SELECT cat FROM apt");
            while (rsApt.next()) {
                aptListModel.addElement(rsApt.getString("cat"));
            }

            connection.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addButton) {
            addCategory();
        } else if (ae.getSource() == deleteButton) {
            deleteCategory();
        } else if (ae.getSource() == backButton) {
            new managead().setVisible(true);
            setVisible(false);
        }
    }

    private void addCategory() {
        String newCategory = categoryField.getText().trim();
        String tableName = techRadio.isSelected() ? "tech" : "apt";

        if (!newCategory.isEmpty()) {
            try {
                conn connection = new conn();
                PreparedStatement stmt = connection.c.prepareStatement("INSERT INTO " + tableName + " (cat) VALUES (?)");
                stmt.setString(1, newCategory);
                stmt.executeUpdate();
                
                // Create a new table for this category
                String createTableQuery = "CREATE TABLE IF NOT EXISTS " + newCategory + " (id INT ,question TEXT, option1 VARCHAR(255), option2 VARCHAR(255), option3 VARCHAR(255), option4 VARCHAR(255), correct_option VARCHAR(255))";
                connection.s.executeUpdate(createTableQuery);
                
                connection.c.close();
                
                if (tableName.equals("tech")) {
                    techListModel.addElement(newCategory);
                } else {
                    aptListModel.addElement(newCategory);
                }

                categoryField.setText("");
                JOptionPane.showMessageDialog(this, "Category Added! Table " + newCategory + " created.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error Adding Category!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Enter a category name!");
        }
    }

    private void deleteCategory() {
        String selectedCategory = techList.getSelectedValue();
        String tableName = "tech";

        if (selectedCategory == null) {
            selectedCategory = aptList.getSelectedValue();
            tableName = "apt";
        }

        if (selectedCategory != null) {
            try {
                conn connection = new conn();
                
                // Delete the category from the table
                PreparedStatement stmt = connection.c.prepareStatement("DELETE FROM " + tableName + " WHERE cat = ?");
                stmt.setString(1, selectedCategory);
                stmt.executeUpdate();
                
                // Drop the table associated with this category
                String dropTableQuery = "DROP TABLE IF EXISTS " + selectedCategory;
                connection.s.executeUpdate(dropTableQuery);
                
                connection.c.close();
                
                if (tableName.equals("tech")) {
                    techListModel.removeElement(selectedCategory);
                } else {
                    aptListModel.removeElement(selectedCategory);
                }

                JOptionPane.showMessageDialog(this, "Category Deleted! Table " + selectedCategory + " removed.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error Deleting Category!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a category to delete!");
        }
    }

    public static void main(String[] args) {
        new arcat();
    }
} 