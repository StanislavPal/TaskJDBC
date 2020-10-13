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
        String sql = "DROP TABLE IF EXISTS `users`;";
        executeUpdate(sql);
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO USERS (name, lastname, age) VALUES"
                + " ('" + name
                + "', '" + lastName
                + "', '" + age + "');";
        executeUpdate(sql);
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM USERS WHERE"
                + " id = " + id + ";";
        executeUpdate(sql);
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
            closeConnection();
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
            closeConnection();
            System.out.println("Err: Query didn't execute");
            throwables.printStackTrace();
            return  -1;
        }
    }

    public void closeConnection () {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (SQLException throwables) {
            System.out.println("Err: Error during connection close");
            throwables.printStackTrace();
        }
    }
}
