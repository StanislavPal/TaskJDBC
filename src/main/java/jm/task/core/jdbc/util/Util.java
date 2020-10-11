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
    private static final String URL = "jdbc:mysql://localhost:3306/jm_3_m_1?useUnicode=true&serverTimezone=Europe/Moscow&useSSL=false";
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
                        .setProperty("URL.connection.driver_class", "com.mysql.jdbc.Driver")
                        .setProperty("URL.connection.url", "jdbc:mysql://localhost:3306/crud")
                        .setProperty("URL.connection.username", "admin")
                        .setProperty("URL.connection.password", "admin")
                        .setProperty("URL.connection.pool_size", "2")
                        .setProperty("URL.connection.autocommit", "false")
                        .setProperty("URL.cache.provider_class", "org.URL.cache.NoCacheProvider")
                        .setProperty("URL.cache.use_second_level_cache", "false")
                        .setProperty("URL.cache.use_query_cache", "false")
                        .setProperty("URL.dialect", "org.URL.dialect.MySQL5Dialect")
                        .setProperty("URL.show_sql", "true")
                        .setProperty("URL.current_session_context_class", "thread")
                        .addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }

}
