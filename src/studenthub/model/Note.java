package studenthub.model;

public class Note {

    private int noteId;
    private int subjectId;
    private String title;
    private String description;
    private String filePath;

    // Empty constructor
    public Note() {
    }

    // Parameterized constructor
    public Note(int noteId, int subjectId, String title,
                String description, String filePath) {

        this.noteId = noteId;
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
    }

    // Getter and Setter for noteId
    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    // Getter and Setter for subjectId
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for filePath
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}