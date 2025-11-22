package com.indramakassar.database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON");

            // STUDENTS TABLE
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS students (
                    student_id    INTEGER PRIMARY KEY AUTOINCREMENT,
                    student_name  TEXT NOT NULL,
                    email         TEXT,
                    phone_number  TEXT,
                    created_at    TEXT DEFAULT CURRENT_TIMESTAMP,
                    updated_at    TEXT DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // CLASSES TABLE
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS classes (
                    class_id     INTEGER PRIMARY KEY AUTOINCREMENT,
                    class_name   TEXT NOT NULL,
                    description  TEXT,
                    instructor   TEXT,
                    created_at   TEXT DEFAULT CURRENT_TIMESTAMP,
                    updated_at   TEXT DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // ENROLLMENTS TABLE (Composite PK)
            stmt.execute("""
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
            """);

            System.out.println("Database initialized successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
