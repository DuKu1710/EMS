package forms;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AdminForm extends JFrame {
    private JPanel MainPanel;
    private JButton jbview;
    private JButton jbadd;
    private JButton jbedit;
    private JButton jbdelete;
    private JButton jbexit;
    private final Connection con; // Final Connection field to ensure it's initialized in the constructor

    // Constructor that takes a Connection object
    public AdminForm(Connection con) {
        this.con = con; // Correctly assign the connection to the field

        setTitle("Admin Form");
        setContentPane(MainPanel);
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Add action listeners
        jbview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewAllForm viewAllForm = new ViewAllForm(AdminForm.this.con); // Pass the connection
                viewAllForm.setVisible(true);
                dispose();
            }
        });

        jbadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputForm inputForm = new InputForm(AdminForm.this.con); // Pass the connection
                inputForm.setVisible(true);
                dispose();
            }
        });

        jbedit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditFormSearch editFormSearch = new EditFormSearch(AdminForm.this.con); // Pass the connection
                editFormSearch.setVisible(true);
                dispose();
            }
        });

        jbdelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteForm deleteForm = new DeleteForm(AdminForm.this.con); // Pass the connection
                deleteForm.setVisible(true);
                dispose();
            }
        });

        jbexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void createUIComponents() {
        // Custom component creation code here
    }

    // IntelliJ IDEA GUI Designer generated code


    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }
}
