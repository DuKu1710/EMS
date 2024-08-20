package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class UserForm extends JFrame {
    private JPanel MainPanel;
    private JLabel lblWelcome;
    private JLabel lblname;
    private JLabel lblemail;
    private JLabel lblage;
    private JButton jbview;
    private JButton jbexit;

    public UserForm(Connection con) {
        // Initialize UI components

        // Set MainPanel as the content pane
        setContentPane(MainPanel);
        setTitle("User Information");
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Add action listeners
        jbview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to ViewForm
                int userId = 0;
                ViewForm viewForm = new ViewForm(con, userId);
                viewForm.setVisible(true);
                dispose();
            }
        });


        jbexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        Connection con = null;
        UserForm userForm = new UserForm(con);
        userForm.setVisible(true);
    }

    public static void setVisible() {
    }



    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }
}
