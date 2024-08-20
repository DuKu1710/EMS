package forms;

import db.DBConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteForm extends JFrame {
    private JPanel MainPanel; // Main panel for the form
    private JTextField userIdField; // TextField for user ID input
    private JButton jbsubmit; // Button to submit the delete request
    private JButton jbback; // Button to go back to the admin form
    private JLabel lbldelete;
    private JTextField txtid;

    private Connection con;

    public DeleteForm(Connection con) {
        this.con = con;

        setTitle("Delete User");
        setContentPane(MainPanel);
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        jbsubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitAction();
            }
        });

        jbback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminForm(con).setVisible(true);
            }
        });
    }


    private void submitAction() {
        try {
            int id = Integer.parseInt(txtid.getText());
            deleteUserById(id);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting user", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUserById(int id) throws SQLException {
        String query = "SELECT * FROM employee WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String deleteQuery = "DELETE FROM employee WHERE id = ?";
                    try (PreparedStatement deleteStatement = con.prepareStatement(deleteQuery)) {
                        deleteStatement.setInt(1, id);
                        deleteStatement.executeUpdate();
                        JOptionPane.showMessageDialog(this, "User deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        txtid.setText(""); // Clear the input field
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "The ID you entered is invalid", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        Connection con = DBConnection.getConnection(); // Replace with actual DB connection method
        DeleteForm deleteForm = new DeleteForm(con);
        deleteForm.setVisible(true);
    }
}
