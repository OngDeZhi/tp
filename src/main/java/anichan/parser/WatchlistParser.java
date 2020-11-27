package anichan.parser;

import anichan.commands.WatchlistCommand;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

//@@author OngDeZhi
/**
 * Handles parsing for watchlist command.
 */
public class WatchlistParser extends CommandParser {
    private static final String CREATE_PARAM = "n";
    private static final String LIST_PARAM = "l";
    private static final String SELECT_PARAM = "s";
    private static final String DELETE_PARAM = "d";
    private static final String BLANK = "";

    private static final String WATCHLIST_COMMAND_TOO_MUCH_FIELDS_ERROR = "Watchlist command" + TOO_MUCH_FIELDS;
    private static final String WATCHLIST_COMMAND_TOO_MANY_PARAMETERS_ERROR = "Watchlist command"
                                                                              + TOO_MUCH_PARAMETERS;
    private static final String WATCHLIST_NAME_IS_EMPTY_ERROR = "Watchlist name cannot be empty!";
    private static final String WATCHLIST_NAME_TOO_LONG_ERROR = "Watchlist name should not exceed 30 characters!";
    private static final String WATCHLIST_NAME_IS_INVALID_ERROR = "Watchlist name can only consist of up to "
                                                                  + "30 alphanumeric characters and/or spaces!";
    private static final String WATCHLIST_INDEX_IS_EMPTY_ERROR = "Watchlist index cannot be empty!";
    private static final String WATCHLIST_INDEX_IS_ZERO_ERROR = "Watchlist index cannot be zero!";
    private static final String INVALID_PARAMETER_ERROR = "Watchlist command only accepts the parameters: "
                                                    + "-n, -l, -s, and -d.";
    private static final String NO_PARAMETER_TO_CHECK = "There should be a one parameter to check!";

    private static final int MAX_WATCHLIST_NAME_LENGTH = 30;
    private static final int DEFAULT_WATCHLIST_INDEX = -1;
    private static final int CREATION_REQUIRED_PARAMETER_COUNT = 2;
    private static final int LIST_REQUIRED_PARAMETER_COUNT = 1;
    private static final int MODIFICATION_REQUIRED_PARAMETER_COUNT = 2;

    private static final Logger LOGGER = AniLogger.getAniLogger(WatchlistParser.class.getName());

    private String parameter;
    private String watchlistName;
    private int watchlistIndex;

    /**
     * Parses the string parameters and creates an initialised {@code WatchlistCommand} according to the parameters.
     *
     * @param description the specified command description
     * @return initialised {@code WatchlistCommand} object
     * @throws AniException when an error occurred while parsing the command description
     */
    public WatchlistCommand parse(String description) throws AniException {
        assert description != null : DESCRIPTION_CANNOT_BE_NULL;
        String[] paramGiven = description.split(DASH, 2);
        paramIsSetCheck(paramGiven);
        if (!paramGiven[0].isBlank()) {
            throw new AniException(paramGiven[0] + NOT_RECOGNISED);
        }

        watchlistName = BLANK;
        watchlistIndex = DEFAULT_WATCHLIST_INDEX;
        parameterParser(paramGiven);
        if (watchlistIndex == 0) {
            throw new AniException(WATCHLIST_INDEX_IS_ZERO_ERROR);
        }

        LOGGER.log(Level.INFO, "Returning WatchlistCommand object with parameter: "
                                    + parameter + ", and information: " + watchlistName + " OR " + watchlistIndex);
        if (!watchlistName.equals(BLANK)) {
            return new WatchlistCommand(parameter, watchlistName);
        } else if (watchlistIndex != DEFAULT_WATCHLIST_INDEX) {
            return new WatchlistCommand(parameter, watchlistIndex);
        } else {
            return new WatchlistCommand(parameter);
        }
    }

