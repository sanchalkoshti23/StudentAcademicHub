package studenthub.model;

public class Subject {

    private int id;
    private String name;
    private String course;
    private int semester;

    // Default constructor
    public Subject() {
    }

    // Constructor
    public Subject(int id, String name, String course, int semester) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.semester = semester;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
    @Override
    public String toString() {
        return getId() + " - " + getName();
    }
}