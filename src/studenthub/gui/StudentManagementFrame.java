
package studenthub.gui;

import studenthub.dao.StudentDAO;
import studenthub.model.Student;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class StudentManagementFrame extends JFrame {

    // =============== PREMIUM COLOR PALETTE ===============

    private static final Color BACKGROUND =
            new Color(7, 13, 27);

    private static final Color PANEL =
            new Color(15, 26, 44);

    private static final Color FIELD =
            new Color(9, 19, 34);

    private static final Color TEAL =
            new Color(45, 212, 191);

    private static final Color CYAN =
            new Color(56, 189, 248);

    private static final Color WHITE =
            new Color(240, 246, 255);

    private static final Color MUTED =
            new Color(158, 177, 201);

    private static final Color BORDER =
            new Color(43, 64, 88);

    private static final Color ALT_ROW =
            new Color(20, 35, 55);

    private static final Color SELECTED_ROW =
            new Color(22, 91, 91);

    // =============== FONT CONFIGURATION ===============

    private static final String DISPLAY_FONT =
            findFont("Bahnschrift", "Aptos Display",
                    "Segoe UI Semibold", "Segoe UI");

    private static final String BODY_FONT =
            findFont("Segoe UI", "Arial");

    private static String findFont(String... candidates) {

        Set<String> installed = new TreeSet<>(
                String.CASE_INSENSITIVE_ORDER
        );

        for (String family :
                GraphicsEnvironment
                        .getLocalGraphicsEnvironment()
                        .getAvailableFontFamilyNames()) {

            installed.add(family);
        }

        for (String candidate : candidates) {
            if (installed.contains(candidate)) {
                return candidate;
            }
        }

        return "SansSerif";
    }

    private static Font display(int size) {
        return new Font(DISPLAY_FONT, Font.BOLD, size);
    }

    private static Font body(int size) {
        return new Font(BODY_FONT, Font.PLAIN, size);
    }

    private static Font bodyBold(int size) {
        return new Font(BODY_FONT, Font.BOLD, size);
    }

    // =============== FORM COMPONENTS ===============

    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField collegeField;
    private JTextField branchField;
    private JTextField yearField;
    private JTextField searchField;

    private JComboBox<String> branchFilter;
    private JComboBox<String> yearFilter;

    private JTable studentTable;
    private DefaultTableModel tableModel;

    private StudentDAO studentDAO;

    private boolean updatingFilters = false;

    // =============== CONSTRUCTOR ===============

    public StudentManagementFrame() {

        studentDAO = new StudentDAO();

        setTitle("StudentAcademicHub | Student Management");
        setSize(1220, 820);
        setMinimumSize(new Dimension(960, 700));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
        loadStudents();
        loadFilterOptions();

        setVisible(true);
    }

    // =============== MAIN INTERFACE ===============

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(0, 22));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(24, 28, 18, 28)
        );

        // ---------- HEADER ----------

        JPanel header = new GradientPanel();
        header.setLayout(new BorderLayout(16, 10));
        header.setBorder(
                BorderFactory.createEmptyBorder(20, 22, 20, 22)
        );

        JPanel headingGroup = new JPanel();
        headingGroup.setOpaque(false);
        headingGroup.setLayout(
                new BoxLayout(headingGroup, BoxLayout.Y_AXIS)
        );

        JLabel eyebrow = new JLabel("STUDENT ACADEMICHUB  /  MANAGEMENT");
        eyebrow.setFont(bodyBold(11));
        eyebrow.setForeground(TEAL);
        eyebrow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel("Student Management");
        heading.setFont(display(32));
        heading.setForeground(WHITE);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Organize student profiles and academic records in one place."
        );
        subtitle.setFont(body(13));
        subtitle.setForeground(MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        headingGroup.add(eyebrow);
        headingGroup.add(Box.createVerticalStrut(7));
        headingGroup.add(heading);
        headingGroup.add(Box.createVerticalStrut(5));
        headingGroup.add(subtitle);

        JLabel logo = new JLabel("S");
        logo.setFont(display(28));
        logo.setForeground(TEAL);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(62, 62));
        logo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEAL, 2),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));

        header.add(headingGroup, BorderLayout.CENTER);
        header.add(logo, BorderLayout.EAST);

        mainPanel.add(header, BorderLayout.NORTH);

        // ---------- CONTENT ----------

        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setOpaque(false);

        // ---------- STUDENT DETAILS FORM ----------

        JPanel formPanel = new JPanel(
                new GridLayout(3, 4, 16, 15)
        );

        formPanel.setBackground(PANEL);
        formPanel.setBorder(createSectionBorder("STUDENT DETAILS"));

        idField = new JTextField();
        idField.setEditable(false);

        nameField = new JTextField();
        emailField = new JTextField();
        collegeField = new JTextField();
        branchField = new JTextField();
        yearField = new JTextField();

        styleField(idField);
        styleField(nameField);
        styleField(emailField);
        styleField(collegeField);
        styleField(branchField);
        styleField(yearField);

        addFormItem(formPanel, "Student ID", idField);
        addFormItem(formPanel, "Student Name", nameField);
        addFormItem(formPanel, "Email Address", emailField);
        addFormItem(formPanel, "College", collegeField);
        addFormItem(formPanel, "Branch", branchField);
        addFormItem(formPanel, "Academic Year", yearField);

        // ---------- ACTION BUTTONS ----------

        JButton addButton =
                createButton("＋  Add Student",
                        new Color(13, 148, 136));

        JButton viewButton =
                createButton("View All",
                        new Color(37, 99, 170));

        JButton updateButton =
                createButton("Update Student",
                        new Color(108, 87, 205));

        JButton deleteButton =
                createButton("Delete Student",
                        new Color(190, 55, 76));

        JButton clearButton =
                createButton("Clear Fields",
                        new Color(66, 83, 106));

        JPanel actions = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 10)
        );
        actions.setBackground(PANEL);

        actions.add(addButton);
        actions.add(viewButton);
        actions.add(updateButton);
        actions.add(deleteButton);
        actions.add(clearButton);

        JPanel formSection = new JPanel(new BorderLayout(0, 12));
        formSection.setOpaque(false);
        formSection.add(formPanel, BorderLayout.CENTER);
        formSection.add(actions, BorderLayout.SOUTH);

        // ---------- SEARCH AND FILTER ----------

        JPanel searchPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 12)
        );

        searchPanel.setBackground(PANEL);
        searchPanel.setBorder(createSectionBorder("FIND STUDENTS"));

        searchField = new JTextField(12);
        styleField(searchField);
        searchField.setToolTipText("Search by student details");

        branchFilter = new JComboBox<>(
                new String[]{"All"}
        );

        yearFilter = new JComboBox<>(
                new String[]{"All", "1", "2", "3", "4", "5", "6"}
        );

        styleCombo(branchFilter);
        styleCombo(yearFilter);

        JButton searchButton =
                createButton("Search", new Color(13, 148, 136));

        JButton filterButton =
                createButton("Apply Filters", new Color(37, 99, 170));

        JButton resetButton =
                createButton("Show All", new Color(66, 83, 106));

        searchPanel.add(createLabel("Keyword"));
        searchPanel.add(searchField);
        searchPanel.add(createLabel("Branch"));
        searchPanel.add(branchFilter);
        searchPanel.add(createLabel("Year"));
        searchPanel.add(yearFilter);
        searchPanel.add(searchButton);
        searchPanel.add(filterButton);
        searchPanel.add(resetButton);

        // ---------- STUDENT TABLE ----------

        tableModel = new DefaultTableModel(
                new String[]{
                    "ID", "Student Name", "Email",
                    "College", "Branch", "Year"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        studentTable.setFont(body(13));
        studentTable.setRowHeight(36);
        studentTable.setAutoCreateRowSorter(true);
        studentTable.setForeground(WHITE);
        studentTable.setBackground(PANEL);
        studentTable.setSelectionBackground(SELECTED_ROW);
        studentTable.setSelectionForeground(Color.WHITE);
        studentTable.setGridColor(BORDER);
        studentTable.setShowGrid(true);
        studentTable.setFillsViewportHeight(true);
        studentTable.setIntercellSpacing(new Dimension(1, 1));
        studentTable.setRowMargin(1);

        // Header styling
        studentTable.getTableHeader().setFont(bodyBold(13));
        studentTable.getTableHeader().setBackground(
                new Color(20, 43, 63)
        );
        studentTable.getTableHeader().setForeground(TEAL);
        studentTable.getTableHeader().setPreferredSize(
                new Dimension(0, 42)
        );
        studentTable.getTableHeader().setReorderingAllowed(false);
        studentTable.getTableHeader().setBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 2, 0, TEAL
                )
        );

        // Alternating rows
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus,
                        row, column
                );

                setFont(body(13));
                setBorder(BorderFactory.createEmptyBorder(
                        0, 10, 0, 10
                ));

                if (isSelected) {
                    setBackground(SELECTED_ROW);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(
                            row % 2 == 0 ? PANEL : ALT_ROW
                    );
                    setForeground(WHITE);
                }

                return this;
            }
        };

        for (int i = 0; i < studentTable.getColumnCount(); i++) {
            studentTable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(renderer);
        }

        // Useful column widths
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(55);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(190);
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(190);
        studentTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(65);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );
        scrollPane.getViewport().setBackground(PANEL);

        // ---------- RECORDS SECTION ----------

        JLabel recordsTitle = new JLabel("STUDENT RECORDS");
        recordsTitle.setFont(display(18));
        recordsTitle.setForeground(WHITE);

        JLabel recordsSubtitle = new JLabel(
                "Select a row to view or update a student"
        );
        recordsSubtitle.setFont(body(12));
        recordsSubtitle.setForeground(MUTED);

        JPanel recordsHeading = new JPanel();
        recordsHeading.setOpaque(false);
        recordsHeading.setLayout(
                new BoxLayout(recordsHeading, BoxLayout.Y_AXIS)
        );
        recordsHeading.add(recordsTitle);
        recordsHeading.add(Box.createVerticalStrut(4));
        recordsHeading.add(recordsSubtitle);

        JPanel tablePanel = new JPanel(new BorderLayout(0, 12));
        tablePanel.setBackground(PANEL);
        tablePanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        tablePanel.add(recordsHeading, BorderLayout.NORTH);
        tablePanel.add(searchPanel, BorderLayout.CENTER);

        JPanel tableArea = new JPanel(new BorderLayout());
        tableArea.setOpaque(false);
        tableArea.add(scrollPane, BorderLayout.CENTER);

        JPanel recordsSection = new JPanel(new BorderLayout(0, 12));
        recordsSection.setOpaque(false);
        recordsSection.add(tablePanel, BorderLayout.NORTH);
        recordsSection.add(tableArea, BorderLayout.CENTER);

        content.add(formSection, BorderLayout.NORTH);
        content.add(recordsSection, BorderLayout.CENTER);

        mainPanel.add(content, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
                "StudentAcademicHub   •   Student Records Workspace"
        );
        footer.setFont(body(11));
        footer.setForeground(MUTED);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);
        footerPanel.add(footer, BorderLayout.WEST);

        JLabel status = new JLabel("●  SYSTEM READY");
        status.setFont(bodyBold(10));
        status.setForeground(TEAL);
        footerPanel.add(status, BorderLayout.EAST);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // ---------- PRESERVED ACTION HANDLERS ----------

        addButton.addActionListener(e -> addStudent());
        viewButton.addActionListener(e -> loadStudents());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearFields());

        searchButton.addActionListener(e -> refreshResults());
        filterButton.addActionListener(e -> refreshResults());

        resetButton.addActionListener(e -> {
            searchField.setText("");
            branchFilter.setSelectedItem("All");
            yearFilter.setSelectedItem("All");
            loadStudents();
        });

        searchField.addActionListener(e -> refreshResults());

        branchFilter.addActionListener(e -> {
            if (!updatingFilters) {
                refreshResults();
            }
        });

        yearFilter.addActionListener(e -> refreshResults());

        // Populate fields when selecting a row
        studentTable.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()
                    && studentTable.getSelectedRow() != -1) {

                int selectedRow = studentTable.getSelectedRow();

                int row = studentTable.convertRowIndexToModel(
                        selectedRow
                );

                idField.setText(
                        tableModel.getValueAt(row, 0).toString()
                );
                nameField.setText(
                        tableModel.getValueAt(row, 1).toString()
                );
                emailField.setText(
                        tableModel.getValueAt(row, 2).toString()
                );
                collegeField.setText(
                        tableModel.getValueAt(row, 3).toString()
                );
                branchField.setText(
                        tableModel.getValueAt(row, 4).toString()
                );
                yearField.setText(
                        tableModel.getValueAt(row, 5).toString()
                );
            }
        });
    }

    // =============== GRADIENT HEADER ===============

    private static class GradientPanel extends JPanel {

        GradientPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(17, 40, 61),
                    getWidth(), getHeight(),
                    new Color(11, 23, 42)
            );

            g2.setPaint(gradient);
            g2.fillRoundRect(
                    0, 0, getWidth(), getHeight(), 22, 22
            );

            // Decorative teal accent
            g2.setColor(TEAL);
            g2.fillRoundRect(
                    0, 15, 4, getHeight() - 30, 4, 4
            );

            g2.dispose();
        }
    }

    // =============== REUSABLE UI HELPERS ===============

    private TitledBorder createSectionBorder(String title) {

        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER),
                "  " + title + "  ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                bodyBold(12),
                TEAL
        );
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(bodyBold(12));
        label.setForeground(MUTED);

        return label;
    }

    private void addFormItem(
            JPanel panel, String label, JTextField field) {

        panel.add(createLabel(label));
        panel.add(field);
    }

    private void styleField(JTextField field) {

        field.setFont(body(14));
        field.setForeground(WHITE);
        field.setCaretColor(TEAL);
        field.setBackground(FIELD);
        field.setPreferredSize(new Dimension(150, 36));
        field.setSelectionColor(new Color(24, 104, 105));
        field.setSelectedTextColor(Color.WHITE);

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));
    }

    private void styleCombo(JComboBox<String> combo) {

        combo.setFont(body(12));
        combo.setForeground(WHITE);
        combo.setBackground(FIELD);
        combo.setPreferredSize(new Dimension(95, 36));
        combo.setFocusable(false);
    }

    private JButton createButton(String text, Color baseColor) {

        JButton button = new JButton(text);

        button.setFont(bodyBold(12));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(baseColor.darker()),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(baseColor.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }

    // =============== LOAD FILTER OPTIONS ===============

    private void loadFilterOptions() {

        try {
            String selectedBranch =
                    (String) branchFilter.getSelectedItem();

            Set<String> branches = new TreeSet<>(
                    String.CASE_INSENSITIVE_ORDER
            );

            for (Student student : studentDAO.getAllStudentsList()) {
                if (student.getBranch() != null
                        && !student.getBranch().trim().isEmpty()) {

                    branches.add(student.getBranch().trim());
                }
            }

            updatingFilters = true;

            branchFilter.removeAllItems();
            branchFilter.addItem("All");

            for (String branch : branches) {
                branchFilter.addItem(branch);
            }

            if (selectedBranch != null
                    && branches.contains(selectedBranch)) {
                branchFilter.setSelectedItem(selectedBranch);
            } else {
                branchFilter.setSelectedItem("All");
            }

        } catch (Exception ex) {
            showError(ex);
        } finally {
            updatingFilters = false;
        }
    }

    // =============== SEARCH AND FILTER ===============

    private void refreshResults() {

        try {
            String keyword = searchField.getText().trim();

            String branch =
                    (String) branchFilter.getSelectedItem();

            String yearText =
                    (String) yearFilter.getSelectedItem();

            List<Student> students;

            if (keyword.isEmpty()) {

                int year = "All".equals(yearText)
                        ? 0 : Integer.parseInt(yearText);

                students = studentDAO.filterStudents(branch, year);

            } else {

                // Copy results before filtering, in case the DAO
                // returns a shared or unmodifiable collection.
                students = new ArrayList<>(
                        studentDAO.searchStudents(keyword)
                );

                if (!"All".equals(branch)
                        || !"All".equals(yearText)) {

                    final String selectedBranch = branch;

                    final int selectedYear = "All".equals(yearText)
                            ? 0 : Integer.parseInt(yearText);

                    students.removeIf(student ->
                            (selectedBranch != null
                                    && !"All".equals(selectedBranch)
                                    && !selectedBranch.equalsIgnoreCase(
                                            student.getBranch()))
                            || (selectedYear != 0
                                    && student.getYear() != selectedYear)
                    );
                }
            }

            tableModel.setRowCount(0);
            displayStudents(students);

            if (students.isEmpty()) {
                showMessage(
                        "No students match your search and filters."
                );
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    // =============== ADD STUDENT ===============

    private void addStudent() {

        try {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String college = collegeField.getText().trim();
            String branch = branchField.getText().trim();
            String yearText = yearField.getText().trim();

            if (name.isEmpty() || email.isEmpty()
                    || college.isEmpty() || branch.isEmpty()
                    || yearText.isEmpty()) {

                showMessage("Please fill in all student details.");
                return;
            }

            if (!email.matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                showMessage("Please enter a valid email address.");
                return;
            }

            int year = Integer.parseInt(yearText);

            if (year < 1 || year > 6) {
                showMessage("Year must be between 1 and 6.");
                return;
            }

            Student student = new Student();

            student.setName(name);
            student.setEmail(email);
            student.setCollege(college);
            student.setBranch(branch);
            student.setYear(year);

            studentDAO.addStudent(student);

            showMessage("Student added successfully.");

            clearFields();
            loadStudents();
            loadFilterOptions();

        } catch (NumberFormatException ex) {
            showMessage("Year must be a valid number.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    // =============== VIEW ALL STUDENTS ===============

    private void loadStudents() {

        try {
            tableModel.setRowCount(0);
            displayStudents(studentDAO.getAllStudentsList());

        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void displayStudents(List<Student> students) {

        for (Student student : students) {
            tableModel.addRow(new Object[]{
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getCollege(),
                student.getBranch(),
                student.getYear()
            });
        }
    }

    // =============== UPDATE STUDENT ===============

    private void updateStudent() {

        try {
            if (idField.getText().trim().isEmpty()) {
                showMessage(
                        "Select a student from the table first."
                );
                return;
            }

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String college = collegeField.getText().trim();
            String branch = branchField.getText().trim();
            String yearText = yearField.getText().trim();

            if (name.isEmpty() || email.isEmpty()
                    || college.isEmpty() || branch.isEmpty()
                    || yearText.isEmpty()) {

                showMessage("Please fill in all student details.");
                return;
            }

            if (!email.matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                showMessage("Please enter a valid email address.");
                return;
            }

            int id = Integer.parseInt(idField.getText().trim());
            int year = Integer.parseInt(yearText);

            if (year < 1 || year > 6) {
                showMessage("Year must be between 1 and 6.");
                return;
            }

            Student student = new Student();

            student.setStudentId(id);
            student.setName(name);
            student.setEmail(email);
            student.setCollege(college);
            student.setBranch(branch);
            student.setYear(year);

            studentDAO.updateStudent(student);

            showMessage("Student updated successfully.");

            clearFields();
            loadStudents();
            loadFilterOptions();

        } catch (NumberFormatException ex) {
            showMessage(
                    "Student ID and year must be valid numbers."
            );
        } catch (Exception ex) {
            showError(ex);
        }
    }

    // =============== DELETE STUDENT ===============

    private void deleteStudent() {

        try {
            if (idField.getText().trim().isEmpty()) {
                showMessage(
                        "Select a student from the table first."
                );
                return;
            }

            int id = Integer.parseInt(
                    idField.getText().trim()
            );

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete student ID "
                            + id + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {

                studentDAO.deleteStudent(id);

                showMessage("Delete request completed.");

                clearFields();
                loadStudents();
                loadFilterOptions();
            }

        } catch (NumberFormatException ex) {
            showMessage("Please select a valid student.");
        } catch (Exception ex) {
            showError(ex);
        }
    }

    // =============== CLEAR FORM ===============

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        collegeField.setText("");
        branchField.setText("");
        yearField.setText("");

        studentTable.clearSelection();
    }

    // =============== MESSAGES ===============

    private void showMessage(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "StudentAcademicHub",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showError(Exception ex) {

        ex.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                "An error occurred:\n" + ex.getMessage(),
                "StudentAcademicHub - Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // =============== MAIN METHOD ===============

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                StudentManagementFrame::new
        );
    }
}
