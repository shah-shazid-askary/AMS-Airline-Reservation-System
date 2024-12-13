package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JourneyDetails extends JFrame implements ActionListener {

    private JTextField pnrField;
    private JButton showDetailsButton, backButton;
    private JTable detailsTable;
    private DefaultTableModel tableModel;

    public JourneyDetails() {
        // Frame settings
        setTitle("Journey Details");
        setSize(816, 600); // Set window size to 800x600
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background Image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/test3.png"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 800, 600); // Match the frame size
        add(backgroundLabel);

        // Title Label
        JLabel titleLabel = new JLabel("Journey Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(200, 20, 400, 30); // Centered horizontally
        backgroundLabel.add(titleLabel);

        // PNR Label and Field
        JLabel pnrLabel = new JLabel("Enter PNR:");
        pnrLabel.setForeground(Color.WHITE);
        pnrLabel.setFont(new Font("Tahoma", Font.BOLD, 14)); // Made the text bold
        pnrLabel.setBounds(50, 80, 100, 25);
        backgroundLabel.add(pnrLabel);

        pnrField = new JTextField();
        pnrField.setBounds(150, 80, 200, 25);
        pnrField.setOpaque(false);
        pnrField.setForeground(Color.WHITE);
        backgroundLabel.add(pnrField);

        // Show Details Button
        showDetailsButton = new JButton("Show Details");
        showDetailsButton.setBounds(370, 80, 150, 25); // Adjusted size to match Back button
        showDetailsButton.addActionListener(this);
        backgroundLabel.add(showDetailsButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(550, 80, 150, 25); // Adjusted size to match Show Details button
        backButton.addActionListener(e -> {
            new Home(); // Navigate back to Home
            setVisible(false);
        });
        backgroundLabel.add(backButton);

        // Table to display reservation details
        tableModel = new DefaultTableModel();
        detailsTable = new JTable(tableModel);
        tableModel.addColumn("PNR");
        tableModel.addColumn("Ticket No");
        tableModel.addColumn("Name");
        tableModel.addColumn("Nationality");
        tableModel.addColumn("Address");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Flight Name");
        tableModel.addColumn("Flight Code");
        tableModel.addColumn("Source");
        tableModel.addColumn("Destination");
        tableModel.addColumn("Date");

        // Set table styles
        detailsTable.setForeground(Color.WHITE);
        detailsTable.setOpaque(false);
        detailsTable.setRowHeight(30); // Increased row height for better readability
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);
        detailsTable.setDefaultRenderer(Object.class, renderer);

        JScrollPane scrollPane = new JScrollPane(detailsTable);
        scrollPane.setBounds(20, 150, 760, 300); // Increased width for more horizontal space
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        backgroundLabel.add(scrollPane);

        // Make frame visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == showDetailsButton) {
            String pnr = pnrField.getText().trim();
            if (pnr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a PNR number.");
            } else {
                fetchJourneyDetails(pnr);
            }
        }
    }

    private void fetchJourneyDetails(String pnr) {
        // Clear previous data from the table
        tableModel.setRowCount(0);

        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();

            String query = "SELECT * FROM reservation WHERE PNR = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, pnr);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Object[] row = {
                    rs.getString("PNR"),
                    rs.getString("TICKET"),
                    rs.getString("name"),
                    rs.getString("nationality"),
                    rs.getString("address"),
                    rs.getString("gender"),
                    rs.getString("flightname"),
                    rs.getString("flightcode"),
                    rs.getString("src"),
                    rs.getString("des"),
                    rs.getString("ddate")
                };
                tableModel.addRow(row);
            } else {
                JOptionPane.showMessageDialog(null, "No journey found with the given PNR.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching journey details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
