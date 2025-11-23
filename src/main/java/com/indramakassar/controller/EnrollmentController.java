package com.indramakassar.controller;

import com.indramakassar.entity.Class;
import com.indramakassar.entity.Enrollment;
import com.indramakassar.entity.Student;
import com.indramakassar.service.ClassService;
import com.indramakassar.service.EnrollmentService;
import com.indramakassar.service.StudentService;
import com.indramakassar.view.EnrollmentView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrollmentController {
    private final EnrollmentView view;
    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final ClassService classService;

    // Maps to store ID mappings for ComboBoxes
    private Map<String, Integer> studentMap;
    private Map<String, Integer> classMap;

    public EnrollmentController(EnrollmentView view) {
        this.view = view;
        this.enrollmentService = new EnrollmentService();
        this.studentService = new StudentService();
        this.classService = new ClassService();
        this.studentMap = new HashMap<>();
        this.classMap = new HashMap<>();

        initController();
    }

    // Constructor for dependency injection (testing)
    public EnrollmentController(EnrollmentView view, EnrollmentService enrollmentService,
                                StudentService studentService, ClassService classService) {
        this.view = view;
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.classService = classService;
        this.studentMap = new HashMap<>();
        this.classMap = new HashMap<>();
        initController();
    }

    private void initController() {
        // Load initial data
        loadStudentsComboBox();
        loadClassesComboBox();
        listAllEnrollments();

        // Button listeners
        view.getBtnEnroll().addActionListener(e -> enrollStudent());
        view.getBtnDrop().addActionListener(e -> dropEnrollment());
        view.getBtnRefresh().addActionListener(e -> listAllEnrollments());
        view.getBtnApplyFilter().addActionListener(e -> applyFilter());

        // Filter type change listener
        view.getCmbFilterType().addActionListener(e -> onFilterTypeChanged());

        // Table selection listener
        view.getTableEnrollments().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedEnrollment();
            }
        });
    }

    // Load students into ComboBox
    private void loadStudentsComboBox() {
        try {
            List<Student> students = studentService.getAll();
            view.getCmbStudent().removeAllItems();
            studentMap.clear();

            for (Student student : students) {
                String display = student.getStudentId() + " - " + student.getStudentName();
                view.getCmbStudent().addItem(display);
                studentMap.put(display, student.getStudentId());
            }
        } catch (SQLException ex) {
            showError("Error loading students: " + ex.getMessage());
        }
    }

    // Load classes into ComboBox
    private void loadClassesComboBox() {
        try {
            List<Class> classes = classService.getAll();
            view.getCmbClass().removeAllItems();
            classMap.clear();

            for (Class classObj : classes) {
                String display = classObj.getClassId() + " - " + classObj.getClassName();
                view.getCmbClass().addItem(display);
                classMap.put(display, classObj.getClassId());
            }
        } catch (SQLException ex) {
            showError("Error loading classes: " + ex.getMessage());
        }
    }

    // List all enrollments
    public void listAllEnrollments() {
        try {
            List<Enrollment> enrollments = enrollmentService.getAll();
            populateTable(enrollments);
        } catch (SQLException ex) {
            showError("Error loading enrollments: " + ex.getMessage());
        }
    }

    // Enroll student to class
    private void enrollStudent() {
        try {
            String selectedStudent = (String) view.getCmbStudent().getSelectedItem();
            String selectedClass = (String) view.getCmbClass().getSelectedItem();

            if (selectedStudent == null || selectedClass == null) {
                showError("Please select both student and class");
                return;
            }

            int studentId = studentMap.get(selectedStudent);
            int classId = classMap.get(selectedClass);

            enrollmentService.enroll(studentId, classId);
            showSuccess("Student enrolled successfully!");
            listAllEnrollments();
        } catch (SQLException ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    // Drop enrollment
    private void dropEnrollment() {
        try {
            int selectedRow = view.getTableEnrollments().getSelectedRow();
            if (selectedRow < 0) {
                showError("Please select an enrollment to drop");
                return;
            }

            int studentId = (int) view.getTableEnrollments().getValueAt(selectedRow, 0);
            int classId = (int) view.getTableEnrollments().getValueAt(selectedRow, 2);

            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Are you sure you want to drop this enrollment?",
                    "Confirm Drop",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                enrollmentService.drop(studentId, classId);
                showSuccess("Enrollment dropped successfully!");
                listAllEnrollments();
            }
        } catch (SQLException ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    // Handle filter type change
    private void onFilterTypeChanged() {
        String filterType = (String) view.getCmbFilterType().getSelectedItem();
        view.getCmbFilterValue().removeAllItems();

        if (filterType.equals("All Enrollments")) {
            view.getCmbFilterValue().setEnabled(false);
            listAllEnrollments();
        } else if (filterType.equals("By Student")) {
            view.getCmbFilterValue().setEnabled(true);
            try {
                List<Student> students = studentService.getAll();
                for (Student student : students) {
                    String display = student.getStudentId() + " - " + student.getStudentName();
                    view.getCmbFilterValue().addItem(display);
                }
            } catch (SQLException ex) {
                showError("Error loading students: " + ex.getMessage());
            }
        } else if (filterType.equals("By Class")) {
            view.getCmbFilterValue().setEnabled(true);
            try {
                List<Class> classes = classService.getAll();
                for (Class classObj : classes) {
                    String display = classObj.getClassId() + " - " + classObj.getClassName();
                    view.getCmbFilterValue().addItem(display);
                }
            } catch (SQLException ex) {
                showError("Error loading classes: " + ex.getMessage());
            }
        }
    }

    // Apply filter
    private void applyFilter() {
        String filterType = (String) view.getCmbFilterType().getSelectedItem();

        if (filterType.equals("All Enrollments")) {
            listAllEnrollments();
            return;
        }

        String selectedValue = (String) view.getCmbFilterValue().getSelectedItem();
        if (selectedValue == null) {
            showError("Please select a filter value");
            return;
        }

        try {
            int id = Integer.parseInt(selectedValue.split(" - ")[0]);
            List<Enrollment> enrollments;

            if (filterType.equals("By Student")) {
                enrollments = enrollmentService.getClassesByStudentId(id);
            } else {
                enrollments = enrollmentService.getStudentsByClassId(id);
            }

            populateTable(enrollments);
            showSuccess("Filter applied: " + enrollments.size() + " records found");
        } catch (SQLException ex) {
            showError("Error applying filter: " + ex.getMessage());
        }
    }

    // Load selected enrollment to form
    private void loadSelectedEnrollment() {
        int selectedRow = view.getTableEnrollments().getSelectedRow();
        if (selectedRow >= 0) {
            int studentId = (int) view.getTableEnrollments().getValueAt(selectedRow, 0);
            String studentName = view.getTableEnrollments().getValueAt(selectedRow, 1).toString();
            int classId = (int) view.getTableEnrollments().getValueAt(selectedRow, 2);
            String className = view.getTableEnrollments().getValueAt(selectedRow, 3).toString();

            String studentDisplay = studentId + " - " + studentName;
            String classDisplay = classId + " - " + className;

            view.getCmbStudent().setSelectedItem(studentDisplay);
            view.getCmbClass().setSelectedItem(classDisplay);
        }
    }

    // Populate table with enrollments
    private void populateTable(List<Enrollment> enrollments) {
        DefaultTableModel model = (DefaultTableModel) view.getTableEnrollments().getModel();
        model.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Enrollment enrollment : enrollments) {
            Object[] row = {
                    enrollment.getStudentId(),
                    enrollment.getStudentName(),
                    enrollment.getClassId(),
                    enrollment.getClassName(),
                    enrollment.getStatus(),
                    enrollment.getEnrolledAt() != null ? enrollment.getEnrolledAt().format(formatter) : ""
            };
            model.addRow(row);
        }
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