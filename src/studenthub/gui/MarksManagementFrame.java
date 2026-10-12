
package studenthub.gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

import studenthub.dao.MarksDAO;
import studenthub.model.Marks;

public class MarksManagementFrame extends JFrame {

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

    private JTextField markIdField;
    private JTextField studentIdField;
    private JTextField subjectIdField;
    private JTextField examTypeField;
    private JTextField marksObtainedField;
    private JTextField maxMarksField;

    private JLabel resultLabel;
    private JLabel countLabel;
    private JLabel statusLabel;

    private JTable marksTable;
    private DefaultTableModel tableModel;

    private final MarksDAO marksDAO = new MarksDAO();

    public MarksManagementFrame() {

        setTitle("StudentAcademicHub | Marks Management");
        setSize(1150, 790);
        setMinimumSize(new Dimension(950, 680));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        refreshMarks();
        setVisible(true);
    }

    // --------------------------------------------------
    // HEADER
    // --------------------------------------------------

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout(16, 5));
        header.setBackground(PANEL);
        header.setBorder(new EmptyBorder(20, 26, 20, 26));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Marks Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 25));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Manage exam scores, calculate percentages and track grades"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(subtitle);

        JLabel logo = new JLabel("M", SwingConstants.CENTER);
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

        JPanel main = new JPanel(new BorderLayout(0, 16));
        main.setBackground(BACKGROUND);
        main.setBorder(new EmptyBorder(18, 22, 18, 22));

        main.add(createFormPanel(), BorderLayout.NORTH);
        main.add(createTablePanel(), BorderLayout.CENTER);

        return main;
    }

    // --------------------------------------------------
    // MARKS FORM
    // --------------------------------------------------

    private JPanel createFormPanel() {

        JPanel formPanel = new JPanel(new BorderLayout(0, 14));
        formPanel.setBackground(PANEL);
        formPanel.setBorder(
                new CompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(16, 18, 16, 18)
                )
        );

        JLabel sectionTitle = new JLabel("Exam Details");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        sectionTitle.setForeground(TEXT);

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 3, 14, 12));
        fieldsPanel.setOpaque(false);

        markIdField = createTextField();
        studentIdField = createTextField();
        subjectIdField = createTextField();
        examTypeField = createTextField();
        marksObtainedField = createTextField();
        maxMarksField = createTextField();

        markIdField.setToolTipText(
                "Required when updating or deleting a record"
        );

        fieldsPanel.add(
                createFieldGroup("MARK ID (UPDATE / DELETE)", markIdField)
        );
        fieldsPanel.add(
                createFieldGroup("STUDENT ID", studentIdField)
        );
        fieldsPanel.add(
                createFieldGroup("SUBJECT ID", subjectIdField)
        );
        fieldsPanel.add(
                createFieldGroup("EXAM TYPE", examTypeField)
        );
        fieldsPanel.add(
                createFieldGroup("MARKS OBTAINED", marksObtainedField)
        );
        fieldsPanel.add(
                createFieldGroup("MAXIMUM MARKS", maxMarksField)
        );

        JPanel resultPanel = new JPanel(new BorderLayout(10, 0));
        resultPanel.setBackground(INPUT);
        resultPanel.setBorder(
                new CompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(10, 12, 10, 12)
                )
        );

        JLabel resultHeading = new JLabel("CALCULATED RESULT");
        resultHeading.setFont(new Font("Segoe UI", Font.BOLD, 11));
        resultHeading.setForeground(MUTED);

        resultLabel = new JLabel(
                "Percentage and grade will appear here."
        );
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        resultLabel.setForeground(TEAL);

        resultPanel.add(resultHeading, BorderLayout.WEST);
        resultPanel.add(resultLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 9, 0)
        );
        buttonPanel.setOpaque(false);

        JButton addButton = createButton("Add Marks", true);
        JButton viewButton = createButton("Refresh Records", false);
        JButton updateButton = createButton("Update Marks", false);
        JButton deleteButton = createButton("Delete Marks", false);
        JButton calculateButton = createButton("Calculate Result", false);
        JButton clearButton = createButton("Clear Fields", false);

        addButton.addActionListener(e -> addMarks());
        viewButton.addActionListener(e -> viewMarks());
        updateButton.addActionListener(e -> updateMarks());
        deleteButton.addActionListener(e -> deleteMarks());
        calculateButton.addActionListener(e -> calculateResult());
        clearButton.addActionListener(e -> clearFields());

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);

        JPanel lowerPanel = new JPanel(new BorderLayout(0, 12));
        lowerPanel.setOpaque(false);
        lowerPanel.add(resultPanel, BorderLayout.NORTH);
        lowerPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setOpaque(false);
        content.add(fieldsPanel, BorderLayout.CENTER);
        content.add(lowerPanel, BorderLayout.SOUTH);

        formPanel.add(sectionTitle, BorderLayout.NORTH);
        formPanel.add(content, BorderLayout.CENTER);

        return formPanel;
    }

    private JPanel createFieldGroup(
            String labelText,
            JComponent field) {

        JPanel group = new JPanel(new BorderLayout(0, 6));
        group.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 10));
        label.setForeground(MUTED);

        group.add(label, BorderLayout.NORTH);
        group.add(field, BorderLayout.CENTER);

        return group;
    }

    private JTextField createTextField() {

        JTextField field = new JTextField();

        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBackground(INPUT);
        field.setCaretColor(TEAL);
        field.setPreferredSize(new Dimension(150, 36));

        field.setBorder(
                new CompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(7, 9, 7, 9)
                )
        );

        return field;
    }

    // --------------------------------------------------
    // MARKS TABLE
    // --------------------------------------------------

    private JPanel createTablePanel() {

        JPanel tablePanel = new JPanel(new BorderLayout(0, 12));
        tablePanel.setBackground(PANEL);
        tablePanel.setBorder(
                new CompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        new EmptyBorder(15, 15, 15, 15)
                )
        );

        JPanel headingPanel = new JPanel(new BorderLayout());
        headingPanel.setOpaque(false);

        JLabel tableTitle = new JLabel("Marks Records");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        tableTitle.setForeground(TEXT);

        countLabel = new JLabel("0 records");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLabel.setForeground(TEAL);

        headingPanel.add(tableTitle, BorderLayout.WEST);
        headingPanel.add(countLabel, BorderLayout.EAST);

        tableModel = new DefaultTableModel(
                new String[]{
                        "Mark ID",
                        "Student ID",
                        "Subject ID",
                        "Exam Type",
                        "Obtained",
                        "Maximum",
                        "Percentage",
                        "Grade"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        marksTable = new JTable(tableModel);
        marksTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        marksTable.setForeground(TEXT);
        marksTable.setBackground(PANEL);
        marksTable.setSelectionBackground(
                new Color(27, 104, 111)
        );
        marksTable.setSelectionForeground(Color.WHITE);
        marksTable.setRowHeight(34);
        marksTable.setShowVerticalLines(false);
        marksTable.setShowHorizontalLines(true);
        marksTable.setGridColor(BORDER);
        marksTable.setFillsViewportHeight(true);
        marksTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        marksTable.setIntercellSpacing(new Dimension(0, 1));
        marksTable.setAutoCreateRowSorter(true);

        JTableHeader header = marksTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setForeground(TEAL);
        header.setBackground(INPUT);
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);

        marksTable.setDefaultRenderer(
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

                        setBorder(new EmptyBorder(0, 8, 0, 8));

                        return component;
                    }
                }
        );

        JScrollPane scrollPane = new JScrollPane(marksTable);
        scrollPane.setBorder(new LineBorder(BORDER, 1, true));
        scrollPane.getViewport().setBackground(PANEL);

        marksTable.getSelectionModel().addListSelectionListener(e -> {

            if (e.getValueIsAdjusting()) {
                return;
            }

            int selectedRow = marksTable.getSelectedRow();

            if (selectedRow >= 0) {

                int row = marksTable.convertRowIndexToModel(selectedRow);

                markIdField.setText(
                        tableModel.getValueAt(row, 0).toString()
                );
                studentIdField.setText(
                        tableModel.getValueAt(row, 1).toString()
                );
                subjectIdField.setText(
                        tableModel.getValueAt(row, 2).toString()
                );
                examTypeField.setText(
                        tableModel.getValueAt(row, 3).toString()
                );
                marksObtainedField.setText(
                        tableModel.getValueAt(row, 4).toString()
                );
                maxMarksField.setText(
                        tableModel.getValueAt(row, 5).toString()
                );

                resultLabel.setText(
                        tableModel.getValueAt(row, 6).toString()
                        + "% - Grade "
                        + tableModel.getValueAt(row, 7).toString()
                );

                setStatus("Marks record selected.");
            }
        });

        tablePanel.add(headingPanel, BorderLayout.NORTH);
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
    // LOAD AND REFRESH RECORDS
    // --------------------------------------------------

    private void refreshMarks() {

        try {
            List<Marks> list = marksDAO.getAllMarksList();

            tableModel.setRowCount(0);

            if (list != null) {
                for (Marks marks : list) {

                    double percentage = marksDAO.getPercentage(
                            marks.getMarksObtained(),
                            marks.getMaxMarks()
                    );

                    String grade = marksDAO.getGrade(percentage);

                    tableModel.addRow(new Object[]{
                            marks.getMarkId(),
                            marks.getStudentId(),
                            marks.getSubjectId(),
                            marks.getExamType(),
                            marks.getMarksObtained(),
                            marks.getMaxMarks(),
                            String.format("%.2f", percentage),
                            grade
                    });
                }
            }

            updateRecordCount();
            setStatus("Marks records refreshed.");

        } catch (Exception ex) {
            setStatus("Unable to load marks records.");
            showError("Could not load marks records.", ex);
        }
    }

    private void updateRecordCount() {

        int count = tableModel.getRowCount();

        countLabel.setText(
                count + (count == 1 ? " record" : " records")
        );
    }

    private void viewMarks() {
        refreshMarks();
    }

    // --------------------------------------------------
    // READ AND VALIDATE FORM
    // --------------------------------------------------

    private Marks readForm(int id) {

        String examType = examTypeField.getText().trim();

        if (examType.isEmpty()) {
            throw new IllegalArgumentException("Enter exam type.");
        }

        int studentId = Integer.parseInt(
                studentIdField.getText().trim()
        );

        int subjectId = Integer.parseInt(
                subjectIdField.getText().trim()
        );

        double obtained = Double.parseDouble(
                marksObtainedField.getText().trim()
        );

        double maximum = Double.parseDouble(
                maxMarksField.getText().trim()
        );

        if (studentId <= 0 || subjectId <= 0) {
            throw new IllegalArgumentException(
                    "Student ID and Subject ID must be positive numbers."
            );
        }

        if (!Double.isFinite(obtained)
                || !Double.isFinite(maximum)) {
            throw new IllegalArgumentException(
                    "Enter valid numeric marks."
            );
        }

        if (maximum <= 0 || obtained < 0 || obtained > maximum) {
            throw new IllegalArgumentException(
                    "Obtained marks must be between 0 and maximum marks."
            );
        }

        return new Marks(
                id, studentId, subjectId,
                examType, obtained, maximum
        );
    }

    // --------------------------------------------------
    // ADD MARKS
    // --------------------------------------------------

    private void addMarks() {

        try {
            Marks marks = readForm(0);
            marksDAO.addMarks(marks);

            refreshMarks();

            setStatus("Add request processed. Verify the table.");

            showMessage(
                    "Add request processed.\n"
                    + "Check the table to verify the result.",
                    "Marks",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {
            showError("Enter valid numeric IDs and marks.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        } catch (Exception ex) {
            showError("Could not add marks.", ex);
        }
    }

    // --------------------------------------------------
    // UPDATE MARKS
    // --------------------------------------------------

    private void updateMarks() {

        try {
            int id = Integer.parseInt(markIdField.getText().trim());

            if (id <= 0) {
                showError("Mark ID must be a positive number.");
                return;
            }

            if (marksDAO.getMarksById(id) == null) {
                showError("No marks record found for this Mark ID.");
                return;
            }

            Marks marks = readForm(id);
            marksDAO.updateMarks(marks);

            refreshMarks();

            setStatus("Update request processed. Verify the table.");

            showMessage(
                    "Update request processed.\n"
                    + "Check the table to verify the result.",
                    "Marks",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {
            showError(
                    "Enter a valid Mark ID, Student ID, Subject ID and marks."
            );
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        } catch (Exception ex) {
            showError("Could not update marks.", ex);
        }
    }

    // --------------------------------------------------
    // DELETE MARKS
    // --------------------------------------------------

    private void deleteMarks() {

        try {
            int id = Integer.parseInt(markIdField.getText().trim());

            if (id <= 0) {
                showError("Mark ID must be a positive number.");
                return;
            }

            if (marksDAO.getMarksById(id) == null) {
                showError("No marks record found for this Mark ID.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Delete marks record ID " + id + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {

                marksDAO.deleteMarks(id);

                refreshMarks();
                clearFields();

                setStatus("Delete request processed. Verify the table.");

                showMessage(
                        "Delete request processed.\n"
                        + "Check the table to verify the result.",
                        "Marks",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {
            showError("Enter a valid Mark ID.");
        } catch (Exception ex) {
            showError("Could not delete marks.", ex);
        }
    }

    // --------------------------------------------------
    // CALCULATE RESULT
    // --------------------------------------------------

    private void calculateResult() {

        try {
            double obtained = Double.parseDouble(
                    marksObtainedField.getText().trim()
            );

            double maximum = Double.parseDouble(
                    maxMarksField.getText().trim()
            );

            if (!Double.isFinite(obtained)
                    || !Double.isFinite(maximum)
                    || maximum <= 0
                    || obtained < 0
                    || obtained > maximum) {

                showError(
                        "Obtained marks must be between 0 and maximum marks."
                );
                return;
            }

            double percentage =
                    marksDAO.getPercentage(obtained, maximum);

            String grade = marksDAO.getGrade(percentage);

            resultLabel.setText(
                    String.format("%.2f%% - Grade %s", percentage, grade)
            );

            setStatus("Result calculated.");

        } catch (NumberFormatException ex) {
            showError("Enter valid numbers for both marks fields.");
        } catch (Exception ex) {
            showError("Could not calculate the result.", ex);
        }
    }

    // --------------------------------------------------
    // CLEAR FIELDS
    // --------------------------------------------------

    private void clearFields() {

        markIdField.setText("");
        studentIdField.setText("");
        subjectIdField.setText("");
        examTypeField.setText("");
        marksObtainedField.setText("");
        maxMarksField.setText("");

        resultLabel.setText(
                "Percentage and grade will appear here."
        );

        marksTable.clearSelection();
        setStatus("Fields cleared.");
    }

    // --------------------------------------------------
    // MESSAGES AND STATUS
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
                this, message, title, messageType
        );
    }

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Marks Management",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showError(String message, Exception ex) {

        ex.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                message + "\n\nDetails: " + ex.getMessage(),
                "Marks Management Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // --------------------------------------------------
    // MAIN METHOD
    // --------------------------------------------------

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                MarksManagementFrame::new
        );
    }
}
