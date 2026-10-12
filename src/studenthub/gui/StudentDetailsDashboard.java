
package studenthub.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import studenthub.dao.StudentDAO;
import studenthub.dao.AttendanceDAO;
import studenthub.dao.MarksDAO;
import studenthub.model.Student;

public class StudentDetailsDashboard extends JFrame {

    private final StudentDAO studentDAO = new StudentDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final MarksDAO marksDAO = new MarksDAO();

    private final JTextField studentIdField = new JTextField(12);

    private JLabel nameValue;
    private JLabel emailValue;
    private JLabel collegeValue;
    private JLabel branchValue;
    private JLabel yearValue;

    private JLabel totalClassesValue;
    private JLabel presentClassesValue;
    private JLabel attendanceValue;

    private JLabel marksCountValue;
    private JLabel averageMarksValue;
    private JLabel statusLabel;

    // Theme colors
    private final Color BACKGROUND = new Color(17, 24, 39);
    private final Color CARD = new Color(30, 41, 59);
    private final Color CARD_LIGHT = new Color(39, 52, 73);
    private final Color TEAL = new Color(45, 212, 191);
    private final Color WHITE = new Color(241, 245, 249);
    private final Color MUTED = new Color(160, 174, 192);
    private final Color BORDER = new Color(55, 70, 91);

    public StudentDetailsDashboard() {
        setTitle("StudentAcademicHub | Student Dashboard");
        setSize(850, 700);
        setMinimumSize(new Dimension(750, 620));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(25, 28, 25, 28));

        mainPanel.add(createHeader(), BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BACKGROUND);

        contentPanel.add(createSearchPanel());
        contentPanel.add(Box.createVerticalStrut(18));
        contentPanel.add(createStudentCard());
        contentPanel.add(Box.createVerticalStrut(18));
        contentPanel.add(createAttendanceCard());
        contentPanel.add(Box.createVerticalStrut(18));
        contentPanel.add(createMarksCard());

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(BACKGROUND);
        scrollPane.getViewport().setBackground(BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);

