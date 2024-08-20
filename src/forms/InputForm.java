package forms;

import db.DBConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InputForm extends JFrame {
    private JPanel MainPanel;
    private JTextField txtusername; // Updated to username
    private JTextField txtid;
    private JComboBox<String> cbgender;
    private JTextField txtphone;
    private JTextField txtemail;
    private JTextField txtposition; // Fixed typo
    private JTextField txtsalary;
    private JButton jbsubmit;
    private JButton jbback;
    private JLabel lblusername; // Updated to username
    private JLabel lblsalary;
    private JLabel lblposition; // Fixed typo
    private JLabel lblemail;
    private JLabel lblphone;
    private JLabel lblgender;
    private JLabel lblid;
    private Connection con;

    public InputForm(Connection con) {
        this.con = con;

        setTitle("Add User");
        setContentPane(MainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);
        pack();

        jbsubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        jbback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminForm adminForm = new AdminForm(con);
                adminForm.setVisible(true);
                dispose();
            }
        });
    }

    private void addUser() {
        // Validate input fields
        if (txtusername.getText().trim().isEmpty() || txtid.getText().trim().isEmpty() || txtphone.getText().trim().isEmpty() ||
                txtemail.getText().trim().isEmpty() || txtposition.getText().trim().isEmpty() || txtsalary.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        String username = txtusername.getText().trim(); // Updated to username
        String id = txtid.getText().trim();
        String gender = (String) cbgender.getSelectedItem();
        String phone = txtphone.getText().trim();
        String email = txtemail.getText().trim();
        String position = txtposition.getText().trim(); // Fixed typo
        String salary = txtsalary.getText().trim();

        try {
            int idInt = Integer.parseInt(id);
            double salaryDouble = Double.parseDouble(salary);

            // Adjust the SQL query to match the actual database schema
            String query = "INSERT INTO employee (id, username, gender, phone_number, email, position, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setInt(1, idInt);
                preparedStatement.setString(2, username); // Updated to username
                preparedStatement.setString(3, gender);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, position); // Fixed typo
                preparedStatement.setDouble(7, salaryDouble);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "User added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Error adding user", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding user. Check SQL query and column names.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format. Please ensure ID is an integer and salary is a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtusername.setText(""); // Updated to username
        txtid.setText("");
        cbgender.setSelectedIndex(0);
        txtphone.setText("");
        txtemail.setText("");
        txtposition.setText("");
        txtsalary.setText("");
    }

    public static void main(String[] args) {
        Connection con = DBConnection.getConnection();
        InputForm inputForm = new InputForm(con);
        inputForm.setVisible(true);
    }
}
