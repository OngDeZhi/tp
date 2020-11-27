package anichan.bookmark;

import anichan.anime.Anime;
import anichan.anime.AnimeData;

import java.util.ArrayList;

/**
 * Represent the bookmark of a workspace.
 */
public class Bookmark {
    private static final String NOTES_EMPTY_MESSAGE = "\tNotes is empty.. :(";
    private static final String LIST_EMPTY_MESSAGE = "\tUhh.. It's empty.. :(";
    private static final int EMPTY_SIZE = 0;
    private static final int ONE_BASED_OFFSET = 1;
    private ArrayList<Integer> animeBookmarkList;
    private ArrayList<Integer> animeEpisode;
    private ArrayList<Note> noteList;

    /**
     * Creates an instance of bookmark.
     */
    public Bookmark() {
        this.animeEpisode = new ArrayList<>();
        this.animeBookmarkList = new ArrayList<>();
        this.noteList = new ArrayList<>();
    }

    /**
     * Add an anime id as new bookmark entry.
     *
     * @param animeIndex the anime id to keep track in bookmark
     */
    public void addAnimeBookmark(int animeIndex) {
        this.animeBookmarkList.add(animeIndex);
        this.animeEpisode.add(0);
        this.noteList.add(new Note());
    }

    /**
     * Add a new bookmark entry using anime id, current episode and note list.
     *
     * @param animeIndex the anime id of the new bookmark entry
     * @param episodeNumber the current episode of the new bookmark entry
     * @param note the note list of the new bookmark entry
     */
    public void addAnimeBookmark(int animeIndex, int episodeNumber, Note note) {
        this.animeBookmarkList.add(animeIndex);
        this.animeEpisode.add(episodeNumber);
        this.noteList.add(note);
    }

    /**
     * Remove the a bookmark entry.
     * Need to remove the bookmark reference within the 3 arraylist.
     *
     * @param bookmarkIndex the bookmark entry id to deleted
     */
    public void removeAnimeBookmark(int bookmarkIndex) {
        this.animeBookmarkList.remove(bookmarkIndex);
        this.animeEpisode.remove(bookmarkIndex);
        this.noteList.remove(bookmarkIndex);
    }

    /**
     * Change the current episode for bookmark entry.
     *
     * @param bookmarkIndex the bookmark entry id
     * @param episode the new current episode to edit to
     */
    public void editAnimeBookmarkEpisode(int bookmarkIndex, int episode) {
        this.animeEpisode.set(bookmarkIndex, episode);
    }

    /**
     * Gets the list of anime id for all bookmark.
     *
     * @return the list of anime id for all bookmark
     */
    public ArrayList<Integer> getAnimeBookmarkList() {
        return animeBookmarkList;
    }

    /**
     * Gets the list of current episode for all bookmark.
     *
     * @return  the list of current episode for all bookmark
     */
    public ArrayList<Integer> getAnimeEpisode() {
        return animeEpisode;
    }

    /**
     * Gets the list of notes for all bookmark.
     *
     * @return the list of notes for all bookmark
     */
    public ArrayList<Note> getAnimeNote() {
        return noteList;
    }

    /**
     * Gets the current episode of the bookmark entry.
     *
     * @param bookmarkIndex the bookmark entry id
     * @return the current episode of the bookmark entry
     */
    public int getBookmarkEpisode(int bookmarkIndex) {
        return animeEpisode.get(bookmarkIndex);
    }

    /**
     * Gets the anime object of the anime id in the bookmark entry.
     *
     * @param animeData anime data source
     * @param bookmarkIndex the bookmark entry id
     * @return the anime object that bookmark entry reference using anime id
     */
    public Anime getAnimeBookmarkByIndex(AnimeData animeData, int bookmarkIndex) {
        int animeIndex = this.animeBookmarkList.get(bookmarkIndex);
        return animeData.getAnime(animeIndex);
    }

    /**
     * Gets the size of bookmark list.
     *
     * @return the size of bookmark list
     */
    public int getBookmarkSize() {
        return animeBookmarkList.size();
    }

    /**
     * Gets the size of the note list for that bookmark entry.
     *
     * @param bookmarkIndex the bookmark entry id
     * @return the size of the note list for the bookmark entry
     */
    public int getNotesSize(int bookmarkIndex) {
        return this.noteList.get(bookmarkIndex).getSize();
    }

    /**
     * Add a note to bookmark entry.
     *
     * @param bookmarkIndex the bookmark entry id
     * @param note the note to be added
     */
    public void addNote(int bookmarkIndex, String note) {
        this.noteList.get(bookmarkIndex).addNote(note);
    }

    /**
     * Remove a note from a bookmark entry.
     *
     * @param bookmarkIndex the bookmark entry id
     * @param noteIndex the note id to be deleted
     * @return the note remove
     */
    public String removeNote(int bookmarkIndex, int noteIndex) {
        Note note = noteList.get(bookmarkIndex);
        String removeNoteOutput = note.removeNote(noteIndex);
        return removeNoteOutput;
    }

    /**
     * Retrieve all notes of a bookmark entry.
     *
     * @param bookmarkIndex the bookmark entry id
     * @return list of all notes or "notes is empty" string
     */
    public String getNoteInString(int bookmarkIndex) {
        StringBuilder sbNoteList = new StringBuilder(System.lineSeparator());
        if (noteList.get(bookmarkIndex).getSize() == EMPTY_SIZE) {
            sbNoteList.append(NOTES_EMPTY_MESSAGE);
            sbNoteList.append(System.lineSeparator());
        } else {
            for (int i = 0; i < noteList.get(bookmarkIndex).getSize(); i++) {
                sbNoteList.append(i + ONE_BASED_OFFSET);
                sbNoteList.append(". ");
                sbNoteList.append(noteList.get(bookmarkIndex).getNote(i) + System.lineSeparator());
            }
        }
        return sbNoteList.toString();
    }

    /**
     * Construct the list of bookmark into a string which consist of the bookmark id with the anime name.
     * Animedata is used to retrieve the anime name of the anime id each bookmark id keeps.
     *
     * @param animeData anime data source
     * @return List of the bookmark
     */
    public String getListInString(AnimeData animeData) {
        StringBuilder sbAnimeList = new StringBuilder(System.lineSeparator());
        if (animeBookmarkList.size() == EMPTY_SIZE) {
            sbAnimeList.append(LIST_EMPTY_MESSAGE);
            sbAnimeList.append(System.lineSeparator());
        }
        for (int i = 0; i < animeBookmarkList.size(); i++) {
            sbAnimeList.append("\t");
            sbAnimeList.append(i + ONE_BASED_OFFSET);
            sbAnimeList.append(". ");
            int animeIndex = this.animeBookmarkList.get(i);
            sbAnimeList.append(animeData.getAnime(animeIndex).getAnimeName());
            sbAnimeList.append(System.lineSeparator());
        }
        return sbAnimeList.toString();
    }

    public String getAnimeBookmarkInfo(AnimeData animeData, int bookmarkIndex) {
        return animeData.returnAnimeInfo(this.animeBookmarkList.get(bookmarkIndex));
    }

    /**
     * Check if anime id already exist within the bookmark.
     *
     * @param animeIndex anime id to be added
     * @return true if already exist, else otherwise
     */
    public boolean checkExist(int animeIndex) {
        boolean alreadyExist = false;
        for (Integer animeID : animeBookmarkList) {
            if (animeID.equals(animeIndex)) {
                alreadyExist = true;
                break;
            }
        }
        return alreadyExist;
    }
}
