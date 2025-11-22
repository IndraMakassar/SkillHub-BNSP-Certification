package com.indramakassar;


import com.indramakassar.controller.StudentController;
import com.indramakassar.database.DatabaseInitializer;
import com.indramakassar.view.StudentView;

import javax.swing.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        // Initialize database
        DatabaseInitializer.initialize();

        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            StudentView view = new StudentView();
            new StudentController(view);
            view.setVisible(true);
        });
    }

}
