package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static Session session;
    public final static Logger logger = Logger.getLogger(String.valueOf(UserDaoHibernateImpl.class));

    public UserDaoHibernateImpl() {
        session = Util.getSessionFactory().openSession();
    }


    @Override
    public void createUsersTable() {
        executeSQLQuery(CREATE_TABLE_QUERY);
    }

    @Override
    public void dropUsersTable() {
        executeSQLQuery("DROP TABLE IF EXISTS `users`;");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            logger.info("\nStas: Successfully Add New Record To The Database Table!\n");
        } catch (Exception e) {
            if (null != session.getTransaction()) {
                logger.info("\nStas: Err: Error During Add New Record!\n" +
                                " .......Transaction Is Being Rolled Back.......\n");
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            logger.info("\nStas: Successfully Remove Record From By Id!\n");
        } catch (Exception e) {
            if (null != session.getTransaction()) {
                logger.info("\nStas: Err: Error During Remove Record By Id!\n"
                                + ".......Transaction Is Being Rolled Back.......\n");
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            session.beginTransaction();
            Query queryObj = session.createQuery("From User");
            users = (List<User>) queryObj.list();
            session.getTransaction().commit();
            logger.info("\nStas: Get All Records Done!!\n");
        } catch (Exception e) {
            if (null != session.getTransaction()) {
                logger.info("\nStas: Err: Error During Get All Records!\n" +
                                    ".......Transaction Is Being Rolled Back.......\n");
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session.beginTransaction();
            Query queryObj = session.createQuery("DELETE FROM User");
            queryObj.executeUpdate();
            session.getTransaction().commit();
            logger.info("\nStas: Successfully Deleted All Records From The Database Table!\n");
        } catch (Exception e) {
            if (null != session.getTransaction()) {
                logger.info("\nStas: Err: Error During Clean Table!\n"
                                + ".......Transaction Is Being Rolled Back.......\n");
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    private void executeSQLQuery(String sql) {
        try {
            session.beginTransaction();
            Query sqlQuery = session.createSQLQuery(sql);
            sqlQuery.executeUpdate();
            session.getTransaction().commit();
            logger.info("\nStas: SQL Quoery Done!\n");
        } catch (Exception e) {
            if (session != null) {
                if (null != session.getTransaction()) {
                    logger.info("\nStas: Err: Error During SQL Query!\n"
                                + ".......Transaction Is Being Rolled Back.......\n");
                    session.getTransaction().rollback();
                }
            }
            e.printStackTrace();
        }
    }

    public void close() {
        session.close();
    }
}
