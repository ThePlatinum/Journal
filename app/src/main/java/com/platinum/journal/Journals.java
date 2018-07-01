package com.platinum.journal;

/**
 * Created by PLATINUM
 * Date 6/29/2018
 * Time 10:17 AM
 * Package com.platinum.journal
 * Project Journal
 */

public class Journals {
    public String Date , Time , Title , Note ;

    public Journals() {
    }

    public Journals(String date, String time, String title, String note) {
        Date = date;
        Time = time;
        Title = title;
        Note = note;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
