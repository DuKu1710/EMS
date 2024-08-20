package forms;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EditFormSearch extends JFrame {
    private JPanel MainPanel;
    private JButton jbsubmit;
    private JButton jbback;
    private JLabel lblsearch;
    private JTextField txtid;
    private Connection con;

    DefaultTableModel employeetableModel;
    public EditFormSearch(Connection con) {
        this.con = con; // Set the connection passed to the constructor

        setTitle("Search Employee for Editing");
        setContentPane(MainPanel);
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        employeetableModel=new DefaultTableModel();

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
        String idText = txtid.getText(); // Get text from JTextField
        employeetableModel.setRowCount(0);
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {

            int id = Integer.parseInt(idText);  // Convert the ID text to an integer

            // Check if the ID exists
            String query = "SELECT * FROM employee WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // ID exists, navigate to EditForm

                    EditForm editForm = new EditForm(con,id); // Pass only the ID
                    editForm.setVisible(true);
                    dispose();
                } else {
                    // ID does not exist
                    JOptionPane.showMessageDialog(this, "The ID you entered is invalid.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
