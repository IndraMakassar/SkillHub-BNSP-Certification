package com.indramakassar;

import com.indramakassar.database.DatabaseInitializer;
import com.indramakassar.view.MainMenuView;

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
            MainMenuView view = new MainMenuView();
            view.setVisible(true);
        });
    }

}
