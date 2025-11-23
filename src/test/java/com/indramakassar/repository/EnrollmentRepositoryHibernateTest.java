package com.indramakassar.repository;

import com.indramakassar.database.DatabaseInitializer;
import com.indramakassar.entity.Class;
import com.indramakassar.entity.Enrollment;
import com.indramakassar.entity.Student;
import com.indramakassar.persistence.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnrollmentRepositoryHibernateTest {

    private static EnrollmentRepository enrollmentRepository;
    private static StudentRepository studentRepository;
    private static ClassRepository classRepository;
    private static Path tempDbPath;

    private static int studentAliceId;
    private static int studentBobId;
    private static int classMathId;
    private static int classScienceId;

    @BeforeAll
    static void setupAll() throws Exception {
        tempDbPath = Files.createTempFile("bnsp-repo-enroll-", ".db");
        Files.deleteIfExists(tempDbPath);

        System.setProperty("DB_URL", "jdbc:sqlite:" + tempDbPath.toAbsolutePath());
        System.setProperty("HIBERNATE_DDL", "create-drop");
        System.setProperty("HIBERNATE_SHOW_SQL", "false");

        SessionFactory sf = HibernateUtil.getSessionFactory();
        assertNotNull(sf);

        // Ensure enrollments table exists
        DatabaseInitializer.initialize();

        studentRepository = new StudentRepositoryHibernate();
        classRepository = new ClassRepositoryHibernate();
        enrollmentRepository = new EnrollmentRepositoryHibernate();

        // Seed students and classes
        Student alice = new Student("Alice", "alice@mail", null);
        studentRepository.insert(alice);
        studentAliceId = alice.getStudentId();

        Student bob = new Student("Bob", "bob@mail", null);
        studentRepository.insert(bob);
        studentBobId = bob.getStudentId();

        Class math = new Class("Math", "Basic", "T1");
        classRepository.insert(math);
        classMathId = math.getClassId();

        Class science = new Class("Science", "Basic", "T2");
        classRepository.insert(science);
        classScienceId = science.getClassId();
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
    void testInsertAndExists() throws SQLException {
        Enrollment e = new Enrollment(studentAliceId, classMathId, "active");
        enrollmentRepository.insert(e);
        assertTrue(enrollmentRepository.exists(studentAliceId, classMathId));
    }

    @Test
    @Order(2)
    void testFindByStudentId() throws SQLException {
        // Enroll Alice to Science, Bob to Math
        enrollmentRepository.insert(new Enrollment(studentAliceId, classScienceId, "active"));
        enrollmentRepository.insert(new Enrollment(studentBobId, classMathId, "active"));

        List<Enrollment> aliceEnrolls = enrollmentRepository.findByStudentId(studentAliceId);
        assertNotNull(aliceEnrolls);
        assertTrue(aliceEnrolls.size() >= 2);
        // Names should be joined properly
        for (Enrollment en : aliceEnrolls) {
            assertEquals(studentAliceId, en.getStudentId());
            assertEquals("Alice", en.getStudentName());
            assertNotNull(en.getClassName());
        }
    }

    @Test
    @Order(3)
    void testFindByClassIdOrderedByStudentName() throws SQLException {
        // Class Math should have Alice and Bob; result ordered by student_name asc per repo query
        List<Enrollment> mathEnrolls = enrollmentRepository.findByClassId(classMathId);
        assertNotNull(mathEnrolls);
        assertTrue(mathEnrolls.size() >= 2);
        List<String> names = mathEnrolls.stream().map(Enrollment::getStudentName).toList();
        List<String> sorted = names.stream().sorted().toList();
        assertEquals(sorted, names);
    }

    @Test
    @Order(4)
    void testFindAllOrderedByEnrolledAtDesc() throws SQLException, InterruptedException {
        // Add one more to influence ordering
        Thread.sleep(20); // ensure timestamp difference if using CURRENT_TIMESTAMP text
        enrollmentRepository.insert(new Enrollment(studentBobId, classScienceId, "active"));

        List<Enrollment> all = enrollmentRepository.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 4);
        // Verify joined fields are present
        for (Enrollment e : all) {
            assertNotNull(e.getStudentName());
            assertNotNull(e.getClassName());
        }
        // Check desc ordering by enrolled_at (best-effort: compare first vs last timestamps if available)
        if (all.size() >= 2 && all.get(0).getEnrolledAt() != null && all.get(all.size()-1).getEnrolledAt() != null) {
            assertTrue(all.get(0).getEnrolledAt().isAfter(all.get(all.size()-1).getEnrolledAt()) ||
                       all.get(0).getEnrolledAt().isEqual(all.get(all.size()-1).getEnrolledAt()));
        }
    }

    @Test
    @Order(5)
    void testDelete() throws SQLException {
        // Delete Alice from Math, should no longer exist
        assertTrue(enrollmentRepository.exists(studentAliceId, classMathId));
        enrollmentRepository.delete(studentAliceId, classMathId);
        assertFalse(enrollmentRepository.exists(studentAliceId, classMathId));
    }
}
