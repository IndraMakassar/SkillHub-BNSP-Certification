package com.indramakassar.service;

import com.indramakassar.database.DatabaseInitializer;
import com.indramakassar.entity.Student;
import com.indramakassar.persistence.HibernateUtil;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentServiceTest {

    private static StudentService studentService;
    private static int testStudentId;

    @BeforeAll
    public static void setUp() {
        HibernateUtil.resetSessionFactory();

        // Initialize database
        DatabaseInitializer.initialize();
        studentService = new StudentService();
    }

    @Test
    @Order(1)
    @DisplayName("Test Create Student - Valid Data")
    public void testCreateStudent_ValidData() {
        // Arrange
        Student student = new Student("John Doe", "john@example.com", "081234567890");

        // Act & Assert
        assertDoesNotThrow(() -> {
            studentService.create(student);
            testStudentId = student.getStudentId();
            assertTrue(testStudentId > 0, "Student ID should be generated");
        });
    }

    @Test
    @Order(2)
    @DisplayName("Test Create Student - Missing Name")
    public void testCreateStudent_MissingName() {
        // Arrange
        Student student = new Student("", "test@example.com", "081234567890");

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            studentService.create(student);
        });

        assertEquals("Student name is required", exception.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("Test Create Student - Invalid Email")
    public void testCreateStudent_InvalidEmail() {
        // Arrange
        Student student = new Student("Jane Doe", "invalid-email", "081234567890");

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            studentService.create(student);
        });

        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Test Create Student - Invalid Phone")
    public void testCreateStudent_InvalidPhone() {
        // Arrange
        Student student = new Student("Jane Doe", "jane@example.com", "abc123");

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            studentService.create(student);
        });

        assertEquals("Invalid phone number format", exception.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Test Get All Students")
    public void testGetAllStudents() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            List<Student> students = studentService.getAll();
            assertNotNull(students);
            assertTrue(students.size() > 0, "Should have at least one student");
        });
    }

    @Test
    @Order(6)
    @DisplayName("Test Get Student By ID")
    public void testGetStudentById() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            Student student = studentService.getById(testStudentId);
            assertNotNull(student);
            assertEquals("John Doe", student.getStudentName());
            assertEquals("john@example.com", student.getEmail());
        });
    }

    @Test
    @Order(7)
    @DisplayName("Test Update Student - Valid Data")
    public void testUpdateStudent_ValidData() {
        // Arrange
        Student student = new Student("John Doe Updated", "john.updated@example.com", "082345678901");

        // Act & Assert
        assertDoesNotThrow(() -> {
            studentService.update(testStudentId, student);
            Student updated = studentService.getById(testStudentId);
            assertEquals("John Doe Updated", updated.getStudentName());
            assertEquals("john.updated@example.com", updated.getEmail());
        });
    }

    @Test
    @Order(8)
    @DisplayName("Test Update Student - Non-existent ID")
    public void testUpdateStudent_NonExistentId() {
        // Arrange
        Student student = new Student("Test", "test@example.com", "081234567890");

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            studentService.update(99999, student);
        });

        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    @Order(9)
    @DisplayName("Test Delete Student")
    public void testDeleteStudent() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            studentService.delete(testStudentId);

            // Verify deletion
            SQLException exception = assertThrows(SQLException.class, () -> {
                studentService.getById(testStudentId);
            });
            assertTrue(exception.getMessage().contains("not found"));
        });
    }

    @Test
    @Order(10)
    @DisplayName("Test Delete Student - Non-existent ID")
    public void testDeleteStudent_NonExistentId() {
        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            studentService.delete(99999);
        });

        assertTrue(exception.getMessage().contains("not found"));
    }
}