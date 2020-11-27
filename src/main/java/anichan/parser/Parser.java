package anichan.parser;

import anichan.commands.Command;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    private static final Logger LOGGER = AniLogger.getAniLogger(Parser.class.getName());
    private static final String COMMAND_WORKSPACE = "workspace";
    private static final String COMMAND_BROWSE = "browse";
    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_WATCHLIST = "watchlist";
    private static final String COMMAND_VIEW = "view";
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_REMOVE = "remove";
    private static final String COMMAND_BOOKMARK = "bookmark";
    private static final String COMMAND_ESTIMATE = "estimate";
    private static final String COMMAND_INFO = "info";
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_EXIT = "exit";
    private static final String UNKNOWN_COMMAND_ERROR = "Unknown command";
    private static final String EMPTY_INPUT_ERROR = "Input is empty";
    private static final String WHITESPACE = " ";
    private static final String INIT_STRING = "";

    /**
     * Parses the full command that the user provides.
     * 
     * @param fullCommand the user input to parse
     * @return initialise {@Code Command} object
     * @throws AniException when an error occurred while parsing the command
     */
    public Command getCommand(String fullCommand) throws AniException {
        LOGGER.log(Level.INFO, "Parse: " + fullCommand);
        
        fullCommand = fullCommand.trim();
        String[] fullCommandSplit = parseUserInput(fullCommand);
        String description = INIT_STRING;
        String command = fullCommandSplit[0];

        if (fullCommandSplit.length > 1) {
            description = fullCommandSplit[1];
        }

        switch (command) {
        case COMMAND_WORKSPACE:
            return new WorkspaceParser().parse(description);
        case COMMAND_BROWSE:
            return new BrowseParser().parse(description);
        case COMMAND_SEARCH:
            return new SearchParser().parse(description);
        case COMMAND_WATCHLIST:
            return new WatchlistParser().parse(description);
        case COMMAND_VIEW:
            return new ViewWatchlistParser().parse(description);
        case COMMAND_ADD:
            return new AddToWatchlistParser().parse(description);
        case COMMAND_REMOVE:
            return new RemoveCommandParser().parse(description);
        case COMMAND_BOOKMARK:
            return new BookmarkParser().parse(description);
        case COMMAND_ESTIMATE:
            return new EstimateParser().parse(description);
        case COMMAND_INFO:
            return new InfoParser().parse(description);
        case COMMAND_HELP:
            return new HelpParser().parse(description);
        case COMMAND_EXIT:
            return new ExitParser().parse(description);
        default:
            throw new AniException(UNKNOWN_COMMAND_ERROR);
        }
    }

    /**
     * Performs input validation on the input before splitting it.
     *
     * @param input the input to parse
     * @return the split input.
     * @throws AniException if the input is empty
     */
    public static String[] parseUserInput(String input) throws AniException {
        if (input == null || input.isEmpty()) {
            LOGGER.log(Level.WARNING, "Exception occurred");
            throw new AniException(EMPTY_INPUT_ERROR);
        }
        LOGGER.log(Level.INFO, "Parser processing succeeded");

        String[] inputSplit = input.split(WHITESPACE, 2);
        return inputSplit;
    }
}
