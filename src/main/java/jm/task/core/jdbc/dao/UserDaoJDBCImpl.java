package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {
        executeUpdate(CREATE_TABLE_QUERY);
    }

    public void dropUsersTable() {
        executeUpdate("DROP TABLE IF EXISTS `users`;");
    }

    public void saveUser(String name, String lastName, byte age) {
        executeUpdate("INSERT INTO USERS (name, lastname, age) VALUES"
                + " ('" + name
                + "', '" + lastName
                + "', '" + age + "');");
    }

    public void removeUserById(long id) {
        executeUpdate("DELETE FROM USERS WHERE"
                + " id = " + id + ";");
    }

    public List<User> getAllUsers() {
        List<User> users = null;
        String sql = "SELECT * FROM USERS;";
        try(Statement statement = connection.createStatement()) {
            ResultSet rs =  statement.executeQuery(sql);
            users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        (byte) rs.getInt("age")));
            }
        } catch (SQLException throwables) {
            close();
            System.out.println("Err: Query didn't execute");
            throwables.printStackTrace();
            return null;
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM USERS;";
        executeUpdate(sql);
    }

    private int executeUpdate(String sql) {
        try(Statement statement = connection.createStatement()) {
            return  statement.executeUpdate(sql) ;
         } catch (SQLException throwables) {
            close();
            System.out.println("Err: Query didn't execute");
            throwables.printStackTrace();
            return  -1;
        }
    }

    public void close () {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (SQLException throwables) {
            System.out.println("Err: Error during connection close");
            throwables.printStackTrace();
        }
    }
}
