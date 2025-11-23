package com.indramakassar.database;

import com.indramakassar.persistence.HibernateUtil;
import org.hibernate.Session;

public class DatabaseInitializer {
    public static void initialize() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Ensure foreign keys are enforced in SQLite and create missing tables.
            session.beginTransaction();

            // Enable foreign keys
            session.createNativeQuery("PRAGMA foreign_keys = ON").executeUpdate();

            // Rely on Hibernate hbm2ddl.auto=update to create 'students' and 'classes' tables
            // Create 'enrollments' table via native SQL because we don't map it as an entity
            session.createNativeQuery("""
                CREATE TABLE IF NOT EXISTS enrollments (
                    class_id     INTEGER NOT NULL,
                    student_id   INTEGER NOT NULL,
                    status       TEXT DEFAULT 'active',
                    enrolled_at  TEXT DEFAULT CURRENT_TIMESTAMP,
                    PRIMARY KEY (class_id, student_id),
                    FOREIGN KEY (class_id) REFERENCES classes(class_id)
                        ON UPDATE CASCADE ON DELETE CASCADE,
                    FOREIGN KEY (student_id) REFERENCES students(student_id)
                        ON UPDATE CASCADE ON DELETE CASCADE
                )
            """).executeUpdate();

            session.getTransaction().commit();

            System.out.println("Database initialized successfully via Hibernate!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
