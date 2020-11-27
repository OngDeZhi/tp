package anichan.commands;

import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.logger.AniLogger;
import anichan.storage.StorageManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command that allows the user to browse through all anime series.
 */
public class BrowseCommand extends Command {
    private int sortType;
    private int order;
    private int page;
    private int indexToPrint;
    private int animePerPage;

    //Constant values used by Browse
    private static final int ZERO_BASE_OFFSET = 1;
    private static final int ZERO_INDEX = 0;

    //Constant values used for sortBrowseList()
    private static final int ANIME_PER_PAGE = 20;
    private static final int ORDER_ASCENDING = 0;
    private static final int ORDER_DESCENDING = 1;
    private static final int ID_SORT = 0;
    private static final int NAME_SORT = 1;
    private static final int RATING_SORT = 2;
    private static final int RESET_SORT = 3;

    //Constant Strings used for buildBrowseOutput()
    private static final String ID_HEADER = " [Id: ";
    private static final String ID_CLOSER = "]";
    private static final String ASCII_ONLY_REGEX = "[^\\x00-\\x7F]";
    private static final String THREE_DOTS = "...";
    private static final String PERCENTAGE_STRING = "%";
    private static final String S_STRING = "s";
    private static final String EMPTY_STRING = "";
    private static final String DOT_SPACE = ". ";
    private static final String LAST_PAGE_INDICATOR = "You have reached the last page!";

    //Constant values used for buildBrowseOutput()
    private static final int MAX_NAME_LEN = 51;
    private static final int MAX_ID_LEN = 3;
    private static final int MAX_INDEX_LEN = 5;
    private static final int TRIM_TITLE_END = 48;
    private static final int TRIM_TITLE_START = 0;

    //Log Messages
    private static final String LAST_ANIME_WARNING = "Printing Last Anime Series from source";
    private static final String BROWSE_PAGE_INDICATOR = "Browsing Page: ";
    private static final String OUT_OF_BOUND_PAGE_WARNING = "Getting page: Tried to start at index: ";
    private static final String OUT_OF_BOUND_PAGE_ERROR = "Page size too large!";
    private static final String ASSERT_SORT_TYPE = "sortType should be < 3";
    private static final String ASSERT_ORDER_TYPE = "order should be < 2";
    private static final String ASSERT_POSITIVE_PAGE_NUM = "Trying to set non-positive page value!";
    private static final String ASSERT_NON_NEGATIVE_INDEX_TO_PRINT = "indexToPrint should not be negative";
    private static final String SORT_ID_DESCENDING = "Sorting by ID descending";
    private static final String SORT_ID_ASCENDING = "Sorting by ID ascending";
    private static final String SORT_NAME_ASCENDING = "Sorting by Name Ascending (A to Z)";
    private static final String SORT_NAME_DESCENDING = "Sorting by Name Descending (Z to A)";
    private static final String SORT_RATING_ASCENDING = "Sorting by Rating Ascending (low to high)";
    private static final String SORT_RATING_DESCENDING = "Sorting by Rating Descending (high to low)";
    private static final String INDEX_TO_PRINT_SET = "indexToPrint set to: ";
    private static final String PAGE_SET = "page set to: ";

    private static final Logger LOGGER = AniLogger.getAniLogger(BrowseCommand.class.getName());

    /**
     * Creates an instance of BrowseCommand, and will set its options.
     *
     * @param sortType this indicates the type of sort needed (if any)
     * @param order this indicates the order the browse displays in
     * @param page this indicates the page to print
     */
    public BrowseCommand(int sortType, int order, int page) {
        setIndexToPrint(ZERO_INDEX);
        setAnimePerPage(ANIME_PER_PAGE);
        setSortType(sortType);
        setOrder(order);
        setPage(page);
    }

