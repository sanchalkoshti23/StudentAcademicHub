
package studenthub.gui;

import studenthub.dao.SubjectDAO;
import studenthub.model.Subject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SubjectManagementFrame extends JFrame {

    // =============== PREMIUM COLOR PALETTE ===============

    private static final Color BACKGROUND = new Color(7, 13, 27);
    private static final Color PANEL = new Color(15, 26, 44);
    private static final Color FIELD = new Color(9, 19, 34);
    private static final Color TEAL = new Color(45, 212, 191);
    private static final Color CYAN = new Color(56, 189, 248);
    private static final Color WHITE = new Color(240, 246, 255);
    private static final Color MUTED = new Color(158, 177, 201);
    private static final Color BORDER = new Color(43, 64, 88);
    private static final Color ALT_ROW = new Color(20, 35, 55);
    private static final Color SELECTED_ROW = new Color(22, 91, 91);

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

        for (String family : GraphicsEnvironment
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
    private JTextField codeField;
    private JTextField semesterField;
    private JTextField searchField;

    private JTable subjectTable;
    private DefaultTableModel tableModel;

    private SubjectDAO subjectDAO;

    private JLabel statusLabel;
    private JLabel countLabel;

    // =============== CONSTRUCTOR ===============

    public SubjectManagementFrame() {

        subjectDAO = new SubjectDAO();

        setTitle("StudentAcademicHub | Subject Management");
        setSize(1150, 760);
        setMinimumSize(new Dimension(900, 650));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
        loadSubjects();

        setVisible(true);
    }

    // =============== MAIN INTERFACE ===============

    private void createGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
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

        JLabel eyebrow = new JLabel(
                "STUDENT ACADEMICHUB  /  ACADEMICS"
        );
        eyebrow.setFont(bodyBold(11));
        eyebrow.setForeground(TEAL);
        eyebrow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel("Subject Management");
        heading.setFont(display(32));
        heading.setForeground(WHITE);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Manage subjects, course codes and semester information."
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

        // ---------- MAIN CONTENT ----------

        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setOpaque(false);

        // ---------- SUBJECT DETAILS FORM ----------

        JPanel formPanel = new JPanel(
                new GridLayout(2, 4, 16, 12)
        );
        formPanel.setBackground(PANEL);
        formPanel.setBorder(createSectionBorder("SUBJECT DETAILS"));

        idField = new JTextField();
        idField.setEditable(false);

        nameField = new JTextField();
        codeField = new JTextField();
        semesterField = new JTextField();

        styleField(idField);
        styleField(nameField);
        styleField(codeField);
        styleField(semesterField);

        idField.setToolTipText("Automatically assigned subject ID");
        nameField.setToolTipText("Enter the subject name");
        codeField.setToolTipText("Enter the subject or course code");
        semesterField.setToolTipText("Enter a semester from 1 to 8");

        addFormItem(formPanel, "Subject ID", idField);
        addFormItem(formPanel, "Subject Name", nameField);
        addFormItem(formPanel, "Subject Code", codeField);
        addFormItem(formPanel, "Semester (1–8)", semesterField);

        // ---------- ACTION BUTTONS ----------

        JButton addButton = createButton(
                "＋  Add Subject", new Color(13, 148, 136)
        );

        JButton viewButton = createButton(
                "↻  Refresh", new Color(37, 99, 170)
        );

        JButton updateButton = createButton(
                "✎  Update Subject", new Color(108, 87, 205)
        );

        JButton deleteButton = createButton(
                "✕  Delete Subject", new Color(190, 55, 76)
        );

        JButton clearButton = createButton(
                "Clear Fields", new Color(66, 83, 106)
        );

        JPanel actions = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 9, 8)
        );
        actions.setBackground(PANEL);

        actions.add(addButton);
        actions.add(viewButton);
        actions.add(updateButton);
        actions.add(deleteButton);
        actions.add(clearButton);

        JPanel formSection = new JPanel(new BorderLayout(0, 10));
        formSection.setOpaque(false);
        formSection.add(formPanel, BorderLayout.CENTER);
        formSection.add(actions, BorderLayout.SOUTH);

        // ---------- SEARCH SECTION ----------

        JPanel searchPanel = new JPanel(
                new BorderLayout(12, 0)
        );
        searchPanel.setBackground(PANEL);
        searchPanel.setBorder(
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        );

        JPanel searchLeft = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 2)
        );
        searchLeft.setOpaque(false);

        JLabel searchLabel = createLabel("Find Subject");

        searchField = new JTextField(20);
        styleField(searchField);
        searchField.setToolTipText(
                "Search by subject name, course code or semester"
        );

        JButton searchButton = createButton(
                "Search", new Color(13, 148, 136)
        );

        JButton showAllButton = createButton(
                "Show All", new Color(66, 83, 106)
        );

        searchLeft.add(searchLabel);
        searchLeft.add(searchField);
        searchLeft.add(searchButton);
        searchLeft.add(showAllButton);

        countLabel = new JLabel("0 SUBJECTS");
        countLabel.setFont(bodyBold(11));
        countLabel.setForeground(TEAL);
        countLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        searchPanel.add(searchLeft, BorderLayout.CENTER);
        searchPanel.add(countLabel, BorderLayout.EAST);

        // ---------- SUBJECT TABLE ----------

        tableModel = new DefaultTableModel(
                new String[]{
                    "Subject ID",
                    "Subject Name",
                    "Subject Code",
                    "Semester"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        subjectTable = new JTable(tableModel);

        subjectTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        subjectTable.setFont(body(13));
        subjectTable.setRowHeight(39);
        subjectTable.setAutoCreateRowSorter(true);
        subjectTable.setForeground(WHITE);
        subjectTable.setBackground(PANEL);
        subjectTable.setSelectionBackground(SELECTED_ROW);
        subjectTable.setSelectionForeground(Color.WHITE);
        subjectTable.setGridColor(BORDER);
        subjectTable.setShowGrid(true);
        subjectTable.setFillsViewportHeight(true);
        subjectTable.setIntercellSpacing(new Dimension(1, 1));
        subjectTable.setRowMargin(1);

        // Table header

        subjectTable.getTableHeader().setFont(bodyBold(13));
        subjectTable.getTableHeader().setBackground(
                new Color(20, 43, 63)
        );
        subjectTable.getTableHeader().setForeground(TEAL);
        subjectTable.getTableHeader().setPreferredSize(
                new Dimension(0, 43)
        );
        subjectTable.getTableHeader().setReorderingAllowed(false);
        subjectTable.getTableHeader().setBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 2, 0, TEAL
                )
        );

        // Alternating row renderer

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
                        0, 12, 0, 12
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

        for (int i = 0;
             i < subjectTable.getColumnCount(); i++) {

            subjectTable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(renderer);
        }

        subjectTable.getColumnModel()
                .getColumn(0).setPreferredWidth(110);

        subjectTable.getColumnModel()
                .getColumn(1).setPreferredWidth(300);

        subjectTable.getColumnModel()
                .getColumn(2).setPreferredWidth(220);

        subjectTable.getColumnModel()
                .getColumn(3).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(subjectTable);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );
        scrollPane.getViewport().setBackground(PANEL);

        // ---------- RECORDS SECTION ----------

        JLabel recordsTitle = new JLabel("SUBJECT RECORDS");
        recordsTitle.setFont(display(18));
        recordsTitle.setForeground(WHITE);

        JLabel recordsSubtitle = new JLabel(
                "Select a row to view, edit or delete a subject."
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

        JPanel recordsTop = new JPanel(
                new BorderLayout(0, 12)
        );
        recordsTop.setOpaque(false);
        recordsTop.add(recordsHeading, BorderLayout.NORTH);
        recordsTop.add(searchPanel, BorderLayout.CENTER);

        JPanel recordsSection = new JPanel(
                new BorderLayout(0, 12)
        );
        recordsSection.setOpaque(false);
        recordsSection.add(recordsTop, BorderLayout.NORTH);
        recordsSection.add(scrollPane, BorderLayout.CENTER);

        content.add(formSection, BorderLayout.NORTH);
        content.add(recordsSection, BorderLayout.CENTER);

        mainPanel.add(content, BorderLayout.CENTER);

        // ---------- FOOTER ----------

        JLabel footer = new JLabel(
                "StudentAcademicHub   •   Academic Workspace"
        );
        footer.setFont(body(11));
        footer.setForeground(MUTED);

        statusLabel = new JLabel("●  SYSTEM READY");
        statusLabel.setFont(bodyBold(10));
        statusLabel.setForeground(TEAL);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);
        footerPanel.add(footer, BorderLayout.WEST);
        footerPanel.add(statusLabel, BorderLayout.EAST);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // ---------- BUTTON ACTIONS ----------

        addButton.addActionListener(e -> addSubject());
        viewButton.addActionListener(e -> loadSubjects());
        updateButton.addActionListener(e -> updateSubject());
        deleteButton.addActionListener(e -> deleteSubject());
        clearButton.addActionListener(e -> clearFields());

        searchButton.addActionListener(e -> searchSubjects());

        searchField.addActionListener(e -> searchSubjects());

        showAllButton.addActionListener(e -> {
            searchField.setText("");
            loadSubjects();
        });

        // ---------- TABLE ROW SELECTION ----------

        subjectTable.getSelectionModel()
                .addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()
                    && subjectTable.getSelectedRow() != -1) {

                int selectedRow = subjectTable.getSelectedRow();

                // Important when the table is sorted.
                int row = subjectTable.convertRowIndexToModel(
                        selectedRow
                );

                idField.setText(
                        tableModel.getValueAt(row, 0).toString()
                );

                nameField.setText(
                        tableModel.getValueAt(row, 1).toString()
                );

                codeField.setText(
                        tableModel.getValueAt(row, 2).toString()
                );

                semesterField.setText(
                        tableModel.getValueAt(row, 3).toString()
                );

                setStatus("SUBJECT SELECTED", TEAL);
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
            JPanel panel,
            String label,
            JTextField field) {

        panel.add(createLabel(label));
        panel.add(field);
    }

    private void styleField(JTextField field) {

        field.setFont(body(14));
        field.setForeground(WHITE);
        field.setCaretColor(TEAL);
        field.setBackground(FIELD);
        field.setPreferredSize(new Dimension(150, 38));
        field.setSelectionColor(new Color(24, 104, 105));
        field.setSelectedTextColor(Color.WHITE);

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));
    }

    private JButton createButton(
            String text,
            Color baseColor) {

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
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));

        // Hover effect

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(
                    java.awt.event.MouseEvent e) {

                button.setBackground(baseColor.brighter());
            }

            @Override
            public void mouseExited(
                    java.awt.event.MouseEvent e) {

                button.setBackground(baseColor);
            }
        });

        return button;
    }

    private void setStatus(String message, Color color) {

        if (statusLabel != null) {
            statusLabel.setText("●  " + message);
            statusLabel.setForeground(color);
        }
    }

    // =============== ADD SUBJECT ===============

    private void addSubject() {

        if (!validateFields()) {
            return;
        }

        try {
            Subject subject = new Subject();

            subject.setName(nameField.getText().trim());
            subject.setCourse(codeField.getText().trim());
            subject.setSemester(
                    Integer.parseInt(semesterField.getText().trim())
            );

            boolean success = subjectDAO.addSubject(subject);

            if (success) {

                showMessage(
                        "Subject added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadSubjects();
                setStatus("SUBJECT ADDED", TEAL);

            } else {

                showMessage(
                        "Could not add subject. Check the console for details.",
                        "Unable to Add Subject",
                        JOptionPane.WARNING_MESSAGE
                );

                setStatus("ADD FAILED", new Color(248, 113, 113));
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    // =============== LOAD ALL SUBJECTS ===============

    private void loadSubjects() {

        try {
            tableModel.setRowCount(0);

            List<Subject> subjects =
                    subjectDAO.fetchAllSubjects();

            if (subjects != null) {
                for (Subject subject : subjects) {

                    tableModel.addRow(new Object[]{
                        subject.getId(),
                        subject.getName(),
                        subject.getCourse(),
                        subject.getSemester()
                    });
                }
            }

            updateSubjectCount();

            setStatus("RECORDS LOADED", TEAL);

        } catch (Exception ex) {
            showError(ex);
        }
    }

    // =============== SEARCH SUBJECTS ===============

    private void searchSubjects() {

        String keyword = searchField.getText()
                .trim()
                .toLowerCase();

        if (keyword.isEmpty()) {
            loadSubjects();
            return;
        }

        try {
            List<Subject> subjects =
                    subjectDAO.fetchAllSubjects();

            tableModel.setRowCount(0);

            if (subjects != null) {

                for (Subject subject : subjects) {

                    String name = subject.getName() == null
                            ? ""
                            : subject.getName().toLowerCase();

                    String code = subject.getCourse() == null
                            ? ""
                            : subject.getCourse().toLowerCase();

                    String semester = String.valueOf(
                            subject.getSemester()
                    );

                    String id = String.valueOf(subject.getId());

                    if (name.contains(keyword)
                            || code.contains(keyword)
                            || semester.contains(keyword)
                            || id.contains(keyword)) {

                        tableModel.addRow(new Object[]{
                            subject.getId(),
                            subject.getName(),
                            subject.getCourse(),
                            subject.getSemester()
                        });
                    }
                }
            }

            updateSubjectCount();

            if (tableModel.getRowCount() == 0) {
                setStatus("NO MATCHING SUBJECTS",
                        new Color(251, 191, 36));
            } else {
                setStatus("SEARCH COMPLETED", TEAL);
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    // =============== UPDATE SUBJECT ===============

    private void updateSubject() {

        if (idField.getText().trim().isEmpty()) {

            showMessage(
                    "Please select a subject from the table first.",
                    "No Subject Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!validateFields()) {
            return;
        }

        try {
            Subject subject = new Subject();

            subject.setId(
                    Integer.parseInt(idField.getText().trim())
            );

            subject.setName(nameField.getText().trim());
            subject.setCourse(codeField.getText().trim());

            subject.setSemester(
                    Integer.parseInt(semesterField.getText().trim())
            );

            boolean success = subjectDAO.updateSubject(subject);

            if (success) {

                showMessage(
                        "Subject updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadSubjects();
                setStatus("SUBJECT UPDATED", TEAL);

            } else {

                showMessage(
                        "Subject was not updated. Check the console for details.",
                        "Update Failed",
                        JOptionPane.WARNING_MESSAGE
                );

                setStatus("UPDATE FAILED",
                        new Color(248, 113, 113));
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    // =============== DELETE SUBJECT ===============

    private void deleteSubject() {

        if (idField.getText().trim().isEmpty()) {

            showMessage(
                    "Please select a subject from the table first.",
                    "No Subject Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this subject?\n"
                        + "Subject: " + nameField.getText().trim()
                        + "\nThis action cannot be undone.",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText().trim());

            boolean success = subjectDAO.deleteSubject(id);

            if (success) {

                showMessage(
                        "Subject deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadSubjects();
                setStatus("SUBJECT DELETED", TEAL);

            } else {

                showMessage(
                        "Subject could not be deleted. It may be linked "
                                + "to enrollments or other records. "
                                + "Check the console for details.",
                        "Unable to Delete Subject",
                        JOptionPane.WARNING_MESSAGE
                );

                setStatus("DELETE FAILED",
                        new Color(248, 113, 113));
            }

        } catch (Exception ex) {
            showError(ex);
        }
    }

    // =============== VALIDATE INPUT FIELDS ===============

    private boolean validateFields() {

        if (nameField.getText().trim().isEmpty()
                || codeField.getText().trim().isEmpty()
                || semesterField.getText().trim().isEmpty()) {

            showMessage(
                    "Please fill in all subject details.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        try {
            int semester = Integer.parseInt(
                    semesterField.getText().trim()
            );

            if (semester < 1 || semester > 8) {

                showMessage(
                        "Semester must be between 1 and 8.",
                        "Invalid Semester",
                        JOptionPane.WARNING_MESSAGE
                );

                return false;
            }

        } catch (NumberFormatException ex) {

            showMessage(
                    "Semester must be a valid whole number.",
                    "Invalid Semester",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        return true;
    }

    // =============== UPDATE SUBJECT COUNT ===============

    private void updateSubjectCount() {

        if (countLabel != null) {

            int count = tableModel.getRowCount();

            countLabel.setText(
                    count + (count == 1 ? " SUBJECT" : " SUBJECTS")
            );
        }
    }

    // =============== CLEAR FIELDS ===============

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        codeField.setText("");
        semesterField.setText("");

        subjectTable.clearSelection();
        nameField.requestFocusInWindow();

        setStatus("FORM CLEARED", MUTED);
    }

    // =============== MESSAGES ===============

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

    private void showError(Exception ex) {

        ex.printStackTrace();

        setStatus("AN ERROR OCCURRED",
                new Color(248, 113, 113));

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
                SubjectManagementFrame::new
        );
    }
}
