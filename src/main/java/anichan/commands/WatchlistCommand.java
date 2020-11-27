package anichan.commands;

import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.Workspace;
import anichan.human.User;
import anichan.logger.AniLogger;
import anichan.storage.StorageManager;
import anichan.watchlist.Watchlist;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author OngDeZhi
/**
 * Represents the command to manage the watchlist(s) in AniChan.
 */
public class WatchlistCommand extends Command {
    private static final String CREATE_PARAM = "n";
    private static final String LIST_PARAM = "l";
    private static final String SELECT_PARAM = "s";
    private static final String DELETE_PARAM = "d";

    private static final String WORKSPACE_IS_NULL = "Active workspace should not be null.";
    private static final String WATCHLIST_LIST_IS_NULL = "Watchlist list should not be null.";
    private static final String WATCHLIST_LIST_IS_EMPTY = "Watchlist list should not be empty.";

    private static final String INVALID_PARAMETER_ERROR = "Watchlist command only accepts the parameters: "
                                                          + "-n, -l, -s, and -d.";
    private static final String WATCHLIST_NAME_IS_NOT_UNIQUE_ERROR = "This watchlist name is not unique!";
    private static final String INVALID_WATCHLIST_INDEX_ERROR = "This is not a valid watchlist index.";
    private static final String CANNOT_SELECT_ACTIVE_WATCHLIST_ERROR = "You cannot select the active watchlist..";
    private static final String CANNOT_DELETE_LAST_WATCHLIST_ERROR = "You cannot delete the last watchlist!";

    private static final Logger LOGGER = AniLogger.getAniLogger(WatchlistCommand.class.getName());

    private final String parameter;
    private String watchlistName;
    private int watchlistIndex;

    /**
     * Creates a new instance of WatchlistCommand with the specified parameter and watchlist name.
     * This is meant for creating watchlist.
     *
     * @param parameter specified watchlist command type
     * @param watchlistName specified watchlist name
     */
    public WatchlistCommand(String parameter, String watchlistName) {
        this.parameter = parameter;
        this.watchlistName = watchlistName;
        LOGGER.log(Level.INFO, "WatchlistCommand object for creating watchlist is created.");
    }

    /**
     * Creates a new instance of WatchlistCommand with the specified parameter.
     * This is meant for listing all watchlist.
     *
     * @param parameter specified watchlist command type
     */
    public WatchlistCommand(String parameter) {
        this.parameter = parameter;
        LOGGER.log(Level.INFO, "WatchlistCommand object for listing all watchlist is created.");
    }

    /**
     * Creates a new instance of WatchlistCommand with the specified parameter and watchlist index
     * This is meant for selecting and deleting watchlist.
     *
     * @param parameter specified watchlist command type
     * @param watchlistIndex specified watchlist index
     */
    public WatchlistCommand(String parameter, int watchlistIndex) {
        this.parameter = parameter;
        this.watchlistIndex = watchlistIndex - 1; // 1-based to 0-based numbering
        LOGGER.log(Level.INFO, "WatchlistCommand object for selecting or deleting watchlist is created.");
    }

    /**
     * Depending on the parameter supplied, it can perform one of the following operations.
     * <ul>
     *     <li>Create a watchlist.</li>
     *     <li>List all watchlist.</li>
     *     <li>Select a watchlist to be the new active watchlist.</li>
     *     <li>Delete a watchlist.</li>
     * </ul>
     *
     * @param animeData used to retrieve anime information
     * @param storageManager used to save or read AniChan data
     * @param user used to modify user data
     * @return result of the command executed
     * @throws AniException when an error occurred while executing the command
     */
    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        Workspace activeWorkspace = user.getActiveWorkspace();
        assert activeWorkspace != null : WORKSPACE_IS_NULL;
        assert activeWorkspace.getWatchlistList() != null : WATCHLIST_LIST_IS_NULL;
        assert activeWorkspace.getWatchlistList().size() != 0 : WATCHLIST_LIST_IS_EMPTY;

