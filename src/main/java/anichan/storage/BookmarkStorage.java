package anichan.storage;

import anichan.bookmark.Bookmark;
import anichan.bookmark.Note;
import anichan.exception.AniException;

import java.io.File;
import java.util.ArrayList;

//@@author OngXinBin

/**
 * Manages the storage of bookmark data.
 */
public class BookmarkStorage extends Storage {
    private static final String BOOKMARK_FILE_NAME = "bookmark.txt";
    private static final String BOOKMARK_LINE_DELIMITER = "~";
    private static final String EMPTY_BOOKMARK_FILE_MESSAGE = "Empty bookmark file.";

    private final String storageDirectory;

    /**
     * Creates a new instance of BookmarkStorage with the specified storage directory.
     *
     * @param storageDirectory the specified path to bookmark directory in hard disk.
     */
    public BookmarkStorage(String storageDirectory) {
        this.storageDirectory = storageDirectory;
    }

    // ========================== Save and Load ==========================

    /**
     * Saves the bookmark data.
     *
     * @param workspaceName the active workspace name which house the bookmark to be saved
     * @param bookmark      the bookmark object to be saved
     * @throws AniException when an error occurred while saving user data
     */
    public void save(String workspaceName, Bookmark bookmark) throws AniException {
        String bookmarkDirectory = storageDirectory + workspaceName + File.separator;
        String bookmarkFilePath = bookmarkDirectory + BOOKMARK_FILE_NAME;
        String encodedWatchlistString = encode(bookmark);
        new File(bookmarkDirectory).mkdirs();
        writeFile(bookmarkFilePath, encodedWatchlistString);
    }

    /**
     * Loads the user data.
     *
     * @param workspaceName the workspace name determine the folder which contains the bookmark file
     * @param bookmark      the bookmark to load the bookmark object
     * @return the bookmark object that was loaded
     * @throws AniException when an error occurred while executing the command
     */
    public String load(String workspaceName, Bookmark bookmark) throws AniException {
        String bookmarkFilePath = storageDirectory + workspaceName + File.separator + BOOKMARK_FILE_NAME;
        String fileString = readFile(bookmarkFilePath);
        if (fileString.isBlank()) {
            return EMPTY_BOOKMARK_FILE_MESSAGE;
        }

        String[] fileLines = fileString.split(System.lineSeparator());
        return decode(fileLines, bookmark);
    }

    // ========================== Encode and Decode ==========================

    /**
     * Encodes the user object into a readable string representation for saving in file.
     *
     * @param bookmark the bookmark object to be saved
     * @return the readable string representation of the bookmark object
     */
    private String encode(Bookmark bookmark) {
        StringBuilder sbBookmark = new StringBuilder();
        ArrayList<Integer> animeBookmarkList = bookmark.getAnimeBookmarkList();
        ArrayList<Integer> animeEpisode = bookmark.getAnimeEpisode();
        ArrayList<Note> animeNote = bookmark.getAnimeNote();
        for (int i = 0; i < bookmark.getBookmarkSize(); i++) {
            sbBookmark.append(animeBookmarkList.get(i));
            sbBookmark.append(BOOKMARK_LINE_DELIMITER);
            sbBookmark.append(animeEpisode.get(i));
            sbBookmark.append(BOOKMARK_LINE_DELIMITER);
            Note note = animeNote.get(i);

            for (int j = 0; j < note.getSize(); j++) {
                sbBookmark.append(note.getNote(j));
                sbBookmark.append(BOOKMARK_LINE_DELIMITER);
            }
            if (note.getSize() != 0) {
                sbBookmark.setLength(sbBookmark.length() - 1); // Remove "~" for the last item in the string.
            }
            sbBookmark.append(System.lineSeparator());
        }
        String encodedBookmarkString = sbBookmark.toString();
        //assert (encodedBookmarkString.isBlank()) : "Encoded bookmark string should not be blank.";
        return encodedBookmarkString;
    }

    /**
     * Decodes the readable string representation of bookmark object.
     *
     * @param fileLines readable string representation of the user object
     * @param bookmark  the bookmark to load bookmark object
     * @return the result of loading the bookmark object (Successful or not successful)
     */
    private String decode(String[] fileLines, Bookmark bookmark) {
        boolean hasCorruptedBookmark = false;
        for (String line : fileLines) {
            String[] lineSplit = line.split(BOOKMARK_LINE_DELIMITER, 3);
            if (!isValidBookmarkString(lineSplit)) {
                hasCorruptedBookmark = true;
                continue;
            }

            int bookmarkIndex = Integer.parseInt(lineSplit[0]);
            int bookmarkEpisode = Integer.parseInt(lineSplit[1]);
            Note note = new Note();
            String[] lineSplitNotes = lineSplit[2].split(BOOKMARK_LINE_DELIMITER);
            if (lineSplitNotes[0].trim().length() > 0) {
                for (String noteString : lineSplitNotes) {
                    note.addNote(noteString.trim());
                }
            }

            bookmark.addAnimeBookmark(bookmarkIndex, bookmarkEpisode, note);
        }

        if (hasCorruptedBookmark) {
            return "Not loaded successfully.";
        } else {
            return "Loaded successfully.";
        }
    }

    // ========================== Validation ==========================

    /**
     * Validates the string representation of the bookmark object.
     *
     * @param lineSplit the string representation of the bookmark object
     * @return {@code true} if the string representation is valid; false otherwise
     */
    private boolean isValidBookmarkString(String[] lineSplit) {
        boolean isValidSplitLength = (lineSplit.length == 3);
        if (!isValidSplitLength) {
            return false;
        }
        boolean isFirstPartInteger = isPositiveOrNegativeInteger(lineSplit[0]);
        boolean isSecondPartInteger = isPositiveOrNegativeInteger(lineSplit[1]);
        return isFirstPartInteger && isSecondPartInteger;
    }
}
