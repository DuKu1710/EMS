package forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ViewForm extends JFrame {
    private final Connection con;
    private JPanel MainPanel;
    private JButton jbback;
    private JTable tbviewem;
    private DefaultTableModel tableModel;

    public ViewForm(Connection con, int userId) {
        this.con = con;
        setTitle("View All Employees");
        setContentPane(MainPanel);
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Phone Number");
        tableModel.addColumn("Email");
        tbviewem.setModel(tableModel);

        // Load information from the database
        loadInformation();

        // Back button action
        jbback.addActionListener(e -> {
            UserForm userForm = new UserForm(con);
            userForm.setVisible(true);
            dispose();
        });
    }

    private void loadInformation() {
        tableModel.setRowCount(0);
        try (Statement statement = con.createStatement()) {
            String query = "SELECT * FROM employee";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String emCode = resultSet.getString("id");
                String emName = resultSet.getString("username");
                String emGender = resultSet.getString("gender");
                String emPhNumber = resultSet.getString("phone_number");
                String emEmail = resultSet.getString("email");

                tableModel.addRow(new Object[]{emCode, emName, emGender, emPhNumber, emEmail});
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cannot get data from database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
