package com.indramakassar.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentView extends JFrame {
    // Form components
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtPhone;

    // Buttons
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;

    // Table
    private JTable tableStudents;
    private DefaultTableModel tableModel;

    // Status label
    private JLabel lblStatus;

    public StudentView() {
        setTitle("Student Management - SkillHub");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Form"));
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

        // Name field
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Name: *"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(20);
        formPanel.add(txtName, gbc);

        // Email field
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);

        // Phone field
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        txtPhone = new JTextField(20);
        formPanel.add(txtPhone, gbc);

        // Buttons panel
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(createButtonPanel(), gbc);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnAdd = new JButton("Add Student");
        btnAdd.setBackground(new Color(76, 175, 80));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);

        btnUpdate = new JButton("Update Student");
        btnUpdate.setBackground(new Color(33, 150, 243));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);

        btnDelete = new JButton("Delete Student");
        btnDelete.setBackground(new Color(244, 67, 54));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);

        btnClear = new JButton("Clear Form");
        btnClear.setBackground(new Color(158, 158, 158));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        return buttonPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Students List"));

        // Table model
        String[] columns = {"ID", "Name", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        tableStudents = new JTable(tableModel);
        tableStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableStudents.getTableHeader().setReorderingAllowed(false);

        // Set column widths
        tableStudents.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableStudents.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableStudents.getColumnModel().getColumn(2).setPreferredWidth(200);
        tableStudents.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(tableStudents);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    // Getters for controller access
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JTextField getTxtPhone() { return txtPhone; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JTable getTableStudents() { return tableStudents; }
}