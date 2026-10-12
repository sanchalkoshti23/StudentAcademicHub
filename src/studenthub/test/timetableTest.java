package studenthub.test;

import studenthub.dao.TimetableDAO;

public class timetableTest {

    public static void main(String[] args) {

        TimetableDAO dao = new TimetableDAO();

        // Search timetable for Monday
        dao.getTimetableByDay("Monday");
    }
}