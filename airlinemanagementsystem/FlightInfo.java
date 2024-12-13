package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FlightInfo extends JFrame {

    private JTable flightTable;
    private JButton backButton;

    public FlightInfo() {
        // Frame settings
        setTitle("Flight Information");
        setSize(800, 600); // Fixed resolution
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background Image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/tesr1.png"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 800, 600);
        add(backgroundLabel);

        // Table model to hold flight data
        DefaultTableModel tableModel = new DefaultTableModel();
        flightTable = new JTable(tableModel);

        // Add columns to the table model
        tableModel.addColumn("Flight Code");
        tableModel.addColumn("Flight Name");
        tableModel.addColumn("Source");
        tableModel.addColumn("Destination");

        // Fetch flight data from the database
        fetchFlightData(tableModel);

        // Customize the table
        styleTable();

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(flightTable);
        scrollPane.setBounds(50, 50, 700, 400);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); // Make scroll pane transparent
        backgroundLabel.add(scrollPane);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(350, 470, 100, 30);
        backButton.addActionListener(e -> {
            new Home(); // Navigate back to Home
            setVisible(false);
        });
        backgroundLabel.add(backButton);

        // Make frame visible
        setVisible(true);
    }

    private void fetchFlightData(DefaultTableModel tableModel) {
        try {
            Conn conn = new Conn(); // Database connection class
            Connection connection = conn.getConnection(); // Get connection
            Statement stmt = connection.createStatement();

            String query = "SELECT * FROM flight"; // SQL query to fetch flight data
            ResultSet rs = stmt.executeQuery(query);

            // Add rows to the table model
            while (rs.next()) {
                String flightCode = rs.getString("f_code");
                String flightName = rs.getString("f_name");
                String source = rs.getString("source");
                String destination = rs.getString("destination");

                tableModel.addRow(new Object[]{flightCode, flightName, source, destination});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching flight data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void styleTable() {
        // Make the table transparent
        flightTable.setOpaque(false);
        flightTable.setBackground(new Color(0, 0, 0, 0));
        flightTable.setForeground(Color.WHITE); // Set text color to white
        flightTable.setGridColor(new Color(255, 255, 255, 80)); // Set grid lines to semi-transparent white

        // Center-align the text in table cells
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setForeground(Color.WHITE); // Set text color to white
        renderer.setBackground(new Color(0, 0, 0, 0)); // Transparent background
        flightTable.setDefaultRenderer(Object.class, renderer);

        // Style the table header
        JTableHeader header = flightTable.getTableHeader();
        header.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent black
        header.setForeground(Color.WHITE); // Header text color
        header.setFont(new Font("Tahoma", Font.BOLD, 14));
        header.setOpaque(false);

        flightTable.setRowHeight(30); // Set row height for better readability
    }

    public static void main(String[] args) {
        new FlightInfo();
    }
}
