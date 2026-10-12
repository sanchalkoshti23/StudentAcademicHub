package studenthub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import studenthub.model.Note;
import studenthub.util.DBConnection;

public class NoteDAO {

    // Add Note
    public void addNote(Note note) {

        String sql = "INSERT INTO notes "
                   + "(subject_id, title, description, file_path) "
                   + "VALUES (?, ?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, note.getSubjectId());
            ps.setString(2, note.getTitle());
            ps.setString(3, note.getDescription());
            ps.setString(4, note.getFilePath());

            ps.executeUpdate();

            System.out.println("Note added successfully!");

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    // View All Notes
    public void getAllNotes() {

        String sql = "SELECT note_id, subject_id, title, description, file_path "
                   + "FROM notes";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println("Note ID: " + rs.getInt("note_id"));
                System.out.println("Subject ID: " + rs.getInt("subject_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("File Path: " + rs.getString("file_path"));
                System.out.println("-------------------------");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    // Find Note By ID
    public Note getNoteById(int noteId) {

        String sql = "SELECT note_id, subject_id, title, description, file_path "
                   + "FROM notes WHERE note_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, noteId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Note note = new Note();

                note.setNoteId(rs.getInt("note_id"));
                note.setSubjectId(rs.getInt("subject_id"));
                note.setTitle(rs.getString("title"));
                note.setDescription(rs.getString("description"));
                note.setFilePath(rs.getString("file_path"));

                con.close();

                return note;
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }


    // Update Note
    public void updateNote(Note note) {

        String sql = "UPDATE notes SET "
                   + "subject_id = ?, "
                   + "title = ?, "
                   + "description = ?, "
                   + "file_path = ? "
                   + "WHERE note_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, note.getSubjectId());
            ps.setString(2, note.getTitle());
            ps.setString(3, note.getDescription());
            ps.setString(4, note.getFilePath());
            ps.setInt(5, note.getNoteId());

            ps.executeUpdate();

            System.out.println("Note updated successfully!");

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }


 // Delete Note
    public void deleteNote(int noteId) {

        String sql = "DELETE FROM notes WHERE note_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, noteId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Note deleted successfully!");
            } else {
                System.out.println("Note not found!");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    } 
 // Search Notes By Subject
    public void getNotesBySubject(int subjectId) {

        String sql = "SELECT note_id, subject_id, title, description, file_path "
                   + "FROM notes WHERE subject_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, subjectId);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("Note ID: " + rs.getInt("note_id"));
                System.out.println("Subject ID: " + rs.getInt("subject_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("File Path: " + rs.getString("file_path"));
                System.out.println("-------------------------");
            }

            if (!found) {
                System.out.println("No notes found for this subject!");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
 // Get all notes as a list for the GUI
    public List<Note> getAllNotesList() {

        List<Note> notes = new ArrayList<>();

        String sql = "SELECT note_id, subject_id, title, description, file_path "
                   + "FROM notes ORDER BY note_id";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {

                Note note = new Note(
                    rs.getInt("note_id"),
                    rs.getInt("subject_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("file_path")
                );

                notes.add(note);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return notes;
    }}