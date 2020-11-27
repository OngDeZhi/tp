package anichan.commands;

import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.human.Workspace;
import anichan.logger.AniLogger;
import anichan.storage.StorageManager;
import anichan.watchlist.Watchlist;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command to manage the workspace(s) in AniChan.
 */
public class WorkspaceCommand extends Command {
    // Constants for command option, logger, and errors
    private static final String CREATE_OPTION = "n";
    private static final String SWITCH_OPTION = "s";
    private static final String LIST_OPTION = "l";
    private static final String DELETE_OPTION = "d";
    private static final Logger LOGGER = AniLogger.getAniLogger(WorkspaceCommand.class.getName());
    private static final String EXPECTED_PARAMETERS_MESSAGE = "Workspace command only accepts the "
            + "parameters: -n, -s, -l, and -d.";
    private static final String EXCEPTION_WORKSPACE_IN_USE = "Please switch workspace before trying to delete it.";
    private static final String ASSERTION_COMMAND_OPTION_NULL = "Option should not be null.";
    public static final String ASSERTION_WORKSPACE_NAME_NULL = "Workspace name should not be null.";

    private final String commandOption;
    private final String workspaceName;

    /**
     * Creates a new instance of WorkspaceCommand with the specified option and workspace name.
     *
     * @param commandOption command option parameter
     * @param workspaceName workspace name for command to operate on
     */
    public WorkspaceCommand(String commandOption, String workspaceName) {
        this.commandOption = commandOption;
        this.workspaceName = workspaceName;
    }

    /**
     * Depending on the option supplied, it can perform one of the following operations.
     * <ul>
     *     <li>Creates a new workspace.</li>
     *     <li>Switches to workspace.</li>
     *     <li>Lists all workspace.</li>
     *     <li>Delete a workspace.</li>
     * </ul>
     *
     * @param animeData      used to retrieve anime information
     * @param storageManager used to save or read AniChan data
     * @param user           used to modify user data
     * @return result after executing the command
     * @throws AniException when an error occurred while executing the command
     */
    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        assert commandOption != null : ASSERTION_COMMAND_OPTION_NULL;

        switch (commandOption) {
        case CREATE_OPTION:
            LOGGER.log(Level.INFO, "Attempting to create workspace.");
            return createWorkspace(storageManager, user);
        case SWITCH_OPTION:
            LOGGER.log(Level.INFO, "Attempting to switch workspace.");
            return switchWorkspace(user);
        case LIST_OPTION:
            LOGGER.log(Level.INFO, "Attempting to list workspaces.");
            return listWorkspace(user);
        case DELETE_OPTION:
            LOGGER.log(Level.INFO, "Attempting to delete workspace.");
            return deleteWorkspace(storageManager, user);
        default:
            LOGGER.log(Level.WARNING, "Invalid workspace command provided.");
            throw new AniException(EXPECTED_PARAMETERS_MESSAGE);
        }
    }

    /**
     * Creates a workspace.
     *
     * @param user           used to modify user data
     * @param storageManager used to save or read AniChan data
     * @return result after executing the command
     * @throws AniException when an error occurred while executing the command
     */
    private String createWorkspace(StorageManager storageManager, User user) throws AniException {
        assert workspaceName != null : ASSERTION_WORKSPACE_NAME_NULL;

        Workspace newWorkspace = user.addWorkspace(workspaceName);

        // Adds Watchlist ArrayList to Workspace
        Watchlist newWatchlist = new Watchlist("Default");
        ArrayList<Watchlist> watchlistList = new ArrayList<>();
        watchlistList.add(newWatchlist);
        newWorkspace.setWatchlistList(watchlistList);

        storageManager.saveWorkspace(newWorkspace);

        LOGGER.log(Level.INFO, "Successfully added new workspace: " + newWorkspace);
        return "Successfully added new workspace: " + newWorkspace;
    }

    /**
     * Switches to specified workspace.
     *
     * @param user used to modify user data
     * @return result after executing the command
     * @throws AniException when an error occurred while executing the command
     */
    private String switchWorkspace(User user) throws AniException {
        assert workspaceName != null : ASSERTION_WORKSPACE_NAME_NULL;

        String trimmedName = workspaceName;
        user.switchActiveWorkspace(trimmedName);

        LOGGER.log(Level.INFO, "Successfully switched to workspace: " + trimmedName);
        return "Workspace switched to " + trimmedName;
    }

    /**
     * Deletes specified workspace.
     *
     * @param user           used to modify user data
     * @param storageManager used to save or read AniChan data
     * @return result after executing the command
     * @throws AniException when an error occurred while executing the command
     */
    private String deleteWorkspace(StorageManager storageManager, User user) throws AniException {
        assert workspaceName != null : ASSERTION_WORKSPACE_NAME_NULL;

        if (user.getActiveWorkspace().toString().equals(workspaceName)) {
            throw new AniException(EXCEPTION_WORKSPACE_IN_USE);
        }

        user.deleteWorkspace(workspaceName);
        storageManager.deleteWorkspace(workspaceName);

        LOGGER.log(Level.INFO, "Successfully deleted workspace: " + workspaceName);
        return "Successfully deleted workspace: " + workspaceName;
    }

    /**
     * Lists all existing workspace.
     *
     * @param user used to modify user data
     * @return result after executing the command
     */
    private String listWorkspace(User user) {
        // Builds string containing list of Workspaces for Ui print
        StringBuilder workspacesString = new StringBuilder();
        ArrayList<Workspace> userWorkspaces = user.getWorkspaceList();

        workspacesString.append("Currently, you have ");
        workspacesString.append(userWorkspaces.size());
        workspacesString.append(" workspace(s):");

        for (int i = 0; i < userWorkspaces.size(); i++) {
            int workspaceIndex = i + 1;
            workspacesString.append(System.lineSeparator()).append(workspaceIndex);
            workspacesString.append(". ");
            workspacesString.append(userWorkspaces.get(i).toString());
        }

        return workspacesString.toString();
    }

}
