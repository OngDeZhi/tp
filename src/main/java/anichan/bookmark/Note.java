package anichan.bookmark;

import java.util.ArrayList;

/**
 * Represents a note for a bookmark entry.
 */
public class Note {

    private ArrayList<String> notes;

    /**
     * Creates a new instance of note.
     */
    public Note() {
        this.notes = new ArrayList<>();
    }

    /**
     * Add a note into note list.
     *
     * @param note the note to be added
     */
    public void addNote(String note) {
        this.notes.add(note);
    }

    /**
     * Gets the note at note id.
     *
     * @param noteIndex the note id of note to retrieve
     * @return the note in String
     */
    public String getNote(int noteIndex) {
        return notes.get(noteIndex);
    }

    /**
     * Gets the size of the notes.
     *
     * @return size of notes
     */
    public int getSize() {
        return notes.size();
    }

    /**
     * Remove a note from notes(note list) using note id.
     *
     * @param noteIndex the note index to be remove
     * @return String of the note removed
     */
    public String removeNote(int noteIndex) {
        String note = notes.get(noteIndex);
        this.notes.remove(noteIndex);
        return note;
    }

}
