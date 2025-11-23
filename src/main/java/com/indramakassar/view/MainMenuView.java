package com.indramakassar.view;

import com.indramakassar.controller.ClassController;
import com.indramakassar.controller.EnrollmentController;
import com.indramakassar.controller.StudentController;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {

    public MainMenuView() {
        setTitle("SkillHub Data Management System (Java Swing)");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JTabbedPane tabbedPane = new JTabbedPane();

        // 1. Student Management Tab
        StudentView studentView = new StudentView();
        new StudentController(studentView); // Initialize Controller
        tabbedPane.addTab("👥 Peserta", studentView);

        // 2. Class Management Tab
        ClassView classView = new ClassView();
        new ClassController(classView); // Initialize Controller
        tabbedPane.addTab("📚 Kelas", classView);

        // 3. Enrollment Management Tab
        EnrollmentView enrollmentView = new EnrollmentView();
        new EnrollmentController(enrollmentView); // Initialize Controller
        tabbedPane.addTab("📝 Pendaftaran", enrollmentView);

        add(tabbedPane);
    }

    public static void main(String[] args) {
        // Use the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new MainMenuView().setVisible(true);
        });
    }
}