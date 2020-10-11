package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/jm_3_m_1?useUnicode=true&serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "987654321";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if ( !connection.isClosed() ) {
                System.out.println("Connection OK!");
            }
        } catch (SQLException e) {
            System.out.println("Connection ERROR!");
            e.printStackTrace();
        }
        return connection;
    }
}
