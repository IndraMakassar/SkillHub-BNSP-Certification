package com.indramakassar.controller;

import com.indramakassar.entity.Student;
import com.indramakassar.service.StudentService;
import com.indramakassar.view.StudentView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerMockitoTest {

    @Mock
    StudentService service;

    StudentView view;
    StudentController controller;

    @BeforeEach
    void setup() throws Exception {
        // Minimal stub: list is empty on initial load
        when(service.getAll()).thenReturn(List.of());
        view = new StudentView();
        controller = new StudentController(view, service);
    }

    @Test
    void listStudents_populates_table_from_service() throws Exception {
        // Arrange a list and trigger refresh
        when(service.getAll()).thenReturn(List.of(
                new Student(1, "Alice", "a@mail", "+1"),
                new Student(2, "Bob", null, null)
        ));

        controller.listStudents();

        JTable table = view.getTableStudents();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        assertEquals(2, model.getRowCount());
        assertEquals("Alice", model.getValueAt(0, 1));
        assertEquals("Bob", model.getValueAt(1, 1));
    }

    @Test
    void clicking_add_calls_service_create_with_form_values() throws Exception {
        // Given valid form inputs
        view.getTxtName().setText("Charlie");
        view.getTxtEmail().setText("c@mail");
        view.getTxtPhone().setText("+62");

        // When clicking the add button
        view.getBtnAdd().doClick();

        // Then service.create is invoked with a Student matching the form
        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(service, times(1)).create(captor.capture());
        Student s = captor.getValue();
        assertEquals("Charlie", s.getStudentName());
        assertEquals("c@mail", s.getEmail());
        assertEquals("+62", s.getPhoneNumber());
    }
}
