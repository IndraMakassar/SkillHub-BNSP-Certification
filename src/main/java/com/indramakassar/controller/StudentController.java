package com.indramakassar.controller;

import com.indramakassar.entity.Student;
import com.indramakassar.service.StudentService;
import com.indramakassar.view.StudentView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class StudentController {
    private final StudentView view;
    private final StudentService service;

    public StudentController(StudentView view) {
        this.view = view;
        this.service = new StudentService();
        initController();
    }

    private void initController() {
        // Load initial data
        listStudents();

        // Add button listeners
        view.getBtnAdd().addActionListener(e -> createStudent());
        view.getBtnUpdate().addActionListener(e -> updateStudent());
        view.getBtnDelete().addActionListener(e -> deleteStudent());
        view.getBtnClear().addActionListener(e -> clearForm());

        // Table selection listener
        view.getTableStudents().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedStudent();
            }
        });
    }

    // List all students in table
    public void listStudents() {
        try {
            List<Student> students = service.getAll();
            DefaultTableModel model = (DefaultTableModel) view.getTableStudents().getModel();
            model.setRowCount(0); // Clear table

            for (Student student : students) {
                Object[] row = {
                        student.getStudentId(),
                        student.getStudentName(),
                        student.getEmail(),
                        student.getPhoneNumber()
                };
                model.addRow(row);
            }
        } catch (SQLException ex) {
            showError("Error loading students: " + ex.getMessage());
        }
    }

    // Create new student
    private void createStudent() {
        try {
            Student student = getStudentFromForm();
            service.create(student);
            showSuccess("Student created successfully!");
            clearForm();
            listStudents();
        } catch (SQLException ex) {
            showError("Error creating student: " + ex.getMessage());
        }
    }

    // Update existing student
    private void updateStudent() {
        try {
            String idText = view.getTxtId().getText().trim();
            if (idText.isEmpty()) {
                showError("Please select a student to update");
                return;
            }

            int id = Integer.parseInt(idText);
            Student student = getStudentFromForm();
            service.update(id, student);
            showSuccess("Student updated successfully!");
            clearForm();
            listStudents();
        } catch (NumberFormatException ex) {
            showError("Invalid student ID");
        } catch (SQLException ex) {
            showError("Error updating student: " + ex.getMessage());
        }
    }

    // Delete student
    private void deleteStudent() {
        try {
            String idText = view.getTxtId().getText().trim();
            if (idText.isEmpty()) {
                showError("Please select a student to delete");
                return;
            }

            int id = Integer.parseInt(idText);
            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Are you sure you want to delete this student?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.delete(id);
                showSuccess("Student deleted successfully!");
                clearForm();
                listStudents();
            }
        } catch (NumberFormatException ex) {
            showError("Invalid student ID");
        } catch (SQLException ex) {
            showError("Error deleting student: " + ex.getMessage());
        }
    }

    // Load selected student from table to form
    private void loadSelectedStudent() {
        int selectedRow = view.getTableStudents().getSelectedRow();
        if (selectedRow >= 0) {
            view.getTxtId().setText(view.getTableStudents().getValueAt(selectedRow, 0).toString());
            view.getTxtName().setText(view.getTableStudents().getValueAt(selectedRow, 1).toString());

            Object email = view.getTableStudents().getValueAt(selectedRow, 2);
            view.getTxtEmail().setText(email != null ? email.toString() : "");

            Object phone = view.getTableStudents().getValueAt(selectedRow, 3);
            view.getTxtPhone().setText(phone != null ? phone.toString() : "");
        }
    }

    // Get student object from form fields
    private Student getStudentFromForm() {
        String name = view.getTxtName().getText().trim();
        String email = view.getTxtEmail().getText().trim();
        String phone = view.getTxtPhone().getText().trim();

        return new Student(name, email.isEmpty() ? null : email, phone.isEmpty() ? null : phone);
    }

    // Clear form fields
    private void clearForm() {
        view.getTxtId().setText("");
        view.getTxtName().setText("");
        view.getTxtEmail().setText("");
        view.getTxtPhone().setText("");
        view.getTableStudents().clearSelection();
    }

    // Show success message
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(view, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // Show error message
    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}