package com.indramakassar.controller;

import com.indramakassar.entity.Class;
import com.indramakassar.entity.Enrollment;
import com.indramakassar.entity.Student;
import com.indramakassar.service.ClassService;
import com.indramakassar.service.EnrollmentService;
import com.indramakassar.service.StudentService;
import com.indramakassar.view.EnrollmentView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentControllerMockitoTest {

    @Mock
    EnrollmentService enrollmentService;
    @Mock
    StudentService studentService;
    @Mock
    ClassService classService;

    EnrollmentView view;
    EnrollmentController controller;

    @BeforeEach
    void setup() throws Exception {
        // Stub initial data used by controller init
        when(studentService.getAll()).thenReturn(List.of(
                new Student(1, "Alice", "a@mail", "+1"),
                new Student(2, "Bob", null, null)
        ));
        when(classService.getAll()).thenReturn(List.of(
                new Class(10, "Math", null, null),
                new Class(20, "Science", null, null)
        ));
        when(enrollmentService.getAll()).thenReturn(List.of());

        view = new EnrollmentView();
        controller = new EnrollmentController(view, enrollmentService, studentService, classService);
    }

    @Test
    void listAllEnrollments_populates_table_from_service() throws Exception {
        Enrollment e1 = new Enrollment(1, 10);
        e1.setStudentName("Alice");
        e1.setClassName("Math");
        e1.setStatus("active");
        e1.setEnrolledAt(LocalDateTime.of(2024, 1, 2, 3, 4));

        Enrollment e2 = new Enrollment(2, 20);
        e2.setStudentName("Bob");
        e2.setClassName("Science");
        e2.setStatus("active");

        when(enrollmentService.getAll()).thenReturn(List.of(e1, e2));

        controller.listAllEnrollments();

        JTable table = view.getTableEnrollments();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        assertEquals(2, model.getRowCount());
        assertEquals(1, model.getValueAt(0, 0)); // studentId
        assertEquals("Alice", model.getValueAt(0, 1));
        assertEquals(10, model.getValueAt(0, 2)); // classId
        assertEquals("Math", model.getValueAt(0, 3));
    }

    @Test
    void clicking_enroll_calls_service_enroll_with_selected_ids() throws Exception {
        // Ensure combos have expected items from setup stubs
        // Select first student (id=1) and first class (id=10)
        view.getCmbStudent().setSelectedIndex(0);
        view.getCmbClass().setSelectedIndex(0);

        view.getBtnEnroll().doClick();

        verify(enrollmentService, times(1)).enroll(1, 10);
    }
}
