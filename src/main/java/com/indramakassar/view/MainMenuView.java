package com.indramakassar.view;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {
    private JButton btnStudents;
    private JButton btnClasses;
    private JButton btnEnrollments;
    private JButton btnExit;

    public MainMenuView() {
        setTitle("SkillHub Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(240, 240, 240));
        JLabel titleLabel = new JLabel("SkillHub Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 150, 243));
        titlePanel.add(titleLabel);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Training Course Management");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);

        JPanel subtitlePanel = new JPanel();
        subtitlePanel.setBackground(new Color(240, 240, 240));
        subtitlePanel.add(subtitleLabel);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(subtitlePanel, BorderLayout.SOUTH);

        // Menu panel
        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 10, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        menuPanel.setBackground(new Color(240, 240, 240));

        // Create buttons
        btnStudents = createMenuButton("👤 Student Management", new Color(76, 175, 80));
        btnClasses = createMenuButton("📚 Class Management", new Color(33, 150, 243));
        btnEnrollments = createMenuButton("📝 Enrollment Management", new Color(255, 152, 0));
        btnExit = createMenuButton("🚪 Exit", new Color(158, 158, 158));

        menuPanel.add(btnStudents);
        menuPanel.add(btnClasses);
        menuPanel.add(btnEnrollments);
        menuPanel.add(btnExit);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 240, 240));
        JLabel footerLabel = new JLabel("© 2024 SkillHub - Programmer Certification Project");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        footerLabel.setForeground(Color.GRAY);
        footerPanel.add(footerLabel);

        // Add to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(menuPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createMenuButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(300, 50));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    // Getters for controller access
    public JButton getBtnStudents() { return btnStudents; }
    public JButton getBtnClasses() { return btnClasses; }
    public JButton getBtnEnrollments() { return btnEnrollments; }
    public JButton getBtnExit() { return btnExit; }
}