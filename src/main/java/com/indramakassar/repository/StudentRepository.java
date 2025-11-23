package com.indramakassar.repository;

import com.indramakassar.entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentRepository {
    void insert(Student student) throws SQLException;
    void update(Student student) throws SQLException;
    void delete(int id) throws SQLException;
    Student findById(int id) throws SQLException;
    List<Student> findAll() throws SQLException;
}
