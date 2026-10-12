
package studenthub.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import studenthub.dao.TimetableDAO;
import studenthub.model.Timetable;

public class TimetableManagementFrame extends JFrame {

    private JTextField timetableIdField;
    private JTextField subjectIdField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField roomField;

    private JComboBox<String> dayComboBox;

    private JButton addButton;
    private JButton viewButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;

    private JTable timetableTable;
    private DefaultTableModel tableModel;

    private final TimetableDAO timetableDAO;

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

    public TimetableManagementFrame() {

        timetableDAO = new TimetableDAO();

        setTitle("StudentAcademicHub | Timetable Management");
        setSize(1050, 720);
        setMinimumSize(new Dimension(850, 620));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(22, 25, 22, 25)
        );

        // Heading
        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
        headingPanel.setBackground(BACKGROUND);

        JLabel heading = new JLabel("Timetable Management");
        heading.setFont(new Font("SansSerif", Font.BOLD, 28));
        heading.setForeground(TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Organize subject schedules, class timings and rooms"
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        headingPanel.add(heading);
        headingPanel.add(Box.createVerticalStrut(6));
        headingPanel.add(subtitle);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PANEL);
        formPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER, 1),
                        BorderFactory.createEmptyBorder(18, 20, 18, 20)
                )
        );

        timetableIdField = createTextField();
        subjectIdField = createTextField();
        startTimeField = createTextField();
        endTimeField = createTextField();
        roomField = createTextField();

        timetableIdField.setToolTipText(
                "Enter an existing ID to update or delete a timetable entry"
        );
        startTimeField.setToolTipText("Example: 09:30");
        endTimeField.setToolTipText("Example: 10:30");

        dayComboBox = new JComboBox<>(new String[]{
                "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"
        });
        styleComboBox(dayComboBox);

        addFormField(formPanel, "Timetable ID (Update/Delete)",
                timetableIdField, 0, 0);
        addFormField(formPanel, "Subject ID",
                subjectIdField, 1, 0);
        addFormField(formPanel, "Day of Week",
                dayComboBox, 0, 1);
        addFormField(formPanel, "Start Time (HH:mm)",
                startTimeField, 1, 1);
        addFormField(formPanel, "End Time (HH:mm)",
                endTimeField, 0, 2);
        addFormField(formPanel, "Room",
                roomField, 1, 2);

        // Buttons
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 0)
        );
        buttonPanel.setBackground(PANEL);

        addButton = createButton("Add Timetable", true);
        viewButton = createButton("Refresh Table", false);
        updateButton = createButton("Update", false);
        deleteButton = createButton("Delete", false);
        clearButton = createButton("Clear Fields", false);

        addButton.addActionListener(e -> addTimetable());
        viewButton.addActionListener(e -> viewTimetable());
        updateButton.addActionListener(e -> updateTimetable());
        deleteButton.addActionListener(e -> deleteTimetable());
        clearButton.addActionListener(e -> clearFields());

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // Table
        String[] columns = {
                "Timetable ID", "Subject ID", "Day",
                "Start Time", "End Time", "Room"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        timetableTable = new JTable(tableModel);
        timetableTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        timetableTable.setForeground(TEXT);
        timetableTable.setBackground(PANEL);
        timetableTable.setSelectionBackground(new Color(35, 92, 105));
        timetableTable.setSelectionForeground(Color.WHITE);
        timetableTable.setRowHeight(30);
        timetableTable.setShowGrid(true);
        timetableTable.setGridColor(BORDER);
        timetableTable.setFillsViewportHeight(true);
        timetableTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        timetableTable.setAutoCreateRowSorter(true);

        JTableHeader tableHeader = timetableTable.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 13));
        tableHeader.setForeground(TEAL);
        tableHeader.setBackground(INPUT);
        tableHeader.setPreferredSize(new Dimension(0, 36));
        tableHeader.setReorderingAllowed(false);

        JScrollPane tableScrollPane = new JScrollPane(timetableTable);
        tableScrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );
        tableScrollPane.getViewport().setBackground(PANEL);
        tableScrollPane.getVerticalScrollBar().setBackground(PANEL);
        tableScrollPane.getHorizontalScrollBar().setBackground(PANEL);

        JPanel tablePanel = new JPanel(new BorderLayout(0, 10));
        tablePanel.setBackground(BACKGROUND);

        JLabel tableHeading = new JLabel("Your Timetable Entries");
        tableHeading.setFont(new Font("SansSerif", Font.BOLD, 17));
        tableHeading.setForeground(TEXT);

        tablePanel.add(tableHeading, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Click a table row to load its values into the form
        timetableTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()
                    && timetableTable.getSelectedRow() != -1) {

                int row = timetableTable.getSelectedRow();

                timetableIdField.setText(
                        String.valueOf(tableModel.getValueAt(row, 0))
                );
                subjectIdField.setText(
                        String.valueOf(tableModel.getValueAt(row, 1))
                );
                dayComboBox.setSelectedItem(
                        String.valueOf(tableModel.getValueAt(row, 2))
                );
                startTimeField.setText(
                        String.valueOf(tableModel.getValueAt(row, 3))
                );
                endTimeField.setText(
                        String.valueOf(tableModel.getValueAt(row, 4))
                );
                roomField.setText(
                        String.valueOf(tableModel.getValueAt(row, 5))
                );
            }
        });

        // Upper section
        JPanel upperPanel = new JPanel(new BorderLayout(0, 16));
        upperPanel.setBackground(BACKGROUND);
        upperPanel.add(headingPanel, BorderLayout.NORTH);

        JPanel formAndButtons = new JPanel(new BorderLayout(0, 14));
        formAndButtons.setBackground(BACKGROUND);
        formAndButtons.add(formPanel, BorderLayout.CENTER);
        formAndButtons.add(buttonPanel, BorderLayout.SOUTH);

        upperPanel.add(formAndButtons, BorderLayout.CENTER);

        mainPanel.add(upperPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel);

        refreshTable();
        setVisible(true);
    }

    // Create styled text fields
    private JTextField createTextField() {

        JTextField field = new JTextField(15);
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBackground(INPUT);
        field.setCaretColor(TEAL);
        field.setPreferredSize(new Dimension(200, 36));
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );

        return field;
    }

    // Style the day dropdown
    private void styleComboBox(JComboBox<String> comboBox) {

        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        comboBox.setForeground(TEXT);
        comboBox.setBackground(INPUT);
        comboBox.setPreferredSize(new Dimension(200, 36));
        comboBox.setBorder(BorderFactory.createLineBorder(BORDER));

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus
                );

                label.setOpaque(true);
                label.setBorder(
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)
                );
                label.setBackground(isSelected ? TEAL_HOVER : INPUT);
                label.setForeground(TEXT);

                return label;
            }
        });
    }

    // Add a label and input to the form grid
    private void addFormField(
            JPanel panel,
            String labelText,
            JComponent input,
            int column,
            int row) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = column;
        gbc.gridy = row;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 8, 6, 8);

        JPanel fieldPanel = new JPanel(new BorderLayout(0, 7));
        fieldPanel.setBackground(PANEL);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(MUTED);

        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(input, BorderLayout.CENTER);

        panel.add(fieldPanel, gbc);
    }

    // Create a button with a hover effect
    private JButton createButton(String text, boolean primary) {

        JButton button = new JButton(text);
        Color normalColor = primary ? TEAL : INPUT;
        Color hoverColor = primary ? TEAL_HOVER : new Color(43, 60, 88);

        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(primary ? BACKGROUND : TEXT);
        button.setBackground(normalColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                primary ? TEAL : BORDER
                        ),
                        BorderFactory.createEmptyBorder(9, 12, 9, 12)
                )
        );

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(normalColor);
            }
        });

        return button;
    }

    // Read and validate the form
    private Timetable readForm(int timetableId) {

        String subjectText = subjectIdField.getText().trim();

        if (subjectText.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please enter the Subject ID!"
            );
        }

        int subjectId = Integer.parseInt(subjectText);

        if (subjectId <= 0) {
            throw new IllegalArgumentException(
                    "Subject ID must be greater than zero!"
            );
        }

        String day = (String) dayComboBox.getSelectedItem();

        String startText = startTimeField.getText().trim();
        String endText = endTimeField.getText().trim();

        if (startText.isEmpty() || endText.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please enter both start time and end time!"
            );
        }

        LocalTime startTime = LocalTime.parse(startText);
        LocalTime endTime = LocalTime.parse(endText);

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "End time must be later than start time!"
            );
        }

        String room = roomField.getText().trim();

        if (room.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please enter the room!"
            );
        }

        return new Timetable(
                timetableId,
                subjectId,
                day,
                startTime,
                endTime,
                room
        );
    }

    // Add timetable entry
    private void addTimetable() {

        try {
            Timetable timetable = readForm(0);

            timetableDAO.addTimetable(timetable);

            showMessage("Add request completed. Check the timetable below.");
            clearFields();
            refreshTable();

        } catch (NumberFormatException e) {
            showMessage("Please enter a valid numeric Subject ID!");
        } catch (DateTimeParseException e) {
            showMessage(
                    "Enter time in HH:mm format, for example 09:30."
            );
        } catch (IllegalArgumentException e) {
            showMessage(e.getMessage());
        }
    }

    // Refresh table data from the database
    private void refreshTable() {

        tableModel.setRowCount(0);

        try {
            List<Timetable> entries =
                    timetableDAO.getAllTimetableList();

            for (Timetable entry : entries) {
                tableModel.addRow(new Object[]{
                        entry.getTimetableId(),
                        entry.getSubjectId(),
                        entry.getDayOfWeek(),
                        entry.getStartTime(),
                        entry.getEndTime(),
                        entry.getRoom()
                });
            }

            // Alternate row colors
            timetableTable.setDefaultRenderer(
                    Object.class,
                    new javax.swing.table.DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {

                            Component component =
                                    super.getTableCellRendererComponent(
                                            table, value, isSelected,
                                            hasFocus, row, column
                                    );

                            if (!isSelected) {
                                component.setBackground(
                                        row % 2 == 0 ? PANEL : ROW_ALT
                                );
                                component.setForeground(TEXT);
                            }

                            setBorder(
                                    BorderFactory.createEmptyBorder(
                                            0, 8, 0, 8
                                    )
                            );

                            return component;
                        }
                    }
            );

        } catch (Exception e) {
            showMessage(
                    "Could not load timetable entries.\n"
                    + "Please check your database connection."
            );
        }
    }

    // View/refresh all timetable entries
    private void viewTimetable() {

        refreshTable();

        if (tableModel.getRowCount() == 0) {
            showMessage("No timetable entries found.");
        } else {
            showMessage(
                    "Timetable refreshed successfully.\nEntries found: "
                    + tableModel.getRowCount()
            );
        }
    }

    // Update timetable entry
    private void updateTimetable() {

        try {
            String idText = timetableIdField.getText().trim();

            if (idText.isEmpty()) {
                showMessage(
                        "Enter a Timetable ID or select a table row first."
                );
                return;
            }

            int id = Integer.parseInt(idText);

            if (id <= 0) {
                showMessage("Timetable ID must be greater than zero!");
                return;
            }

            Timetable existing = timetableDAO.getTimetableById(id);

            if (existing == null) {
                showMessage("Timetable ID not found!");
                return;
            }

            Timetable timetable = readForm(id);
            timetableDAO.updateTimetable(timetable);

            showMessage("Update request completed.");
            refreshTable();

        } catch (NumberFormatException e) {
            showMessage("Enter valid numeric ID values!");
        } catch (DateTimeParseException e) {
            showMessage("Enter time in HH:mm format, for example 14:30.");
        } catch (IllegalArgumentException e) {
            showMessage(e.getMessage());
        }
    }

    // Delete timetable entry
    private void deleteTimetable() {

        try {
            String idText = timetableIdField.getText().trim();

            if (idText.isEmpty()) {
                showMessage(
                        "Enter a Timetable ID or select a table row first."
                );
                return;
            }

            int id = Integer.parseInt(idText);

            if (id <= 0) {
                showMessage("Timetable ID must be greater than zero!");
                return;
            }

            Timetable existing = timetableDAO.getTimetableById(id);

            if (existing == null) {
                showMessage("Timetable ID not found!");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Delete timetable entry ID " + id + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                timetableDAO.deleteTimetable(id);

                showMessage("Delete request completed.");
                clearFields();
                refreshTable();
            }

        } catch (NumberFormatException e) {
            showMessage("Please enter a valid Timetable ID!");
        }
    }

    // Clear all form fields
    private void clearFields() {

        timetableIdField.setText("");
        subjectIdField.setText("");
        dayComboBox.setSelectedIndex(0);
        startTimeField.setText("");
        endTimeField.setText("");
        roomField.setText("");

        timetableTable.clearSelection();
    }

    private void showMessage(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "StudentAcademicHub",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                TimetableManagementFrame::new
        );
    }
}
