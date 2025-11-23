package com.indramakassar.repository;

import com.indramakassar.entity.Student;
import com.indramakassar.persistence.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class StudentRepositoryHibernate implements StudentRepository {
    @Override
    public void insert(Student student) throws SQLException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new SQLException(e);
        }
    }

    @Override
    public void update(Student student) throws SQLException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(student);
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
            Student s = session.get(Student.class, id);
            if (s != null) session.remove(s);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new SQLException(e);
        }
    }

    @Override
    public Student findById(int id) throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, id);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Student> findAll() throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student s order by s.studentName", Student.class).getResultList();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
}
