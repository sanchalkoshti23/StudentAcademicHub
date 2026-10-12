package studenthub.test;

import studenthub.dao.MarksDAO;
import studenthub.model.Marks;

public class marksTest {

    public static void main(String[] args) {

        MarksDAO dao = new MarksDAO();

        Marks marks = new Marks();

        marks.setStudentId(1);
        marks.setSubjectId(2);
        marks.setExamType("Midterm");
        marks.setMarksObtained(42);
        marks.setMaxMarks(50);

        dao.addMarks(marks);

    }
}