package com.indramakassar.repository;

import com.indramakassar.entity.Student;
import com.indramakassar.persistence.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentRepositoryHibernateTest {

    private static StudentRepository repository;
    private static Path tempDbPath;

    @BeforeAll
    static void setupAll() throws Exception {
        // Use a temp SQLite file DB just for this test class
        tempDbPath = Files.createTempFile("bnsp-repo-test-", ".db");
        // Ensure clean slate
        Files.deleteIfExists(tempDbPath);

        System.setProperty("DB_URL", "jdbc:sqlite:" + tempDbPath.toAbsolutePath());
        System.setProperty("HIBERNATE_DDL", "create-drop");
        System.setProperty("HIBERNATE_SHOW_SQL", "false");

        HibernateUtil.resetSessionFactory();

        // Initialize SessionFactory after properties are set
        SessionFactory sf = HibernateUtil.getSessionFactory();
        assertNotNull(sf, "SessionFactory should initialize");

        repository = new StudentRepositoryHibernate();
    }

    @AfterAll
    static void tearDownAll() throws Exception {
        // Best-effort cleanup of the temp DB file
        try {
            if (tempDbPath != null) {
                Files.deleteIfExists(tempDbPath);
            }
        } catch (Exception ignored) {}
    }

    @Test
    @Order(1)
    void testInsertAndFindById() throws SQLException {
        Student s = new Student("Repo Tester", "repo@test.local", "+62-111");
        repository.insert(s);
        // After persist with IDENTITY, Hibernate should assign an ID to entity instance
        assertTrue(s.getStudentId() > 0, "ID should be generated");

        Student fetched = repository.findById(s.getStudentId());
        assertNotNull(fetched);
        assertEquals("Repo Tester", fetched.getStudentName());
        assertEquals("repo@test.local", fetched.getEmail());
        assertEquals("+62-111", fetched.getPhoneNumber());
    }

    @Test
    @Order(2)
    void testUpdate() throws SQLException {
        // Create a new student, then update
        Student s = new Student("To Update", "old@mail", "000");
        repository.insert(s);
        int id = s.getStudentId();

        s.setStudentName("Updated Name");
        s.setEmail("new@mail");
        s.setPhoneNumber("12345");
        repository.update(s);

        Student fetched = repository.findById(id);
        assertNotNull(fetched);
        assertEquals("Updated Name", fetched.getStudentName());
        assertEquals("new@mail", fetched.getEmail());
        assertEquals("12345", fetched.getPhoneNumber());
    }

    @Test
    @Order(3)
    void testFindAllOrdered() throws SQLException {
        // Insert known names to verify ordering by studentName
        repository.insert(new Student("Charlie", null, null));
        repository.insert(new Student("Alice", null, null));
        repository.insert(new Student("Bob", null, null));

        List<Student> all = repository.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 3);

        // Extract names and assert they are sorted ascending by studentName
        List<String> names = all.stream().map(Student::getStudentName).toList();
        // Make a sorted copy and compare ordering relative
        List<String> sorted = names.stream().sorted().toList();
        assertEquals(sorted, names, "Students should be ordered by studentName asc");
    }

    @Test
    @Order(4)
    void testDelete() throws SQLException {
        Student s = new Student("Temp Delete", null, null);
        repository.insert(s);
        int id = s.getStudentId();

        assertNotNull(repository.findById(id));
        repository.delete(id);
        assertNull(repository.findById(id), "Deleted entity should return null on findById");
    }
}
