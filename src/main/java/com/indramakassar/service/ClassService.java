package com.indramakassar.service;

import com.indramakassar.entity.Class;
import com.indramakassar.repository.ClassRepository;

import java.sql.SQLException;
import java.util.List;

public class ClassService {
    private final ClassRepository classRepository;

    public ClassService() {
        this.classRepository = new ClassRepository();
    }

    // Create new class with validation
    public void create(Class classObj) throws SQLException {
        validateClass(classObj);
        classRepository.insert(classObj);
    }

    // Update existing class
    public void update(int id, Class classObj) throws SQLException {
        validateClass(classObj);
        classObj.setClassId(id);

        Class existing = classRepository.findById(id);
        if (existing == null) {
            throw new SQLException("Class with ID " + id + " not found");
        }

        classRepository.update(classObj);
    }

    // Delete class
    public void delete(int id) throws SQLException {
        Class existing = classRepository.findById(id);
        if (existing == null) {
            throw new SQLException("Class with ID " + id + " not found");
        }
        classRepository.delete(id);
    }

    // Get class by ID
    public Class getById(int id) throws SQLException {
        Class classObj = classRepository.findById(id);
        if (classObj == null) {
            throw new SQLException("Class with ID " + id + " not found");
        }
        return classObj;
    }

    // Get all classes
    public List<Class> getAll() throws SQLException {
        return classRepository.findAll();
    }

    // Validation helper
    private void validateClass(Class classObj) throws SQLException {
        if (classObj.getClassName() == null || classObj.getClassName().trim().isEmpty()) {
            throw new SQLException("Class name is required");
        }

        if (classObj.getClassName().length() > 100) {
            throw new SQLException("Class name must be 100 characters or less");
        }

        if (classObj.getDescription() != null && classObj.getDescription().length() > 500) {
            throw new SQLException("Description must be 500 characters or less");
        }

        if (classObj.getInstructor() != null && classObj.getInstructor().length() > 100) {
            throw new SQLException("Instructor name must be 100 characters or less");
        }
    }
}