package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BoardingPass extends JFrame implements ActionListener {

    private JTextField pnrField, nameField, nationalityField, sourceField, destinationField, flightNameField, flightCodeField, dateField;
    private JButton showButton, backButton;

    public BoardingPass() {
        // Frame settings
        setTitle("Boarding Pass");
        setSize(850, 350);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background Image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/test3.png"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 850, 350);
        add(backgroundLabel);

        int labelWidth = 150;
        int fieldWidth = 200;
        int buttonWidth = 120;
        int verticalGap = 40;
        int startX = 50;
        int startY = 30;

        // PNR Label and Field
        JLabel pnrLabel = new JLabel("Enter PNR:");
        pnrLabel.setBounds(startX, startY, labelWidth, 25);
        pnrLabel.setForeground(Color.WHITE);
        pnrLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        backgroundLabel.add(pnrLabel);

        pnrField = new JTextField();
        pnrField.setBounds(startX + labelWidth, startY, fieldWidth, 25);
        pnrField.setOpaque(false);
        pnrField.setForeground(Color.WHITE);
        backgroundLabel.add(pnrField);

        // Show Button
        showButton = new JButton("Show");
        showButton.setBounds(startX + labelWidth + fieldWidth + 20, startY, buttonWidth, 25);
        showButton.addActionListener(this);
        backgroundLabel.add(showButton);

        // Name Label and Field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(startX, startY + verticalGap, labelWidth, 25);
        nameLabel.setForeground(Color.WHITE);
        backgroundLabel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(startX + labelWidth, startY + verticalGap, fieldWidth, 25);
        nameField.setEditable(false);
        nameField.setOpaque(false);
        nameField.setForeground(Color.WHITE);
        backgroundLabel.add(nameField);

        // Nationality Label and Field
        JLabel nationalityLabel = new JLabel("Nationality:");
        nationalityLabel.setBounds(startX, startY + verticalGap * 2, labelWidth, 25);
        nationalityLabel.setForeground(Color.WHITE);
        backgroundLabel.add(nationalityLabel);

        nationalityField = new JTextField();
        nationalityField.setBounds(startX + labelWidth, startY + verticalGap * 2, fieldWidth, 25);
        nationalityField.setEditable(false);
        nationalityField.setOpaque(false);
        nationalityField.setForeground(Color.WHITE);
        backgroundLabel.add(nationalityField);

        // Source and Destination Labels and Fields
        JLabel sourceLabel = new JLabel("Source:");
        sourceLabel.setBounds(startX, startY + verticalGap * 3, labelWidth, 25);
        sourceLabel.setForeground(Color.WHITE);
        backgroundLabel.add(sourceLabel);

        sourceField = new JTextField();
        sourceField.setBounds(startX + labelWidth, startY + verticalGap * 3, fieldWidth / 2 - 5, 25);
        sourceField.setEditable(false);
        sourceField.setOpaque(false);
        sourceField.setForeground(Color.WHITE);
        backgroundLabel.add(sourceField);

        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setBounds(startX + labelWidth + fieldWidth / 2 + 10, startY + verticalGap * 3, labelWidth, 25);
        destinationLabel.setForeground(Color.WHITE);
        backgroundLabel.add(destinationLabel);

        destinationField = new JTextField();
        destinationField.setBounds(startX + labelWidth + fieldWidth / 2 + 100, startY + verticalGap * 3, fieldWidth / 2 - 5, 25);
        destinationField.setEditable(false);
        destinationField.setOpaque(false);
        destinationField.setForeground(Color.WHITE);
        backgroundLabel.add(destinationField);

        // Flight Name and Code Labels and Fields
        JLabel flightNameLabel = new JLabel("Flight Name:");
        flightNameLabel.setBounds(startX, startY + verticalGap * 4, labelWidth, 25);
        flightNameLabel.setForeground(Color.WHITE);
        backgroundLabel.add(flightNameLabel);

        flightNameField = new JTextField();
        flightNameField.setBounds(startX + labelWidth, startY + verticalGap * 4, fieldWidth / 2 - 5, 25);
        flightNameField.setEditable(false);
        flightNameField.setOpaque(false);
        flightNameField.setForeground(Color.WHITE);
        backgroundLabel.add(flightNameField);

        JLabel flightCodeLabel = new JLabel("Flight Code:");
        flightCodeLabel.setBounds(startX + labelWidth + fieldWidth / 2 + 10, startY + verticalGap * 4, labelWidth, 25);
        flightCodeLabel.setForeground(Color.WHITE);
        backgroundLabel.add(flightCodeLabel);

        flightCodeField = new JTextField();
        flightCodeField.setBounds(startX + labelWidth + fieldWidth / 2 + 100, startY + verticalGap * 4, fieldWidth / 2 - 5, 25);
        flightCodeField.setEditable(false);
        flightCodeField.setOpaque(false);
        flightCodeField.setForeground(Color.WHITE);
        backgroundLabel.add(flightCodeField);

        // Date Label and Field
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(startX, startY + verticalGap * 5, labelWidth, 25);
        dateLabel.setForeground(Color.WHITE);
        backgroundLabel.add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(startX + labelWidth, startY + verticalGap * 5, fieldWidth, 25);
        dateField.setEditable(false);
        dateField.setOpaque(false);
        dateField.setForeground(Color.WHITE);
        backgroundLabel.add(dateField);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(startX + labelWidth + fieldWidth + 20, startY + verticalGap * 5, buttonWidth, 25);
        backButton.addActionListener(e -> {
            new Home(); // Navigate back to Home
            setVisible(false);
        });
        backgroundLabel.add(backButton);

        // Make frame visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == showButton) {
            String pnr = pnrField.getText().trim();
            if (pnr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a PNR number.");
                return;
            }
            fetchBoardingPassDetails(pnr);
        }
    }

    private void fetchBoardingPassDetails(String pnr) {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();

            String query = "SELECT name, nationality, src, des, flightname, flightcode, ddate FROM reservation WHERE PNR = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, pnr);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                nationalityField.setText(rs.getString("nationality"));
                sourceField.setText(rs.getString("src"));
                destinationField.setText(rs.getString("des"));
                flightNameField.setText(rs.getString("flightname"));
                flightCodeField.setText(rs.getString("flightcode"));
                dateField.setText(rs.getString("ddate"));
            } else {
                JOptionPane.showMessageDialog(null, "No reservation found with the given PNR.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching boarding pass details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BoardingPass();
    }
}
