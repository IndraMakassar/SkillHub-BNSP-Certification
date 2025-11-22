package com.indramakassar.service;

import com.indramakassar.database.DatabaseInitializer;
import com.indramakassar.entity.Class;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClassServiceTest {

    private static ClassService classService;
    private static int testClassId;

    @BeforeAll
    public static void setUp() {
        DatabaseInitializer.initialize();
        classService = new ClassService();
    }

    @Test
    @Order(1)
    @DisplayName("Test Create Class - Valid Data")
    public void testCreateClass_ValidData() {
        // Arrange
        Class classObj = new Class("Java Programming", "Learn Java basics", "Dr. Smith");

        // Act & Assert
        assertDoesNotThrow(() -> {
            classService.create(classObj);
            testClassId = classObj.getClassId();
            assertTrue(testClassId > 0, "Class ID should be generated");
        });
    }

    @Test
    @Order(2)
    @DisplayName("Test Create Class - Missing Name")
    public void testCreateClass_MissingName() {
        // Arrange
        Class classObj = new Class("", "Description", "Instructor");

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            classService.create(classObj);
        });

        assertEquals("Class name is required", exception.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("Test Create Class - Name Too Long")
    public void testCreateClass_NameTooLong() {
        // Arrange
        String longName = "A".repeat(101);
        Class classObj = new Class(longName, "Description", "Instructor");

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            classService.create(classObj);
        });

        assertEquals("Class name must be 100 characters or less", exception.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Test Get All Classes")
    public void testGetAllClasses() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            List<Class> classes = classService.getAll();
            assertNotNull(classes);
            assertTrue(classes.size() > 0, "Should have at least one class");
        });
    }

    @Test
    @Order(5)
    @DisplayName("Test Get Class By ID")
    public void testGetClassById() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            Class classObj = classService.getById(testClassId);
            assertNotNull(classObj);
            assertEquals("Java Programming", classObj.getClassName());
            assertEquals("Dr. Smith", classObj.getInstructor());
        });
    }

    @Test
    @Order(6)
    @DisplayName("Test Update Class - Valid Data")
    public void testUpdateClass_ValidData() {
        // Arrange
        Class classObj = new Class("Advanced Java Programming", "Learn advanced Java", "Dr. Johnson");

        // Act & Assert
        assertDoesNotThrow(() -> {
            classService.update(testClassId, classObj);
            Class updated = classService.getById(testClassId);
            assertEquals("Advanced Java Programming", updated.getClassName());
            assertEquals("Dr. Johnson", updated.getInstructor());
        });
    }

    @Test
    @Order(7)
    @DisplayName("Test Update Class - Non-existent ID")
    public void testUpdateClass_NonExistentId() {
        // Arrange
        Class classObj = new Class("Test", "Test", "Test");

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            classService.update(99999, classObj);
        });

        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    @Order(8)
    @DisplayName("Test Delete Class")
    public void testDeleteClass() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            classService.delete(testClassId);

            // Verify deletion
            SQLException exception = assertThrows(SQLException.class, () -> {
                classService.getById(testClassId);
            });
            assertTrue(exception.getMessage().contains("not found"));
        });
    }

    @Test
    @Order(9)
    @DisplayName("Test Delete Class - Non-existent ID")
    public void testDeleteClass_NonExistentId() {
        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            classService.delete(99999);
        });

        assertTrue(exception.getMessage().contains("not found"));
    }
}