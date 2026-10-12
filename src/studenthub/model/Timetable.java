package studenthub.model;

import java.time.LocalTime;

public class Timetable {

    private int timetableId;
    private int subjectId;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;


    public Timetable() {
    }


    public Timetable(int timetableId, int subjectId,
                     String dayOfWeek, LocalTime startTime,
                     LocalTime endTime, String room) {

        this.timetableId = timetableId;
        this.subjectId = subjectId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }
    public int getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(int timetableId) {
        this.timetableId = timetableId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}