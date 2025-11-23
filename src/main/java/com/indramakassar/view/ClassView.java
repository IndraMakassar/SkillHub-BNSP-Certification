package com.indramakassar.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClassView extends JPanel {
    private JTextField txtId, txtClassName, txtInstructor;
    private JTextArea txtDescription;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable tableClasses;
    private JLabel lblStatus;

    public ClassView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Input Form Panel ---
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.NORTH);

        // --- Table Panel ---
        tableClasses = createClassesTable();
        tableClasses.setName("class.table");
        JScrollPane scrollPane = new JScrollPane(tableClasses);
        add(scrollPane, BorderLayout.CENTER);

        // --- Status Panel ---
        lblStatus = new JLabel("Ready.");
        lblStatus.setName("class.lblStatus");
        lblStatus.setBorder(BorderFactory.createEtchedBorder());
        add(lblStatus, BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Manajemen Data Kelas"));

        // Left side: Labels and Text Fields
        JPanel inputGrid = new JPanel(new GridLayout(4, 2, 5, 5));

        txtId = new JTextField();
        txtId.setName("class.txtId");
        txtId.setEditable(false);
        txtId.setEnabled(false);
        txtClassName = new JTextField();
        txtClassName.setName("class.txtClassName");
        txtInstructor = new JTextField();
        txtInstructor.setName("class.txtInstructor");
        txtDescription = new JTextArea(3, 20);
        txtDescription.setName("class.txtDescription");
        JScrollPane descriptionScroll = new JScrollPane(txtDescription);

        inputGrid.add(new JLabel("ID Kelas:"));
        inputGrid.add(txtId);
        inputGrid.add(new JLabel("Nama Kelas:"));
        inputGrid.add(txtClassName);
        inputGrid.add(new JLabel("Instruktur:"));
        inputGrid.add(txtInstructor);
        inputGrid.add(new JLabel("Deskripsi:"));
        inputGrid.add(descriptionScroll); // Use the JScrollPane here

        panel.add(inputGrid, BorderLayout.CENTER);

        // Right side: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        btnAdd = new JButton("Tambah");
        btnAdd.setName("class.btnAdd");
        btnUpdate = new JButton("Ubah");
        btnUpdate.setName("class.btnUpdate");
        btnDelete = new JButton("Hapus");
        btnDelete.setName("class.btnDelete");
        btnClear = new JButton("Bersihkan Form");
        btnClear.setName("class.btnClear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private JTable createClassesTable() {
        String[] columnNames = {"ID", "Nama Kelas", "Deskripsi", "Instruktur"};
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
    public JTextField getTxtClassName() { return txtClassName; }
    public JTextField getTxtInstructor() { return txtInstructor; }
    public JTextArea getTxtDescription() { return txtDescription; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JTable getTableClasses() { return tableClasses; }
}