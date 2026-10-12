package studenthub.test;

import studenthub.dao.NoteDAO;

public class noteTest {

    public static void main(String[] args) {

        NoteDAO dao = new NoteDAO();

        dao.deleteNote(2);

    }
}