package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class BookFlight extends JFrame implements ActionListener {

    private JTextField nidField, nameField, nationalityField, addressField, genderField, flightNameField, flightCodeField;
    private JComboBox<String> sourceComboBox, destinationComboBox;
    private JDateChooser travelDateChooser;
    private JButton fetchButton, bookButton, resetButton, backButton;

    public BookFlight() {
        // Frame settings
        setTitle("Book Flight");
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
        int fieldWidth = 200;
        int verticalGap = 40;
        int startX = 50;
        int startY = 30;

        // NID Label and Field
        JLabel nidLabel = new JLabel("Enter NID:");
        nidLabel.setBounds(startX, startY, labelWidth, 25);
        nidLabel.setForeground(Color.WHITE); // Set text color to white
        backgroundLabel.add(nidLabel);

        nidField = new JTextField();
        nidField.setBounds(startX + labelWidth + 10, startY, fieldWidth, 25);
        nidField.setOpaque(false);
        nidField.setForeground(Color.WHITE);
        backgroundLabel.add(nidField);

        fetchButton = new JButton("Fetch");
        fetchButton.setBounds(startX + labelWidth + fieldWidth + 20, startY, 100, 25);
        fetchButton.addActionListener(this);
        backgroundLabel.add(fetchButton);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(startX, startY + verticalGap, labelWidth, 25);
        nameLabel.setForeground(Color.WHITE);
        backgroundLabel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(startX + labelWidth + 10, startY + verticalGap, fieldWidth, 25);
        nameField.setEditable(false);
        nameField.setOpaque(false);
        nameField.setForeground(Color.WHITE);
        backgroundLabel.add(nameField);

        // Nationality
        JLabel nationalityLabel = new JLabel("Nationality:");
        nationalityLabel.setBounds(startX, startY + verticalGap * 2, labelWidth, 25);
        nationalityLabel.setForeground(Color.WHITE);
        backgroundLabel.add(nationalityLabel);

        nationalityField = new JTextField();
        nationalityField.setBounds(startX + labelWidth + 10, startY + verticalGap * 2, fieldWidth, 25);
        nationalityField.setEditable(false);
        nationalityField.setOpaque(false);
        nationalityField.setForeground(Color.WHITE);
        backgroundLabel.add(nationalityField);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(startX, startY + verticalGap * 3, labelWidth, 25);
        addressLabel.setForeground(Color.WHITE);
        backgroundLabel.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(startX + labelWidth + 10, startY + verticalGap * 3, fieldWidth, 25);
        addressField.setEditable(false);
        addressField.setOpaque(false);
        addressField.setForeground(Color.WHITE);
        backgroundLabel.add(addressField);

        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(startX, startY + verticalGap * 4, labelWidth, 25);
        genderLabel.setForeground(Color.WHITE);
        backgroundLabel.add(genderLabel);

        genderField = new JTextField();
        genderField.setBounds(startX + labelWidth + 10, startY + verticalGap * 4, fieldWidth, 25);
        genderField.setEditable(false);
        genderField.setOpaque(false);
        genderField.setForeground(Color.WHITE);
        backgroundLabel.add(genderField);

        // Source
        JLabel sourceLabel = new JLabel("Source:");
        sourceLabel.setBounds(startX, startY + verticalGap * 5, labelWidth, 25);
        sourceLabel.setForeground(Color.WHITE);
        backgroundLabel.add(sourceLabel);

        sourceComboBox = new JComboBox<>();
        sourceComboBox.setBounds(startX + labelWidth + 10, startY + verticalGap * 5, fieldWidth, 25);
        sourceComboBox.addActionListener(this);
        backgroundLabel.add(sourceComboBox);

        // Destination
        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setBounds(startX, startY + verticalGap * 6, labelWidth, 25);
        destinationLabel.setForeground(Color.WHITE);
        backgroundLabel.add(destinationLabel);

        destinationComboBox = new JComboBox<>();
        destinationComboBox.setBounds(startX + labelWidth + 10, startY + verticalGap * 6, fieldWidth, 25);
        destinationComboBox.addActionListener(this);
        backgroundLabel.add(destinationComboBox);

        // Flight Name
        JLabel flightNameLabel = new JLabel("Flight Name:");
        flightNameLabel.setBounds(startX, startY + verticalGap * 7, labelWidth, 25);
        flightNameLabel.setForeground(Color.WHITE);
        backgroundLabel.add(flightNameLabel);

        flightNameField = new JTextField();
        flightNameField.setBounds(startX + labelWidth + 10, startY + verticalGap * 7, fieldWidth, 25);
        flightNameField.setEditable(false);
        flightNameField.setOpaque(false);
        flightNameField.setForeground(Color.WHITE);
        backgroundLabel.add(flightNameField);

        // Flight Code
        JLabel flightCodeLabel = new JLabel("Flight Code:");
        flightCodeLabel.setBounds(startX, startY + verticalGap * 8, labelWidth, 25);
        flightCodeLabel.setForeground(Color.WHITE);
        backgroundLabel.add(flightCodeLabel);

        flightCodeField = new JTextField();
        flightCodeField.setBounds(startX + labelWidth + 10, startY + verticalGap * 8, fieldWidth, 25);
        flightCodeField.setEditable(false);
        flightCodeField.setOpaque(false);
        flightCodeField.setForeground(Color.WHITE);
        backgroundLabel.add(flightCodeField);

        // Travel Date
        JLabel travelDateLabel = new JLabel("Travel Date:");
        travelDateLabel.setBounds(startX, startY + verticalGap * 9, labelWidth, 25);
        travelDateLabel.setForeground(Color.WHITE);
        backgroundLabel.add(travelDateLabel);

        travelDateChooser = new JDateChooser();
        travelDateChooser.setBounds(startX + labelWidth + 10, startY + verticalGap * 9, fieldWidth, 25);
        travelDateChooser.setDateFormatString("yyyy-MM-dd");
        backgroundLabel.add(travelDateChooser);

        // Buttons
        bookButton = new JButton("Book");
        bookButton.setBounds(200, 500, 100, 30);
        bookButton.addActionListener(this);
        backgroundLabel.add(bookButton);

        resetButton = new JButton("Reset");
        resetButton.setBounds(350, 500, 100, 30);
        resetButton.addActionListener(this);
        backgroundLabel.add(resetButton);

        backButton = new JButton("Back");
        backButton.setBounds(500, 500, 100, 30);
        backButton.addActionListener(e -> {
            new Home(); // Navigate back to Home
            setVisible(false);
        });
        backgroundLabel.add(backButton);

        loadSourceAndDestination(); // Load source and destination options
        setVisible(true);
    }

    private void loadSourceAndDestination() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();
            Statement stmt = connection.createStatement();

            String query = "SELECT DISTINCT source, destination FROM flight";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                sourceComboBox.addItem(rs.getString("source"));
                destinationComboBox.addItem(rs.getString("destination"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading sources and destinations: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void resetForm() {
        nidField.setText("");
        nameField.setText("");
        nationalityField.setText("");
        addressField.setText("");
        genderField.setText("");
        flightNameField.setText("");
        flightCodeField.setText("");
        sourceComboBox.setSelectedIndex(0);
        destinationComboBox.setSelectedIndex(0);
        travelDateChooser.setDate(null);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String nid = nidField.getText().trim();
            if (nid.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter an NID.");
            } else {
                fetchCustomerDetails(nid);
            }
        } else if (ae.getSource() == sourceComboBox || ae.getSource() == destinationComboBox) {
            String source = (String) sourceComboBox.getSelectedItem();
            String destination = (String) destinationComboBox.getSelectedItem();

            if (source != null && destination != null && !source.equals(destination)) {
                fetchFlightDetails(source, destination);
            } else {
                flightNameField.setText("");
                flightCodeField.setText("");
            }
        } else if (ae.getSource() == bookButton) {
            String nid = nidField.getText().trim();
            String name = nameField.getText().trim();
            String nationality = nationalityField.getText().trim();
            String address = addressField.getText().trim();
            String gender = genderField.getText().trim();
            String source = (String) sourceComboBox.getSelectedItem();
            String destination = (String) destinationComboBox.getSelectedItem();
            String flightName = flightNameField.getText().trim();
            String flightCode = flightCodeField.getText().trim();

            Date travelDate = travelDateChooser.getDate();
            if (travelDate == null) {
                JOptionPane.showMessageDialog(null, "Please select a valid travel date.");
                return;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(travelDate);

            String pnr = "PNR" + System.currentTimeMillis();
            String ticketNumber = "TICKET" + (int) (Math.random() * 1000000);

            if (nid.isEmpty() || name.isEmpty() || nationality.isEmpty() || address.isEmpty() || gender.isEmpty() ||
                    source == null || destination == null || flightName.isEmpty() || flightCode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            try {
                Conn conn = new Conn();
                Connection connection = conn.getConnection();

                String query = "INSERT INTO reservation (PNR, TICKET, nid, name, nationality, address, gender, flightname, flightcode, src, des, ddate) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, pnr);
                pst.setString(2, ticketNumber);
                pst.setString(3, nid);
                pst.setString(4, name);
                pst.setString(5, nationality);
                pst.setString(6, address);
                pst.setString(7, gender);
                pst.setString(8, flightName);
                pst.setString(9, flightCode);
                pst.setString(10, source);
                pst.setString(11, destination);
                pst.setString(12, formattedDate);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Flight booked successfully!\nPNR: " + pnr + "\nTicket Number: " + ticketNumber);
                resetForm();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error while booking flight: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (ae.getSource() == resetButton) {
            resetForm();
        }
    }

    private void fetchCustomerDetails(String nid) {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();

            String query = "SELECT * FROM passenger WHERE nid = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, nid);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                nationalityField.setText(rs.getString("nationality"));
                addressField.setText(rs.getString("address"));
                genderField.setText(rs.getString("gender"));
            } else {
                JOptionPane.showMessageDialog(null, "No customer found with the given NID.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching customer details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void fetchFlightDetails(String source, String destination) {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();

            String query = "SELECT f_name, f_code FROM flight WHERE source = ? AND destination = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, source);
            pst.setString(2, destination);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                flightNameField.setText(rs.getString("f_name"));
                flightCodeField.setText(rs.getString("f_code"));
            } else {
                flightNameField.setText("");
                flightCodeField.setText("");
                JOptionPane.showMessageDialog(null, "No flights available for the selected route.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching flight details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BookFlight();
    }
}
