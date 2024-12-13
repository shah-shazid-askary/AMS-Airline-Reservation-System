package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Payment extends JFrame implements ActionListener {

    private JTextField nidField, cardNumberField;
    private JLabel nameLabel, destinationLabel;
    private JButton fetchDetailsButton, payButton, backButton;

    public Payment() {
        // Frame settings
        setTitle("Payment");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Keep Payment open, but allow closing independently
        setLayout(null);

        // Background Image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/cus3.png"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 800, 600);
        add(backgroundLabel);

        // Label and TextField for NID
        JLabel nidLabel = new JLabel("Enter NID:");
        nidLabel.setForeground(Color.WHITE);
        nidLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        nidLabel.setBounds(270, 150, 150, 25);
        backgroundLabel.add(nidLabel);

        nidField = new JTextField();
        nidField.setBounds(430, 150, 250, 25);
        backgroundLabel.add(nidField);

        // Fetch Details Button
        fetchDetailsButton = new JButton("Fetch Details");
        fetchDetailsButton.setBounds(430, 200, 150, 30);
        fetchDetailsButton.addActionListener(this);
        backgroundLabel.add(fetchDetailsButton);

        // Name and Destination Labels (Initially Empty)
        nameLabel = new JLabel("Name: ");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(270, 250, 400, 25);
        backgroundLabel.add(nameLabel);

        destinationLabel = new JLabel("Destination: ");
        destinationLabel.setForeground(Color.WHITE);
        destinationLabel.setBounds(270, 280, 400, 25);
        backgroundLabel.add(destinationLabel);

        // Label and TextField for Card Number (Initially Hidden)
        JLabel cardNumberLabel = new JLabel("Enter Card Number:");
        cardNumberLabel.setForeground(Color.WHITE);
        cardNumberLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        cardNumberLabel.setBounds(270, 320, 150, 25);
        backgroundLabel.add(cardNumberLabel);

        cardNumberField = new JTextField();
        cardNumberField.setBounds(430, 320, 250, 25);
        cardNumberField.setVisible(false);  // Initially hidden
        backgroundLabel.add(cardNumberField);

        // Pay Button (Initially Hidden)
        payButton = new JButton("Pay");
        payButton.setBounds(430, 370, 100, 30);
        payButton.setVisible(false);  // Initially hidden
        payButton.addActionListener(this);
        backgroundLabel.add(payButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(540, 370, 100, 30);
        backButton.addActionListener(e -> {
            new Home();  // Show Home screen without affecting the current Payment screen
            setVisible(false);  // Only hide the Payment window, not close it
        });
        backgroundLabel.add(backButton);

        // Make frame visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchDetailsButton) {
            String nid = nidField.getText().trim();
            if (nid.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid NID number.");
                return;
            }
            fetchFlightDetails(nid);
        } else if (ae.getSource() == payButton) {
            String cardNumber = cardNumberField.getText().trim();
            if (cardNumber.isEmpty() || !cardNumber.matches("\\d{16}")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid 16-digit card number.");
                return;
            }

            processPayment(cardNumber);
        }
    }

    private void fetchFlightDetails(String nid) {
        try {
            Conn conn = new Conn(); // Assuming Conn is your database connection class
            Connection connection = conn.getConnection();

            // Query to fetch user name, flight destination, and flight code based on NID from the reservation table
            String query = "SELECT name, flightcode, des FROM reservation WHERE nid = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, nid);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String flightCode = rs.getString("flightcode");
                String destination = rs.getString("des");

                // Display user details and flight destination
                nameLabel.setText("Name: " + name);
                destinationLabel.setText("Destination: " + destination);

                // Show the card details section and Pay button
                cardNumberField.setVisible(true);
                payButton.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No flight details found for the given NID.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching flight details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processPayment(String cardNumber) {
        try {
            Conn conn = new Conn(); // Assuming Conn is your database connection class
            Connection connection = conn.getConnection();

            // Fetch user details (name, flight code, destination) again before inserting into payments table
            String nid = nidField.getText().trim();
            String query = "SELECT name, flightcode, des FROM reservation WHERE nid = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, nid);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String flightCode = rs.getString("flightcode");
                String destination = rs.getString("des");

                // Insert user information along with card details into payments table
                String insertQuery = "INSERT INTO payments (user_name, flight_code, destination, card_number) VALUES (?, ?, ?, ?)";
                PreparedStatement insertPst = connection.prepareStatement(insertQuery);
                insertPst.setString(1, name);
                insertPst.setString(2, flightCode);
                insertPst.setString(3, destination);
                insertPst.setString(4, cardNumber);

                int result = insertPst.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Payment successful!");
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Payment failed. Please try again.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "User not found for the given NID.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error processing payment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void resetForm() {
        nidField.setText("");  // Clear NID field
        nameLabel.setText("Name: ");
        destinationLabel.setText("Destination: ");
        cardNumberField.setText("");  // Clear card number field
        cardNumberField.setVisible(false);  // Hide card number field again
        payButton.setVisible(false);  // Hide Pay button
    }

    public static void main(String[] args) {
        // Show the Payment frame
        new Payment();
    }
}
