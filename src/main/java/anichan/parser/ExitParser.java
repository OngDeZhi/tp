package anichan.parser;

import anichan.commands.ExitCommand;
import anichan.exception.AniException;

/**
 * Handles parsing for Exit command.
 */
public class ExitParser extends CommandParser {

    /**
     * Parses the specified command description.
     *
     * @param description the specified command description
     * @return initialised {@code ExitCommand} object
     * @throws AniException when an error occurred while parsing the command description
     */
    public ExitCommand parse(String description) throws AniException {
        if (!description.isBlank()) {
            throw new AniException(DESCRIPTION_NOT_REQUIRED);
        }
        return new ExitCommand();
    }
}
