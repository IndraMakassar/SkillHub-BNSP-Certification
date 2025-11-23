package com.indramakassar.service;

import com.indramakassar.entity.Student;
import com.indramakassar.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceMockitoTest {

    @Mock
    StudentRepository repo;

    StudentService service;

    @BeforeEach
    void init() {
        service = new StudentService(repo);
    }

    @Test
    void create_calls_insert_when_valid() throws Exception {
        Student s = new Student("Alice", "alice@mail", "+62");

        service.create(s);

        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(repo, times(1)).insert(captor.capture());
        Student passed = captor.getValue();
        assertEquals("Alice", passed.getStudentName());
        assertEquals("alice@mail", passed.getEmail());
        assertEquals("+62", passed.getPhoneNumber());
    }

    @Test
    void create_throws_on_empty_name() throws Exception {
        Student s = new Student(" ", "a@mail", "+62");
        SQLException ex = assertThrows(SQLException.class, () -> service.create(s));
        assertEquals("Student name is required", ex.getMessage());
        verify(repo, never()).insert(any());
    }

    @Test
    void update_throws_when_not_found() throws Exception {
        when(repo.findById(99)).thenReturn(null);
        Student s = new Student("Bob", null, null);
        SQLException ex = assertThrows(SQLException.class, () -> service.update(99, s));
        assertTrue(ex.getMessage().contains("not found"));
        verify(repo, never()).update(any());
    }

    @Test
    void delete_throws_when_not_found() throws Exception {
        when(repo.findById(77)).thenReturn(null);
        SQLException ex = assertThrows(SQLException.class, () -> service.delete(77));
        assertTrue(ex.getMessage().contains("not found"));
        verify(repo, never()).delete(anyInt());
    }

    @Test
    void getById_throws_when_not_found() throws Exception {
        when(repo.findById(55)).thenReturn(null);
        SQLException ex = assertThrows(SQLException.class, () -> service.getById(55));
        assertTrue(ex.getMessage().contains("not found"));
    }
}
