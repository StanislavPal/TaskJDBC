package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/jm_3_lesson_1_1_3?useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456789";

    public static Connection getConnection() {
        Connection connection = null;

        try {
//            Class.forName("org.mysql.Driver"); //Что за херня?
//            Driver driver = new FabricMySQLDriver(); //Почему не работало с последним драйвером?
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

    public static void closeConnection(Connection connection) {
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
