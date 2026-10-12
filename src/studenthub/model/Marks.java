package studenthub.model;

public class Marks {

    private int markId;
    private int studentId;
    private int subjectId;
    private String examType;
    private double marksObtained;
    private double maxMarks;

    public Marks() {
    }

    public Marks(int markId, int studentId, int subjectId,
                 String examType, double marksObtained, double maxMarks) {

        this.markId = markId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.examType = examType;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
    }

    public int getMarkId() {
        return markId;
    }

    public void setMarkId(int markId) {
        this.markId = markId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(double marksObtained) {
        this.marksObtained = marksObtained;
    }

    public double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(double maxMarks) {
        this.maxMarks = maxMarks;
    }
}