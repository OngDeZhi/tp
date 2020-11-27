package anichan.parser;

import anichan.commands.SearchCommand;
import anichan.exception.AniException;

/**
 * Handles parsing for search command.
 */
public class SearchParser extends CommandParser {
    private static final String NO_PARAM_PROVIDED = "Please provide a parameter type. Search will accept -n or -g.";
    private static final String INIT_STRING = "";
    private static final int INVALID_SEARCH_TYPE = -1;
    private static final int SEARCH_BY_NAME = 0;
    private static final int SEARCH_BY_GENRE = 1;

    private String searchTerm = INIT_STRING;
    private String searchGenre = INIT_STRING;
    private int searchType = INVALID_SEARCH_TYPE;

    /**
     * Parses the string parameters and creates an executable searchCommand according to the parameters.
     *
     * @param description is the parameters portion of the user input
     * @return an executable BrowseCommand object
     * @throws AniException if an error is encountered while parsing
     */
    public SearchCommand parse(String description) throws AniException {
        String[] paramGiven = description.split(DASH, FIELD_SPLIT_LIMIT);
        paramIsSetCheck(paramGiven);
        parameterParser(paramGiven);
        return new SearchCommand(searchTerm, searchGenre, searchType);
    }

    /**
     * Loops through each parameter and sets the option specified by each parameter.
     *
     * @param paramGiven is a String Array containing the processed parameters
     * @throws AniException if invalid parameters are parsed in
     */
    public void parameterParser(String[] paramGiven) throws AniException {
        paramInputValidation(paramGiven);
        String[] paramParts = paramGiven[1].split(WHITESPACE, FIELD_SPLIT_LIMIT);

        switch (paramParts[0].trim()) {
        case NAME_PARAM:
            paramFieldCheck(paramParts);
            searchTerm = paramParts[1].trim();
            searchType = SEARCH_BY_NAME;
            break;
        case GENRE_PARAM:
            paramFieldCheck(paramParts);
            searchGenre = paramParts[1].trim();
            searchType = SEARCH_BY_GENRE;
            break;
        default:
            String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven[1] + NOT_RECOGNISED;
            throw new AniException(invalidParameter);
        }
    }

    /**
     * Checks for input that are dash only, or input that prepends invalid command before the dash.
     *
     * @param paramGiven the parmeter to check
     * @throws AniException if any invalid input was detected
     */
    private void paramInputValidation(String[] paramGiven) throws AniException {
        if (paramGiven[1].isBlank()) {
            throw new AniException(NO_PARAM_PROVIDED);
        }
        if (!paramGiven[0].isBlank()) {
            throw new AniException(paramGiven[0].trim() + INVALID_OPTION);
        }
    }
}
