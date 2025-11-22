package com.indramakassar.controller;

import com.indramakassar.entity.Class;
import com.indramakassar.service.ClassService;
import com.indramakassar.view.ClassView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class ClassController {
    private final ClassView view;
    private final ClassService service;

    public ClassController(ClassView view) {
        this.view = view;
        this.service = new ClassService();
        initController();
    }

    private void initController() {
        // Load initial data
        listClasses();

        // Add button listeners
        view.getBtnAdd().addActionListener(e -> createClass());
        view.getBtnUpdate().addActionListener(e -> updateClass());
        view.getBtnDelete().addActionListener(e -> deleteClass());
        view.getBtnClear().addActionListener(e -> clearForm());

        // Table selection listener
        view.getTableClasses().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedClass();
            }
        });
    }

    // List all classes in table
    public void listClasses() {
        try {
            List<Class> classes = service.getAll();
            DefaultTableModel model = (DefaultTableModel) view.getTableClasses().getModel();
            model.setRowCount(0); // Clear table

            for (Class classObj : classes) {
                Object[] row = {
                        classObj.getClassId(),
                        classObj.getClassName(),
                        classObj.getDescription(),
                        classObj.getInstructor()
                };
                model.addRow(row);
            }
        } catch (SQLException ex) {
            showError("Error loading classes: " + ex.getMessage());
        }
    }

    // Create new class
    private void createClass() {
        try {
            Class classObj = getClassFromForm();
            service.create(classObj);
            showSuccess("Class created successfully!");
            clearForm();
            listClasses();
        } catch (SQLException ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    // Update existing class
    private void updateClass() {
        try {
            String idText = view.getTxtId().getText().trim();
            if (idText.isEmpty()) {
                showError("Please select a class to update");
                return;
            }

            int id = Integer.parseInt(idText);
            Class classObj = getClassFromForm();
            service.update(id, classObj);
            showSuccess("Class updated successfully!");
            clearForm();
            listClasses();
        } catch (NumberFormatException ex) {
            showError("Invalid class ID");
        } catch (SQLException ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    // Delete class
    private void deleteClass() {
        try {
            String idText = view.getTxtId().getText().trim();
            if (idText.isEmpty()) {
                showError("Please select a class to delete");
                return;
            }

            int id = Integer.parseInt(idText);
            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Are you sure you want to delete this class?\nAll enrollments will also be deleted.",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.delete(id);
                showSuccess("Class deleted successfully!");
                clearForm();
                listClasses();
            }
        } catch (NumberFormatException ex) {
            showError("Invalid class ID");
        } catch (SQLException ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    // Load selected class from table to form
    private void loadSelectedClass() {
        int selectedRow = view.getTableClasses().getSelectedRow();
        if (selectedRow >= 0) {
            view.getTxtId().setText(view.getTableClasses().getValueAt(selectedRow, 0).toString());
            view.getTxtClassName().setText(view.getTableClasses().getValueAt(selectedRow, 1).toString());

            Object description = view.getTableClasses().getValueAt(selectedRow, 2);
            view.getTxtDescription().setText(description != null ? description.toString() : "");

            Object instructor = view.getTableClasses().getValueAt(selectedRow, 3);
            view.getTxtInstructor().setText(instructor != null ? instructor.toString() : "");
        }
    }

    // Get class object from form fields
    private Class getClassFromForm() {
        String className = view.getTxtClassName().getText().trim();
        String description = view.getTxtDescription().getText().trim();
        String instructor = view.getTxtInstructor().getText().trim();

        return new Class(
                className,
                description.isEmpty() ? null : description,
                instructor.isEmpty() ? null : instructor
        );
    }

    // Clear form fields
    private void clearForm() {
        view.getTxtId().setText("");
        view.getTxtClassName().setText("");
        view.getTxtDescription().setText("");
        view.getTxtInstructor().setText("");
        view.getTableClasses().clearSelection();
    }

    // Show success message
    private void showSuccess(String message) {
        view.setStatusMessage(message, false);
    }

    // Show error message
    private void showError(String message) {
        view.setStatusMessage(message, true);
    }
}