package anichan.parser;

import anichan.commands.BrowseCommand;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles parsing for browse command.
 */
public class BrowseParser extends CommandParser {
    //Parameters and their fields
    private static final String SORT_PARAM = "s";
    private static final String ORDER_PARAM = "o";
    private static final String PAGE_PARAM = "p";
    private static final String ASCENDING_FIELD = "asc";
    private static final String DESCENDING_FIELD = "dsc";
    private static final String NAME_FIELD = "name";
    private static final String RATING_FIELD = "rating";

    private static final String PARAMETER_ERROR_HEADER = "Parameter : -";
    private static final String BROWSE_SETTINGS_CHANGED_INFO = "Default values modified";
    private static final String NO_PARAMETER_SPECIFIED_ERROR = "Seems like you did not specify a parameter type";
    private static final String INVALID_INPUT_ERROR = "This input is not accepted, please try again!";
    private static final String NO_SPACE_BETWEEN_PARAM_ERROR = "Please leave a spacing between each parameter!";
    private static final String MULTIPLE_PAGE_PARAM_ERROR = "Please specify only one -p parameter!";
    private static final String MULTIPLE_ORDER_PARAM_ERROR = "Please specify only one -o parameter!";
    private static final String MULTIPLE_SORT_PARAM_ERROR = "Please specify only one -s parameter!";
    private static final String NON_POSITIVE_PAGE_NUMBER_ERROR = "Please enter a positive integer for the page!";

    private static final char CHAR_DASH = '-';
    private static final char CHAR_WHITESPACE = ' ';
    private static final int ORDER_ASCENDING = 0;
    private static final int ORDER_DESCENDING = 1;
    private static final int ORDER_NOT_SET = 2;
    private static final int SORT_NAME = 1;
    private static final int SORT_RATING = 2;
    private static final int SORT_ID = 0;
    private static final int FIRST_LOOP = 1;

    private static final Logger LOGGER = AniLogger.getAniLogger(BrowseParser.class.getName());
    private static final int FIRST_PAGE = 1;


    private boolean sortFlag = false;
    private boolean orderFlag = false;
    private boolean pageFlag = false;

    //Default values of Browse
    private int sortType = SORT_ID;
    private int order = ORDER_NOT_SET;
    private int page = FIRST_PAGE;

    /**
     * Parses the string parameters and creates an executable browseCommand according to the parameters.
     *
     * @param description is the parameters portion of the user input
     * @return an executable BrowseCommand object
     * @throws AniException if an error is encountered while parsing
     */
    public BrowseCommand parse(String description) throws AniException {
        checkForDashAbuse(description);
        String[] paramGiven = parameterSplitter(description);
        if (paramGiven.length > 1) {
            parameterParser(paramGiven);
            LOGGER.log(Level.INFO, BROWSE_SETTINGS_CHANGED_INFO);
        } else {
            checkForExtraParam(paramGiven[0]);
        }
        return new BrowseCommand(sortType, order, page);
    }

    /**
     * Checks for any additional extra input which are invalid.
     *
     * @param extraParamCheck the parameter to check
     * @throws AniException if additional input was found
     */
    private void checkForExtraParam(String extraParamCheck) throws AniException {
        if (!extraParamCheck.isBlank()) {
            throw new AniException(extraParamCheck.trim() + INVALID_OPTION);
        }
    }

    /**
     * Prevents input that abuses dash splitting, such as "- - " or "---" inputs.
     *
     * @param description the raw parameter string
     * @throws AniException if dash abuse is detected
     */
    private void checkForDashAbuse(String description) throws AniException {
        //Check for invalid --- inputs
        for (int i = 0; i < description.length(); i++) {
            if (description.charAt(i) == CHAR_DASH) {
                if (i + 1 >= description.length() || description.charAt(i + 1) == CHAR_WHITESPACE) {
                    throw new AniException(NO_PARAMETER_SPECIFIED_ERROR);
                }
                if (description.charAt(i + 1) == CHAR_DASH) {
                    throw new AniException(INVALID_INPUT_ERROR);
                }
            }
        }
    }

