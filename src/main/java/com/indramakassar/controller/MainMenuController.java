package com.indramakassar.controller;

import com.indramakassar.view.ClassView;
import com.indramakassar.view.EnrollmentView;
import com.indramakassar.view.MainMenuView;
import com.indramakassar.view.StudentView;

import javax.swing.*;

public class MainMenuController {
    private final MainMenuView view;

    public MainMenuController(MainMenuView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // Student Management button
        view.getBtnStudents().addActionListener(e -> openStudentManagement());

        // Class Management button
        view.getBtnClasses().addActionListener(e -> openClassManagement());

        // Enrollment Management button
        view.getBtnEnrollments().addActionListener(e -> openEnrollmentManagement());

        // Exit button
        view.getBtnExit().addActionListener(e -> exitApplication());
    }

    private void openStudentManagement() {
        StudentView studentView = new StudentView();
        new StudentController(studentView);
        studentView.setVisible(true);
    }

    private void openClassManagement() {
        ClassView classView = new ClassView();
        new ClassController(classView);
        classView.setVisible(true);
    }

    private void openEnrollmentManagement() {
        EnrollmentView enrollmentView = new EnrollmentView();
        new EnrollmentController(enrollmentView);
        enrollmentView.setVisible(true);
    }

    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}