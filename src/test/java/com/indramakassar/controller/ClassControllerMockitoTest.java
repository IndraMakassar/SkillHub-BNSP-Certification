package com.indramakassar.controller;

import com.indramakassar.entity.Class;
import com.indramakassar.service.ClassService;
import com.indramakassar.view.ClassView;
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
class ClassControllerMockitoTest {

    @Mock
    ClassService service;

    ClassView view;
    ClassController controller;

    @BeforeEach
    void setup() throws Exception {
        when(service.getAll()).thenReturn(List.of());
        view = new ClassView();
        controller = new ClassController(view, service);
    }

    @Test
    void listClasses_populates_table_from_service() throws Exception {
        when(service.getAll()).thenReturn(List.of(
                new Class(1, "Beginner Java", "Intro course", "Alice"),
                new Class(2, "Advanced Java", null, null)
        ));

        controller.listClasses();

        JTable table = view.getTableClasses();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        assertEquals(2, model.getRowCount());
        assertEquals("Beginner Java", model.getValueAt(0, 1));
        assertEquals("Advanced Java", model.getValueAt(1, 1));
    }

    @Test
    void clicking_add_calls_service_create_with_form_values() throws Exception {
        view.getTxtClassName().setText("Databases 101");
        view.getTxtDescription().setText("Basics of SQL");
        view.getTxtInstructor().setText("Bob");

        view.getBtnAdd().doClick();

        ArgumentCaptor<Class> captor = ArgumentCaptor.forClass(Class.class);
        verify(service, times(1)).create(captor.capture());
        Class c = captor.getValue();
        assertEquals("Databases 101", c.getClassName());
        assertEquals("Basics of SQL", c.getDescription());
        assertEquals("Bob", c.getInstructor());
    }
}
