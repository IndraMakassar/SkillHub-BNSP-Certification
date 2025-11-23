package com.indramakassar.repository;

import com.indramakassar.entity.Enrollment;

import java.sql.SQLException;
import java.util.List;

public interface EnrollmentRepository {
    void insert(Enrollment enrollment) throws SQLException;
    void delete(int studentId, int classId) throws SQLException;
    boolean exists(int studentId, int classId) throws SQLException;
    List<Enrollment> findAll() throws SQLException;
    List<Enrollment> findByStudentId(int studentId) throws SQLException;
    List<Enrollment> findByClassId(int classId) throws SQLException;
}
