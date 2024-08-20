import cls.AuthenticationState;
import db.DBConnection;
import forms.*;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection con = DBConnection.getConnection();
        if (con != null) {
            System.out.println("Connection is established successfully.");
        } else {
            System.out.println("Can't connect to database");
        }

        AdminForm adminForm = new AdminForm(con);
        UserForm userForm = new UserForm(con);
        LoginForm loginForm = new LoginForm(adminForm, userForm);

        loginForm.setVisible(true);

        if (AuthenticationState.isAuthenticated()) {
            if (AuthenticationState.isAdmin()) {
                adminForm.setVisible(true);
            } else {
                userForm.setVisible(true);
            }
        }
    }
}