    /**
     * Handles the main execution of browse command with calls to two core operations buildBrowseOutput() and
     * sortBrowseList().
     *
     * @param animeData used to retrieve anime information
     * @param storageManager used to save or read AniChan data
     * @param user used to modify user data
     * @return a printable string that contains the browse output
     * @throws AniException when an error occurred while executing the command
     */
    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        assert (sortType < 4) : ASSERT_SORT_TYPE;
        assert (order < 3) : ASSERT_ORDER_TYPE;
        assert (page > 0) : ASSERT_POSITIVE_PAGE_NUM;
        ArrayList<Anime> usableList = animeData.getAnimeDataList();
        sortBrowseList(usableList);
        String result = buildBrowseOutput(usableList);
        setSortType(RESET_SORT);
        sortBrowseList(usableList);
        return result;
    }

    /**
     * Called by execute to build a printable output in browse format.
     *
     * @param usableList used to retrieve the Anime object within the browse window
     * @return printable string that is formatted for browse output
     * @throws AniException if the starting index of the page supplied exceeds total number of anime
     */
    private String buildBrowseOutput(ArrayList<Anime> usableList) throws AniException {
        checkForPageBound(usableList);
        assert indexToPrint >= 0 : ASSERT_NON_NEGATIVE_INDEX_TO_PRINT;
        StringBuilder result = new StringBuilder();
        for (int i = indexToPrint; i < indexToPrint + animePerPage; i++) {
            Anime browseAnime = usableList.get(i);
            String animeName = browseAnime.getAnimeName();
            //Removes non-ascii and trim long titles.
            animeName = animeName.replaceAll(ASCII_ONLY_REGEX, EMPTY_STRING);
            if (animeName.length() >= MAX_NAME_LEN) {
                animeName = animeName.substring(TRIM_TITLE_START, TRIM_TITLE_END);
                animeName += THREE_DOTS;
            }

            //Pads the output if necessary
            String currAnimeID = Integer.toString(browseAnime.getAnimeID());
            String browseIndex = i + ZERO_BASE_OFFSET + DOT_SPACE;
            animeName = String.format(PERCENTAGE_STRING + (-MAX_NAME_LEN) + S_STRING, animeName.trim());
            currAnimeID = String.format(PERCENTAGE_STRING + (-MAX_ID_LEN) + S_STRING, currAnimeID);
            browseIndex = String.format(PERCENTAGE_STRING + (-MAX_INDEX_LEN) + S_STRING, browseIndex);

            result.append(browseIndex);
            result.append(animeName);
            result.append(ID_HEADER);
            result.append(currAnimeID);
            result.append(ID_CLOSER);
            result.append(System.lineSeparator());
            if (i + ZERO_BASE_OFFSET >= usableList.size()) {
                result.append(LAST_PAGE_INDICATOR).append(System.lineSeparator());
                LOGGER.log(Level.WARNING, LAST_ANIME_WARNING);
                break;
            }
        }
        result.append(BROWSE_PAGE_INDICATOR).append(page);
        return result.toString();
    }

    /**
     * Checks if the page supplied exceed the initial starting index.
     * 
     * @param usableList the list containing Anime objects to be sorted
     * @throws AniException if the page supplied too big to be used.
     */
    private void checkForPageBound(ArrayList<Anime> usableList) throws AniException {
        if (indexToPrint < 0 || indexToPrint >= usableList.size()) {
            LOGGER.log(Level.WARNING, OUT_OF_BOUND_PAGE_WARNING + indexToPrint);
            throw new AniException(OUT_OF_BOUND_PAGE_ERROR);
        }
    }

    /**
     * Called by execute to sort the list depending on the optional parameters supplied. May also be called to
     * reset the list back to its original state.
     *
     * @param usableList the list containing Anime objects to be sorted
     */
    private void sortBrowseList(ArrayList<Anime> usableList) {
        if (sortType == ID_SORT && order == ORDER_DESCENDING) {
            LOGGER.log(Level.INFO, SORT_ID_DESCENDING);
            usableList.sort(Comparator.comparing(Anime::getAnimeID).reversed());
        } else if (sortType == NAME_SORT && order == ORDER_DESCENDING) {
            LOGGER.log(Level.INFO, SORT_NAME_DESCENDING);
            usableList.sort(Comparator.comparing(Anime::getAnimeName).reversed());
        } else if (sortType == NAME_SORT) {
            LOGGER.log(Level.INFO, SORT_NAME_ASCENDING);
            usableList.sort(Comparator.comparing(Anime::getAnimeName));
        } else if (sortType == RATING_SORT && order == ORDER_ASCENDING) {
            LOGGER.log(Level.INFO, SORT_RATING_ASCENDING);
            usableList.sort(Comparator.comparing(Anime::getRating));
        } else if (sortType == RATING_SORT) {
            LOGGER.log(Level.INFO, SORT_RATING_DESCENDING);
            usableList.sort(Comparator.comparing(Anime::getRating).reversed());
        } else if (sortType == RESET_SORT) {
            usableList.sort(Comparator.comparing(Anime::getAnimeID));
        } else {
            LOGGER.log(Level.INFO, SORT_ID_ASCENDING);
        }
    }

    /**
     * Sets the page to the supplied parameter and calculate the indexToPrint.
     *
     * @param page the page that was requested
     */
    public void setPage(int page) {
        assert page > 0 : ASSERT_POSITIVE_PAGE_NUM;
        this.page = page;
        page = page - ZERO_BASE_OFFSET;
        indexToPrint = page * getAnimePerPage();
        LOGGER.log(Level.INFO, INDEX_TO_PRINT_SET + indexToPrint);
        LOGGER.log(Level.INFO, PAGE_SET + page);
    }

    /**
     * Gets the currently set page.
     *
     * @return the page number
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the sortType.
     *
     * @param sortType indicates the type of sort
     */
    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    /**
     * Gets the currently set sort type.
     *
     * @return the type of sort
     */
    public int getSortType() {
        return sortType;
    }

    /**
     * Sets the order to display.
     *
     * @param order indicates the order to display
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Gets the order to display.
     *
     * @return  the order to display
     */
    public int getOrder() {
        return order;
    }

    /**
     * Gets the amount of anime to print per page.
     *
     * @return the amount of anime to print per page
     */
    public int getAnimePerPage() {
        return animePerPage;
    }

    /**
     * Sets the amount of anime to print per page.
     *
     * @param animePerPage indicates how many anime to be printed per page
     */
    public void setAnimePerPage(int animePerPage) {
        this.animePerPage = animePerPage;
    }


    /**
     * Sets the starting index to begin printing.
     *
     * @param indexToPrint indicates which index to start at
     */
    public void setIndexToPrint(int indexToPrint) {
        this.indexToPrint = indexToPrint;
    }
}
