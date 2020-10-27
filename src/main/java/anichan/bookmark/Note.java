package anichan.bookmark;

import anichan.exception.AniException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Note implements DateTime {

    public ArrayList<String> notes;
    public ArrayList<Date> deadlines;
    public Date deadline;
    public boolean isDateTime;
    public boolean isDate;

    public Note() {
        this.notes = new ArrayList<>();
        this.deadlines = new ArrayList<>();
    }

    public void addNote(String note, String deadlineString) throws AniException {
        this.notes.add(note);
        parseToDate(deadlineString);
        if (isDateTime || isDate) {
            this.deadlines.add(deadline);
        } else {
            throw new AniException("hmm no date ?");
        }
    }

    public void addNote(String note) {
        this.notes.add(note);
        this.deadlines.add(null);
    }

    public String getNote(int noteIndex) {
        return notes.get(noteIndex);
    }

    public int getSize() {
        return notes.size();
    }

    public String removeNote(int noteIndex) {
        String note = notes.get(noteIndex);
        this.notes.remove(noteIndex);
        this.deadlines.remove(noteIndex);
        return note;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < notes.size(); i++) {
            result += notes.get(i) + System.lineSeparator();
        }
        return result;
    }

    @Override
    public void parseToDate(String input) throws AniException {
        try {
            deadline = stringToDate.parse(input);
            isDate = true;
            if (!input.equals(stringToDate.format(deadline))) {
                deadline = null;
                isDate = false;
            }

            if (isDate) {
                return;
            }

            deadline = stringToDateTime.parse(input);
            isDateTime = true;
            if (!input.equals(stringToDateTime.format(deadline))) {
                deadline = null;
                isDateTime = false;
            }
        } catch (ParseException e) {
            isDate = false;
            isDateTime = false;
            throw new AniException("hmm wrong date");
        }
    }
}
