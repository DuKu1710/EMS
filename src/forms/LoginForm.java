package forms;

import cls.AuthenticationState;
import cls.User;
import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JDialog {
    private JPanel MainPanel;
    private JPanel RightPanel;
    private JLabel lblIcon;
    private JLabel lblWelcome;
    private JTextField txtUserName;
    private JButton btnLogin;
    private JButton btnCancel;
    private JLabel lblUserName;
    private JLabel lblPassword;
    private JLabel lblTitle;
    private JPasswordField txtPassword;
    private JLabel lblMsg;
    private JPanel LeftPanel;

    private AdminForm adminForm;
    private UserForm userForm;

    public LoginForm(AdminForm adminForm, UserForm userForm) {
        super();
        this.adminForm = adminForm;
        this.userForm = userForm;

        setContentPane(MainPanel);
        setTitle("Login");
        setMinimumSize(new Dimension(600, 400));
        setModal(true);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnCancel.addActionListener(e -> System.exit(0));

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUserName.getText();
                String password = String.valueOf(txtPassword.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    lblMsg.setText("Please enter both username and password.");
                    lblMsg.setForeground(Color.RED);
                    return;
                }

                try (Connection con = DBConnection.getConnection()) {
                    String query = "SELECT * FROM User WHERE username=? AND password=?";
                    PreparedStatement preparedStatement = con.prepareStatement(query);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    ResultSet rs = preparedStatement.executeQuery();

                    if (rs.next()) {
                        User.setUserName(rs.getString("username"));
                        User.setRoleID(rs.getInt("roleID"));

                        boolean isAdmin = rs.getInt("roleID") == 1; // Assuming 1 is the admin role ID
                        AuthenticationState.authenticate(isAdmin);

                        if (isAdmin) {
                            adminForm.setVisible(true);
                        } else {
                            userForm.setVisible(true);
                        }
                        dispose();
                    } else {
                        AuthenticationState.logout();
                        lblMsg.setText("Login failed. Invalid username or password.");
                        lblMsg.setForeground(Color.RED);
                    }
                } catch (SQLException ex) {
                    lblMsg.setText("Database connection failed.");
                    lblMsg.setForeground(Color.RED);
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        // Create AdminForm and UserForm instances
        Connection con = null;
        AdminForm adminForm = new AdminForm(con);
        UserForm userForm = new UserForm(con);

        // Show the login form
        LoginForm loginForm = new LoginForm(adminForm, userForm);
        loginForm.setVisible(true);
    }
}
