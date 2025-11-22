package com.indramakassar.service;

import com.indramakassar.database.DatabaseInitializer;
import com.indramakassar.entity.Class;
import com.indramakassar.entity.Enrollment;
import com.indramakassar.entity.Student;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EnrollmentServiceTest {

    private static EnrollmentService enrollmentService;
    private static StudentService studentService;
    private static ClassService classService;

    private static int testStudentId;
    private static int testClassId;

    @BeforeAll
    public static void setUp() throws SQLException {
        DatabaseInitializer.initialize();
        enrollmentService = new EnrollmentService();
        studentService = new StudentService();
        classService = new ClassService();

        // Create test student
        Student student = new Student("Test Student", "test@example.com", "081234567890");
        studentService.create(student);
        testStudentId = student.getStudentId();

        // Create test class
        Class classObj = new Class("Test Class", "Test Description", "Test Instructor");
        classService.create(classObj);
        testClassId = classObj.getClassId();
    }

    @Test
    @Order(1)
    @DisplayName("Test Enroll Student - Valid")
    public void testEnrollStudent_Valid() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            enrollmentService.enroll(testStudentId, testClassId);
        });
    }

    @Test
    @Order(2)
    @DisplayName("Test Enroll Student - Duplicate")
    public void testEnrollStudent_Duplicate() {
        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            enrollmentService.enroll(testStudentId, testClassId);
        });

        assertEquals("Student is already enrolled in this class", exception.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("Test Get All Enrollments")
    public void testGetAllEnrollments() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            List<Enrollment> enrollments = enrollmentService.getAll();
            assertNotNull(enrollments);
            assertTrue(enrollments.size() > 0, "Should have at least one enrollment");
        });
    }

    @Test
    @Order(4)
    @DisplayName("Test Get Classes By Student")
    public void testGetClassesByStudent() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            List<Enrollment> enrollments = enrollmentService.getClassesByStudentId(testStudentId);
            assertNotNull(enrollments);
            assertTrue(enrollments.size() > 0);
            assertEquals(testClassId, enrollments.get(0).getClassId());
        });
    }

    @Test
    @Order(5)
    @DisplayName("Test Get Students By Class")
    public void testGetStudentsByClass() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            List<Enrollment> enrollments = enrollmentService.getStudentsByClassId(testClassId);
            assertNotNull(enrollments);
            assertTrue(enrollments.size() > 0);
            assertEquals(testStudentId, enrollments.get(0).getStudentId());
        });
    }

    @Test
    @Order(6)
    @DisplayName("Test Drop Enrollment - Valid")
    public void testDropEnrollment_Valid() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            enrollmentService.drop(testStudentId, testClassId);

            // Verify dropped
            List<Enrollment> enrollments = enrollmentService.getClassesByStudentId(testStudentId);
            assertEquals(0, enrollments.size(), "Enrollment should be removed");
        });
    }

    @Test
    @Order(7)
    @DisplayName("Test Drop Enrollment - Non-existent")
    public void testDropEnrollment_NonExistent() {
        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            enrollmentService.drop(testStudentId, testClassId);
        });

        assertEquals("Enrollment not found", exception.getMessage());
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        // Clean up test data
        studentService.delete(testStudentId);
        classService.delete(testClassId);
    }
}