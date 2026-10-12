
package studenthub.gui;

import studenthub.dao.StudentDAO;
import studenthub.dao.SubjectDAO;
import studenthub.dao.StudentSubjectDAO;
import studenthub.model.Student;
import studenthub.model.Subject;
import studenthub.model.StudentSubject;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class EnrollmentManagementFrame extends JFrame {

    // Theme colors
    private static final Color BACKGROUND = new Color(13, 20, 38);
    private static final Color PANEL = new Color(23, 33, 55);
    private static final Color INPUT = new Color(30, 43, 67);
    private static final Color TEAL = new Color(20, 184, 166);
    private static final Color TEAL_HOVER = new Color(13, 148, 136);
    private static final Color TEXT = new Color(235, 242, 255);
    private static final Color MUTED = new Color(155, 171, 195);
    private static final Color BORDER = new Color(53, 69, 96);
    private static final Color ROW_ALT = new Color(25, 37, 60);

    private JComboBox<Student> studentCombo;
    private JComboBox<Subject> subjectCombo;
    private JTable enrollmentTable;
    private DefaultTableModel tableModel;
    private JLabel countLabel;
    private JLabel statusLabel;

    private final StudentDAO studentDAO = new StudentDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final StudentSubjectDAO enrollmentDAO =
            new StudentSubjectDAO();

    public EnrollmentManagementFrame() {

        setTitle("StudentAcademicHub | Enrollment Management");
        setSize(1000, 680);
        setMinimumSize(new Dimension(850, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        refreshData();

        setVisible(true);
    }

    // HEADER
    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout(15, 5));
        header.setBackground(PANEL);
        header.setBorder(new EmptyBorder(22, 28, 22, 28));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Student-Subject Enrollment");
        title.setFont(new Font("Segoe UI", Font.BOLD, 25));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Manage student registrations and subject assignments"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(subtitle);

        JLabel logo = new JLabel("E", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 25));
        logo.setForeground(Color.WHITE);
        logo.setOpaque(true);
        logo.setBackground(TEAL);
        logo.setPreferredSize(new Dimension(54, 54));

        header.add(logo, BorderLayout.WEST);
        header.add(titlePanel, BorderLayout.CENTER);

        return header;
    }

    // MAIN CONTENT
    private JPanel createMainContent() {

        JPanel main = new JPanel(new BorderLayout(0, 18));
        main.setBackground(BACKGROUND);
        main.setBorder(new EmptyBorder(22, 25, 20, 25));

        main.add(createFormPanel(), BorderLayout.NORTH);
        main.add(createTablePanel(), BorderLayout.CENTER);

        return main;
    }

    // ENROLLMENT FORM
    private JPanel createFormPanel() {

        JPanel formPanel = new JPanel(new BorderLayout(0, 18));
        formPanel.setBackground(PANEL);
        formPanel.setBorder(
                new CompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(20, 22, 20, 22)
                )
        );

        JLabel sectionTitle = new JLabel("Enrollment Details");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        sectionTitle.setForeground(TEXT);

        JPanel fieldsPanel = new JPanel(new GridLayout(1, 2, 18, 0));
        fieldsPanel.setOpaque(false);

        // FIX: Each dropdown uses its correct generic type.
        studentCombo = createStudentComboBox();
        subjectCombo = createSubjectComboBox();

        fieldsPanel.add(
                createFieldGroup("SELECT STUDENT", studentCombo)
        );
        fieldsPanel.add(
                createFieldGroup("SELECT SUBJECT", subjectCombo)
        );

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 0)
        );
        buttonPanel.setOpaque(false);

        JButton enrollButton = createButton("Enroll Student", true);
        JButton removeButton = createButton("Remove Enrollment", false);
        JButton refreshButton = createButton("Refresh Data", false);
        JButton clearButton = createButton("Clear Selection", false);

        enrollButton.addActionListener(e -> enrollStudent());
        removeButton.addActionListener(e -> removeEnrollment());
        refreshButton.addActionListener(e -> refreshData());
        clearButton.addActionListener(e -> clearSelection());

        buttonPanel.add(enrollButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(clearButton);

        JPanel lowerPanel = new JPanel(new BorderLayout(0, 18));
        lowerPanel.setOpaque(false);
        lowerPanel.add(fieldsPanel, BorderLayout.CENTER);
        lowerPanel.add(buttonPanel, BorderLayout.SOUTH);

        formPanel.add(sectionTitle, BorderLayout.NORTH);
        formPanel.add(lowerPanel, BorderLayout.CENTER);

        return formPanel;
    }

    private JPanel createFieldGroup(
            String labelText,
            JComponent field) {

        JPanel group = new JPanel(new BorderLayout(0, 8));
        group.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(MUTED);

        group.add(label, BorderLayout.NORTH);
        group.add(field, BorderLayout.CENTER);

        return group;
    }

    private JComboBox<Student> createStudentComboBox() {

        JComboBox<Student> combo = new JComboBox<>();
        styleComboBox(combo);
        return combo;
    }

    private JComboBox<Subject> createSubjectComboBox() {

        JComboBox<Subject> combo = new JComboBox<>();
        styleComboBox(combo);
        return combo;
    }

    private void styleComboBox(JComboBox<?> combo) {

        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(INPUT);
        combo.setForeground(TEXT);
        combo.setPreferredSize(new Dimension(200, 42));
        combo.setFocusable(true);

        combo.setBorder(
                new CompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(4, 8, 4, 8)
                )
        );
    }

    // ENROLLMENT TABLE
    private JPanel createTablePanel() {

        JPanel tablePanel = new JPanel(new BorderLayout(0, 14));
        tablePanel.setBackground(PANEL);
        tablePanel.setBorder(
                new CompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(18, 18, 18, 18)
                )
        );

        JPanel tableHeading = new JPanel(new BorderLayout());
        tableHeading.setOpaque(false);

        JLabel tableTitle = new JLabel("Enrollment Records");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        tableTitle.setForeground(TEXT);

        countLabel = new JLabel("0 enrollments");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLabel.setForeground(TEAL);

        tableHeading.add(tableTitle, BorderLayout.WEST);
        tableHeading.add(countLabel, BorderLayout.EAST);

        tableModel = new DefaultTableModel(
                new String[]{
                        "Student ID",
                        "Student Name",
                        "Subject ID",
                        "Subject Name",
                        "Subject Code"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        enrollmentTable = new JTable(tableModel);
        enrollmentTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        enrollmentTable.setForeground(TEXT);
        enrollmentTable.setBackground(PANEL);
        enrollmentTable.setSelectionBackground(
                new Color(27, 104, 111)
        );
        enrollmentTable.setSelectionForeground(Color.WHITE);
        enrollmentTable.setRowHeight(36);
        enrollmentTable.setShowVerticalLines(false);
        enrollmentTable.setShowHorizontalLines(true);
        enrollmentTable.setGridColor(BORDER);
        enrollmentTable.setFillsViewportHeight(true);
        enrollmentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        enrollmentTable.setIntercellSpacing(new Dimension(0, 1));
        enrollmentTable.setAutoCreateRowSorter(true);

        JTableHeader header = enrollmentTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setForeground(TEAL);
        header.setBackground(INPUT);
        header.setPreferredSize(new Dimension(0, 40));
        header.setReorderingAllowed(false);

        enrollmentTable.setDefaultRenderer(
                Object.class,
                new javax.swing.table.DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        Component component =
                                super.getTableCellRendererComponent(
                                        table,
                                        value,
                                        isSelected,
                                        hasFocus,
                                        row,
                                        column
                                );

                        if (isSelected) {
                            component.setBackground(
                                    table.getSelectionBackground()
                            );
                            component.setForeground(Color.WHITE);
                        } else {
                            component.setBackground(
                                    row % 2 == 0 ? PANEL : ROW_ALT
                            );
                            component.setForeground(TEXT);
                        }

                        setBorder(
                                new EmptyBorder(0, 10, 0, 10)
                        );

                        return component;
                    }
                }
        );

        JScrollPane scrollPane = new JScrollPane(enrollmentTable);
        scrollPane.setBorder(new LineBorder(BORDER, 1, true));
        scrollPane.getViewport().setBackground(PANEL);

        enrollmentTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (e.getValueIsAdjusting()) {
                        return;
                    }

                    int selectedRow = enrollmentTable.getSelectedRow();

                    if (selectedRow >= 0) {

                        int modelRow =
                                enrollmentTable.convertRowIndexToModel(
                                        selectedRow
                                );

                        int studentId = Integer.parseInt(
                                tableModel.getValueAt(modelRow, 0)
                                        .toString()
                        );

                        int subjectId = Integer.parseInt(
                                tableModel.getValueAt(modelRow, 2)
                                        .toString()
                        );

                        selectStudentById(studentId);
                        selectSubjectById(subjectId);

                        setStatus("Enrollment selected.");
                    }
                });

        tablePanel.add(tableHeading, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    // FOOTER
    private JPanel createFooter() {

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(PANEL);
        footer.setBorder(new EmptyBorder(11, 25, 11, 25));

        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(MUTED);

        JLabel footerLabel = new JLabel("StudentAcademicHub");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(MUTED);

        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(footerLabel, BorderLayout.EAST);

        return footer;
    }

    // BUTTONS AND HOVER EFFECTS
    private JButton createButton(String text, boolean primary) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        Color normal = primary ? TEAL : INPUT;
        Color hover = primary
                ? TEAL_HOVER
                : new Color(43, 60, 87);

        button.setBackground(normal);
        button.setForeground(primary ? Color.WHITE : TEXT);
        button.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                primary ? TEAL : BORDER, 1, true
                        ),
                        new EmptyBorder(9, 12, 9, 12)
                )
        );

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(hover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(normal);
            }
        });

        return button;
    }

    // DATABASE LOADING
    private void refreshData() {

        try {
            loadStudents();
            loadSubjects();
            loadEnrollments();

            setStatus("Student, subject and enrollment data refreshed.");

        } catch (Exception ex) {
            setStatus("Unable to refresh data.");
            showError("Could not load enrollment data.", ex);
        }
    }

    private void loadStudents() {

        studentCombo.removeAllItems();

        List<Student> students = studentDAO.getAllStudentsList();

        if (students != null) {
            for (Student student : students) {
                studentCombo.addItem(student);
            }
        }
    }

    private void loadSubjects() {

        subjectCombo.removeAllItems();

        List<Subject> subjects = subjectDAO.fetchAllSubjects();

        if (subjects != null) {
            for (Subject subject : subjects) {
                subjectCombo.addItem(subject);
            }
        }
    }

    private void loadEnrollments() {

        tableModel.setRowCount(0);

        List<Object[]> enrollments =
                enrollmentDAO.fetchAllEnrollments();

        if (enrollments != null) {
            for (Object[] enrollment : enrollments) {
                tableModel.addRow(enrollment);
            }
        }

        updateEnrollmentCount();
    }

    private void updateEnrollmentCount() {

        int count = tableModel.getRowCount();

        countLabel.setText(
                count + (count == 1 ? " enrollment" : " enrollments")
        );
    }

    // ENROLL STUDENT
    private void enrollStudent() {

        Student student =
                (Student) studentCombo.getSelectedItem();

        Subject subject =
                (Subject) subjectCombo.getSelectedItem();

        if (student == null || subject == null) {
            showMessage(
                    "Please add at least one student and one subject first.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        StudentSubject enrollment = new StudentSubject();
        enrollment.setStudentId(student.getStudentId());
        enrollment.setSubjectId(subject.getId());

        try {
            enrollmentDAO.enrollStudent(enrollment);

            refreshData();

            setStatus(
                    "Enrollment request processed. Verify the table."
            );

            showMessage(
                    "Enrollment request processed.\n"
                    + "Check the table to verify the result.",
                    "Enrollment",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            setStatus("Enrollment could not be processed.");
            showError("Unable to enroll this student.", ex);
        }
    }

    // REMOVE ENROLLMENT
    private void removeEnrollment() {

        int selectedRow = enrollmentTable.getSelectedRow();

        if (selectedRow < 0) {
            showMessage(
                    "Please select an enrollment from the table first.",
                    "No Enrollment Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow =
                enrollmentTable.convertRowIndexToModel(selectedRow);

        int studentId = Integer.parseInt(
                tableModel.getValueAt(modelRow, 0).toString()
        );

        int subjectId = Integer.parseInt(
                tableModel.getValueAt(modelRow, 2).toString()
        );

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Remove this student's subject enrollment?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {

            try {
                enrollmentDAO.removeEnrollment(studentId, subjectId);

                refreshData();
                clearSelection();

                setStatus(
                        "Removal request processed. Verify the table."
                );

                showMessage(
                        "Removal request processed.\n"
                        + "Check the table to verify the result.",
                        "Enrollment Removal",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception ex) {
                setStatus("Enrollment removal could not be processed.");
                showError("Unable to remove this enrollment.", ex);
            }
        }
    }

    // SELECTION HELPERS
    private void selectStudentById(int studentId) {

        for (int i = 0; i < studentCombo.getItemCount(); i++) {

            Student student = studentCombo.getItemAt(i);

            if (student.getStudentId() == studentId) {
                studentCombo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selectSubjectById(int subjectId) {

        for (int i = 0; i < subjectCombo.getItemCount(); i++) {

            Subject subject = subjectCombo.getItemAt(i);

            if (subject.getId() == subjectId) {
                subjectCombo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void clearSelection() {

        studentCombo.setSelectedIndex(-1);
        subjectCombo.setSelectedIndex(-1);
        enrollmentTable.clearSelection();

        setStatus("Selection cleared.");
    }

    // MESSAGES AND STATUS
    private void setStatus(String message) {

        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }

    private void showMessage(
            String message,
            String title,
            int messageType) {

        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                messageType
        );
    }

    private void showError(String message, Exception ex) {

        ex.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                message + "\n\nDetails: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // MAIN METHOD
    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                EnrollmentManagementFrame::new
        );
    }
}
