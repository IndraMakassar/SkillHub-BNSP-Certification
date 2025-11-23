package com.indramakassar.repository;

public class RepositoryFactory {
    // Hibernate is now the default and only implementation
    public static StudentRepository createStudentRepository() {
        return new StudentRepositoryHibernate();
    }

    public static ClassRepository createClassRepository() {
        return new ClassRepositoryHibernate();
    }

    public static EnrollmentRepository createEnrollmentRepository() {
        return new EnrollmentRepositoryHibernate();
    }
}
