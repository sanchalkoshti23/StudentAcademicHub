
package studenthub.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import studenthub.dao.StudentDAO;
import studenthub.dao.SubjectDAO;
import studenthub.dao.AttendanceDAO;
import studenthub.dao.MarksDAO;

public class MainDashboard extends JFrame {

    // ================= PREMIUM COLOR SYSTEM =================

    private static final Color BACKGROUND =
            new Color(8, 15, 30);

    private static final Color SIDEBAR =
            new Color(12, 23, 42);

    private static final Color CARD_COLOR =
            new Color(18, 32, 52);

    private static final Color CARD_HOVER =
            new Color(27, 48, 68);

    private static final Color TEAL =
            new Color(45, 212, 191);

    private static final Color CYAN =
            new Color(56, 189, 248);

    private static final Color WHITE =
            new Color(241, 245, 249);

    private static final Color MUTED =
            new Color(148, 163, 184);

    private static final Color BORDER =
            new Color(39, 58, 82);

    private final List<Runnable> entranceAnimations =
            new ArrayList<>();

    public MainDashboard() {

        setTitle("StudentAcademicHub | Academic Command Center");
        setSize(1280, 850);
        setMinimumSize(new Dimension(1000, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        // ================= SIDEBAR =================

        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(245, 0));
        sidebar.setBorder(new EmptyBorder(28, 18, 24, 18));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("◈  StudentHub");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(TEAL);

        JLabel logoSubtitle = new JLabel("   ACADEMIC COMMAND CENTER");
        logoSubtitle.setFont(new Font("Segoe UI", Font.BOLD, 9));
        logoSubtitle.setForeground(MUTED);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(7));
        sidebar.add(logoSubtitle);
        sidebar.add(Box.createVerticalStrut(48));

        JLabel workspace = new JLabel("   WORKSPACE");
        styleSmallHeading(workspace);
        sidebar.add(workspace);
        sidebar.add(Box.createVerticalStrut(15));

        JPanel activeItem = new JPanel(new BorderLayout(10, 0));
        activeItem.setBackground(new Color(20, 57, 67));
        activeItem.setBorder(new EmptyBorder(14, 14, 14, 12));
        activeItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel activeDot = new JLabel("●");
        activeDot.setForeground(TEAL);
        activeDot.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel activeText = new JLabel("Dashboard");
        activeText.setForeground(WHITE);
        activeText.setFont(new Font("Segoe UI", Font.BOLD, 14));

        activeItem.add(activeDot, BorderLayout.WEST);
        activeItem.add(activeText, BorderLayout.CENTER);
        sidebar.add(activeItem);

        sidebar.add(Box.createVerticalStrut(32));

        JLabel management = new JLabel("   MANAGEMENT");
        styleSmallHeading(management);
        sidebar.add(management);
        sidebar.add(Box.createVerticalStrut(13));

        JLabel description = new JLabel(
                "<html><div style='width:175px; line-height:1.8;'>"
                + "Everything you need to organize student records, "
                + "subjects, attendance, examination results and "
                + "academic schedules."
                + "</div></html>"
        );

        description.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        description.setForeground(MUTED);
        description.setBorder(new EmptyBorder(8, 12, 8, 5));
        sidebar.add(description);

        // Quick visual feature list
        sidebar.add(Box.createVerticalStrut(25));

        addSidebarFeature(sidebar, "✓", "Student records");
        addSidebarFeature(sidebar, "✓", "Academic analytics");
        addSidebarFeature(sidebar, "✓", "Attendance tracking");
        addSidebarFeature(sidebar, "✓", "Exam performance");

        sidebar.add(Box.createVerticalGlue());

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel footerLine = new JLabel(
                "━━━━━━━━━━━━━━━━━━━━"
        );
        footerLine.setForeground(BORDER);

        JLabel footerTitle = new JLabel("STUDENT ACADEMICHUB");
        footerTitle.setForeground(WHITE);
        footerTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));

        JLabel footerVersion = new JLabel("Java + MySQL Edition");
        footerVersion.setForeground(MUTED);
        footerVersion.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        JLabel footerStatus = new JLabel("●  LOCAL DATABASE");
        footerStatus.setForeground(TEAL);
        footerStatus.setFont(new Font("Segoe UI", Font.BOLD, 10));

        footer.add(footerLine);
        footer.add(Box.createVerticalStrut(13));
        footer.add(footerTitle);
        footer.add(Box.createVerticalStrut(5));
        footer.add(footerVersion);
        footer.add(Box.createVerticalStrut(13));
        footer.add(footerStatus);

        sidebar.add(footer);

        // ================= MAIN CONTENT =================

        JPanel content = new JPanel(new BorderLayout(0, 23));
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(28, 30, 20, 30));

        // ================= HERO HEADER =================

        GradientPanel hero = new GradientPanel(
                new Color(18, 45, 65),
                new Color(18, 35, 57)
        );

        hero.setLayout(new BorderLayout(20, 0));
        hero.setBorder(new EmptyBorder(24, 27, 24, 25));

        JPanel headingPanel = new JPanel();
        headingPanel.setOpaque(false);
        headingPanel.setLayout(new BoxLayout(
                headingPanel, BoxLayout.Y_AXIS
        ));

        JLabel eyebrow = new JLabel(
                "YOUR PERSONAL ACADEMIC OVERVIEW"
        );
        eyebrow.setFont(new Font("Segoe UI", Font.BOLD, 10));
        eyebrow.setForeground(TEAL);

        JLabel welcome = new JLabel(
                "Welcome to StudentAcademicHub"
        );
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcome.setForeground(WHITE);

        JLabel subtitle = new JLabel(
                "One workspace. Every academic detail. Better organization."
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(MUTED);

        headingPanel.add(eyebrow);
        headingPanel.add(Box.createVerticalStrut(9));
        headingPanel.add(welcome);
        headingPanel.add(Box.createVerticalStrut(7));
        headingPanel.add(subtitle);

        JPanel statusPanel = new JPanel();
        statusPanel.setOpaque(false);
        statusPanel.setLayout(new BoxLayout(
                statusPanel, BoxLayout.Y_AXIS
        ));

        JLabel status = new JLabel("● SYSTEM READY");
        status.setFont(new Font("Segoe UI", Font.BOLD, 11));
        status.setForeground(TEAL);
        status.setOpaque(true);
        status.setBackground(new Color(14, 61, 62));
        status.setBorder(new EmptyBorder(10, 13, 10, 13));
        status.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel edition = new JLabel("DESKTOP EDITION");
        edition.setFont(new Font("Segoe UI", Font.BOLD, 9));
        edition.setForeground(MUTED);
        edition.setAlignmentX(Component.RIGHT_ALIGNMENT);

        statusPanel.add(status);
        statusPanel.add(Box.createVerticalStrut(9));
        statusPanel.add(edition);

        hero.add(headingPanel, BorderLayout.CENTER);
        hero.add(statusPanel, BorderLayout.EAST);

        // ================= STATISTICS =================

        StudentDAO studentDAO = new StudentDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        MarksDAO marksDAO = new MarksDAO();

        JPanel statsPanel = new JPanel(
                new GridLayout(2, 3, 15, 15)
        );
        statsPanel.setOpaque(false);

        statsPanel.add(createStatCard(
                "TOTAL STUDENTS",
                String.valueOf(studentDAO.getTotalStudents()),
                "Registered student records",
                "01"
        ));

        statsPanel.add(createStatCard(
                "TOTAL SUBJECTS",
                String.valueOf(
                        subjectDAO.fetchAllSubjects().size()
                ),
                "Available academic subjects",
                "02"
        ));

        statsPanel.add(createStatCard(
                "AVERAGE ATTENDANCE",
                String.format(Locale.US, "%.1f",
                        attendanceDAO.getOverallAttendancePercentage())
                        + "%",
                "Based on recorded attendance",
                "03"
        ));

        statsPanel.add(createStatCard(
                "TOTAL MARKS RECORDS",
                String.valueOf(marksDAO.getTotalMarksRecords()),
                "Recorded exam results",
                "04"
        ));

        statsPanel.add(createStatCard(
                "AVERAGE MARKS",
                String.format(Locale.US, "%.1f",
                        marksDAO.getAveragePercentage()) + "%",
                "Across valid exam records",
                "05"
        ));

        statsPanel.add(createStatCard(
                "HIGHEST MARKS",
                String.format(Locale.US, "%.1f",
                        marksDAO.getHighestPercentage()) + "%",
                "Highest recorded percentage",
                "06"
        ));

        // ================= TOP AREA =================

        JPanel topArea = new JPanel();
        topArea.setOpaque(false);
        topArea.setLayout(new BoxLayout(
                topArea, BoxLayout.Y_AXIS
        ));

        hero.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        topArea.add(hero);
        topArea.add(Box.createVerticalStrut(21));
        topArea.add(statsPanel);

        // ================= MODULE SECTION =================

        JPanel moduleSection = new JPanel(
                new BorderLayout(0, 15)
        );
        moduleSection.setOpaque(false);

        JPanel sectionHeading = new JPanel(
                new BorderLayout(10, 0)
        );
        sectionHeading.setOpaque(false);

        JPanel sectionTitles = new JPanel();
        sectionTitles.setOpaque(false);
        sectionTitles.setLayout(new BoxLayout(
                sectionTitles, BoxLayout.Y_AXIS
        ));

        JLabel sectionTitle = new JLabel("Your Workspace");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sectionTitle.setForeground(WHITE);

        JLabel sectionSubtitle = new JLabel(
                "CHOOSE A MODULE TO MANAGE YOUR ACADEMIC DATA"
        );
        sectionSubtitle.setFont(
                new Font("Segoe UI", Font.BOLD, 9)
        );
        sectionSubtitle.setForeground(MUTED);

        sectionTitles.add(sectionTitle);
        sectionTitles.add(Box.createVerticalStrut(5));
        sectionTitles.add(sectionSubtitle);

        JLabel moduleCount = new JLabel("08 MODULES");
        moduleCount.setFont(new Font("Segoe UI", Font.BOLD, 10));
        moduleCount.setForeground(TEAL);
        moduleCount.setOpaque(true);
        moduleCount.setBackground(new Color(19, 48, 59));
        moduleCount.setBorder(new EmptyBorder(8, 11, 8, 11));

        sectionHeading.add(sectionTitles, BorderLayout.CENTER);
        sectionHeading.add(moduleCount, BorderLayout.EAST);

        JPanel cardGrid = new JPanel(
                new GridLayout(0, 2, 15, 15)
        );
        cardGrid.setOpaque(false);
        cardGrid.setBorder(new EmptyBorder(2, 2, 8, 2));

        addModuleCard(
                cardGrid, "01", "Student Management",
                "Add, view, update and delete student records.",
                "STUDENTS", StudentManagementFrame.class
        );

        addModuleCard(
                cardGrid, "02", "Subject Management",
                "Manage subjects, course codes and semesters.",
                "SUBJECTS", SubjectManagementFrame.class
        );

        addModuleCard(
                cardGrid, "03", "Enrollment Management",
                "Connect students with their enrolled subjects.",
                "ENROLLMENT", EnrollmentManagementFrame.class
        );

        addModuleCard(
                cardGrid, "04", "Marks Management",
                "Record examination marks and review results.",
                "ASSESSMENT", MarksManagementFrame.class
        );

        addModuleCard(
                cardGrid, "05", "Notes Management",
                "Organize academic notes and learning resources.",
                "RESOURCES", NoteManagementFrame.class
        );

        addModuleCard(
                cardGrid, "06", "Timetable Management",
                "Manage class schedules and timetable entries.",
                "SCHEDULE", TimetableManagementFrame.class
        );

        addModuleCard(
                cardGrid, "07", "Attendance Management",
                "Record attendance and calculate percentages.",
                "ATTENDANCE", AttendanceManagementFrame.class
        );

        addModuleCard(
                cardGrid, "08", "Student Details Dashboard",
                "Explore student profiles and academic performance.",
                "ANALYTICS", StudentDetailsDashboard.class
        );

        JScrollPane scrollPane = new JScrollPane(cardGrid);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setPreferredSize(
                new Dimension(7, 0)
        );
        scrollPane.getVerticalScrollBar().setBackground(BACKGROUND);

        moduleSection.add(sectionHeading, BorderLayout.NORTH);
        moduleSection.add(scrollPane, BorderLayout.CENTER);

        JLabel bottomText = new JLabel(
                "StudentAcademicHub   •   Your academic journey, organized."
        );
        bottomText.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        bottomText.setForeground(MUTED);

        content.add(topArea, BorderLayout.NORTH);
        content.add(moduleSection, BorderLayout.CENTER);
        content.add(bottomText, BorderLayout.SOUTH);

        root.add(sidebar, BorderLayout.WEST);
        root.add(content, BorderLayout.CENTER);

        setContentPane(root);

        // Display the completed interface before starting animations.
        setVisible(true);

        SwingUtilities.invokeLater(() -> {
            for (Runnable animation : entranceAnimations) {
                animation.run();
            }
        });
    }

    // ================= SMALL SIDEBAR HEADING =================

    private void styleSmallHeading(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 10));
        label.setForeground(MUTED);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // ================= SIDEBAR FEATURE =================

    private void addSidebarFeature(
            JPanel sidebar, String symbol, String text) {

        JPanel row = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 9, 7
        ));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icon = new JLabel(symbol);
        icon.setForeground(TEAL);
        icon.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel label = new JLabel(text);
        label.setForeground(MUTED);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        row.add(icon);
        row.add(label);
        sidebar.add(row);
    }

    // ================= ANIMATED STAT CARD =================

    private JPanel createStatCard(
            String title,
            String value,
            String description,
            String number) {

        AnimatedCard card = new AnimatedCard();
        card.setLayout(new BoxLayout(
                card, BoxLayout.Y_AXIS
        ));
        card.setBorder(new EmptyBorder(16, 18, 15, 16));
        card.setPreferredSize(new Dimension(250, 130));

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        topRow.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 20)
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 10)
        );
        titleLabel.setForeground(MUTED);

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 10)
        );
        numberLabel.setForeground(new Color(78, 113, 137));

        topRow.add(titleLabel, BorderLayout.WEST);
        topRow.add(numberLabel, BorderLayout.EAST);

        JLabel valueLabel = new JLabel("0");
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 29));
        valueLabel.setForeground(TEAL);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 10)
        );
        descriptionLabel.setForeground(MUTED);

        topRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(topRow);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(descriptionLabel);

        // Animate the displayed statistic from zero to its real value.
        entranceAnimations.add(() -> animateNumber(valueLabel, value));

        return card;
    }

    // ================= COUNT-UP ANIMATION =================

    private void animateNumber(JLabel label, String targetText) {

        String numericText = targetText.replace("%", "").trim();

        final double target;

        try {
            target = Double.parseDouble(numericText);
        } catch (NumberFormatException ex) {
            label.setText(targetText);
            return;
        }

        boolean percentage = targetText.contains("%");
        boolean decimal = numericText.contains(".");

        final int duration = 1000;
        final long startTime = System.currentTimeMillis();

        Timer timer = new Timer(16, null);

        timer.addActionListener(e -> {

            double progress = Math.min(
                    1.0,
                    (System.currentTimeMillis() - startTime)
                            / (double) duration
            );

            // Smooth ease-out animation.
            double eased = 1.0 - Math.pow(1.0 - progress, 3);

            double current = target * eased;

            String formatted;

            if (decimal || percentage) {
                formatted = String.format(
                        Locale.US, "%.1f", current
                );
            } else {
                formatted = String.valueOf(
                        Math.round(current)
                );
            }

            label.setText(
                    percentage ? formatted + "%" : formatted
            );

            if (progress >= 1.0) {
                label.setText(targetText);
                timer.stop();
            }
        });

        timer.start();
    }

    // ================= INTERACTIVE MODULE CARD =================

    private void addModuleCard(
            JPanel panel,
            String number,
            String title,
            String description,
            String category,
            Class<? extends JFrame> frameClass) {

        AnimatedCard card = new AnimatedCard();
        card.setLayout(new BorderLayout(0, 12));
        card.setBorder(new EmptyBorder(17, 18, 17, 18));
        card.setPreferredSize(new Dimension(350, 140));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Top row
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);

        JLabel numberLabel = new JLabel("  " + number + "  ");
        numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        numberLabel.setForeground(TEAL);
        numberLabel.setOpaque(true);
        numberLabel.setBackground(new Color(21, 55, 65));
        numberLabel.setBorder(new EmptyBorder(8, 5, 8, 5));

        JLabel arrow = new JLabel("↗");
        arrow.setFont(new Font("Segoe UI", Font.BOLD, 21));
        arrow.setForeground(TEAL);

        topRow.add(numberLabel, BorderLayout.WEST);
        topRow.add(arrow, BorderLayout.EAST);

        // Card content
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(
                textPanel, BoxLayout.Y_AXIS
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );
        titleLabel.setForeground(WHITE);

        JLabel descriptionLabel = new JLabel(
                "<html><div style='width:290px;'>"
                        + description
                        + "</div></html>"
        );
        descriptionLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 11)
        );
        descriptionLabel.setForeground(MUTED);

        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 9)
        );
        categoryLabel.setForeground(new Color(94, 150, 164));

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(7));
        textPanel.add(descriptionLabel);
        textPanel.add(Box.createVerticalStrut(8));
        textPanel.add(categoryLabel);

        card.add(topRow, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);

        // Mouse and keyboard-like click feedback
        card.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setHovered(true);
                arrow.setForeground(WHITE);
                titleLabel.setForeground(TEAL);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setHovered(false);
                arrow.setForeground(TEAL);
                titleLabel.setForeground(WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    openModule(title, frameClass);
                }
            }
        });

        panel.add(card);
    }

    // ================= OPEN MODULE =================

    private void openModule(
            String moduleName,
            Class<? extends JFrame> frameClass) {

        try {
            JFrame module = frameClass
                    .getDeclaredConstructor()
                    .newInstance();

            // Keep the existing module's own design intact.
            // Each module can be themed consistently in the next phase.

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to open " + moduleName + ".\n"
                            + ex.getMessage(),
                    "Unable to Open Module",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= GRADIENT PANEL =================

    private static class GradientPanel extends JPanel {

        private final Color start;
        private final Color end;

        GradientPanel(Color start, Color end) {
            this.start = start;
            this.end = end;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {

            Graphics2D g = (Graphics2D) graphics.create();

            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g.setPaint(new GradientPaint(
                    0, 0, start,
                    getWidth(), getHeight(), end
            ));

            g.fillRoundRect(
                    0, 0, getWidth(), getHeight(), 20, 20
            );

            // Subtle teal accent line
            g.setColor(new Color(45, 212, 191, 100));
            g.fillRoundRect(
                    0, 0, 5, getHeight(), 5, 5
            );

            g.dispose();
            super.paintComponent(graphics);
        }
    }

    // ================= HOVER ANIMATION CARD =================

    private static class AnimatedCard extends JPanel {

        private float hoverProgress = 0f;
        private Timer hoverTimer;
        private float targetProgress = 0f;

        AnimatedCard() {
            setOpaque(false);
        }

        void setHovered(boolean hovered) {

            targetProgress = hovered ? 1f : 0f;

            if (hoverTimer != null && hoverTimer.isRunning()) {
                hoverTimer.stop();
            }

            hoverTimer = new Timer(15, e -> {

                float difference = targetProgress - hoverProgress;

                if (Math.abs(difference) < 0.04f) {
                    hoverProgress = targetProgress;
                    hoverTimer.stop();
                } else {
                    hoverProgress += difference * 0.24f;
                }

                repaint();
            });

            hoverTimer.start();
        }

        private Color blend(Color a, Color b, float amount) {

            float inverse = 1f - amount;

            return new Color(
                    (int) (a.getRed() * inverse
                            + b.getRed() * amount),
                    (int) (a.getGreen() * inverse
                            + b.getGreen() * amount),
                    (int) (a.getBlue() * inverse
                            + b.getBlue() * amount)
            );
        }

        @Override
        protected void paintComponent(Graphics graphics) {

            Graphics2D g = (Graphics2D) graphics.create();

            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            Color fill = blend(
                    CARD_COLOR, CARD_HOVER, hoverProgress
            );

            Color border = blend(
                    BORDER, TEAL, hoverProgress
            );

            // Soft glow layers on hover
            if (hoverProgress > 0.01f) {

                int alpha = (int) (24 * hoverProgress);

                g.setColor(new Color(45, 212, 191, alpha));
                g.fillRoundRect(
                        0, 0, getWidth(), getHeight(), 19, 19
                );
            }

            g.setColor(fill);
            g.fillRoundRect(
                    2, 2, getWidth() - 4, getHeight() - 4,
                    17, 17
            );

            g.setStroke(new BasicStroke(
                    1.0f + hoverProgress * 0.5f
            ));
            g.setColor(border);
            g.drawRoundRect(
                    2, 2, getWidth() - 5, getHeight() - 5,
                    17, 17
            );

            g.dispose();
            super.paintComponent(graphics);
        }
    }

    // ================= MAIN METHOD =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            new MainDashboard();
        });
    }
}
