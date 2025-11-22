package com.indramakassar.repository;

import com.indramakassar.database.DatabaseConnection;
import com.indramakassar.entity.Enrollment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository {

    // Insert new enrollment
    public void insert(Enrollment enrollment) throws SQLException {
        String sql = "INSERT INTO enrollments (student_id, class_id, status) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, enrollment.getStudentId());
            pstmt.setInt(2, enrollment.getClassId());
            pstmt.setString(3, enrollment.getStatus() != null ? enrollment.getStatus() : "active");

            pstmt.executeUpdate();
        }
    }

    // Delete enrollment
    public void delete(int studentId, int classId) throws SQLException {
        String sql = "DELETE FROM enrollments WHERE student_id = ? AND class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, classId);
            pstmt.executeUpdate();
        }
    }

    // Find all enrollments with student and class details
    public List<Enrollment> findAll() throws SQLException {
        String sql = """
            SELECT e.student_id, e.class_id, e.status, e.enrolled_at,
                   s.student_name, c.class_name
            FROM enrollments e
            JOIN students s ON e.student_id = s.student_id
            JOIN classes c ON e.class_id = c.class_id
            ORDER BY e.enrolled_at DESC
        """;

        List<Enrollment> enrollments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                enrollments.add(mapResultSetToEnrollment(rs));
            }
        }
        return enrollments;
    }

    // Find classes by student ID
    public List<Enrollment> findByStudentId(int studentId) throws SQLException {
        String sql = """
            SELECT e.student_id, e.class_id, e.status, e.enrolled_at,
                   s.student_name, c.class_name
            FROM enrollments e
            JOIN students s ON e.student_id = s.student_id
            JOIN classes c ON e.class_id = c.class_id
            WHERE e.student_id = ?
            ORDER BY e.enrolled_at DESC
        """;

        List<Enrollment> enrollments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                enrollments.add(mapResultSetToEnrollment(rs));
            }
        }
        return enrollments;
    }

    // Find students by class ID
    public List<Enrollment> findByClassId(int classId) throws SQLException {
        String sql = """
            SELECT e.student_id, e.class_id, e.status, e.enrolled_at,
                   s.student_name, c.class_name
            FROM enrollments e
            JOIN students s ON e.student_id = s.student_id
            JOIN classes c ON e.class_id = c.class_id
            WHERE e.class_id = ?
            ORDER BY s.student_name
        """;

        List<Enrollment> enrollments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                enrollments.add(mapResultSetToEnrollment(rs));
            }
        }
        return enrollments;
    }

    // Check if enrollment exists
    public boolean exists(int studentId, int classId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM enrollments WHERE student_id = ? AND class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, classId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // Helper method to map ResultSet to Enrollment object
    private Enrollment mapResultSetToEnrollment(ResultSet rs) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(rs.getInt("student_id"));
        enrollment.setClassId(rs.getInt("class_id"));
        enrollment.setStatus(rs.getString("status"));
        enrollment.setStudentName(rs.getString("student_name"));
        enrollment.setClassName(rs.getString("class_name"));

        String enrolledAt = rs.getString("enrolled_at");
        if (enrolledAt != null) {
            enrollment.setEnrolledAt(LocalDateTime.parse(enrolledAt.replace(" ", "T")));
        }

        return enrollment;
    }
}