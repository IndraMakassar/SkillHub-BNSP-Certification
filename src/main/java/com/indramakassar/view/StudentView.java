package com.indramakassar.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentView extends JPanel {
    private JTextField txtId, txtName, txtEmail, txtPhone;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable tableStudents;
    private JLabel lblStatus;

    public StudentView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Input Form Panel ---
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.NORTH);

        // --- Table Panel ---
        tableStudents = createStudentsTable();
        tableStudents.setName("student.table");
        JScrollPane scrollPane = new JScrollPane(tableStudents);
        add(scrollPane, BorderLayout.CENTER);

        // --- Status Panel ---
        lblStatus = new JLabel("Ready.");
        lblStatus.setName("student.lblStatus");
        lblStatus.setBorder(BorderFactory.createEtchedBorder());
        add(lblStatus, BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Manajemen Data Peserta"));

        // Left side: Labels and Text Fields
        JPanel inputGrid = new JPanel(new GridLayout(4, 2, 5, 5));

        txtId = new JTextField();
        txtId.setName("student.txtId");
        txtId.setEditable(false);
        txtId.setEnabled(false);
        txtName = new JTextField();
        txtName.setName("student.txtName");
        txtEmail = new JTextField();
        txtEmail.setName("student.txtEmail");
        txtPhone = new JTextField();
        txtPhone.setName("student.txtPhone");

        inputGrid.add(new JLabel("ID Peserta:"));
        inputGrid.add(txtId);
        inputGrid.add(new JLabel("Nama Peserta:"));
        inputGrid.add(txtName);
        inputGrid.add(new JLabel("Email:"));
        inputGrid.add(txtEmail);
        inputGrid.add(new JLabel("Telepon:"));
        inputGrid.add(txtPhone);

        panel.add(inputGrid, BorderLayout.CENTER);

        // Right side: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        btnAdd = new JButton("Tambah");
        btnAdd.setName("student.btnAdd");
        btnUpdate = new JButton("Ubah");
        btnUpdate.setName("student.btnUpdate");
        btnDelete = new JButton("Hapus");
        btnDelete.setName("student.btnDelete");
        btnClear = new JButton("Bersihkan Form");
        btnClear.setName("student.btnClear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private JTable createStudentsTable() {
        String[] columnNames = {"ID", "Nama Peserta", "Email", "Telepon"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing of table cells
            }
        };
        JTable table = new JTable(model);
        return table;
    }

    public void setStatusMessage(String message, boolean isError) {
        lblStatus.setText(message);
        lblStatus.setForeground(isError ? Color.RED : Color.BLUE);
    }

    // --- Getters for Controller access ---

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