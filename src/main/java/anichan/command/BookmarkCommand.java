package anichan.command;

import anichan.human.User;
import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.bookmark.Bookmark;
import anichan.exception.AniException;
import anichan.human.Workspace;
import anichan.parser.BookmarkParser;
import anichan.storage.StorageManager;

import static anichan.logger.AniLogger.getAniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BookmarkCommand extends Command {

    private static final String ANIME_ID_ERROR = " Anime index is outside AnimeData range "
            + "(Bigger than number of anime).";
    private static final String ANIME_ID_EXIST_ERROR = " Anime index is already in bookmark.";
    private static final String BOOKMARK_ID_ERROR = " Bookmark index is outside Bookmark range "
            + "(Bigger than bookmark entries).";
    private static final String NOTES_ID_ERROR = " Bookmark index is outside Bookmark range "
            + "(Bigger than number of notes).";
    private static final String BOOKMARK_EXECUTE_ERROR_HEADER = "Bookmark command execute failed:";
    private static final String BOOKMARK_ERROR_MESSAGE = " provided is invalid.";
    private static final String BOOKMARK_EXECUTE_EDIT = "Executing Edit Episode.";
    private static final String BOOKMARK_EXECUTE_ADD = "Executing Add Anime to Bookmark.";
    private static final String BOOKMARK_EXECUTE_DELETE = "Executing Delete Anime from Bookmark.";
    private static final String BOOKMARK_EXECUTE_LIST = "Executing List all anime in Bookmark.";
    private static final String BOOKMARK_EXECUTE_SUCCESS = "Execute Bookmark command successful.";
    private static final String BOOKMARK_LIST_HEADER = "Listing all anime in bookmark:";
    private int bookmarkIndex;
    private int animeIndex;


    private int noteIndex;
    private int bookmarkEpisode;
    private String bookmarkAction;
    private String bookmarkNote;
    private static final Logger LOGGER = getAniLogger(BookmarkCommand.class.getName());

    public BookmarkCommand() {
        // Set log levels
        // LOGGER.setLevel(Level.WARNING);
        bookmarkAction = null;
        LOGGER.log(Level.INFO, "Successfully loaded fields for Bookmark command.");
    }

    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        String result = "";
        Workspace workspace = user.getActiveWorkspace();
        Bookmark bookmark = user.getActiveWorkspace().getBookmark();
        switch (bookmarkAction) {
        case BookmarkParser.EPISODE_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_EDIT);
            result = editBookmarkEpisode(animeData, bookmark);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        case BookmarkParser.ADD_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_ADD);
            result = addBookmarkEntry(animeData, bookmark);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        case BookmarkParser.DELETE_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_DELETE);
            result = deleteBookmarkEntry(animeData, bookmark);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        case BookmarkParser.LIST_PARAM:
            LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_LIST);
            result = BOOKMARK_LIST_HEADER;
            String bookmarkList = listBookmark(animeData, bookmark);
            result += bookmarkList;
            break;
        case BookmarkParser.INFO_PARAM:
            LOGGER.log(Level.INFO, "Executing anime info for bookmark");
            result = "Here is the information for that anime." + System.lineSeparator();
            result += getBookmarkInfo(animeData, bookmark);
            break;
        case BookmarkParser.ADD_NOTE_PARAM:
            LOGGER.log(Level.INFO, "Executing bookmark add note");
            result = addNoteToBookmark(animeData, bookmark);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        case BookmarkParser.REMOVE_NOTE_PARAM:
            LOGGER.log(Level.INFO, "Executing bookmark add note");
            result = removeNoteFromBookmark(animeData, bookmark);
            storageManager.saveBookmark(workspace.getName(), bookmark);
            break;
        default:
            break;
        }
        LOGGER.log(Level.INFO, BOOKMARK_EXECUTE_SUCCESS);
        return result;
    }


    private String getBookmarkInfo(AnimeData animeData, Bookmark bookmark) throws AniException {
        String result = "";
        String animeInfo = getAnimeInfoFromBookmark(animeData, bookmark);
        result += animeInfo + System.lineSeparator() + System.lineSeparator();

        Integer bookmarkEpisodeInfo = bookmark.getBookmarkEpisode(bookmarkIndex - 1);
        if (bookmarkEpisodeInfo != 0) {
            result += "Current Episode: ";
            result += bookmarkEpisodeInfo;
            result += System.lineSeparator() + System.lineSeparator();
        }

        result += "Notes for anime:";
        String notesInfo = getAnimeNotesFromBookmark(bookmark);
        result += notesInfo;
        return result;
    }

    private String getAnimeNotesFromBookmark(Bookmark bookmark) {
        String notesInfo = bookmark.getNoteInString(bookmarkIndex - 1);
        return notesInfo;
    }

    private String removeNoteFromBookmark(AnimeData animeData, Bookmark bookmark) throws AniException {
        checkBookmarkIndex(bookmark);
        checkNoteIndex(bookmark);
        String result = "Deleting note: ";
        result += bookmark.removeNote(bookmarkIndex - 1, noteIndex - 1);
        Anime animeToRemoveNote = bookmark.getAnimeBookmarkByIndex(animeData, bookmarkIndex - 1);
        result += System.lineSeparator() + "From " + animeToRemoveNote.getAnimeName() + ".";
        return result;
    }

    private String addNoteToBookmark(AnimeData animeData, Bookmark bookmark) throws AniException {
        String result;
        checkBookmarkIndex(bookmark);
        bookmark.addNote(bookmarkIndex - 1, bookmarkNote);
        Anime animeToDelete = bookmark.getAnimeBookmarkByIndex(animeData, bookmarkIndex - 1);
        result = "Adding note:\"" + bookmarkNote + "\" to " + animeToDelete.getAnimeName() + "!";
        return result;
    }

    private String getAnimeInfoFromBookmark(AnimeData animeData, Bookmark bookmark) throws AniException {
        checkBookmarkIndex(bookmark);
        String animeInfo = bookmark.getAnimeBookmarkInfo(animeData, bookmarkIndex - 1);
        return animeInfo;
    }

    private String deleteBookmarkEntry(AnimeData animeData, Bookmark bookmark) throws AniException {
        checkBookmarkIndex(bookmark);
        String result;
        Anime animeToDelete = bookmark.getAnimeBookmarkByIndex(animeData, bookmarkIndex - 1);
        result = "Removing " + animeToDelete.getAnimeName() + "! :(";
        bookmark.removeAnimeBookmark(bookmarkIndex - 1);
        return result;
    }


    private String addBookmarkEntry(AnimeData animeData, Bookmark bookmark) throws AniException {
        checkAnimeIndex(animeData);
        checkAnimeNotInBookmark(bookmark);
        String result;
        Anime animeToAdd = animeData.getAnime(animeIndex - 1);
        result = "Saving " + animeToAdd.getAnimeID() + ". " + animeToAdd.getAnimeName() + " to bookmark.";
        bookmark.addAnimeBookmark(animeIndex - 1);
        return result;
    }

    private void checkAnimeNotInBookmark(Bookmark bookmark) throws AniException {
        if (bookmark.checkExist(animeIndex - 1)) {
            String invalidAnimeIndex = "Anime index " + animeIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + ANIME_ID_EXIST_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidAnimeIndex);
            throw new AniException(invalidAnimeIndex);
        }
    }

    private String editBookmarkEpisode(AnimeData animeData, Bookmark bookmark) throws AniException {
        checkBookmarkIndex(bookmark);
        String result;
        bookmark.editAnimeBookmarkEpisode(bookmarkIndex - 1, bookmarkEpisode);
        Anime animeToEdit = bookmark.getAnimeBookmarkByIndex(animeData, bookmarkIndex - 1);
        result = "Editing " + animeToEdit.getAnimeName() + " to have " + bookmarkEpisode + " episode";
        return result;
    }

    private void checkBookmarkIndex(Bookmark bookmark) throws AniException {
        //Bookmark index is one based numbering
        if (bookmarkIndex > bookmark.getBookmarkSize()) {
            String invalidBookmarkIndex = "Bookmark index " + bookmarkIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + BOOKMARK_ID_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidBookmarkIndex);
            throw new AniException(invalidBookmarkIndex);
        }
    }

    private void checkNoteIndex(Bookmark bookmark) throws AniException {
        //Bookmark index is one based numbering
        if (noteIndex > bookmark.getNotesSize(bookmarkIndex - 1)) {
            String invalidBookmarkIndex = "Notes index " + noteIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + NOTES_ID_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidBookmarkIndex);
            throw new AniException(invalidBookmarkIndex);
        }
    }

    private void checkAnimeIndex(AnimeData animeData) throws AniException {
        //Anime index is one based numbering
        if (animeIndex > animeData.getSize()) {
            String invalidAnimeIndex = "Anime index " + animeIndex + BOOKMARK_ERROR_MESSAGE
                    + System.lineSeparator() + ANIME_ID_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_EXECUTE_ERROR_HEADER + invalidAnimeIndex);
            throw new AniException(invalidAnimeIndex);
        }
    }

    private String listBookmark(AnimeData animeData, Bookmark bookmark) throws AniException {
        return bookmark.getListInString(animeData);
    }

    public String getBookmarkAction() {
        return this.bookmarkAction;
    }

    public void setBookmarkAction(String actionString) {
        this.bookmarkAction = actionString;
    }

    public void setBookmarkIndex(String bookmarkIndexString) {
        this.bookmarkIndex = Integer.parseInt(bookmarkIndexString);
    }

    public void setAnimeIndex(String animeIndexString) {
        this.animeIndex = Integer.parseInt(animeIndexString);
    }

    public void setBookmarkEpisode(String bookmarkEpisodeString) {
        this.bookmarkEpisode = Integer.parseInt(bookmarkEpisodeString);
    }

    public void setBookmarkNote(String note) {
        this.bookmarkNote = note;
    }

    public void setNoteIndex(String noteIndex) {
        this.noteIndex = Integer.parseInt(noteIndex);
    }

}
