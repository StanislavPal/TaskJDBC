package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

/*
 В методе main класса Main должны происходить следующие операции:

 1. Создание таблицы User(ов)
 2. Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
 3. Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
 4. Очистка таблицы User(ов)
 5. Удаление таблицы
 */
public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Pol", "MacCartney", (byte) 23);
        userService.saveUser("John", "Lennon", (byte) 25);
        userService.saveUser("Ringo", "Starr", (byte) 25);
        userService.saveUser("George", "Harrison", (byte) 22);
        for ( User user : userService.getAllUsers() ) {
            System.out.println(user);
        }
        userService.dropUsersTable();

        userService.close();
    }
}
