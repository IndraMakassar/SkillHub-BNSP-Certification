package com.indramakassar.service;

import com.indramakassar.entity.Enrollment;
import com.indramakassar.repository.EnrollmentRepository;
import com.indramakassar.repository.RepositoryFactory;

import java.sql.SQLException;
import java.util.List;

public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService() {
        this.enrollmentRepository = RepositoryFactory.createEnrollmentRepository();
    }

    // Constructor for dependency injection (testing)
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    // Enroll student to class
    public void enroll(int studentId, int classId) throws SQLException {
        // Check if already enrolled
        if (enrollmentRepository.exists(studentId, classId)) {
            throw new SQLException("Student is already enrolled in this class");
        }

        Enrollment enrollment = new Enrollment(studentId, classId);
        enrollmentRepository.insert(enrollment);
    }

    // Drop enrollment
    public void drop(int studentId, int classId) throws SQLException {
        if (!enrollmentRepository.exists(studentId, classId)) {
            throw new SQLException("Enrollment not found");
        }

        enrollmentRepository.delete(studentId, classId);
    }

    // Get all enrollments
    public List<Enrollment> getAll() throws SQLException {
        return enrollmentRepository.findAll();
    }

    // Get classes by student
    public List<Enrollment> getClassesByStudentId(int studentId) throws SQLException {
        return enrollmentRepository.findByStudentId(studentId);
    }

    // Get students by class
    public List<Enrollment> getStudentsByClassId(int classId) throws SQLException {
        return enrollmentRepository.findByClassId(classId);
    }
}