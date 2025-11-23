package com.indramakassar.persistence;

import com.indramakassar.entity.Class;
import com.indramakassar.entity.Student;
// Note: Enrollment is not a mapped entity; its table is managed via DatabaseInitializer DDL
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

            Map<String, Object> settings = new HashMap<>();

            settings.put("hibernate.connection.driver_class", "org.sqlite.JDBC");

            String sqliteUrl = "jdbc:sqlite:database/training_app.db?foreign_keys=on";
            settings.put("hibernate.connection.url", sqliteUrl);

            // SQLite does not require username/password
            settings.put("hibernate.connection.username", "");
            settings.put("hibernate.connection.password", "");

            // 3. SQLite Dialect
            String dialect = "org.hibernate.community.dialect.SQLiteDialect";
            settings.put("hibernate.dialect", dialect);

            // You may need to specify this for the connection pool to work with SQLite
            settings.put("hibernate.connection.pool_size", "1");
            // 🛠️ CHANGES FOR SQLITE END HERE 🛠️

            // Original settings (unchanged, but using the updated dialect variable)
            String ddl = "update"; // update|validate|create|create-drop
            settings.put("hibernate.hbm2ddl.auto", ddl);
            settings.put("hibernate.show_sql", "false");
            settings.put("hibernate.format_sql", "true");


            registryBuilder.applySettings(settings);
            StandardServiceRegistry registry = registryBuilder.build();

            MetadataSources sources = new MetadataSources(registry)
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(Class.class);

            sessionFactory = sources.buildMetadata().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void resetSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }


}