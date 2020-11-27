package anichan.storage;

import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author OngDeZhi
/**
 * Abstract class to represent the various storage type.
 */
public abstract class Storage {
    private static final String REGEX_POSITIVE_INTEGER = "^\\d+$";
    private static final String REGEX_NEGATIVE_INTEGER = "^[-]\\d+$";

    private static final String EMPTY_STRING = "";
    private static final String FILE_DOES_NOT_EXIST_ERROR = "File does not exist.";
    private static final String FILE_CONTAINS_INVALID_CHARACTER_ERROR = "File name contains invalid characters!";
    private static final String WRITE_TO_FILE_FAILED_ERROR = "Failed to write to file.";

    protected static final int MAX_ANIME_INDEX = 510;
    private static final Logger LOGGER = AniLogger.getAniLogger(Storage.class.getName());

    /**
     * Reads input file.
     *
     * @param filePath the path of the file to read from
     * @return {@code String} containing the file content
     * @throws AniException when unable to read from the file or the file name is invalid
     */
    public String readFile(String filePath) throws AniException {
        String fileContent = EMPTY_STRING;
        try {
            fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            LOGGER.log(Level.INFO, "Read from file: " + filePath);
        } catch (IOException exception) {
            LOGGER.log(Level.WARNING, "File does not exist at: " + filePath);
            throw new AniException(FILE_DOES_NOT_EXIST_ERROR);
        } catch (InvalidPathException exception) {
            // Invalid characters not caught in parser.
            LOGGER.log(Level.WARNING, "File name contains invalid characters: " + filePath);
            throw new AniException(FILE_CONTAINS_INVALID_CHARACTER_ERROR);
        }

        return fileContent;
    }

    /**
     * Writes to file based on the content supplied.
     *
     * @param filePath the path to the file to be written
     * @param fileContent the content to be written
     * @throws AniException When unable to write to the file
     */
    public void writeFile(String filePath, String fileContent) throws AniException {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(fileContent);
            fileWriter.close();
            LOGGER.log(Level.INFO, "Wrote to file: " + filePath);
        } catch (IOException exception) {
            LOGGER.log(Level.WARNING, "Failed to write to file at: " + filePath);
            throw new AniException(WRITE_TO_FILE_FAILED_ERROR);
        }
    }

    /**
     * Checks if a {@code String} is a positive integer.
     *
     * @param integerString the {@code String} to be checked
     * @return {@code true} if {@code integerString} is a positive integer; {@code false} otherwise
     */
    public boolean isPositiveInteger(String integerString) {
        return integerString.matches(REGEX_POSITIVE_INTEGER);
    }

    /**
     * Checks if a {@code String} is a (positive or negative) integer.
     *
     * @param integerString the {@code String} to be checked
     * @return {@code true} if {@code integerString} is a (positive or negative) integer; {@code false} otherwise
     */
    public boolean isPositiveOrNegativeInteger(String integerString) {
        return integerString.matches(REGEX_POSITIVE_INTEGER) || integerString.matches(REGEX_NEGATIVE_INTEGER);
    }

    /**
     * Parses the string argument as a signed integer.
     *
     * @param stringInteger {@code String} argument to be parsed to {@code integer}
     * @return the {@code integer} that was parsed successfully
     */
    protected int parseStringToInteger(String stringInteger) {
        try {
            return Integer.parseInt(stringInteger);
        } catch (NumberFormatException exception) {
            // To indicate parsing failed, it is not a good idea to throw exception here
            // because that would break the entire data loading process, causing valid
            // watchlist entry to not be loaded too.

            LOGGER.log(Level.WARNING, "Received invalid anime index: " + stringInteger);
            return (MAX_ANIME_INDEX + 1);
        }
    }
}
