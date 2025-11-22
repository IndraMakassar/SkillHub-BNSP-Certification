package com.indramakassar.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EnrollmentView extends JFrame {
    // Form components
    private JComboBox<String> cmbStudent;
    private JComboBox<String> cmbClass;
    private JComboBox<String> cmbFilterType;
    private JComboBox<String> cmbFilterValue;

    // Buttons
    private JButton btnEnroll;
    private JButton btnDrop;
    private JButton btnRefresh;
    private JButton btnApplyFilter;

    // Table
    private JTable tableEnrollments;
    private DefaultTableModel tableModel;

    // Status label
    private JLabel lblStatus;

    public EnrollmentView() {
        setTitle("Enrollment Management - SkillHub");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel with enrollment form and filter
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        topPanel.add(createEnrollmentPanel());
        topPanel.add(createFilterPanel());

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Table Panel (Center)
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);

        // Status Panel (South)
        mainPanel.add(createStatusPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createEnrollmentPanel() {
        JPanel enrollPanel = new JPanel(new GridBagLayout());
        enrollPanel.setBorder(BorderFactory.createTitledBorder("Enroll Student to Class"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Student ComboBox
        gbc.gridx = 0; gbc.gridy = 0;
        enrollPanel.add(new JLabel("Select Student: *"), gbc);
        gbc.gridx = 1;
        cmbStudent = new JComboBox<>();
        cmbStudent.setPreferredSize(new Dimension(250, 25));
        enrollPanel.add(cmbStudent, gbc);

        // Class ComboBox
        gbc.gridx = 0; gbc.gridy = 1;
        enrollPanel.add(new JLabel("Select Class: *"), gbc);
        gbc.gridx = 1;
        cmbClass = new JComboBox<>();
        cmbClass.setPreferredSize(new Dimension(250, 25));
        enrollPanel.add(cmbClass, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnEnroll = new JButton("Enroll");
        btnEnroll.setBackground(new Color(76, 175, 80));
        btnEnroll.setForeground(Color.WHITE);
        btnEnroll.setFocusPainted(false);
        btnEnroll.setOpaque(true);

        btnDrop = new JButton("Drop");
        btnDrop.setBackground(new Color(244, 67, 54));
        btnDrop.setForeground(Color.WHITE);
        btnDrop.setFocusPainted(false);
        btnDrop.setOpaque(true);

        buttonPanel.add(btnEnroll);
        buttonPanel.add(btnDrop);
        enrollPanel.add(buttonPanel, gbc);

        return enrollPanel;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter Enrollments"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Filter Type ComboBox
        gbc.gridx = 0; gbc.gridy = 0;
        filterPanel.add(new JLabel("Filter By:"), gbc);
        gbc.gridx = 1;
        cmbFilterType = new JComboBox<>(new String[]{"All Enrollments", "By Student", "By Class"});
        cmbFilterType.setPreferredSize(new Dimension(250, 25));
        filterPanel.add(cmbFilterType, gbc);

        // Filter Value ComboBox
        gbc.gridx = 0; gbc.gridy = 1;
        filterPanel.add(new JLabel("Select:"), gbc);
        gbc.gridx = 1;
        cmbFilterValue = new JComboBox<>();
        cmbFilterValue.setPreferredSize(new Dimension(250, 25));
        cmbFilterValue.setEnabled(false);
        filterPanel.add(cmbFilterValue, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnApplyFilter = new JButton("Apply Filter");
        btnApplyFilter.setBackground(new Color(33, 150, 243));
        btnApplyFilter.setForeground(Color.WHITE);
        btnApplyFilter.setFocusPainted(false);
        btnApplyFilter.setOpaque(true);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(97, 97, 97));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setOpaque(true);

        buttonPanel.add(btnApplyFilter);
        buttonPanel.add(btnRefresh);
        filterPanel.add(buttonPanel, gbc);

        return filterPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Enrollments List"));

        // Table model
        String[] columns = {"Student ID", "Student Name", "Class ID", "Class Name", "Status", "Enrolled At"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableEnrollments = new JTable(tableModel);
        tableEnrollments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableEnrollments.getTableHeader().setReorderingAllowed(false);

        // Set column widths
        tableEnrollments.getColumnModel().getColumn(0).setPreferredWidth(80);
        tableEnrollments.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableEnrollments.getColumnModel().getColumn(2).setPreferredWidth(80);
        tableEnrollments.getColumnModel().getColumn(3).setPreferredWidth(150);
        tableEnrollments.getColumnModel().getColumn(4).setPreferredWidth(80);
        tableEnrollments.getColumnModel().getColumn(5).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(tableEnrollments);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStatus.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        statusPanel.add(lblStatus, BorderLayout.CENTER);
        return statusPanel;
    }

    // Method to set status message
    public void setStatusMessage(String message, boolean isError) {
        lblStatus.setText(message);
        if (isError) {
            lblStatus.setForeground(new Color(211, 47, 47));
            lblStatus.setBackground(new Color(255, 235, 238));
        } else {
            lblStatus.setForeground(new Color(56, 142, 60));
            lblStatus.setBackground(new Color(232, 245, 233));
        }
        lblStatus.setOpaque(true);

        Timer timer = new Timer(5000, e -> {
            lblStatus.setText(" ");
            lblStatus.setOpaque(false);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void clearStatusMessage() {
        lblStatus.setText(" ");
        lblStatus.setOpaque(false);
    }

    // Getters for controller access
    public JComboBox<String> getCmbStudent() { return cmbStudent; }
    public JComboBox<String> getCmbClass() { return cmbClass; }
    public JComboBox<String> getCmbFilterType() { return cmbFilterType; }
    public JComboBox<String> getCmbFilterValue() { return cmbFilterValue; }
    public JButton getBtnEnroll() { return btnEnroll; }
    public JButton getBtnDrop() { return btnDrop; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnApplyFilter() { return btnApplyFilter; }
    public JTable getTableEnrollments() { return tableEnrollments; }
}