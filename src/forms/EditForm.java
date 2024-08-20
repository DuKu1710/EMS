package forms;

import db.DBConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditForm extends JFrame {
    private JPanel MainPanel;
    private JLabel lbfname;
    private JLabel lblgender;
    private JLabel lblphone;
    private JLabel lblemail;
    private JLabel lblposition;
    private JLabel lblsalary;
    private JTextField txtname;
    private JComboBox<String> cbgender;
    private JTextField txtphone;
    private JTextField txtemail;
    private JTextField txtposition;
    private JTextField txtsalary;
    private JButton jbback;
    private JButton jbsubmit;

    private String employeeId;
    private Connection con;

    public EditForm(Connection con, int employeeId) {
        this.con = con;
        this.employeeId = String.valueOf(employeeId);

        loadEmployeeData();

        setTitle("Edit Employee");
        setContentPane(MainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);

        addActionListeners();
    }

    private void addActionListeners() {
        jbsubmit.addActionListener(e -> updateEmployeeData());
        jbback.addActionListener(e -> {
            new AdminForm(con).setVisible(true);
            dispose();
        });
    }

    private void loadEmployeeData() {
        System.out.println("Loading employee data for ID: " + employeeId); // Debugging line

        String query = "SELECT * FROM employee WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    txtname.setText(rs.getString("username"));
                    txtposition.setText(rs.getString("position"));
                    txtsalary.setText(rs.getString("salary"));
                    txtphone.setText(rs.getString("phone_number"));
                    txtemail.setText(rs.getString("email"));
                    cbgender.setSelectedItem(rs.getString("gender"));
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not found", "Error", JOptionPane.ERROR_MESSAGE);
                    new AdminForm(con).setVisible(true);
                    dispose();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading employee data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployeeData() {
        String name = txtname.getText();
        String position = txtposition.getText();
        String salary = txtsalary.getText();
        String phone = txtphone.getText();
        String email = txtemail.getText();
        String gender = (String) cbgender.getSelectedItem();

        String query = "UPDATE employee SET username = ?, position = ?, salary = ?, phone_number = ?, email = ?, gender = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, position);
            ps.setString(3, salary);
            ps.setString(4, phone);
            ps.setString(5, email);
            ps.setString(6, gender);
            ps.setString(7, employeeId);  // Make sure to include WHERE clause to specify which record to update

            int updated = ps.executeUpdate();

            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Employee data updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                new AdminForm(con).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating employee data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Connection con = DBConnection.getConnection();
        int employeeId = 1; // Example ID
        new EditForm(con,employeeId).setVisible(true);
    }

    @Override
    public void dispose() {
        try {
            if (con != null && !con.isClosed()) {
//
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        super.dispose();
    }
}