        clearDashboard();
        setVisible(true);
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BACKGROUND);

        JLabel title = new JLabel("Student Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 27));
        title.setForeground(WHITE);

        JLabel subtitle = new JLabel(
                "View student information, attendance and academic performance");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(subtitle);

        JLabel logo = new JLabel("SAH");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 21));
        logo.setForeground(TEAL);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(65, 55));
        logo.setOpaque(true);
        logo.setBackground(CARD);
        logo.setBorder(BorderFactory.createLineBorder(BORDER, 1));

        panel.add(titlePanel, BorderLayout.CENTER);
        panel.add(logo, BorderLayout.EAST);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = createCardPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 14));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = createLabel("Student ID");
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));

        studentIdField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentIdField.setForeground(WHITE);
        studentIdField.setBackground(CARD_LIGHT);
        studentIdField.setCaretColor(TEAL);
        studentIdField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(9, 10, 9, 10)
        ));

        JButton loadButton = new JButton("Load Dashboard");
        loadButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loadButton.setForeground(BACKGROUND);
        loadButton.setBackground(TEAL);
        loadButton.setFocusPainted(false);
        loadButton.setBorder(new EmptyBorder(10, 17, 10, 17));
        loadButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loadButton.addActionListener(e -> loadStudentDashboard());

        studentIdField.addActionListener(e -> loadStudentDashboard());

        panel.add(label);
        panel.add(studentIdField);
        panel.add(loadButton);

        return panel;
    }

    private JPanel createStudentCard() {
        JPanel panel = createCardPanel();
        panel.setLayout(new BorderLayout(0, 15));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(createSectionTitle("STUDENT INFORMATION"), BorderLayout.NORTH);

        JPanel details = new JPanel(new GridLayout(3, 2, 20, 15));
        details.setBackground(CARD);

        nameValue = createValueLabel("—");
        emailValue = createValueLabel("—");
        collegeValue = createValueLabel("—");
        branchValue = createValueLabel("—");
        yearValue = createValueLabel("—");

        details.add(createInfoItem("Full Name", nameValue));
        details.add(createInfoItem("Email Address", emailValue));
        details.add(createInfoItem("College", collegeValue));
        details.add(createInfoItem("Branch", branchValue));
        details.add(createInfoItem("Academic Year", yearValue));

        panel.add(details, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createAttendanceCard() {
        JPanel panel = createCardPanel();
        panel.setLayout(new BorderLayout(0, 15));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(createSectionTitle("ATTENDANCE SUMMARY"), BorderLayout.NORTH);

        JPanel stats = new JPanel(new GridLayout(1, 3, 12, 0));
        stats.setBackground(CARD);

        totalClassesValue = createStatValue("0");
        presentClassesValue = createStatValue("0");
        attendanceValue = createStatValue("0.0%");

        stats.add(createStatCard(
                "Total Classes", totalClassesValue, "All attendance records"));

        stats.add(createStatCard(
                "Present Classes", presentClassesValue, "Classes attended"));

        stats.add(createStatCard(
                "Attendance Rate", attendanceValue, "Overall percentage"));

        panel.add(stats, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMarksCard() {
        JPanel panel = createCardPanel();
        panel.setLayout(new BorderLayout(0, 15));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(createSectionTitle("ACADEMIC PERFORMANCE"), BorderLayout.NORTH);

        JPanel stats = new JPanel(new GridLayout(1, 2, 12, 0));
        stats.setBackground(CARD);

        marksCountValue = createStatValue("0");
        averageMarksValue = createStatValue("0.0%");

        stats.add(createStatCard(
                "Marks Records", marksCountValue, "Recorded assessments"));

        stats.add(createStatCard(
                "Average Marks", averageMarksValue, "Average percentage"));

        panel.add(stats, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(18, 18, 18, 18)
        ));
        return panel;
    }

    private JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEAL);
        return label;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(MUTED);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private JLabel createStatValue(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEAL);
        label.setFont(new Font("Segoe UI", Font.BOLD, 27));
        return label;
    }

    private JPanel createInfoItem(String title, JLabel value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD);

        JLabel titleLabel = createLabel(title);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        value.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(value);

        return panel;
    }

    private JPanel createStatCard(
            String title, JLabel valueLabel, String description) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_LIGHT);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(14, 13, 14, 13)
        ));

        JLabel titleLabel = createLabel(title);
        JLabel descriptionLabel = createLabel(description);
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(valueLabel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(descriptionLabel);

        return panel;
    }

    private void loadStudentDashboard() {
        String input = studentIdField.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a Student ID.",
                    "Missing Student ID",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int studentId;

        try {
            studentId = Integer.parseInt(input);

            if (studentId <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Student ID must be a positive number.",
                    "Invalid Student ID",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            Student student = studentDAO.getStudentById(studentId);

            if (student == null) {
                clearDashboard();
                JOptionPane.showMessageDialog(
                        this,
                        "No student found with ID " + studentId + ".",
                        "Student Not Found",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            nameValue.setText(safeText(student.getName()));
            emailValue.setText(safeText(student.getEmail()));
            collegeValue.setText(safeText(student.getCollege()));
            branchValue.setText(safeText(student.getBranch()));
            yearValue.setText(String.valueOf(student.getYear()));

            int total = attendanceDAO.getStudentTotalClasses(studentId);
            int present = attendanceDAO.getStudentPresentClasses(studentId);
            double attendance =
                    attendanceDAO.getStudentAttendancePercentage(studentId);

            totalClassesValue.setText(String.valueOf(total));
            presentClassesValue.setText(String.valueOf(present));
            attendanceValue.setText(String.format("%.1f%%", attendance));

            int records = marksDAO.getStudentMarksCount(studentId);
            double average =
                    marksDAO.getStudentAveragePercentage(studentId);

            marksCountValue.setText(String.valueOf(records));
            averageMarksValue.setText(String.format("%.1f%%", average));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load the student dashboard.\n"
                            + "Please check your database connection.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String safeText(String value) {
        return value == null || value.trim().isEmpty() ? "—" : value;
    }

    private void clearDashboard() {
        if (nameValue == null) {
            return;
        }

        nameValue.setText("—");
        emailValue.setText("—");
        collegeValue.setText("—");
        branchValue.setText("—");
        yearValue.setText("—");

        totalClassesValue.setText("0");
        presentClassesValue.setText("0");
        attendanceValue.setText("0.0%");

        marksCountValue.setText("0");
        averageMarksValue.setText("0.0%");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentDetailsDashboard::new);
    }
}
