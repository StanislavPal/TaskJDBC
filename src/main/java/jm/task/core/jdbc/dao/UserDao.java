package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao {
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS `users` (\n" +
            "  `id` BIGINT(19) NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `lastname` VARCHAR(45) NULL,\n" +
            "  `age` INT(3) NULL,\n" +
            "  PRIMARY KEY (`id`));";

    
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();

    void close();
}
