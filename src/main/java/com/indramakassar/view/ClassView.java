package com.indramakassar.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClassView extends JFrame {
    // Form components
    private JTextField txtId;
    private JTextField txtClassName;
    private JTextArea txtDescription;
    private JTextField txtInstructor;

    // Buttons
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;

    // Table
    private JTable tableClasses;
    private DefaultTableModel tableModel;

    // Status label
    private JLabel lblStatus;

    public ClassView() {
        setTitle("Class Management - SkillHub");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel (North)
        mainPanel.add(createFormPanel(), BorderLayout.NORTH);

        // Table Panel (Center)
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);

        // Status Panel (South)
        mainPanel.add(createStatusPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Class Form"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID field (hidden/readonly)
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(20);
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtId, gbc);

        // Class Name field
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Class Name: *"), gbc);
        gbc.gridx = 1;
        txtClassName = new JTextField(20);
        formPanel.add(txtClassName, gbc);

        // Description field (TextArea)
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescription = new JTextArea(3, 20);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        formPanel.add(scrollDesc, gbc);

        // Instructor field
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(new JLabel("Instructor:"), gbc);
        gbc.gridx = 1;
        txtInstructor = new JTextField(20);
        formPanel.add(txtInstructor, gbc);

        // Buttons panel
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(createButtonPanel(), gbc);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnAdd = new JButton("Add Class");
        btnAdd.setBackground(new Color(76, 175, 80));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setOpaque(true);
        btnAdd.setBorderPainted(true);

        btnUpdate = new JButton("Update Class");
        btnUpdate.setBackground(new Color(33, 150, 243));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.setOpaque(true);
        btnUpdate.setBorderPainted(true);

        btnDelete = new JButton("Delete Class");
        btnDelete.setBackground(new Color(244, 67, 54));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setOpaque(true);
        btnDelete.setBorderPainted(true);

        btnClear = new JButton("Clear Form");
        btnClear.setBackground(new Color(97, 97, 97));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        btnClear.setOpaque(true);
        btnClear.setBorderPainted(true);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        return buttonPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Classes List"));

        // Table model
        String[] columns = {"ID", "Class Name", "Description", "Instructor"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        tableClasses = new JTable(tableModel);
        tableClasses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableClasses.getTableHeader().setReorderingAllowed(false);

        // Set column widths
        tableClasses.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableClasses.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableClasses.getColumnModel().getColumn(2).setPreferredWidth(300);
        tableClasses.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(tableClasses);
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
            lblStatus.setForeground(new Color(211, 47, 47)); // Red
            lblStatus.setBackground(new Color(255, 235, 238)); // Light red
        } else {
            lblStatus.setForeground(new Color(56, 142, 60)); // Green
            lblStatus.setBackground(new Color(232, 245, 233)); // Light green
        }
        lblStatus.setOpaque(true);

        // Clear message after 5 seconds
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
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtClassName() { return txtClassName; }
    public JTextArea getTxtDescription() { return txtDescription; }
    public JTextField getTxtInstructor() { return txtInstructor; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JTable getTableClasses() { return tableClasses; }
}