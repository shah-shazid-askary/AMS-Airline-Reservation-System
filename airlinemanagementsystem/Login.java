package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {

    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton resetButton, submitButton, closeButton;

    public Login() {
        // Frame settings
        setLayout(null);

        // Background Image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/test3.png"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 400, 250); // Match frame size
        add(backgroundLabel);

        // Username Label and Text Field
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(20, 20, 100, 20);
        lblUsername.setForeground(Color.WHITE); // Make text white for visibility
        backgroundLabel.add(lblUsername);

        tfUsername = new JTextField();
        tfUsername.setBounds(130, 20, 200, 20);
        tfUsername.setOpaque(false); // Make text field background transparent
        tfUsername.setForeground(Color.WHITE);
        backgroundLabel.add(tfUsername);

        // Password Label and Text Field
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(20, 60, 100, 20);
        lblPassword.setForeground(Color.WHITE); // Make text white for visibility
        backgroundLabel.add(lblPassword);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(130, 60, 200, 20);
        tfPassword.setOpaque(false); // Make text field background transparent
        tfPassword.setForeground(Color.WHITE);
        backgroundLabel.add(tfPassword);

        // Reset Button
        resetButton = new JButton("Reset");
        resetButton.setBounds(40, 120, 120, 20);
        backgroundLabel.add(resetButton);
        resetButton.addActionListener(this);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.setBounds(190, 120, 120, 20);
        backgroundLabel.add(submitButton);
        submitButton.addActionListener(this);

        // Close Button
        closeButton = new JButton("Close");
        closeButton.setBounds(120, 160, 120, 20);
        backgroundLabel.add(closeButton);
        closeButton.addActionListener(this);

        // Frame settings
        setSize(400, 250);
        setLocation(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submitButton) {
            String username = tfUsername.getText();
            String password = new String(tfPassword.getPassword());

            // Validate inputs
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            // Attempt to connect to the database and validate user
            try {
                Conn conn = new Conn();
                Connection connection = conn.getConnection();

                if (connection == null) {
                    JOptionPane.showMessageDialog(null, "Database connection failed.");
                    return;
                }

                String query = "SELECT * FROM login WHERE username = ? AND password = ?";
                try (PreparedStatement pst = connection.prepareStatement(query)) {
                    pst.setString(1, username);
                    pst.setString(2, password);

                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(null, "Login Successful!");
                            setVisible(false); // Close the Login frame
                            new Home(); // Open the Home screen
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Username or Password.");
                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error while connecting to the database: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (ae.getSource() == resetButton) {
            tfUsername.setText("");
            tfPassword.setText("");
        } else if (ae.getSource() == closeButton) {
            setVisible(false);
            dispose();
        }
    }

    public static void main(String[] args) {
        new Login(); // Start with the Login screen
    }
}
