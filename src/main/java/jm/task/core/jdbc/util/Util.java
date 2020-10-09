package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/jm_3_lesson_1_1_3";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456789";

    public static Connection getConnection() {
        try {
//            Class.forName("org.mysql.Driver");
//            Driver driver = new FabricMySQLDriver();
//            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if ( !connection.isClosed() ) {
                System.out.println("Connection is open!");
            }
        } catch (SQLException e) {
            System.out.println("JDBC driver is not load!");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
            if ( connection.isClosed() ) {
                System.out.println("Connection is close!");
            }
        } catch (SQLException throwables) {
            System.out.println("Connection close error");
        }
    }
}
