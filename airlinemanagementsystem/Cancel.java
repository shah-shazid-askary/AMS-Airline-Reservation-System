package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cancel extends JFrame implements ActionListener {

    private JTextField pnrField, nameField, cancelNoField, flightCodeField, dateField;
    private JButton showDetailsButton, cancelButton, backButton;

    public Cancel() {
        // Frame settings
        setTitle("Cancel Reservation");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background Image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/test2.png"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 800, 600);
        add(backgroundLabel);

        int labelWidth = 150;
        int fieldWidth = 250;
        int verticalGap = 50;
        int startX = (800 - (labelWidth + fieldWidth + 30)) / 2; // Centered horizontally
        int startY = (600 - (verticalGap * 5 + 50)) / 2; // Centered vertically

        // PNR Label and Field
        JLabel pnrLabel = new JLabel("Enter PNR:");
        pnrLabel.setForeground(Color.WHITE);
        pnrLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnrLabel.setBounds(startX, startY, labelWidth, 25);
        backgroundLabel.add(pnrLabel);

        pnrField = createTransparentTextField();
        pnrField.setBounds(startX + labelWidth + 10, startY, fieldWidth, 25);
        backgroundLabel.add(pnrField);

        // Show Details Button
        showDetailsButton = new JButton("Show");
        showDetailsButton.setBounds(startX + labelWidth + fieldWidth + 20, startY, 80, 25); // Smaller width
        showDetailsButton.addActionListener(this);
        backgroundLabel.add(showDetailsButton);

        // Name Label and Field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(startX, startY + verticalGap, labelWidth, 25);
        backgroundLabel.add(nameLabel);

        nameField = createTransparentTextField();
        nameField.setBounds(startX + labelWidth + 10, startY + verticalGap, fieldWidth, 25);
        nameField.setEditable(false);
        backgroundLabel.add(nameField);

        // Cancellation Number Label and Field
        JLabel cancelNoLabel = new JLabel("Cancel No:");
        cancelNoLabel.setForeground(Color.WHITE);
        cancelNoLabel.setBounds(startX, startY + verticalGap * 2, labelWidth, 25);
        backgroundLabel.add(cancelNoLabel);

        cancelNoField = createTransparentTextField();
        cancelNoField.setBounds(startX + labelWidth + 10, startY + verticalGap * 2, fieldWidth, 25);
        cancelNoField.setEditable(false);
        backgroundLabel.add(cancelNoField);

        // Flight Code Label and Field
        JLabel flightCodeLabel = new JLabel("Flight Code:");
        flightCodeLabel.setForeground(Color.WHITE);
        flightCodeLabel.setBounds(startX, startY + verticalGap * 3, labelWidth, 25);
        backgroundLabel.add(flightCodeLabel);

        flightCodeField = createTransparentTextField();
        flightCodeField.setBounds(startX + labelWidth + 10, startY + verticalGap * 3, fieldWidth, 25);
        flightCodeField.setEditable(false);
        backgroundLabel.add(flightCodeField);

        // Date Label and Field
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setBounds(startX, startY + verticalGap * 4, labelWidth, 25);
        backgroundLabel.add(dateLabel);

        dateField = createTransparentTextField();
        dateField.setBounds(startX + labelWidth + 10, startY + verticalGap * 4, fieldWidth, 25);
        dateField.setEditable(false);
        backgroundLabel.add(dateField);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(startX, startY + verticalGap * 5, fieldWidth / 2 - 10, 30);
        cancelButton.addActionListener(this);
        backgroundLabel.add(cancelButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(startX + fieldWidth / 2 + 20, startY + verticalGap * 5, fieldWidth / 2 - 10, 30);
        backButton.addActionListener(e -> {
            new Home(); // Navigate back to Home
            setVisible(false);
        });
        backgroundLabel.add(backButton);

        // Make frame visible
        setVisible(true);
    }

    private JTextField createTransparentTextField() {
        JTextField textField = new JTextField();
        textField.setOpaque(false);
        textField.setForeground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        return textField;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == showDetailsButton) {
            String pnr = pnrField.getText().trim();
            if (pnr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a PNR number.");
                return;
            }
            fetchReservationDetails(pnr);
        } else if (ae.getSource() == cancelButton) {
            String pnr = pnrField.getText().trim();
            if (pnr.isEmpty() || nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No reservation selected to cancel.");
                return;
            }
            cancelReservation(pnr);
        }
    }

    private void fetchReservationDetails(String pnr) {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();

            String query = "SELECT name, flightcode, ddate FROM reservation WHERE PNR = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, pnr);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                flightCodeField.setText(rs.getString("flightcode"));
                dateField.setText(rs.getString("ddate"));

                // Generate a random 6-digit cancellation number
                String cancelNo = String.format("%06d", (int) (Math.random() * 1000000));
                cancelNoField.setText(cancelNo);
            } else {
                JOptionPane.showMessageDialog(null, "No reservation found with the given PNR.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching reservation details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cancelReservation(String pnr) {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();

            // Insert the cancellation details into the cancel table
            String insertQuery = "INSERT INTO cancel (pnr, name, cancelno, fcode, ddate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertPst = connection.prepareStatement(insertQuery);
            insertPst.setString(1, pnr);
            insertPst.setString(2, nameField.getText());
            insertPst.setString(3, cancelNoField.getText());
            insertPst.setString(4, flightCodeField.getText());
            insertPst.setString(5, dateField.getText());
            insertPst.executeUpdate();

            // Delete the reservation from the reservation table
            String deleteQuery = "DELETE FROM reservation WHERE PNR = ?";
            PreparedStatement deletePst = connection.prepareStatement(deleteQuery);
            deletePst.setString(1, pnr);
            deletePst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Reservation canceled successfully!");
            resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error while canceling reservation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void resetForm() {
        pnrField.setText("");
        nameField.setText("");
        cancelNoField.setText("");
        flightCodeField.setText("");
        dateField.setText("");
    }

    public static void main(String[] args) {
        new Cancel();
    }
}