    /**
     * Parses the parameter provided in the command description.
     *
     * @param paramGiven an String Array containing the parameters and the value
     * @throws AniException when an error while parsing the parameters
     */
    private void parameterParser(String[] paramGiven) throws AniException {
        if (paramGiven[1].matches(REGEX_PARAMETER)) {
            throw new AniException(WATCHLIST_COMMAND_TOO_MANY_PARAMETERS_ERROR);
        } else if (paramGiven[1].startsWith(WHITESPACE)) {
            throw new AniException(INVALID_PARAMETER_ERROR);
        }

        String[] parsedParts = paramGiven[1].trim().split(WHITESPACE, 2);
        parameter = parsedParts[0].trim();
        switch (parameter) {
        case CREATE_PARAM:
            checkCreationParameters(parsedParts);
            watchlistName = parsedParts[1].trim();
            return;
        case LIST_PARAM:
            checkListParameters(parsedParts);
            return;
        case SELECT_PARAM:
            // Fallthrough because select parameter will call checkModificationParameters method too.
        case DELETE_PARAM:
            checkModificationParameters(parsedParts);
            watchlistIndex = parseStringToInteger(parsedParts[1].trim());
            return;
        default:
            throw new AniException(INVALID_PARAMETER_ERROR);
        }
    }

    /**
     * Validates that watchlist creation parameters are valid.
     * <ul>
     *     <li>Have the exact required parameter count.</li>
     *     <li>Watchlist name is not empty.</li>
     *     <li>Watchlist name does not contain special characters.</li>
     * </ul>
     *
     * @param parsedParts the parsed parameters and the value
     * @throws AniException when the watchlist creation parameters are invalid
     */
    private void checkCreationParameters(String[] parsedParts) throws AniException {
        assert parsedParts.length != 0 : NO_PARAMETER_TO_CHECK;
        if (parsedParts.length != CREATION_REQUIRED_PARAMETER_COUNT) {
            throw new AniException(WATCHLIST_NAME_IS_EMPTY_ERROR);
        }

        String watchlistName = parsedParts[1].trim();
        if (watchlistName.length() > MAX_WATCHLIST_NAME_LENGTH) {
            throw new AniException(WATCHLIST_NAME_TOO_LONG_ERROR);
        }

        if (!watchlistName.matches(REGEX_ALPHANUMERIC_WITH_SPACE)) {
            throw new AniException(WATCHLIST_NAME_IS_INVALID_ERROR);
        }
    }

    /**
     * Validates that the watchlist list parameters are valid.
     * <ul>
     *     <li>Have the exact required parameter count.</li>
     * </ul>
     *
     * @param parsedParts parsed parameters and the value
     * @throws AniException when the watchlist list parameters are invalid
     */
    private void checkListParameters(String[] parsedParts) throws AniException {
        assert parsedParts.length != 0 : NO_PARAMETER_TO_CHECK;
        if (parsedParts.length > LIST_REQUIRED_PARAMETER_COUNT) {
            throw new AniException(WATCHLIST_COMMAND_TOO_MUCH_FIELDS_ERROR);
        }
    }

    /**
     * Validates that the watchlist modification (select and delete) parameters are valid.
     * <ul>
     *     <li>Have the exact required parameter count.</li>
     *     <li>Provided only one parameter value.</li>
     *     <li>Provided a parameter value that can be parsed to a positive integer.</li>
     * </ul>
     *
     * @param parsedParts the parsed parameters and the value
     * @throws AniException when the watchlist modification parameters are invalid
     */
    private void checkModificationParameters(String[] parsedParts) throws AniException {
        assert parsedParts.length != 0 : NO_PARAMETER_TO_CHECK;
        if (parsedParts.length != MODIFICATION_REQUIRED_PARAMETER_COUNT) {
            throw new AniException(WATCHLIST_INDEX_IS_EMPTY_ERROR);
        }

        String watchlistIndex = parsedParts[1].trim();
        if (watchlistIndex.contains(WHITESPACE)) {
            throw new AniException(WATCHLIST_COMMAND_TOO_MUCH_FIELDS_ERROR);
        }

        if (isNegativeInteger(watchlistIndex)) {
            throw new AniException(NOT_POSITIVE_INTEGER);
        }

        if (!isInteger(watchlistIndex)) {
            throw new AniException(NOT_INTEGER);
        }
    }
}
