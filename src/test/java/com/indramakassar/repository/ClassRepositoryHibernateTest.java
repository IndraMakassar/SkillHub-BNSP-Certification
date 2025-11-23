package com.indramakassar.repository;

import com.indramakassar.entity.Class;
import com.indramakassar.persistence.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClassRepositoryHibernateTest {

    private static ClassRepository repository;
    private static Path tempDbPath;

    @BeforeAll
    static void setupAll() throws Exception {
        tempDbPath = Files.createTempFile("bnsp-repo-class-", ".db");
        Files.deleteIfExists(tempDbPath);

        System.setProperty("DB_URL", "jdbc:sqlite:" + tempDbPath.toAbsolutePath());
        System.setProperty("HIBERNATE_DDL", "create-drop");
        System.setProperty("HIBERNATE_SHOW_SQL", "false");

        SessionFactory sf = HibernateUtil.getSessionFactory();
        assertNotNull(sf);

        repository = new ClassRepositoryHibernate();
    }

    @AfterAll
    static void tearDownAll() throws Exception {
        try {
            if (tempDbPath != null) {
                Files.deleteIfExists(tempDbPath);
            }
        } catch (Exception ignored) {}
    }

    @Test
    @Order(1)
    void testInsertAndFindById() throws SQLException {
        Class c = new Class("Math", "Basic math", "Alice");
        repository.insert(c);
        assertTrue(c.getClassId() > 0);

        Class fetched = repository.findById(c.getClassId());
        assertNotNull(fetched);
        assertEquals("Math", fetched.getClassName());
        assertEquals("Basic math", fetched.getDescription());
        assertEquals("Alice", fetched.getInstructor());
    }

    @Test
    @Order(2)
    void testUpdate() throws SQLException {
        Class c = new Class("Science", "Old", "Bob");
        repository.insert(c);
        int id = c.getClassId();

        c.setClassName("Science Advanced");
        c.setDescription("New");
        c.setInstructor("Carol");
        repository.update(c);

        Class fetched = repository.findById(id);
        assertNotNull(fetched);
        assertEquals("Science Advanced", fetched.getClassName());
        assertEquals("New", fetched.getDescription());
        assertEquals("Carol", fetched.getInstructor());
    }

    @Test
    @Order(3)
    void testFindAllOrdered() throws SQLException {
        repository.insert(new Class("Zoology", null, null));
        repository.insert(new Class("Algebra", null, null));
        repository.insert(new Class("Botany", null, null));

        List<Class> all = repository.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 3);

        List<String> names = all.stream().map(Class::getClassName).toList();
        List<String> sorted = names.stream().sorted().toList();
        assertEquals(sorted, names, "Classes should be ordered by className asc");
    }

    @Test
    @Order(4)
    void testDelete() throws SQLException {
        Class c = new Class("Temp", null, null);
        repository.insert(c);
        int id = c.getClassId();

        assertNotNull(repository.findById(id));
        repository.delete(id);
        assertNull(repository.findById(id));
    }
}
