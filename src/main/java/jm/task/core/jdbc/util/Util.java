package jm.task.core.jdbc.util;

import com.mysql.jdbc.Driver;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/task_jdbc?useUnicode=true&serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "987654321";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Connection OK!");
            }
        } catch (SQLException e) {
            System.out.println("Connection ERROR!");
            e.printStackTrace();
        }
        return connection;
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
//                Configuration configuration = new Configuration().configure();  //hibernate.cfg.xml
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .setProperty("hibernate.connection.url", URL)
                        .setProperty("hibernate.connection.username", USERNAME)
                        .setProperty("hibernate.connection.password", PASSWORD)

//                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
//                        .setProperty("hibernate.connection.url", URL)
//                        .setProperty("hibernate.connection.username", USERNAME)
//                        .setProperty("hibernate.connection.password", PASSWORD)
//                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
//                        .setProperty("hibernate.show_sql", "true")
//                        .setProperty("hibernate.current_session_context_class", "thread")
//                        .addAnnotatedClass(User.class);

//                        .setProperty("hibernate.connection.pool_size", "2")
                        .setProperty("hibernate.connection.autocommit", "false")
//                        .setProperty("hibernate.cache.provider_class", "org.URL.cache.NoCacheProvider")
                        .setProperty("hibernate.cache.use_second_level_cache", "false")
                        .setProperty("hibernate.cache.use_query_cache", "false")
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                        .setProperty("hibernate.show_sql", "true")
                        .setProperty("hibernate.current_session_context_class", "thread")
                        .addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
                if (!sessionFactory.isClosed()) {
                    System.out.println("SessionFactory: Connection OK!");
                }
            } catch (Exception e) {
                System.out.println("Error: SessionFactory create fail! " + e);
            }
        }
        return sessionFactory;
    }

}
