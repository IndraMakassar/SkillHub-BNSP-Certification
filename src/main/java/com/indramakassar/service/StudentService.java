package com.indramakassar.service;

import com.indramakassar.entity.Student;
import com.indramakassar.repository.StudentRepository;
import com.indramakassar.repository.RepositoryFactory;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService() {
        this.studentRepository = RepositoryFactory.createStudentRepository();
    }

    // Create new student with validation
    public void create(Student student) throws SQLException {
        validateStudent(student);
        studentRepository.insert(student);
    }

    // Update existing student
    public void update(int id, Student student) throws SQLException {
        validateStudent(student);
        student.setStudentId(id);

        Student existing = studentRepository.findById(id);
        if (existing == null) {
            throw new SQLException("Student with ID " + id + " not found");
        }

        studentRepository.update(student);
    }

    // Delete student
    public void delete(int id) throws SQLException {
        Student existing = studentRepository.findById(id);
        if (existing == null) {
            throw new SQLException("Student with ID " + id + " not found");
        }
        studentRepository.delete(id);
    }

    // Get student by ID
    public Student getById(int id) throws SQLException {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new SQLException("Student with ID " + id + " not found");
        }
        return student;
    }

    // Get all students
    public List<Student> getAll() throws SQLException {
        return studentRepository.findAll();
    }

    // Validation helper
    private void validateStudent(Student student) throws SQLException {
        if (student.getStudentName() == null || student.getStudentName().trim().isEmpty()) {
            throw new SQLException("Student name is required");
        }

        if (student.getEmail() != null && !student.getEmail().trim().isEmpty()) {
            if (!student.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new SQLException("Invalid email format");
            }
        }

        if (student.getPhoneNumber() != null && !student.getPhoneNumber().trim().isEmpty()) {
            if (!student.getPhoneNumber().matches("^[0-9+()\\-\\s]+$")) {
                throw new SQLException("Invalid phone number format");
            }
        }
    }
}