package anichan.parser;

import anichan.commands.WorkspaceCommand;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles parsing for workspace command.
 */
public class WorkspaceParser extends CommandParser {
    private static final Logger LOGGER = AniLogger.getAniLogger(WorkspaceParser.class.getName());
    private static final String ASSERTION_INVALID_MESSAGE = "Input should not be null.";
    private static final String EXCEPTION_INVALID_PARAMETERS = "Invalid parameters detected!";
    private static final String EXCEPTION_ILLEGAL_WORKSPACE_NAME = "Workspace name must be shorter than "
            + "31 characters and consist only alphanumeric characters and/or spaces.";

    private static final String COMMAND_NEW = "n";
    private static final String COMMAND_SWITCH = "s";
    private static final String COMMAND_LIST = "l";
    private static final String COMMAND_DELETE = "d";
    private static final String SPACE_CHARACTER = " ";
    private static final int MAXIMUM_WORKSPACE_NAME_LENGTH = 30;

    private String commandOption;
    private String commandDescription;

    /**
     * Parses the specified command description.
     *
     * @param description the specified command description
     * @return initialised WorkspaceCommand object
     * @throws AniException when an error occurred while parsing the command description
     */
    public WorkspaceCommand parse(String description) throws AniException {
        assert description != null : ASSERTION_INVALID_MESSAGE;

        String[] paramGiven = parameterSplitter(description);
        paramIsSetCheck(paramGiven);
        paramFieldCheck(paramGiven);

        parameterParser(paramGiven);

        LOGGER.log(Level.INFO, "Returning WorkspaceCommand object with option: "
                + commandOption + ", and description: " + commandDescription);

        return new WorkspaceCommand(commandOption, commandDescription);
    }

    /**
     * Parses the parameter provided in the command description.
     *
     * @param paramGiven a String Array containing the parameters and the value
     * @throws AniException when an error occurred while parsing the parameters
     */
    private void parameterParser(String[] paramGiven) throws AniException {
        if (paramGiven.length != 2) {
            LOGGER.log(Level.WARNING, EXCEPTION_INVALID_PARAMETERS);
            throw new AniException(EXCEPTION_INVALID_PARAMETERS);
        }

        try {
            String cleanedCommand = paramGiven[1].trim();

            String[] givenOption = cleanedCommand.split(SPACE_CHARACTER, 2);

            switch (givenOption[0]) {
            case COMMAND_NEW:
                commandOption = COMMAND_NEW;
                break;
            case COMMAND_SWITCH:
                commandOption = COMMAND_SWITCH;
                break;
            case COMMAND_LIST:
                commandOption = COMMAND_LIST;
                break;
            case COMMAND_DELETE:
                commandOption = COMMAND_DELETE;
                break;
            default:
                LOGGER.log(Level.WARNING, EXCEPTION_INVALID_PARAMETERS);
                throw new AniException(EXCEPTION_INVALID_PARAMETERS);
            }

            if (!commandOption.equals(COMMAND_LIST)) {
                commandDescription = givenOption[1].trim();
                checkName(commandDescription);
            }
        } catch (IndexOutOfBoundsException exception) {
            LOGGER.log(Level.WARNING, EXCEPTION_INVALID_PARAMETERS);
            throw new AniException(EXCEPTION_INVALID_PARAMETERS);
        }
    }

    /**
     * Checks if workspace name is valid.
     *
     * @param workspaceName name of workspace
     * @throws AniException when name is not of valid format
     */
    private void checkName(String workspaceName) throws AniException {
        if (workspaceName != null) {
            boolean isValid = workspaceName.matches(REGEX_ALPHANUMERIC_WITH_SPACE);

            if (!isValid || workspaceName.length() > MAXIMUM_WORKSPACE_NAME_LENGTH) {
                LOGGER.log(Level.WARNING, EXCEPTION_ILLEGAL_WORKSPACE_NAME);
                throw new AniException(EXCEPTION_ILLEGAL_WORKSPACE_NAME);
            }
        }
    }

}
