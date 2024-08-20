package forms;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewAllForm extends JFrame {
    private final Connection con;
    private JPanel MainPanel;
    private JTable tbinformation;
    private JScrollPane scrollPane;
    private JButton jbback;
    private DefaultTableModel tableModel;

    public ViewAllForm(Connection con) {
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
        tableModel.addColumn("Position");
        tableModel.addColumn("Salary");
        tbinformation.setModel(tableModel);

        // Load information from the database
        loadInformation();

        // Back button action
        jbback.addActionListener(e -> {
            AdminForm adminForm = new AdminForm(con);
            adminForm.setVisible(true);
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
                String emPosition = resultSet.getString("position");
                int emSalary = resultSet.getInt("salary");
                tableModel.addRow(new Object[]{emCode, emName, emGender, emPhNumber, emEmail, emPosition, emSalary});
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cannot get data from database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
