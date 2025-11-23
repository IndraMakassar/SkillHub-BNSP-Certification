package com.indramakassar.repository;

import com.indramakassar.entity.Class;

import java.sql.SQLException;
import java.util.List;

public interface ClassRepository {
    void insert(Class classObj) throws SQLException;
    void update(Class classObj) throws SQLException;
    void delete(int id) throws SQLException;
    Class findById(int id) throws SQLException;
    List<Class> findAll() throws SQLException;
}
