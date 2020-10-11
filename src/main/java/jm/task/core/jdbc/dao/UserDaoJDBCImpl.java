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
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE `users` (\n" +
                "  `id` BIGINT(19) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastname` VARCHAR(45) NULL,\n" +
                "  `age` INT(3) NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;";
        executeUpdate(sql);
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE `users`;";
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
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet rs =  statement.executeQuery(sql);
            users = new ArrayList<>();
            while (rs.next()) {
                    users.add(new User(rs.getString("name"),
                                       rs.getString("lastname"),
                                       (byte) rs.getInt("age")));
            }
        } catch (SQLException throwables) {
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
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            return  statement.executeUpdate(sql) ;
         } catch (SQLException throwables) {
            System.out.println("Err: Query didn't execute");
            throwables.printStackTrace();
            return  -1;
        }
    }
}
