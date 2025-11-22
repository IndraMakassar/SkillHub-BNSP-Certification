package com.indramakassar.repository;

import com.indramakassar.database.DatabaseConnection;
import com.indramakassar.entity.Student;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    // Insert new student
    public void insert(Student student) throws SQLException {
        String sql = "INSERT INTO students (student_name, email, phone_number) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, student.getStudentName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getPhoneNumber());

            pstmt.executeUpdate();

            // Get generated ID
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                student.setStudentId(rs.getInt(1));
            }
        }
    }

    // Update existing student
    public void update(Student student) throws SQLException {
        String sql = "UPDATE students SET student_name = ?, email = ?, phone_number = ?, " +
                "updated_at = CURRENT_TIMESTAMP WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getStudentName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getPhoneNumber());
            pstmt.setInt(4, student.getStudentId());

            pstmt.executeUpdate();
        }
    }

    // Delete student by ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Find student by ID
    public Student findById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToStudent(rs);
            }
        }
        return null;
    }

    // Find all students
    public List<Student> findAll() throws SQLException {
        String sql = "SELECT * FROM students ORDER BY student_name";
        List<Student> students = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
        }
        return students;
    }

    // Helper method to map ResultSet to Student object
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("student_id"));
        student.setStudentName(rs.getString("student_name"));
        student.setEmail(rs.getString("email"));
        student.setPhoneNumber(rs.getString("phone_number"));

        String createdAt = rs.getString("created_at");
        String updatedAt = rs.getString("updated_at");

        if (createdAt != null) {
            student.setCreatedAt(LocalDateTime.parse(createdAt.replace(" ", "T")));
        }
        if (updatedAt != null) {
            student.setUpdatedAt(LocalDateTime.parse(updatedAt.replace(" ", "T")));
        }

        return student;
    }
}