    /**
     * Loops through each parameter and sets the option specified by each parameter.
     *
     * @param paramGiven is a String Array containing the processed parameters
     * @throws AniException if invalid parameters are parsed in
     */
    private void parameterParser(String[] paramGiven) throws AniException {
        int paramLoops = 0;
        for (String param : paramGiven) {
            paramLoops++;
            //Skip first empty field which is a blank
            if (firstSplitHandler(paramLoops, param)) {
                continue;
            }
            String[] paramParts = param.split(WHITESPACE, FIELD_SPLIT_LIMIT);
            switch (paramParts[0].trim()) {
            case SORT_PARAM:
                parseSortParam(paramParts);
                break;
            case ORDER_PARAM:
                checkOrderParam(paramParts);
                break;
            case PAGE_PARAM:
                parsePageParam(paramParts);
                break;
            default:
                String invalidParameter = PARAMETER_ERROR_HEADER + param + NOT_RECOGNISED;
                throw new AniException(invalidParameter);
            }
            checkForParamStacking(paramGiven.length, paramLoops, paramParts[1]);
        }
    }

    /**
     * Checks if it is the first string after split which should be blank.
     * Possible vector of entering invalid input, example "browse invalidInput -s rating".
     *
     * @param paramLoops is the current loop.
     * @param param the param string to check.
     * @return true if first loop, false if not first loop.
     * @throws AniException if not blank to prevent input injection.
     */
    private boolean firstSplitHandler(int paramLoops, String param) throws AniException {
        if (paramLoops == FIRST_LOOP) {
            checkForExtraParam(param);
            return true;
        }
        return false;
    }

    /**
     * Checks and prevents parameter stacking e.g. "-o asc-s rating" or "-s name-p 2".
     *
     * @param totalParams is the total numbber of parameters the user parsed in
     * @param paramLoops is the current parameter being handled
     * @param theParam is the parameter being handled
     * @throws AniException if parameter stacking was found
     */
    private void checkForParamStacking(int totalParams, int paramLoops, String theParam) throws AniException {
        //Forgive the last param
        if (totalParams > 2 && !theParam.endsWith(WHITESPACE) && paramLoops != totalParams) {
            throw new AniException(NO_SPACE_BETWEEN_PARAM_ERROR);
        }
    }

    /**
     * Performs input validation of the page parameter and its field before setting it.
     *
     * @param paramParts the parameter and field String array
     * @throws AniException if previously page was set already or the page is a negative number.
     */
    private void parsePageParam(String[] paramParts) throws AniException {
        paramFieldCheck(paramParts);
        paramExtraFieldCheck(paramParts);
        page = parseStringToInteger(paramParts[1].trim());
        if (page <= 0) {
            throw new AniException(NON_POSITIVE_PAGE_NUMBER_ERROR);
        }
        if (pageFlag) {
            throw new AniException(MULTIPLE_PAGE_PARAM_ERROR);
        } else {
            pageFlag = true;
        }
    }

    /**
     * Performs input validation of the order parameter and its field before setting it.
     *
     * @param paramParts the field of the order parameter.
     * @throws AniException if the field is invalid
     */
    private void checkOrderParam(String[] paramParts) throws AniException {
        paramFieldCheck(paramParts);
        paramExtraFieldCheck(paramParts);
        switch (paramParts[1].trim()) {
        case ASCENDING_FIELD:
            order = ORDER_ASCENDING;
            break;
        case DESCENDING_FIELD:
            order = ORDER_DESCENDING;
            break;
        default:
            String paramFieldError = paramParts[1] + INVALID_OPTION;
            throw new AniException(paramFieldError);
        }
        if (orderFlag) {
            throw new AniException(MULTIPLE_ORDER_PARAM_ERROR);
        } else {
            orderFlag = true;
        }
    }

    /**
     * Performs input validation of the sortType parameter and its field before setting it.
     *
     * @param paramParts the field of the sortType parameter.
     * @throws AniException if the field is invalid
     */
    private void parseSortParam(String[] paramParts) throws AniException {
        paramFieldCheck(paramParts);
        paramExtraFieldCheck(paramParts);
        switch (paramParts[1].trim()) {
        case NAME_FIELD:
            sortType = SORT_NAME;
            break;
        case RATING_FIELD:
            sortType = SORT_RATING;
            break;
        default:
            String paramFieldError = paramParts[1] + INVALID_OPTION;
            throw new AniException(paramFieldError);
        }
        if (sortFlag) {
            throw new AniException(MULTIPLE_SORT_PARAM_ERROR);
        } else {
            sortFlag = true;
        }
    }
}
