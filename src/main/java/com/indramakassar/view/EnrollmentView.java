package com.indramakassar.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(1200, 700); // Ukuran lebih besar untuk tabel pendaftaran yang lebar
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form/Sidebar Panel (WEST): Menggabungkan Enrollment dan Filter
        JPanel formSidebar = createSidebarPanel();
        formSidebar.setPreferredSize(new Dimension(350, 650));

        // Table Panel (CENTER)
        JPanel tablePanel = createTablePanel();

        // Menggunakan JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formSidebar, tablePanel);
        splitPane.setDividerLocation(350);
        splitPane.setDividerSize(5);
        splitPane.setResizeWeight(0);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Status Panel (South)
        mainPanel.add(createStatusPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    /** Helper untuk membuat input row dengan ComboBox */
    private JPanel createComboRow(String labelText, JComboBox<?> comboBox) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 5));
        rowPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 25)); // Lebar label lebih besar
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));

        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        comboBox.setPreferredSize(new Dimension(200, 30));
        comboBox.setMaximumSize(new Dimension(Short.MAX_VALUE, comboBox.getPreferredSize().height));

        rowPanel.add(label, BorderLayout.WEST);
        rowPanel.add(comboBox, BorderLayout.CENTER);
        rowPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

        return rowPanel;
    }

    /** Helper untuk membuat tombol yang seragam dan bergaya */
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 35));

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

    private JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        sidebarPanel.setBackground(Color.WHITE);

        // --- Panel Pendaftaran ---
        JPanel enrollPanel = createEnrollmentPanel();
        enrollPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebarPanel.add(enrollPanel);

        sidebarPanel.add(Box.createVerticalStrut(20));
        sidebarPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        sidebarPanel.add(Box.createVerticalStrut(20));

        // --- Panel Filter ---
        JPanel filterPanel = createFilterPanel();
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebarPanel.add(filterPanel);

        sidebarPanel.add(Box.createVerticalGlue());

        return sidebarPanel;
    }

    private JPanel createEnrollmentPanel() {
        JPanel enrollPanel = new JPanel();
        enrollPanel.setLayout(new BoxLayout(enrollPanel, BoxLayout.Y_AXIS));
        enrollPanel.setBorder(BorderFactory.createTitledBorder("Enrollment"));
        enrollPanel.setOpaque(false);

        JLabel title = new JLabel("ENROLL STUDENT TO CLASS");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        enrollPanel.add(title);
        enrollPanel.add(Box.createVerticalStrut(15));

        cmbStudent = new JComboBox<>();
        cmbClass = new JComboBox<>();

        enrollPanel.add(createComboRow("Student:", cmbStudent));
        enrollPanel.add(createComboRow("Class:", cmbClass));
        enrollPanel.add(Box.createVerticalStrut(20));

        // Tombol Pendaftaran/Drop
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        btnEnroll = createStyledButton("Enroll", new Color(76, 175, 80));
        btnDrop = createStyledButton("Drop", new Color(244, 67, 54));

        buttonPanel.add(btnEnroll);
        buttonPanel.add(btnDrop);

        enrollPanel.add(buttonPanel);

        return enrollPanel;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtering"));
        filterPanel.setOpaque(false);

        JLabel title = new JLabel("FILTER ENROLLMENTS");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        filterPanel.add(title);
        filterPanel.add(Box.createVerticalStrut(15));

        cmbFilterType = new JComboBox<>(new String[]{"All Enrollments", "By Student", "By Class"});
        cmbFilterValue = new JComboBox<>();
        cmbFilterValue.setEnabled(false);

        filterPanel.add(createComboRow("Filter By:", cmbFilterType));
        filterPanel.add(createComboRow("Select Value:", cmbFilterValue));
        filterPanel.add(Box.createVerticalStrut(20));

        // Tombol Filter/Refresh
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        btnApplyFilter = createStyledButton("Apply Filter", new Color(33, 150, 243));
        btnRefresh = createStyledButton("Refresh", new Color(97, 97, 97));

        buttonPanel.add(btnApplyFilter);
        buttonPanel.add(btnRefresh);

        filterPanel.add(buttonPanel);

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

        // Perbaikan Visual Tabel
        tableEnrollments.setRowHeight(28);
        tableEnrollments.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tableEnrollments.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tableEnrollments.getTableHeader().setBackground(new Color(240, 240, 240));

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
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        lblStatus.setOpaque(true);

        statusPanel.add(lblStatus, BorderLayout.CENTER);
        return statusPanel;
    }

    // Method to set status message (Dibiarkan sama)
    public void setStatusMessage(String message, boolean isError) {
        lblStatus.setText(message);
        if (isError) {
            lblStatus.setForeground(new Color(198, 40, 40));
            lblStatus.setBackground(new Color(255, 235, 238));
        } else {
            lblStatus.setForeground(new Color(46, 125, 50));
            lblStatus.setBackground(new Color(232, 245, 233));
        }
        lblStatus.setOpaque(true);

        Timer timer = new Timer(5000, e -> {
            lblStatus.setText(" ");
            lblStatus.setOpaque(false);
            lblStatus.setBackground(null);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void clearStatusMessage() {
        lblStatus.setText(" ");
        lblStatus.setOpaque(false);
        lblStatus.setBackground(null);
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