
package studenthub.gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

import studenthub.dao.NoteDAO;
import studenthub.model.Note;

public class NoteManagementFrame extends JFrame {

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

    private JTextField noteIdField;
    private JTextField subjectIdField;
    private JTextField titleField;
    private JTextField filePathField;
    private JTextArea descriptionArea;

    private JLabel countLabel;
    private JLabel statusLabel;

    private JTable notesTable;
    private DefaultTableModel tableModel;

    private final NoteDAO noteDAO;

    public NoteManagementFrame() {

        noteDAO = new NoteDAO();

        setTitle("StudentAcademicHub | Notes Management");
        setSize(1150, 790);
        setMinimumSize(new Dimension(950, 680));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BACKGROUND);
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        refreshNotes();
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
        titlePanel.setLayout(
                new BoxLayout(titlePanel, BoxLayout.Y_AXIS)
        );

        JLabel heading = new JLabel("Notes Management");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 25));
        heading.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Organize subject notes, descriptions and file locations"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        titlePanel.add(heading);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(subtitle);

        JLabel logo = new JLabel("N", SwingConstants.CENTER);
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
    // NOTE FORM
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

        JLabel sectionTitle = new JLabel("Note Details");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        sectionTitle.setForeground(TEXT);

        noteIdField = createTextField();
        subjectIdField = createTextField();
        titleField = createTextField();
        filePathField = createTextField();

        noteIdField.setToolTipText(
                "Select a record or enter its ID to update or delete"
        );
        filePathField.setToolTipText(
                "Enter the path or location of your notes file"
        );

        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        descriptionArea.setForeground(TEXT);
        descriptionArea.setBackground(INPUT);
        descriptionArea.setCaretColor(TEAL);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(
                new EmptyBorder(8, 9, 8, 9)
        );

        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        descriptionScroll.setBorder(
                new LineBorder(BORDER, 1, true)
        );
        descriptionScroll.getViewport().setBackground(INPUT);

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 14, 12));
        fieldsPanel.setOpaque(false);

        fieldsPanel.add(
                createFieldGroup("NOTE ID (UPDATE / DELETE)", noteIdField)
        );
        fieldsPanel.add(
                createFieldGroup("SUBJECT ID", subjectIdField)
        );
        fieldsPanel.add(
                createFieldGroup("NOTE TITLE", titleField)
        );
        fieldsPanel.add(
                createFieldGroup("FILE PATH", filePathField)
        );

        JPanel descriptionPanel = new JPanel(new BorderLayout(0, 6));
        descriptionPanel.setOpaque(false);

        JLabel descriptionLabel = new JLabel("DESCRIPTION");
        descriptionLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 10)
        );
        descriptionLabel.setForeground(MUTED);

        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        descriptionPanel.add(descriptionScroll, BorderLayout.CENTER);

        JPanel fieldsAndDescription = new JPanel(new BorderLayout(0, 12));
        fieldsAndDescription.setOpaque(false);
        fieldsAndDescription.add(fieldsPanel, BorderLayout.NORTH);
        fieldsAndDescription.add(descriptionPanel, BorderLayout.CENTER);

        JButton addButton = createButton("Add Note", true);
        JButton viewButton = createButton("Refresh Notes", false);
        JButton updateButton = createButton("Update Note", false);
        JButton deleteButton = createButton("Delete Note", false);
        JButton clearButton = createButton("Clear Fields", false);

        addButton.addActionListener(e -> addNote());
        viewButton.addActionListener(e -> viewNotes());
        updateButton.addActionListener(e -> updateNote());
        deleteButton.addActionListener(e -> deleteNote());
        clearButton.addActionListener(e -> clearFields());

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 9, 0)
        );
        buttonPanel.setOpaque(false);

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setOpaque(false);
        content.add(fieldsAndDescription, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);

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
    // NOTES TABLE
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

        JLabel tableTitle = new JLabel("Saved Notes");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        tableTitle.setForeground(TEXT);

        countLabel = new JLabel("0 notes");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLabel.setForeground(TEAL);

        headingPanel.add(tableTitle, BorderLayout.WEST);
        headingPanel.add(countLabel, BorderLayout.EAST);

        tableModel = new DefaultTableModel(
                new String[]{
                        "Note ID",
                        "Subject ID",
                        "Title",
                        "Description",
                        "File Path"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        notesTable = new JTable(tableModel);
        notesTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        notesTable.setForeground(TEXT);
        notesTable.setBackground(PANEL);
        notesTable.setSelectionBackground(
                new Color(27, 104, 111)
        );
        notesTable.setSelectionForeground(Color.WHITE);
        notesTable.setRowHeight(34);
        notesTable.setShowVerticalLines(false);
        notesTable.setShowHorizontalLines(true);
        notesTable.setGridColor(BORDER);
        notesTable.setFillsViewportHeight(true);
        notesTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        notesTable.setIntercellSpacing(new Dimension(0, 1));
        notesTable.setAutoCreateRowSorter(true);
        notesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        notesTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        notesTable.getColumnModel().getColumn(1).setPreferredWidth(85);
        notesTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        notesTable.getColumnModel().getColumn(3).setPreferredWidth(260);
        notesTable.getColumnModel().getColumn(4).setPreferredWidth(240);

        JTableHeader header = notesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setForeground(TEAL);
        header.setBackground(INPUT);
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);

        notesTable.setDefaultRenderer(
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

        JScrollPane scrollPane = new JScrollPane(notesTable);
        scrollPane.setBorder(new LineBorder(BORDER, 1, true));
        scrollPane.getViewport().setBackground(PANEL);

        notesTable.getSelectionModel().addListSelectionListener(e -> {

            if (e.getValueIsAdjusting()) {
                return;
            }

            int selectedRow = notesTable.getSelectedRow();

            if (selectedRow >= 0) {

                int row = notesTable.convertRowIndexToModel(selectedRow);

                noteIdField.setText(
                        tableModel.getValueAt(row, 0).toString()
                );
                subjectIdField.setText(
                        tableModel.getValueAt(row, 1).toString()
                );
                titleField.setText(
                        tableModel.getValueAt(row, 2).toString()
                );
                descriptionArea.setText(
                        tableModel.getValueAt(row, 3).toString()
                );
                filePathField.setText(
                        tableModel.getValueAt(row, 4).toString()
                );

                descriptionArea.setCaretPosition(0);
                setStatus("Note selected.");
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
    // REFRESH NOTES
    // --------------------------------------------------

    private void refreshNotes() {

        try {
            List<Note> notes = noteDAO.getAllNotesList();

            tableModel.setRowCount(0);

            if (notes != null) {
                for (Note note : notes) {
                    tableModel.addRow(new Object[]{
                            note.getNoteId(),
                            note.getSubjectId(),
                            note.getTitle(),
                            note.getDescription(),
                            note.getFilePath()
                    });
                }
            }

            updateCount();
            setStatus("Notes refreshed.");

        } catch (Exception ex) {
            ex.printStackTrace();
            setStatus("Unable to load notes.");
            showMessage(
                    "Could not load notes.\nDetails: " + ex.getMessage()
            );
        }
    }

    private void updateCount() {

        int count = tableModel.getRowCount();

        countLabel.setText(
                count + (count == 1 ? " note" : " notes")
        );
    }

    private void viewNotes() {
        refreshNotes();
    }

    // --------------------------------------------------
    // ADD NOTE
    // --------------------------------------------------

    private void addNote() {

        try {
            int subjectId = Integer.parseInt(
                    subjectIdField.getText().trim()
            );

            if (subjectId <= 0) {
                showMessage("Subject ID must be a positive number.");
                return;
            }

            String title = titleField.getText().trim();

            if (title.isEmpty()) {
                showMessage("Please enter a note title.");
                return;
            }

            Note note = new Note(
                    0,
                    subjectId,
                    title,
                    descriptionArea.getText().trim(),
                    filePathField.getText().trim()
            );

            noteDAO.addNote(note);

            refreshNotes();
            clearFields();

            setStatus("Add request processed. Verify the table.");

            showMessage(
                    "Add request processed.\n"
                    + "Check the notes table to verify the result."
            );

        } catch (NumberFormatException ex) {
            showMessage("Please enter a valid Subject ID.");
        } catch (Exception ex) {
            ex.printStackTrace();
            showMessage(
                    "Could not add note.\nDetails: " + ex.getMessage()
            );
        }
    }

    // --------------------------------------------------
    // UPDATE NOTE
    // --------------------------------------------------

    private void updateNote() {

        try {
            int noteId = Integer.parseInt(
                    noteIdField.getText().trim()
            );

            int subjectId = Integer.parseInt(
                    subjectIdField.getText().trim()
            );

            if (noteId <= 0 || subjectId <= 0) {
                showMessage(
                        "Note ID and Subject ID must be positive numbers."
                );
                return;
            }

            String title = titleField.getText().trim();

            if (title.isEmpty()) {
                showMessage("Please enter a note title.");
                return;
            }

            Note existingNote = noteDAO.getNoteById(noteId);

            if (existingNote == null) {
                showMessage("Note ID not found.");
                return;
            }

            Note note = new Note(
                    noteId,
                    subjectId,
                    title,
                    descriptionArea.getText().trim(),
                    filePathField.getText().trim()
            );

            noteDAO.updateNote(note);

            refreshNotes();

            setStatus("Update request processed. Verify the table.");

            showMessage(
                    "Update request processed.\n"
                    + "Check the notes table to verify the result."
            );

        } catch (NumberFormatException ex) {
            showMessage("Enter valid Note ID and Subject ID values.");
        } catch (Exception ex) {
            ex.printStackTrace();
            showMessage(
                    "Could not update note.\nDetails: " + ex.getMessage()
            );
        }
    }

    // --------------------------------------------------
    // DELETE NOTE
    // --------------------------------------------------

    private void deleteNote() {

        try {
            int noteId = Integer.parseInt(
                    noteIdField.getText().trim()
            );

            if (noteId <= 0) {
                showMessage("Note ID must be a positive number.");
                return;
            }

            Note existingNote = noteDAO.getNoteById(noteId);

            if (existingNote == null) {
                showMessage("Note ID not found.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Delete note ID " + noteId + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {

                noteDAO.deleteNote(noteId);

                refreshNotes();
                clearFields();

                setStatus("Delete request processed. Verify the table.");

                showMessage(
                        "Delete request processed.\n"
                        + "Check the notes table to verify the result."
                );
            }

        } catch (NumberFormatException ex) {
            showMessage("Please enter a valid Note ID.");
        } catch (Exception ex) {
            ex.printStackTrace();
            showMessage(
                    "Could not delete note.\nDetails: " + ex.getMessage()
            );
        }
    }

    // --------------------------------------------------
    // CLEAR FIELDS
    // --------------------------------------------------

    private void clearFields() {

        noteIdField.setText("");
        subjectIdField.setText("");
        titleField.setText("");
        descriptionArea.setText("");
        filePathField.setText("");

        notesTable.clearSelection();
        setStatus("Fields cleared.");
    }

    // --------------------------------------------------
    // STATUS AND MESSAGES
    // --------------------------------------------------

    private void setStatus(String message) {

        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }

    private void showMessage(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Notes Management",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // --------------------------------------------------
    // MAIN METHOD
    // --------------------------------------------------

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                NoteManagementFrame::new
        );
    }
}
