package anichan.storage;

import anichan.exception.AniException;
import anichan.logger.AniLogger;
import anichan.watchlist.Watchlist;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author OngDeZhi
/**
 * Represents the class to manage watchlist data.
 */
public class WatchlistStorage extends Storage {
    private static final String WATCHLIST_FILE_NAME = "watchlist.txt";
    private static final String WATCHLIST_LINE_DELIMITER_FOR_DECODE = "\\|";
    private static final String WATCHLIST_LINE_DELIMITER_FOR_ENCODE = " | ";
    private static final String DELIMITER_FOR_ENCODED_ANIME_LIST = ",";
    private static final String ENCODED_ANIME_LIST_FIRST_CHARACTER = "[";
    private static final String ENCODED_ANIME_LIST_LAST_CHARACTER = "]";

    private static final String EMPTY_WATCHLIST_FILE = "Empty watchlist file.";
    private static final String NO_WATCHLIST_LOADED = "No watchlist loaded successfully (all invalid).";
    private static final String SOME_WATCHLIST_LOADED = "Not all loaded successfully (some invalid).";
    private static final String LOAD_SUCCESS = "Loaded successfully.";

    private static final String REGEX_ALPHANUMERIC_WITH_SPACE = "^[a-zA-Z0-9\\s]*$";
    private static final int MAX_WATCHLIST_NAME_LENGTH = 30;
    private static final Logger LOGGER = AniLogger.getAniLogger(WatchlistStorage.class.getName());

    private final String storageDirectory;

    /**
     * Creates a new instance of WatchlistStorage with the specified storage directory.
     *
     * @param storageDirectory the specified path to storage directory in hard disk
     */
    public WatchlistStorage(String storageDirectory) {
        this.storageDirectory = storageDirectory;
    }

    // ========================== Save and Load ==========================

    /**
     * Saves the watchlist list at the specified workspace folder.
     *
     * @param workspaceName the name of the workspace to save the list under
     * @param watchlistList the watchlist list to save
     * @throws AniException when an error occurred while saving the watchlist list data
     */
    public void save(String workspaceName, ArrayList<Watchlist> watchlistList) throws AniException {
        String watchlistDirectory = storageDirectory + workspaceName + File.separator;
        String watchlistFilePath = watchlistDirectory + WATCHLIST_FILE_NAME;
        String encodedWatchlistString = encode(watchlistList);

        new File(watchlistDirectory).mkdirs();
        writeFile(watchlistFilePath, encodedWatchlistString);
    }

    /**
     * Loads the watchlist list from the specified workspace.
     *
     * @param workspaceName the name of the workspace to load the list from
     * @param watchlistList the watchlist list to load the data into
     * @return the load result message
     * @throws AniException when an error occurred while loading the watchlist list data
     */
    public String load(String workspaceName, ArrayList<Watchlist> watchlistList) throws AniException {
        String watchlistFilePath = storageDirectory + workspaceName + File.separator + WATCHLIST_FILE_NAME;
        String fileContent = readFile(watchlistFilePath);
        if (fileContent.isBlank()) {
            LOGGER.log(Level.WARNING, "Empty watchlist file: " + watchlistFilePath);
            return EMPTY_WATCHLIST_FILE;
        }

        boolean hasInvalidWatchlist = false;
        String[] fileLines = fileContent.split(System.lineSeparator());
        LOGGER.log(Level.FINE, "Processing: " + System.lineSeparator() + fileContent);
        for (String line : fileLines) {
            Watchlist decodedWatchlist = decode(line);
            boolean isValidWatchlist = (decodedWatchlist != null) && !(watchlistList.contains(decodedWatchlist));
            if (!isValidWatchlist) {
                hasInvalidWatchlist = true;
                continue;
            }

            watchlistList.add(decodedWatchlist);
        }

        if (hasInvalidWatchlist && watchlistList.size() == 0) {
            LOGGER.log(Level.WARNING, "All invalid watchlist entries at: " + watchlistFilePath);
            return NO_WATCHLIST_LOADED;
        } else if (hasInvalidWatchlist) {
            LOGGER.log(Level.WARNING, "Some invalid watchlist entries at: " + watchlistFilePath);
            return SOME_WATCHLIST_LOADED;
        }

        LOGGER.log(Level.INFO, "Loaded successfully: " + watchlistFilePath);
        return LOAD_SUCCESS;
    }

    // ========================== Encode and Decode ==========================

    /**
     * Encodes the watchlistList object into a readable string representation for saving in file.
     *
     * @param watchlistList the arraylist of watchlist object to be encoded
     * @return the readable string representation of the arraylist of watchlist object
     */
    private String encode(ArrayList<Watchlist> watchlistList) {
        StringBuilder sbWatchlist = new StringBuilder();
        for (Watchlist watchlist : watchlistList) {
            sbWatchlist.append(watchlist.getName());
            sbWatchlist.append(WATCHLIST_LINE_DELIMITER_FOR_ENCODE);
            sbWatchlist.append(watchlist.getAnimeList().toString());
            sbWatchlist.append(System.lineSeparator());
        }

        return sbWatchlist.toString();
    }

    /**
     * Decodes the readable string representation of the watchlist object.
     *
     * @param line the readable string representation of the watchlist object
     * @return the decoded watchlist object
     */
    private Watchlist decode(String line) {
        String[] lineSplit = line.split(WATCHLIST_LINE_DELIMITER_FOR_DECODE, 2);
        if (!isValidWatchlistString(lineSplit)) {
            return null;
        }

        String watchlistName = lineSplit[0].trim();
        if (watchlistName.isBlank()) {
            return null;
        }

        String animeListString = lineSplit[1].trim();
        String animeListStringContent = animeListString.substring(1, animeListString.length() - 1);

        ArrayList<Integer> animeList = new ArrayList<>();
        if (animeListStringContent.isBlank()) {
            return new Watchlist(watchlistName, animeList);
        }

        String[] animes = animeListStringContent.split(DELIMITER_FOR_ENCODED_ANIME_LIST);
        if (animes.length == 0) {
            return null;
        }

        for (String animeIndex : animes) {
            String trimmedIndex = animeIndex.trim();
            if (!isPositiveInteger(trimmedIndex)) {
                return null;
            }

            int parsedAnimeIndex = parseStringToInteger(trimmedIndex);
            boolean isValidAnimeIndex = (parsedAnimeIndex <= MAX_ANIME_INDEX)
                                        && !(animeList.contains(parsedAnimeIndex));
            if (!isValidAnimeIndex) {
                return null;
            }

            animeList.add(parsedAnimeIndex);
        }

        return new Watchlist(watchlistName, animeList);
    }

    // ========================== Validation ==========================

    /**
     * Validates the string representation of the watchlist object.
     *
     * @param lineSplit the string representation of the watchlist object
     * @return {@code true} if the string representation is valid; {@code false} otherwise
     */
    private boolean isValidWatchlistString(String[] lineSplit) {
        boolean isValidSplitLength = (lineSplit.length == 2);
        if (!isValidSplitLength) {
            return false;
        }

        String watchlistName = lineSplit[0].trim();
        boolean isValidWatchlistName = (watchlistName.length() <= MAX_WATCHLIST_NAME_LENGTH)
                                        && (watchlistName.matches(REGEX_ALPHANUMERIC_WITH_SPACE));
        if (!isValidWatchlistName) {
            return false;
        }

        String animeListString = lineSplit[1].trim();
        return (animeListString.startsWith(ENCODED_ANIME_LIST_FIRST_CHARACTER))
                && (animeListString.endsWith(ENCODED_ANIME_LIST_LAST_CHARACTER));
    }
}
