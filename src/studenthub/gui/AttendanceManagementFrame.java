
package studenthub.gui;

import studenthub.dao.AttendanceDAO;
import studenthub.dao.StudentDAO;
import studenthub.dao.SubjectDAO;
import studenthub.model.Attendance;
import studenthub.model.Student;
import studenthub.model.Subject;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AttendanceManagementFrame extends JFrame {

    // Premium navy and teal theme
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
    private JTextField dateField;
    private JComboBox<String> statusCombo;

    private JTable attendanceTable;
    private DefaultTableModel tableModel;
    private JLabel countLabel;
    private JLabel statusLabel;

    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();

    public AttendanceManagementFrame() {

        setTitle("StudentAcademicHub | Attendance Management");
        setSize(1050, 750);
        setMinimumSize(new Dimension(900, 650));
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

    // --------------------------------------------------
    // HEADER
    // --------------------------------------------------

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout(16, 5));
        header.setBackground(PANEL);
        header.setBorder(new EmptyBorder(22, 28, 22, 28));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Attendance Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 25));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Record attendance, update status and monitor attendance percentage"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(subtitle);

        JLabel logo = new JLabel("A", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 25));
        logo.setForeground(Color.WHITE);
        logo.setOpaque(true);
        logo.setBackground(TEAL);
        logo.setPreferredSize(new Dimension(54, 54));

        header.add(logo, BorderLayout.WEST);
        header.add(titlePanel, BorderLayout.CENTER);

        return header;
    }

    // --------------------------------------------------
    // MAIN CONTENT
    // --------------------------------------------------

    private JPanel createMainContent() {

        JPanel main = new JPanel(new BorderLayout(0, 18));
        main.setBackground(BACKGROUND);
        main.setBorder(new EmptyBorder(20, 24, 20, 24));

        main.add(createFormPanel(), BorderLayout.NORTH);
        main.add(createTablePanel(), BorderLayout.CENTER);

        return main;
    }

    // --------------------------------------------------
    // ATTENDANCE FORM
    // --------------------------------------------------

    private JPanel createFormPanel() {

        JPanel formPanel = new JPanel(new BorderLayout(0, 17));
        formPanel.setBackground(PANEL);
        formPanel.setBorder(
                new CompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(18, 20, 18, 20)
                )
        );

        JLabel sectionTitle = new JLabel("Attendance Details");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        sectionTitle.setForeground(TEXT);

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 18, 14));
        fieldsPanel.setOpaque(false);

        studentCombo = createStudentComboBox();
        subjectCombo = createSubjectComboBox();

        dateField = new JTextField();
        styleTextField(dateField);
        dateField.setToolTipText("Enter date as YYYY-MM-DD");

        statusCombo = new JComboBox<>(
                new String[]{"Present", "Absent"}
        );
        styleComboBox(statusCombo);

        fieldsPanel.add(
                createFieldGroup("SELECT STUDENT", studentCombo)
        );
        fieldsPanel.add(
                createFieldGroup("SELECT SUBJECT", subjectCombo)
        );
        fieldsPanel.add(
                createFieldGroup("DATE (YYYY-MM-DD)", dateField)
        );
        fieldsPanel.add(
                createFieldGroup("ATTENDANCE STATUS", statusCombo)
        );

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 9, 0)
        );
        buttonPanel.setOpaque(false);

        JButton addButton = createButton("Add Attendance", true);
        JButton updateButton = createButton("Update Record", false);
        JButton deleteButton = createButton("Delete Record", false);
        JButton refreshButton = createButton("Refresh Data", false);
        JButton percentageButton =
                createButton("Attendance Summary", false);
        JButton clearButton = createButton("Clear Fields", false);

        addButton.addActionListener(e -> addAttendance());
        updateButton.addActionListener(e -> updateAttendance());
        deleteButton.addActionListener(e -> deleteAttendance());
        refreshButton.addActionListener(e -> refreshData());
        percentageButton.addActionListener(e -> showPercentage());
        clearButton.addActionListener(e -> clearFields());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(percentageButton);
        buttonPanel.add(clearButton);

        JPanel lowerPanel = new JPanel(new BorderLayout(0, 17));
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

        JPanel group = new JPanel(new BorderLayout(0, 7));
        group.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(MUTED);

        group.add(label, BorderLayout.NORTH);
        group.add(field, BorderLayout.CENTER);

        return group;
    }

    // Correctly typed student dropdown
    private JComboBox<Student> createStudentComboBox() {

        JComboBox<Student> combo = new JComboBox<>();
        styleComboBox(combo);
        return combo;
    }

    // Correctly typed subject dropdown
    private JComboBox<Subject> createSubjectComboBox() {

        JComboBox<Subject> combo = new JComboBox<>();
        styleComboBox(combo);
        return combo;
    }

    private void styleComboBox(JComboBox<?> combo) {

        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(INPUT);
        combo.setForeground(TEXT);
        combo.setPreferredSize(new Dimension(200, 40));
        combo.setFocusable(true);

        combo.setBorder(
                new CompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(4, 8, 4, 8)
                )
        );
    }

    private void styleTextField(JTextField field) {

        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBackground(INPUT);
        field.setCaretColor(TEAL);
        field.setPreferredSize(new Dimension(200, 40));

        field.setBorder(
                new CompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );
    }

    // --------------------------------------------------
    // ATTENDANCE TABLE
    // --------------------------------------------------

    private JPanel createTablePanel() {

        JPanel tablePanel = new JPanel(new BorderLayout(0, 13));
        tablePanel.setBackground(PANEL);
        tablePanel.setBorder(
                new CompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(16, 16, 16, 16)
                )
        );

        JPanel tableHeading = new JPanel(new BorderLayout());
        tableHeading.setOpaque(false);

        JLabel tableTitle = new JLabel("Attendance Records");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        tableTitle.setForeground(TEXT);

        countLabel = new JLabel("0 records");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLabel.setForeground(TEAL);

        tableHeading.add(tableTitle, BorderLayout.WEST);
        tableHeading.add(countLabel, BorderLayout.EAST);

        tableModel = new DefaultTableModel(
                new String[]{
                        "Attendance ID",
                        "Student ID",
                        "Subject ID",
                        "Date",
                        "Status"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        attendanceTable = new JTable(tableModel);
        attendanceTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        attendanceTable.setForeground(TEXT);
        attendanceTable.setBackground(PANEL);
        attendanceTable.setSelectionBackground(
                new Color(27, 104, 111)
        );
        attendanceTable.setSelectionForeground(Color.WHITE);
        attendanceTable.setRowHeight(35);
        attendanceTable.setShowVerticalLines(false);
        attendanceTable.setShowHorizontalLines(true);
        attendanceTable.setGridColor(BORDER);
        attendanceTable.setFillsViewportHeight(true);
        attendanceTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        attendanceTable.setIntercellSpacing(new Dimension(0, 1));
        attendanceTable.setAutoCreateRowSorter(true);

        JTableHeader header = attendanceTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setForeground(TEAL);
        header.setBackground(INPUT);
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);

        attendanceTable.setDefaultRenderer(
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
                                        table, value, isSelected,
                                        hasFocus, row, column
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

                        setBorder(new EmptyBorder(0, 10, 0, 10));

                        return component;
                    }
                }
        );

        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        scrollPane.setBorder(new LineBorder(BORDER, 1, true));
        scrollPane.getViewport().setBackground(PANEL);

        // Convert selected view row to the correct model row.
        attendanceTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (e.getValueIsAdjusting()) {
                        return;
                    }

                    int selectedRow = attendanceTable.getSelectedRow();

                    if (selectedRow >= 0) {

                        int row = attendanceTable.convertRowIndexToModel(
                                selectedRow
                        );

                        int studentId = Integer.parseInt(
                                tableModel.getValueAt(row, 1).toString()
                        );

                        int subjectId = Integer.parseInt(
                                tableModel.getValueAt(row, 2).toString()
                        );

                        selectStudentById(studentId);
                        selectSubjectById(subjectId);

                        dateField.setText(
                                tableModel.getValueAt(row, 3).toString()
                        );

                        statusCombo.setSelectedItem(
                                tableModel.getValueAt(row, 4).toString()
                        );

                        setStatus("Attendance record selected.");
                    }
                });

        tablePanel.add(tableHeading, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    // --------------------------------------------------
    // FOOTER
    // --------------------------------------------------

    private JPanel createFooter() {

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(PANEL);
        footer.setBorder(new EmptyBorder(10, 24, 10, 24));

        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(MUTED);

        JLabel footerText = new JLabel("StudentAcademicHub");
        footerText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerText.setForeground(MUTED);

        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(footerText, BorderLayout.EAST);

        return footer;
    }

    // --------------------------------------------------
    // BUTTONS AND HOVER EFFECTS
    // --------------------------------------------------

    private JButton createButton(String text, boolean primary) {

        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
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
                        new EmptyBorder(9, 10, 9, 10)
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

    // --------------------------------------------------
    // DATABASE LOADING
    // --------------------------------------------------

    private void refreshData() {

        try {
            loadStudents();
            loadSubjects();
            loadAttendance();

            setStatus("Student, subject and attendance data refreshed.");

        } catch (Exception ex) {
            setStatus("Unable to refresh attendance data.");
            showError("Could not load attendance data.", ex);
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

    private void loadAttendance() {

        tableModel.setRowCount(0);

        List<Attendance> records =
                attendanceDAO.fetchAllAttendance();

        if (records != null) {
            for (Attendance attendance : records) {
                tableModel.addRow(new Object[]{
                        attendance.getAttendanceId(),
                        attendance.getStudentId(),
                        attendance.getSubjectId(),
                        attendance.getAttendanceDate(),
                        attendance.getStatus()
                });
            }
        }

        updateRecordCount();
    }

    private void updateRecordCount() {

        int count = tableModel.getRowCount();

        countLabel.setText(
                count + (count == 1 ? " record" : " records")
        );
    }

    // --------------------------------------------------
    // ADD ATTENDANCE
    // --------------------------------------------------

    private void addAttendance() {

        if (!validateFields()) {
            return;
        }

        Student student = (Student) studentCombo.getSelectedItem();
        Subject subject = (Subject) subjectCombo.getSelectedItem();

        Attendance attendance = new Attendance();
        attendance.setStudentId(student.getStudentId());
        attendance.setSubjectId(subject.getId());
        attendance.setAttendanceDate(dateField.getText().trim());
        attendance.setStatus((String) statusCombo.getSelectedItem());

        try {
            attendanceDAO.addAttendance(attendance);

            refreshData();
            clearFields();

            setStatus("Add request processed. Verify the table.");

            showMessage(
                    "Add request processed.\n"
                    + "Check the table to verify the result.",
                    "Attendance",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            setStatus("Unable to add attendance.");
            showError("Could not add attendance.", ex);
        }
    }

    // --------------------------------------------------
    // UPDATE ATTENDANCE
    // --------------------------------------------------

    private void updateAttendance() {

        int selectedRow = attendanceTable.getSelectedRow();

        if (selectedRow < 0) {
            showMessage(
                    "Please select an attendance record from the table.",
                    "No Record Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!validateFields()) {
            return;
        }

        int row = attendanceTable.convertRowIndexToModel(selectedRow);

        Student student = (Student) studentCombo.getSelectedItem();
        Subject subject = (Subject) subjectCombo.getSelectedItem();

        Attendance attendance = new Attendance();

        attendance.setAttendanceId(
                Integer.parseInt(
                        tableModel.getValueAt(row, 0).toString()
                )
        );

        attendance.setStudentId(student.getStudentId());
        attendance.setSubjectId(subject.getId());
        attendance.setAttendanceDate(dateField.getText().trim());
        attendance.setStatus((String) statusCombo.getSelectedItem());

        try {
            attendanceDAO.updateAttendance(attendance);

            refreshData();
            clearFields();

            setStatus("Update request processed. Verify the table.");

            showMessage(
                    "Update request processed.\n"
                    + "Check the table to verify the result.",
                    "Attendance",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            setStatus("Unable to update attendance.");
            showError("Could not update attendance.", ex);
        }
    }

    // --------------------------------------------------
    // DELETE ATTENDANCE
    // --------------------------------------------------

    private void deleteAttendance() {

        int selectedRow = attendanceTable.getSelectedRow();

        if (selectedRow < 0) {
            showMessage(
                    "Please select an attendance record first.",
                    "No Record Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int row = attendanceTable.convertRowIndexToModel(selectedRow);

        int id = Integer.parseInt(
                tableModel.getValueAt(row, 0).toString()
        );

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this attendance record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {

            try {
                attendanceDAO.deleteAttendance(id);

                refreshData();
                clearFields();

                setStatus("Delete request processed. Verify the table.");

                showMessage(
                        "Delete request processed.\n"
                        + "Check the table to verify the result.",
                        "Attendance",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception ex) {
                setStatus("Unable to delete attendance.");
                showError("Could not delete attendance.", ex);
            }
        }
    }

    // --------------------------------------------------
    // ATTENDANCE PERCENTAGE
    // --------------------------------------------------

    private void showPercentage() {

        Student student = (Student) studentCombo.getSelectedItem();
        Subject subject = (Subject) subjectCombo.getSelectedItem();

        if (student == null || subject == null) {
            showMessage(
                    "Please select a student and a subject.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            int present = attendanceDAO.getPresentCount(
                    student.getStudentId(), subject.getId()
            );

            int total = attendanceDAO.getTotalClasses(
                    student.getStudentId(), subject.getId()
            );

            double percentage = attendanceDAO.getAttendancePercentage(
                    student.getStudentId(), subject.getId()
            );

            showMessage(
                    "Student: " + student.getName()
                    + "\nSubject: " + subject.getName()
                    + "\nPresent Classes: " + present
                    + "\nTotal Classes: " + total
                    + "\nAttendance Percentage: "
                    + String.format("%.2f%%", percentage),
                    "Attendance Summary",
                    JOptionPane.INFORMATION_MESSAGE
            );

            setStatus("Attendance summary displayed.");

        } catch (Exception ex) {
            setStatus("Unable to calculate attendance percentage.");
            showError("Could not calculate attendance percentage.", ex);
        }
    }

    // --------------------------------------------------
    // VALIDATION
    // --------------------------------------------------

    private boolean validateFields() {

        Student student = (Student) studentCombo.getSelectedItem();
        Subject subject = (Subject) subjectCombo.getSelectedItem();

        if (student == null || subject == null) {
            showMessage(
                    "Please select a student and a subject.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        String date = dateField.getText().trim();

        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException ex) {
            showMessage(
                    "Enter a valid date in YYYY-MM-DD format.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );
            dateField.requestFocusInWindow();
            return false;
        }

        return true;
    }

    // --------------------------------------------------
    // DROPDOWN SELECTION HELPERS
    // --------------------------------------------------

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

    // --------------------------------------------------
    // CLEAR FIELDS
    // --------------------------------------------------

    private void clearFields() {

        studentCombo.setSelectedIndex(-1);
        subjectCombo.setSelectedIndex(-1);
        dateField.setText("");
        statusCombo.setSelectedIndex(0);
        attendanceTable.clearSelection();

        setStatus("Fields cleared.");
    }

    // --------------------------------------------------
    // MESSAGE HELPERS
    // --------------------------------------------------

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
                "Attendance Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // --------------------------------------------------
    // MAIN METHOD
    // --------------------------------------------------

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                AttendanceManagementFrame::new
        );
    }
}
