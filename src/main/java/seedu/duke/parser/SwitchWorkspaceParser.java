package seedu.duke.parser;

import seedu.duke.command.SwitchWorkspaceCommand;
import seedu.duke.exception.AniException;

public class SwitchWorkspaceParser extends CommandParser {
    private final SwitchWorkspaceCommand switchWorkspaceCommand;

    public SwitchWorkspaceParser() {
        switchWorkspaceCommand = new SwitchWorkspaceCommand();
    }

    public SwitchWorkspaceCommand parse(String description) throws AniException {
        String[] paramGiven = parameterSplitter(description);
        paramIsSetCheck(paramGiven);
        parameterParser(paramGiven);
        return switchWorkspaceCommand;
    }

    public void parameterParser(String[] paramGiven) throws AniException {
        for (String param : paramGiven) {
            String[] paramParts = param.split(" ", 2);
            if (paramParts.length == 0) {
                break;
            }
            switch (paramParts[0].trim()) {
            case "": //skip the first empty param
                break;
            case "n": //Name of Workspace
                paramFieldCheck(paramParts);
                switchWorkspaceCommand.setSwitchToThisWorkspace(paramParts[1]);
                break;
            default:
                String invalidParameter = PARAMETER_ERROR_HEADER + param + NOT_RECOGNISED;
                throw new AniException(invalidParameter);
            }
        }
    }
}