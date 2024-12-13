package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddCustomer extends JFrame implements ActionListener {

    private JTextField nameField, nationalityField, nidField, addressField, phoneField;
    private JRadioButton maleButton, femaleButton, otherButton;
    private JButton submitButton, resetButton, backButton;
    private ButtonGroup genderGroup;

    public AddCustomer() {
        // Frame settings
        setTitle("Add Customer Details");
        setSize(800, 600); // Fixed resolution
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background Image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/cus3.png"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 800, 600);
        add(backgroundLabel);

        // Centering parameters
        int frameWidth = 800;
        int frameHeight = 600;

        int formWidth = 400; // Width of the form area
        int formHeight = 300; // Approximate height of the form area
        int startX = (frameWidth - formWidth) / 2; // X position for centering
        int startY = (frameHeight - formHeight) / 2; // Y position for centering
        int labelWidth = 100;
        int fieldWidth = 250;
        int verticalGap = 40; // Vertical gap between rows

        // Title Label
        JLabel titleLabel = new JLabel("Add Customer Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(startX, startY - 50, formWidth, 30);
        backgroundLabel.add(titleLabel);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(startX, startY, labelWidth, 25);
        nameLabel.setForeground(Color.WHITE);
        backgroundLabel.add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(startX + labelWidth + 20, startY, fieldWidth, 25);
        nameField.setOpaque(false);
        nameField.setForeground(Color.WHITE);
        backgroundLabel.add(nameField);

        // Nationality
        JLabel nationalityLabel = new JLabel("Nationality:");
        nationalityLabel.setBounds(startX, startY + verticalGap, labelWidth, 25);
        nationalityLabel.setForeground(Color.WHITE);
        backgroundLabel.add(nationalityLabel);
        nationalityField = new JTextField();
        nationalityField.setBounds(startX + labelWidth + 20, startY + verticalGap, fieldWidth, 25);
        nationalityField.setOpaque(false);
        nationalityField.setForeground(Color.WHITE);
        backgroundLabel.add(nationalityField);

        // NID Number
        JLabel nidLabel = new JLabel("NID Number:");
        nidLabel.setBounds(startX, startY + verticalGap * 2, labelWidth, 25);
        nidLabel.setForeground(Color.WHITE);
        backgroundLabel.add(nidLabel);
        nidField = new JTextField();
        nidField.setBounds(startX + labelWidth + 20, startY + verticalGap * 2, fieldWidth, 25);
        nidField.setOpaque(false);
        nidField.setForeground(Color.WHITE);
        backgroundLabel.add(nidField);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(startX, startY + verticalGap * 3, labelWidth, 25);
        addressLabel.setForeground(Color.WHITE);
        backgroundLabel.add(addressLabel);
        addressField = new JTextField();
        addressField.setBounds(startX + labelWidth + 20, startY + verticalGap * 3, fieldWidth, 25);
        addressField.setOpaque(false);
        addressField.setForeground(Color.WHITE);
        backgroundLabel.add(addressField);

        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(startX, startY + verticalGap * 4, labelWidth, 25);
        genderLabel.setForeground(Color.WHITE);
        backgroundLabel.add(genderLabel);

        maleButton = new JRadioButton("Male");
        maleButton.setBounds(startX + labelWidth + 20, startY + verticalGap * 4, 70, 25);
        maleButton.setOpaque(false);
        maleButton.setForeground(Color.WHITE);
        backgroundLabel.add(maleButton);

        femaleButton = new JRadioButton("Female");
        femaleButton.setBounds(startX + labelWidth + 100, startY + verticalGap * 4, 80, 25);
        femaleButton.setOpaque(false);
        femaleButton.setForeground(Color.WHITE);
        backgroundLabel.add(femaleButton);

        otherButton = new JRadioButton("Other");
        otherButton.setBounds(startX + labelWidth + 190, startY + verticalGap * 4, 70, 25);
        otherButton.setOpaque(false);
        otherButton.setForeground(Color.WHITE);
        backgroundLabel.add(otherButton);

        // Group gender buttons
        genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(otherButton);

        // Phone Number
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(startX, startY + verticalGap * 5, labelWidth, 25);
        phoneLabel.setForeground(Color.WHITE);
        backgroundLabel.add(phoneLabel);
        phoneField = new JTextField();
        phoneField.setBounds(startX + labelWidth + 20, startY + verticalGap * 5, fieldWidth, 25);
        phoneField.setOpaque(false);
        phoneField.setForeground(Color.WHITE);
        backgroundLabel.add(phoneField);

        // Buttons
        submitButton = new JButton("Submit");
        submitButton.setBounds(startX, startY + verticalGap * 6 + 10, 100, 30);
        submitButton.addActionListener(this);
        backgroundLabel.add(submitButton);

        resetButton = new JButton("Reset");
        resetButton.setBounds(startX + 130, startY + verticalGap * 6 + 10, 100, 30);
        resetButton.addActionListener(this);
        backgroundLabel.add(resetButton);

        backButton = new JButton("Back");
        backButton.setBounds(startX + 260, startY + verticalGap * 6 + 10, 100, 30);
        backButton.addActionListener(this);
        backgroundLabel.add(backButton);

        // Make frame visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submitButton) {
            // Fetch form data
            String name = nameField.getText();
            String nationality = nationalityField.getText();
            String nid = nidField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String gender = null;

            if (maleButton.isSelected()) {
                gender = "Male";
            } else if (femaleButton.isSelected()) {
                gender = "Female";
            } else if (otherButton.isSelected()) {
                gender = "Other";
            }

            // Validate inputs
            if (name.isEmpty() || nationality.isEmpty() || nid.isEmpty() || address.isEmpty() || phone.isEmpty() || gender == null) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            // Insert data into the database
            try {
                Conn conn = new Conn(); // Database connection class
                Connection connection = conn.getConnection(); // Get connection

                String query = "INSERT INTO passenger (name, nationality, phone, address, nid, gender) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2, nationality);
                pst.setString(3, phone);
                pst.setString(4, address);
                pst.setString(5, nid);
                pst.setString(6, gender);

                pst.executeUpdate(); // Execute query
                JOptionPane.showMessageDialog(null, "Customer added successfully!");
                resetForm(); // Reset form fields
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error while saving data: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (ae.getSource() == resetButton) {
            resetForm();
        } else if (ae.getSource() == backButton) {
            new Home();
            setVisible(false);
        }
    }

    private void resetForm() {
        nameField.setText("");
        nationalityField.setText("");
        nidField.setText("");
        addressField.setText("");
        phoneField.setText("");
        genderGroup.clearSelection();
    }

    public static void main(String[] args) {
        new AddCustomer();
    }
}
