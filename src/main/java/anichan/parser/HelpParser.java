package anichan.parser;

import anichan.commands.HelpCommand;
import anichan.exception.AniException;

/**
 * Handles parsing for Help command.
 */
public class HelpParser extends CommandParser {
    private static final String COMMAND_EMPTY = "";
    private static final String COMMAND_ESTIMATE = "estimate";
    private static final String COMMAND_BROWSE = "browse";
    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_INFO = "info";
    private static final String COMMAND_WORKSPACE = "workspace";
    private static final String COMMAND_WATCHLIST = "watchlist";
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_REMOVE = "remove";
    private static final String COMMAND_VIEW = "view";
    private static final String COMMAND_BOOKMARK = "bookmark";
    private static final String HELP_COMMAND = "Help command";
    private String commandOption;
    
    /**
     * Parses the specified command description.
     * 
     * @param description the specified command description
     * @return initialised {@code HelpCommand} object
     * @throws AniException when an error occurred while parsing the command description
     */
    public HelpCommand parse(String description) throws AniException {
        parameterParser(description.trim());
        return new HelpCommand(commandOption);
    }

    /**
     * Parses the field provided in the command description.
     * 
     * @param description the specified command description
     * @throws AniException when an error occurred while parsing the command description
     */
    public void parameterParser(String description) throws AniException {
        String[] splitDescription = description.split(WHITESPACE);
        
        if (splitDescription.length > 1) {
            throw new AniException(HELP_COMMAND + TOO_MUCH_FIELDS);
        }
        
        switch (description) {
        case COMMAND_EMPTY:
            commandOption = COMMAND_EMPTY;
            break;
        case COMMAND_ESTIMATE:
            commandOption = COMMAND_ESTIMATE;
            break;
        case COMMAND_BROWSE:
            commandOption = COMMAND_BROWSE;
            break;
        case COMMAND_SEARCH:
            commandOption = COMMAND_SEARCH;
            break;
        case COMMAND_INFO:
            commandOption = COMMAND_INFO;
            break;
        case COMMAND_WORKSPACE:
            commandOption = COMMAND_WORKSPACE;
            break;
        case COMMAND_WATCHLIST:
            commandOption = COMMAND_WATCHLIST;
            break;
        case COMMAND_ADD:
            commandOption = COMMAND_ADD;
            break;
        case COMMAND_REMOVE:
            commandOption = COMMAND_REMOVE;
            break;
        case COMMAND_VIEW:
            commandOption = COMMAND_VIEW;
            break;
        case COMMAND_BOOKMARK:
            commandOption = COMMAND_BOOKMARK;
            break;
        default:
            throw new AniException(description + NOT_RECOGNISED);
        }
    }
}
