package com.indramakassar.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        // Diperbesar untuk menampung split pane
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form/Sidebar Panel (WEST)
        JPanel formSidebar = createFormPanel();
        formSidebar.setPreferredSize(new Dimension(350, 600)); // Lebar yang konsisten untuk sidebar

        // Table Panel (CENTER)
        JPanel tablePanel = createTablePanel();

        // Menggunakan JSplitPane untuk membagi Form dan Tabel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formSidebar, tablePanel);
        splitPane.setDividerLocation(350);
        splitPane.setDividerSize(5);
        splitPane.setResizeWeight(0); // Memastikan sidebar tidak mengecil

        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Status Panel (South)
        mainPanel.add(createStatusPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    /** Helper untuk membuat baris input yang rapi dengan BoxLayout */
    private JPanel createInputRow(String labelText, JComponent input) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 5));
        rowPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(80, 25)); // Lebar label konsisten
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));

        // Pastikan input field mengisi ruang yang tersedia
        input.setMaximumSize(new Dimension(Short.MAX_VALUE, input.getPreferredSize().height));

        rowPanel.add(label, BorderLayout.WEST);
        rowPanel.add(input, BorderLayout.CENTER);
        rowPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

        return rowPanel;
    }

    private JPanel createFormPanel() {
        // Menggunakan BoxLayout untuk penataan vertikal yang bersih
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20) // Padding di dalam panel
        ));
        formPanel.setBackground(Color.WHITE);

        // Header Formulir
        JLabel formTitle = new JLabel("STUDENT FORM");
        formTitle.setFont(new Font("Arial", Font.BOLD, 18));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(formTitle);
        formPanel.add(Box.createVerticalStrut(15));

        // Inisialisasi komponen
        txtId = new JTextField(20);
        txtId.setEditable(false);
        txtId.setBackground(new Color(245, 245, 245)); // Warna ID yang lebih halus
        txtId.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        txtName = new JTextField(20);
        txtEmail = new JTextField(20);
        txtPhone = new JTextField(20);

        // Tambahkan baris input
        formPanel.add(createInputRow("ID:", txtId));
        formPanel.add(createInputRow("Name: *", txtName));
        formPanel.add(createInputRow("Email:", txtEmail));
        formPanel.add(createInputRow("Phone:", txtPhone));

        formPanel.add(Box.createVerticalStrut(30)); // Spacing before buttons

        // Tambahkan panel tombol
        JPanel buttonContainer = createButtonPanel();
        buttonContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(buttonContainer);

        // Push content to the top
        formPanel.add(Box.createVerticalGlue());

        return formPanel;
    }

    private JPanel createButtonPanel() {
        // Menggunakan GridLayout 2x2 untuk tombol yang rapi dan seragam
        JPanel buttonGrid = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonGrid.setOpaque(false);

        // Inisialisasi Tombol dengan gaya yang sedikit lebih besar
        btnAdd = createStyledButton("Add Student", new Color(76, 175, 80));
        btnUpdate = createStyledButton("Update Student", new Color(33, 150, 243));
        btnDelete = createStyledButton("Delete Student", new Color(244, 67, 54));
        btnClear = createStyledButton("Clear Form", new Color(97, 97, 97));

        buttonGrid.add(btnAdd);
        buttonGrid.add(btnUpdate);
        buttonGrid.add(btnDelete);
        buttonGrid.add(btnClear);

        return buttonGrid;
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

        // Efek hover sederhana
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

        // Perbaikan Visual Tabel
        tableStudents.setRowHeight(28);
        tableStudents.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tableStudents.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tableStudents.getTableHeader().setBackground(new Color(240, 240, 240));

        // Set column widths
        tableStudents.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableStudents.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableStudents.getColumnModel().getColumn(2).setPreferredWidth(200);
        tableStudents.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(tableStudents);
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
        lblStatus.setOpaque(true); // Penting untuk menampilkan background warna

        statusPanel.add(lblStatus, BorderLayout.CENTER);
        return statusPanel;
    }

    // Method to set status message (Dibiarkan sama, namun disesuaikan warnanya)
    public void setStatusMessage(String message, boolean isError) {
        lblStatus.setText(message);
        if (isError) {
            lblStatus.setForeground(new Color(198, 40, 40)); // Darker Red
            lblStatus.setBackground(new Color(255, 235, 238)); // Light red
        } else {
            lblStatus.setForeground(new Color(46, 125, 50)); // Darker Green
            lblStatus.setBackground(new Color(232, 245, 233)); // Light green
        }
        lblStatus.setOpaque(true);

        // Clear message after 5 seconds
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