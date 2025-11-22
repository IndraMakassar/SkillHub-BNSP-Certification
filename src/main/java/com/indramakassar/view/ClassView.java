package com.indramakassar.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(1100, 650); // Ukuran sedikit lebih besar
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
        splitPane.setResizeWeight(0);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Status Panel (South)
        mainPanel.add(createStatusPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    /** Helper untuk membuat baris input */
    private JPanel createInputRow(String labelText, JComponent input, int height) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 5));
        rowPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(80, height));
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));

        // Jika bukan JTextArea, set max size
        if (!(input instanceof JScrollPane)) {
            input.setMaximumSize(new Dimension(Short.MAX_VALUE, input.getPreferredSize().height));
        }

        rowPanel.add(label, BorderLayout.WEST);
        rowPanel.add(input, BorderLayout.CENTER);
        rowPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

        return rowPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        formPanel.setBackground(Color.WHITE);

        JLabel formTitle = new JLabel("CLASS FORM");
        formTitle.setFont(new Font("Arial", Font.BOLD, 18));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(formTitle);
        formPanel.add(Box.createVerticalStrut(15));

        // Inisialisasi komponen
        txtId = new JTextField(20);
        txtId.setEditable(false);
        txtId.setBackground(new Color(245, 245, 245));
        txtId.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        txtClassName = new JTextField(20);
        txtInstructor = new JTextField(20);

        // Khusus untuk JTextArea
        txtDescription = new JTextArea(4, 20);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        scrollDesc.setPreferredSize(new Dimension(20, 80)); // Tinggi yang lebih jelas
        scrollDesc.setBorder(UIManager.getBorder("TextField.border"));

        // Tambahkan baris input
        formPanel.add(createInputRow("ID:", txtId, 25));
        formPanel.add(createInputRow("Name: *", txtClassName, 25));
        formPanel.add(createInputRow("Instructor:", txtInstructor, 25));
        formPanel.add(createInputRow("Description:", scrollDesc, 80)); // Tinggi khusus untuk area teks

        formPanel.add(Box.createVerticalStrut(30));

        JPanel buttonContainer = createButtonPanel();
        buttonContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(buttonContainer);

        formPanel.add(Box.createVerticalGlue());

        return formPanel;
    }

    private JPanel createButtonPanel() {
        // Menggunakan GridLayout 2x2
        JPanel buttonGrid = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonGrid.setOpaque(false);

        btnAdd = createStyledButton("Add Class", new Color(76, 175, 80));
        btnUpdate = createStyledButton("Update Class", new Color(33, 150, 243));
        btnDelete = createStyledButton("Delete Class", new Color(244, 67, 54));
        btnClear = createStyledButton("Clear Form", new Color(97, 97, 97));

        buttonGrid.add(btnAdd);
        buttonGrid.add(btnUpdate);
        buttonGrid.add(btnDelete);
        buttonGrid.add(btnClear);

        return buttonGrid;
    }

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

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Classes List"));

        String[] columns = {"ID", "Class Name", "Description", "Instructor"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableClasses = new JTable(tableModel);
        tableClasses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableClasses.getTableHeader().setReorderingAllowed(false);

        // Perbaikan Visual Tabel
        tableClasses.setRowHeight(28);
        tableClasses.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tableClasses.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tableClasses.getTableHeader().setBackground(new Color(240, 240, 240));

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