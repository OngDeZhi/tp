package anichan.parser;

import anichan.command.BookmarkCommand;
import anichan.exception.AniException;

import static anichan.logger.AniLogger.getAniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BookmarkParser extends CommandParser {

    public static final String ADD_PARAM = "a";
    public static final String DELETE_PARAM = "d";
    public static final String EPISODE_PARAM = "e";
    public static final String LIST_PARAM = "l";
    public static final String INFO_PARAM = "i";
    public static final String ADD_NOTE_PARAM = "n";
    public static final String REMOVE_NOTE_PARAM = "r";
    public static final String DASH_PARAM = "-";
    private static final String PARAMETER_ERROR_HEADER = "Parameter :";
    private static final String DESCRIPTION_ERROR_HEADER = "Description :";
    private static final String BOOKMARK_LOAD_ERROR_HEADER = "Could not load bookmark command :";
    private static final Logger LOGGER = getAniLogger(BookmarkParser.class.getName());

    private BookmarkCommand bookmarkCommand;

    public BookmarkParser() {
        bookmarkCommand = new BookmarkCommand();
        // LOGGER.setLevel(Level.WARNING);
    }

    public BookmarkCommand parse(String description) throws AniException {
        String[] paramGiven = getSplitDescription(description);
        if (paramGiven.length > 1) {
            parameterParser(paramGiven[1]);
            setFirstParameter(paramGiven[0]);
        } else {
            setSingleParameter(description);
        }

        return bookmarkCommand;
    }

    private void parameterParser(String paramGiven) throws AniException {
        String[] paramParts = paramGiven.split(" ");
        paramEmptyCheck(paramGiven, paramParts);
        switch (paramParts[0].trim()) {
        case EPISODE_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkCommand.setBookmarkAction(paramParts[0]);
            if (!isInt(paramParts[1].trim())) {
                String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                        + System.lineSeparator() + " Bookmark edit episode param requires integer.";
                LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidParameter);
                throw new AniException(invalidParameter);
            }
            bookmarkCommand.setBookmarkEpisode(paramParts[1].trim());
            break;
        case ADD_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkCommand.setBookmarkAction(paramParts[0]);
            if (!isInt(paramParts[1].trim())) {
                String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                        + System.lineSeparator() + " Bookmark Add param requires integer.";
                LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidParameter);
                throw new AniException(invalidParameter);
            }
            bookmarkCommand.setAnimeIndex(paramParts[1].trim());
            break;
        case DELETE_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkCommand.setBookmarkAction(paramParts[0]);
            if (!isInt(paramParts[1].trim())) {
                String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                        + System.lineSeparator() + " Bookmark delete param requires integer.";
                LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidParameter);
                throw new AniException(invalidParameter);
            }
            bookmarkCommand.setBookmarkIndex(paramParts[1].trim());
            break;
        case LIST_PARAM:
            bookmarkCommand.setBookmarkAction(paramParts[0]);
            listFieldCheck(paramParts);
            break;
        case ADD_NOTE_PARAM:
            paramFieldCheck(paramParts);
            paramParts = paramGiven.split(" ", 2);
            bookmarkCommand.setBookmarkAction(paramParts[0]);
            bookmarkCommand.setBookmarkNote(paramParts[1].trim());
            break;
        case REMOVE_NOTE_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkCommand.setBookmarkAction(paramParts[0]);
            if (!isInt(paramParts[1].trim())) {
                String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                        + System.lineSeparator() + " Bookmark remove note param requires integer.";
                LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidParameter);
                throw new AniException(invalidParameter);
            }
            bookmarkCommand.setNoteIndex(paramParts[1]);
            break;
        default:
            String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED;
            throw new AniException(invalidParameter);
        }
    }

    private void paramEmptyCheck(String paramGiven, String[] paramParts) throws AniException {
        if (paramParts.length == 0) {
            String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                    + System.lineSeparator() + " The parameter is empty";
            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidParameter);
            throw new AniException(invalidParameter);
        }
    }

    private void listFieldCheck(String[] paramParts) throws AniException {
        if (paramParts.length > 1) {
            String invalidExtraField = PARAMETER_ERROR_HEADER + paramParts[1] + NOT_RECOGNISED
                    + System.lineSeparator() + " Bookmark list should not have extra field.";
            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidExtraField);
            throw new AniException(invalidExtraField);
        }
    }

    private void setFirstParameter(String paramGiven) throws AniException {
        //Action edit(e), note(n), remove note(r) requires first parameter as bookmarkIndex
        if (bookmarkCommand.getBookmarkAction().equals("e")
                || bookmarkCommand.getBookmarkAction().equals("n")
                || bookmarkCommand.getBookmarkAction().equals("r")) {
            if (!isInt(paramGiven.trim())) {
                String invalidBookmarkIndex = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                        + System.lineSeparator() + " Bookmark index for edit episode requires integer.";
                LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidBookmarkIndex);
                throw new AniException(invalidBookmarkIndex);
            }
            bookmarkCommand.setBookmarkIndex(paramGiven.trim());
        } else {
            boolean isEmpty = paramGiven.trim().equals("");
            if (!isEmpty) {
                String invalidFirstParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                        + System.lineSeparator() + " Add/Delete/List should not have extra first param.";
                LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidFirstParameter);
                throw new AniException(invalidFirstParameter);
            }
        }
    }

    private void setSingleParameter(String paramGiven) throws AniException {
        if (!isInt(paramGiven.trim())) {
            String invalidBookmarkIndex = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                    + System.lineSeparator() + " Bookmark index for info requires integer.";
            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidBookmarkIndex);
            throw new AniException(invalidBookmarkIndex);
        }
        bookmarkCommand.setBookmarkAction(INFO_PARAM);
        bookmarkCommand.setBookmarkIndex(paramGiven.trim());
    }

    private String[] getSplitDescription(String description) throws AniException {
        String[] paramGiven = description.split(DASH_PARAM);
        if (paramGiven.length > 2) {
            String invalidDescription = DESCRIPTION_ERROR_HEADER + description + TOO_MUCH_FIELDS;
            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidDescription);
            throw new AniException(invalidDescription);
        }
        //        else if (paramGiven.length < 2) {
        //            String invalidDescription = DESCRIPTION_ERROR_HEADER + description + REQUIRE_ADDITIONAL_FIELD;
        //            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidDescription);
        //            throw new AniException(invalidDescription);
        //        }
        return paramGiven;
    }
}
