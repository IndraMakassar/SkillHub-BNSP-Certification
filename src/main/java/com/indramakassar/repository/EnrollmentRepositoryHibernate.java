package com.indramakassar.repository;

import com.indramakassar.entity.Enrollment;
import com.indramakassar.persistence.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepositoryHibernate implements EnrollmentRepository {
    @Override
    public void insert(Enrollment enrollment) throws SQLException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery(
                    "INSERT INTO enrollments (student_id, class_id, status) VALUES (:sid, :cid, :st)")
                .setParameter("sid", enrollment.getStudentId())
                .setParameter("cid", enrollment.getClassId())
                .setParameter("st", enrollment.getStatus() != null ? enrollment.getStatus() : "active")
                .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new SQLException(e);
        }
    }

    @Override
    public void delete(int studentId, int classId) throws SQLException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery("DELETE FROM enrollments WHERE student_id = :sid AND class_id = :cid")
                .setParameter("sid", studentId)
                .setParameter("cid", classId)
                .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new SQLException(e);
        }
    }

    @Override
    public boolean exists(int studentId, int classId) throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Number count = (Number) session.createNativeQuery(
                    "SELECT COUNT(*) FROM enrollments WHERE student_id = :sid AND class_id = :cid")
                .setParameter("sid", studentId)
                .setParameter("cid", classId)
                .getSingleResult();
            return count != null && count.intValue() > 0;
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Enrollment> findAll() throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Object[]> rows = session.createNativeQuery(
                "SELECT e.student_id, e.class_id, e.status, e.enrolled_at, s.student_name, c.class_name " +
                "FROM enrollments e JOIN students s ON e.student_id = s.student_id " +
                "JOIN classes c ON e.class_id = c.class_id ORDER BY e.enrolled_at DESC")
                .getResultList();
            return mapRows(rows);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Enrollment> findByStudentId(int studentId) throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Object[]> rows = session.createNativeQuery(
                "SELECT e.student_id, e.class_id, e.status, e.enrolled_at, s.student_name, c.class_name " +
                "FROM enrollments e JOIN students s ON e.student_id = s.student_id " +
                "JOIN classes c ON e.class_id = c.class_id WHERE e.student_id = :sid ORDER BY e.enrolled_at DESC")
                .setParameter("sid", studentId)
                .getResultList();
            return mapRows(rows);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Enrollment> findByClassId(int classId) throws SQLException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Object[]> rows = session.createNativeQuery(
                "SELECT e.student_id, e.class_id, e.status, e.enrolled_at, s.student_name, c.class_name " +
                "FROM enrollments e JOIN students s ON e.student_id = s.student_id " +
                "JOIN classes c ON e.class_id = c.class_id WHERE e.class_id = :cid ORDER BY s.student_name")
                .setParameter("cid", classId)
                .getResultList();
            return mapRows(rows);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private List<Enrollment> mapRows(List<Object[]> rows) {
        List<Enrollment> list = new ArrayList<>();
        for (Object[] r : rows) {
            Enrollment e = new Enrollment();
            e.setStudentId(((Number) r[0]).intValue());
            e.setClassId(((Number) r[1]).intValue());
            e.setStatus(r[2] != null ? r[2].toString() : null);
            if (r[3] != null) {
                Object v = r[3];
                if (v instanceof java.sql.Timestamp ts) {
                    e.setEnrolledAt(ts.toLocalDateTime());
                } else if (v instanceof String s) {
                    // SQLite default CURRENT_TIMESTAMP returns text like "YYYY-MM-DD HH:MM:SS"
                    e.setEnrolledAt(java.time.LocalDateTime.parse(s.replace(" ", "T")));
                }
            }
            e.setStudentName(r[4] != null ? r[4].toString() : null);
            e.setClassName(r[5] != null ? r[5].toString() : null);
            list.add(e);
        }
        return list;
    }
}
