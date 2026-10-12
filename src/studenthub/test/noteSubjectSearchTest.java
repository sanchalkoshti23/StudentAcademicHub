package studenthub.test;

import studenthub.dao.NoteDAO;

public class noteSubjectSearchTest {

    public static void main(String[] args) {

        NoteDAO dao = new NoteDAO();

        dao.getNotesBySubject(2);

    }
}