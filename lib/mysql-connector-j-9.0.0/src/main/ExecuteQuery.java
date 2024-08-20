package src.main.com.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecuteQuery {

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM user")) {

            System.out.println("(ID\tUsername\tPassword)");

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String username = resultSet.getString("Username"); 
                String password = resultSet.getString("password");

                String result = id + "\t" + username + "\t" + password;
                System.out.println(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class DatabaseConnection {

    private static final String URL = "JDBC:mysql://database1.c3gwqmk8sdu5.ap-southeast-1.rds.amazonaws.com:3306/TestDB";
    public static final String USER = "admin"; 
    public static final String PASSWORD = "Rupp123";  

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }
}

