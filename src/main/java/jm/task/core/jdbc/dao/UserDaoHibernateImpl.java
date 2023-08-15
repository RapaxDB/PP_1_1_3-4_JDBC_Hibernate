package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util = new Util();
    public UserDaoHibernateImpl() {
    }
    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE users (" +
                "ID BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(25), " +
                "LASTNAME VARCHAR(25), " +
                "AGE INT)";

        try (Session session = util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            if (tx.getStatus().equals(TransactionStatus.ACTIVE)) {
                tx.commit();
            }
        } catch (Exception ignore) {
        }
    }
    @Override
    public void dropUsersTable() {
        try (Session session = util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery("DROP TABLE users").addEntity(User.class).executeUpdate();
            if (tx.getStatus().equals(TransactionStatus.ACTIVE)) {
                tx.commit();
            }
        } catch (Exception ignore) {
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            if (tx.getStatus().equals(TransactionStatus.ACTIVE)) {
                tx.commit();
            }
        }
    }
    @Override
    public void removeUserById(long id) {
        try (Session session = util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            if (tx.getStatus().equals(TransactionStatus.ACTIVE)) {
                tx.commit();
            }
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = util.getSessionFactory().openSession()) {
            users = session.createQuery("SELECT a FROM User a")
                    .getResultList();
        }
        return users;
    }
    @Override
    public void cleanUsersTable() {
        try (Session session = util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction(); //<--
            session.createQuery("DELETE FROM User").executeUpdate();
            if (tx.getStatus().equals(TransactionStatus.ACTIVE)) {
                tx.commit();
            }
        }
    }
}
