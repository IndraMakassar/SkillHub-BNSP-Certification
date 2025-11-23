package com.indramakassar.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EnrollmentView extends JPanel {
    private JComboBox<String> cmbStudent, cmbClass, cmbFilterType, cmbFilterValue;
    private JButton btnEnroll, btnDrop, btnRefresh, btnApplyFilter;
    private JTable tableEnrollments;
    private JLabel lblStatus;

    public EnrollmentView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Enrollment Form Panel ---
        JPanel formPanel = createEnrollmentPanel();
        add(formPanel, BorderLayout.NORTH);

        // --- Table & Filter Panel ---
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        tableEnrollments = createEnrollmentTable();
        tableEnrollments.setName("enrollment.table");
        JScrollPane scrollPane = new JScrollPane(tableEnrollments);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = createFilterPanel();
        centerPanel.add(filterPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // --- Status Panel ---
        lblStatus = new JLabel("Ready.");
        lblStatus.setName("enrollment.lblStatus");
        lblStatus.setBorder(BorderFactory.createEtchedBorder());
        add(lblStatus, BorderLayout.SOUTH);
    }

    private JPanel createEnrollmentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Catat Pendaftaran Baru"));

        JPanel inputGrid = new JPanel(new GridLayout(2, 2, 5, 5));
        cmbStudent = new JComboBox<>();
        cmbStudent.setName("enrollment.cmbStudent");
        cmbClass = new JComboBox<>();
        cmbClass.setName("enrollment.cmbClass");
        btnEnroll = new JButton("Daftarkan");
        btnEnroll.setName("enrollment.btnEnroll");
        btnRefresh = new JButton("Refresh Data");
        btnRefresh.setName("enrollment.btnRefresh");

        inputGrid.add(new JLabel("Pilih Peserta:"));
        inputGrid.add(cmbStudent);
        inputGrid.add(new JLabel("Pilih Kelas:"));
        inputGrid.add(cmbClass);

        panel.add(inputGrid, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        buttonPanel.add(btnEnroll);
        buttonPanel.add(btnRefresh);

        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Filter Pendaftaran"));

        cmbFilterType = new JComboBox<>(new String[]{"All Enrollments", "By Student", "By Class"});
        cmbFilterType.setName("enrollment.cmbFilterType");
        cmbFilterValue = new JComboBox<>();
        cmbFilterValue.setName("enrollment.cmbFilterValue");
        btnApplyFilter = new JButton("Terapkan Filter");
        btnApplyFilter.setName("enrollment.btnApplyFilter");
        btnDrop = new JButton("Batalkan Pendaftaran Terpilih");
        btnDrop.setName("enrollment.btnDrop");

        panel.add(new JLabel("Filter Berdasarkan:"));
        panel.add(cmbFilterType);
        panel.add(new JLabel("Nilai Filter:"));
        panel.add(cmbFilterValue);
        panel.add(btnApplyFilter);
        panel.add(btnDrop);

        return panel;
    }

    private JTable createEnrollmentTable() {
        // Updated model columns based on EnrollmentController logic
        String[] columnNames = {"ID Peserta", "Nama Peserta", "ID Kelas", "Nama Kelas", "Status", "Tanggal Daftar"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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