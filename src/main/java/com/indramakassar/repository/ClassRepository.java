package com.indramakassar.repository;

import com.indramakassar.database.DatabaseConnection;
import com.indramakassar.entity.Class;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClassRepository {

    // Insert new class
    public void insert(Class classObj) throws SQLException {
        String sql = "INSERT INTO classes (class_name, description, instructor) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, classObj.getClassName());
            pstmt.setString(2, classObj.getDescription());
            pstmt.setString(3, classObj.getInstructor());

            pstmt.executeUpdate();

            // Get generated ID
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                classObj.setClassId(rs.getInt(1));
            }
        }
    }

    // Update existing class
    public void update(Class classObj) throws SQLException {
        String sql = "UPDATE classes SET class_name = ?, description = ?, instructor = ?, " +
                "updated_at = CURRENT_TIMESTAMP WHERE class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, classObj.getClassName());
            pstmt.setString(2, classObj.getDescription());
            pstmt.setString(3, classObj.getInstructor());
            pstmt.setInt(4, classObj.getClassId());

            pstmt.executeUpdate();
        }
    }

    // Delete class by ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM classes WHERE class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Find class by ID
    public Class findById(int id) throws SQLException {
        String sql = "SELECT * FROM classes WHERE class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToClass(rs);
            }
        }
        return null;
    }

    // Find all classes
    public List<Class> findAll() throws SQLException {
        String sql = "SELECT * FROM classes ORDER BY class_name";
        List<Class> classes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                classes.add(mapResultSetToClass(rs));
            }
        }
        return classes;
    }

    // Helper method to map ResultSet to Class object
    private Class mapResultSetToClass(ResultSet rs) throws SQLException {
        Class classObj = new Class();
        classObj.setClassId(rs.getInt("class_id"));
        classObj.setClassName(rs.getString("class_name"));
        classObj.setDescription(rs.getString("description"));
        classObj.setInstructor(rs.getString("instructor"));

        String createdAt = rs.getString("created_at");
        String updatedAt = rs.getString("updated_at");

        if (createdAt != null) {
            classObj.setCreatedAt(LocalDateTime.parse(createdAt.replace(" ", "T")));
        }
        if (updatedAt != null) {
            classObj.setUpdatedAt(LocalDateTime.parse(updatedAt.replace(" ", "T")));
        }

        return classObj;
    }
}