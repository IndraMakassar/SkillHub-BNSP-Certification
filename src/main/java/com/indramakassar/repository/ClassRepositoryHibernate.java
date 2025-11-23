package com.indramakassar.repository;

import com.indramakassar.entity.Class;
import com.indramakassar.persistence.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class ClassRepositoryHibernate implements ClassRepository {
    @Override
    public void insert(Class classObj) throws SQLException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(classObj);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new SQLException(e);
        }
    }

    @Override
    public void update(Class classObj) throws SQLException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(classObj);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new SQLException(e);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Class c = session.get(Class.class, id);
            if (c != null) session.remove(c);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new SQLException(e);
        }
    }

    @Override
    public Class findById(int id) throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Class.class, id);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Class> findAll() throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Class c order by c.className", Class.class).getResultList();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
}
