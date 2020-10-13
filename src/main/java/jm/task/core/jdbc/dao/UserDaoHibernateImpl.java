package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

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
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();

            NativeQuery queryObj = session.createNativeQuery(CREATE_TABLE_QUERY);
            queryObj.executeUpdate();

            session.getTransaction().commit();
            logger.info("\nStas: Table have been created!\n");
        } catch (Exception sqlException) {
            if (null != session.getTransaction()) {
                logger.info("\nStas:.......Table created Transaction Is Being Rolled Back.......\n");
                session.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        User user = findUserById(id);
        if (user != null) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = (List<User>) Util.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }

    @Override
    public void cleanUsersTable() {
//        https://examples.javacodegeeks.com/enterprise-java/hibernate/hibernate-crud-operations-tutorial
        try {
            session.beginTransaction();

            NativeQuery queryObj = session.createQuery("DELETE FROM User");
            queryObj.executeUpdate();

            session.getTransaction().commit();
            logger.info("\nStas: Successfully Deleted All Records From The Database Table!\n");
        } catch (Exception sqlException) {
            if (null != session.getTransaction()) {
                logger.info("\nStas: .......Transaction Is Being Rolled Back.......\n");
                session.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        }
    }

    public User findUserById(long id) {
        User user = null;
        try {
            session.beginTransaction();

            user = (User) session.get(User.class, id);
        } catch (Exception sqlException) {
            if (null != session.getTransaction()) {
                logger.info("\nStas: .......Transaction Is Being Rolled Back.......\n");
                session.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        }
        return user;
    }

    public void closeConnection() {
        session.close();
    }
}