        switch (parameter) {
        case CREATE_PARAM:
            LOGGER.log(Level.INFO, "Attempting to create watchlist now..");
            return createWatchlist(storageManager, activeWorkspace);
        case LIST_PARAM:
            LOGGER.log(Level.INFO, "Attempting to list all watchlist now..");
            return listAllWatchlist(activeWorkspace);
        case SELECT_PARAM:
            LOGGER.log(Level.INFO, "Attempting to select watchlist now..");
            return selectWatchlist(activeWorkspace);
        case DELETE_PARAM:
            LOGGER.log(Level.INFO, "Attempting to delete watchlist now..");
            return deleteWatchlist(storageManager, activeWorkspace);
        default:
            throw new AniException(INVALID_PARAMETER_ERROR);
        }
    }

    /**
     * Creates a new watchlist in the current workspace.
     *
     * @param storageManager used to save watchlist data
     * @param activeWorkspace used to update watchlist list and save watchlist data to correct folder
     * @return result of the command executed
     * @throws AniException when an error occurred while creating the watchlist
     */
    private String createWatchlist(StorageManager storageManager, Workspace activeWorkspace) throws AniException {
        Watchlist createdWatchlist = new Watchlist(watchlistName);
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();

        if (watchlistList.contains(createdWatchlist)) {
            throw new AniException(WATCHLIST_NAME_IS_NOT_UNIQUE_ERROR);
        }

        watchlistList.add(createdWatchlist);
        storageManager.saveWatchlistList(activeWorkspace.getName(), watchlistList);
        LOGGER.log(Level.INFO, "Watchlist \"" + watchlistName + "\" created successfully.");
        return "Watchlist \"" + watchlistName + "\" has been created successfully!";
    }

    /**
     * Lists all watchlist created in the current workspace in a readable format.
     *
     * @param activeWorkspace used to retrieve all watchlist in the current workspace
     * @return names of all watchlist in the current workspace
     */
    private String listAllWatchlist(Workspace activeWorkspace) {
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();

        StringBuilder sbWatchlistList = new StringBuilder();
        sbWatchlistList.append("Currently, you have ");
        sbWatchlistList.append(watchlistList.size()).append(" watchlist(s):");
        for (int i = 0; i < watchlistList.size(); i++) {
            Watchlist watchlist = watchlistList.get(i);
            sbWatchlistList.append(System.lineSeparator());
            sbWatchlistList.append("\t").append(i + 1).append(". ");
            sbWatchlistList.append(watchlist.getName());
        }

        LOGGER.log(Level.INFO, "Listing watchlist of size: " + watchlistList.size());
        return sbWatchlistList.toString();
    }

    /**
     * Selects a watchlist in the current workspace to be the new active watchlist.
     *
     * @param activeWorkspace used to update the active watchlist in the active workspace
     * @return result of the command executed
     * @throws AniException when an error occurred while selecting a new active watchlist
     */
    private String selectWatchlist(Workspace activeWorkspace) throws AniException {
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();
        validateWatchlistIndex(watchlistList, watchlistIndex);

        Watchlist selectedWatchlist = watchlistList.get(watchlistIndex);
        Watchlist activeWatchlist = activeWorkspace.getActiveWatchlist();
        if (selectedWatchlist.equals(activeWatchlist)) {
            LOGGER.log(Level.INFO, "Select failed because the active watchlist is selected.");
            throw new AniException(CANNOT_SELECT_ACTIVE_WATCHLIST_ERROR);
        }

        activeWorkspace.setActiveWatchlist(selectedWatchlist);
        LOGGER.log(Level.INFO, "New active watchlist: " + activeWorkspace.getActiveWatchlistName());
        return "\"" + selectedWatchlist.getName() + "\" is now your active watchlist!";
    }

    /**
     * Deletes a watchlist in the current workspace.
     *
     * @param storageManager used to save watchlist data
     * @param activeWorkspace used to update watchlist list and save watchlist data to correct folder
     * @return result of the command executed
     * @throws AniException when an error occurred while deleting the watchlist
     */
    private String deleteWatchlist(StorageManager storageManager, Workspace activeWorkspace) throws AniException {
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();
        validateWatchlistIndex(watchlistList, watchlistIndex);

        Watchlist deletedWatchlist = watchlistList.get(watchlistIndex);
        Watchlist activeWatchlist = activeWorkspace.getActiveWatchlist();
        watchlistList.remove(watchlistIndex);

        String commandOutput = "Watchlist \"" + deletedWatchlist.getName() + "\" has been deleted successfully!";
        if (deletedWatchlist.equals(activeWatchlist)) {
            activeWorkspace.setActiveWatchlist(watchlistList.get(0));
            String activeWatchlistName = activeWorkspace.getActiveWatchlistName();
            commandOutput += System.lineSeparator();
            commandOutput += "Changed active watchlist to: \"" + activeWatchlistName + "\".";
        }

        storageManager.saveWatchlistList(activeWorkspace.getName(), watchlistList);
        LOGGER.log(Level.INFO, "Watchlist: \"" + deletedWatchlist.getName() + "\" deleted successfully.");
        return commandOutput;
    }

    /**
     * Validates that the watchlist index supplied for the select and delete watchlist command is valid.
     * It checks.
     * <ul>
     *     <li>If watchlist index specified is out of range.</li>
     *     <li>If it is an attempts to delete the last watchlist in the list.</li>
     * </ul>
     *
     * @param watchlistList a list containing all of the watchlist for the active workspace
     * @param index the watchlist index to check
     * @throws AniException when the watchlist index is invalid
     */
    private void validateWatchlistIndex(ArrayList<Watchlist> watchlistList, int index) throws AniException {
        if (index >= watchlistList.size()) {
            throw new AniException(INVALID_WATCHLIST_INDEX_ERROR);
        }

        if (watchlistList.size() == 1 && parameter.equals(DELETE_PARAM)) {
            throw new AniException(CANNOT_DELETE_LAST_WATCHLIST_ERROR);
        }
    }
}
