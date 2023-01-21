package com.sda.dao;

import com.sda.db.HibernateUtils;
import com.sda.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsersDAO {

    public void create(User user) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }
    }

    public boolean deleteByUsername(String username) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, username);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
            return user != null;
        }
    }

    public List<User> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<User> users = session.createQuery("SELECT u from User u", User.class).list();
        session.close();
        return users;
    }
}
