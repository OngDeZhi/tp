package anichan.storage;

import anichan.bookmark.Bookmark;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.human.Workspace;
import anichan.logger.AniLogger;
import anichan.watchlist.Watchlist;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the class to manage all of AniChan's data.
 */
public class StorageManager {
    private static final String REGEX_ALPHANUMERIC_WITH_SPACE = "^[a-zA-Z0-9\\s]*$";
    private static final int MAXIMUM_WORKSPACE_NAME_LENGTH = 30;
    private static final Logger LOGGER = AniLogger.getAniLogger(StorageManager.class.getName());
    public static final String EXCEPTION_DELETE_FAILED = "Failed to delete workspace folder on file system,"
            + " if it is still in the data folder, you may try to manually delete it.";

    private final String storageDirectory;
    private final UserStorage userStorage;
    private final WatchlistStorage watchlistStorage;
    private final BookmarkStorage bookmarkStorage;
    private final ScriptStorage scriptStorage;

    //@@author OngDeZhi
    /**
     * Creates a new instance of StorageManager with the specified storage directory.
     *
     * @param storageDirectory the specified path to storage directory in hard disk
     */
    public StorageManager(String storageDirectory) {
        this.storageDirectory = storageDirectory;
        userStorage = new UserStorage(this.storageDirectory);
        watchlistStorage = new WatchlistStorage(this.storageDirectory);
        bookmarkStorage = new BookmarkStorage(this.storageDirectory);
        scriptStorage = new ScriptStorage(this.storageDirectory);
    }

    /**
     * Retrieves and validates the list of workspace found in the storage directory.
     *
     * @return the list of workspace found in the storage directory
     */
    public String[] retrieveWorkspaceList() {
        File file = new File(storageDirectory);
        String[] workspaceList = file.list((current, name) -> {
            boolean workspaceValid = name.matches(REGEX_ALPHANUMERIC_WITH_SPACE);
            if (name.length() <= MAXIMUM_WORKSPACE_NAME_LENGTH && workspaceValid) {
                return new File(current, name).isDirectory();
            }

            return false;
        });
        if (workspaceList == null) {
            LOGGER.log(Level.INFO, "Found 0 workspace.");
            return new String[0];
        }

        LOGGER.log(Level.INFO, "Found " + workspaceList.length + " workspace(s).");
        return workspaceList;
    }

    // ========================== Workspace Saving ==========================

    /**
     * Saves the workspace.
     *
     * @param workspace the name of workspace
     * @throws AniException when an error occurred while trying to save the workspace
     */
    public void saveWorkspace(Workspace workspace) throws AniException {
        new File(storageDirectory + workspace.getName()).mkdirs();
        watchlistStorage.save(workspace.getName(), workspace.getWatchlistList());
    }

    // ========================== Workspace Deletion ==========================

    //@@author
    /**
     * Deletes directory containing specified workspace.
     *
     * @param name name of workspace
     * @throws AniException when an error occurred while trying to delete directory or if folder does not exist
     */
    public void deleteWorkspace(String name) throws AniException {
        assert (name != null) : "Workspace name is null.";
        String deletePathString = storageDirectory + name;

        try {
            File deleteFolder = new File(deletePathString);
            Path deletePath = Paths.get(deletePathString);

            LOGGER.log(Level.INFO, "Deleting workspace " + name);

            if (deleteFolder.isDirectory()) {
                Files.walk(deletePath)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }

            // Verifies that Workspace is deleted
            if (deleteFolder.isDirectory()) {
                LOGGER.log(Level.WARNING, "Exception: " + EXCEPTION_DELETE_FAILED);
                throw new AniException(EXCEPTION_DELETE_FAILED);
            }
        } catch (IOException exception) {
            LOGGER.log(Level.WARNING, "Exception: " + EXCEPTION_DELETE_FAILED);
            throw new AniException(EXCEPTION_DELETE_FAILED);
        }
    }

    // ========================== User Saving and Loading ==========================

    //@@author OngDeZhi
    /**
     * Invokes the save method in UserStorage to save the user data.
     *
     * @param user the user object to save
     * @throws AniException when an error occurred while saving the user data
     */
    public void saveUser(User user) throws AniException {
        userStorage.save(user);
    }

    /**
     * Invokes the load method in UserStorage to load the user data.
     *
     * @return the user object loaded
     * @throws AniException when an error occurred while loading the user data
     */
    public User loadUser() throws AniException {
        return userStorage.load();
    }

    // ========================== Watchlist Saving and Loading ==========================

    /**
     * Invokes the save method in WatchlistStorage to save the watchlist data.
     *
     * @param workspaceName the name of the workspace to save the list under
     * @param watchlistList the watchlist list to save
     * @throws AniException when an error occurred while saving the watchlist list data
     */
    public void saveWatchlistList(String workspaceName, ArrayList<Watchlist> watchlistList) throws AniException {
        watchlistStorage.save(workspaceName, watchlistList);
    }

    /**
     * Invokes the load method in WatchlistStorage to load the watchlist data.
     *
     * @param workspaceName the name of the workspace to load the list from
     * @param watchlistList the watchlist list to load the data into
     * @return the load result message
     * @throws AniException when an error occurred while loading the watchlist list data
     */
    public String loadWatchlistList(String workspaceName, ArrayList<Watchlist> watchlistList) throws AniException {
        return watchlistStorage.load(workspaceName, watchlistList);
    }

    // ========================== Bookmark Saving and Loading ==========================

    //@@author OngXinBin
    /**
     * Invokes the save method in bookmarkStorage to save the bookmark data.
     *
     * @param workspaceName the name of the workspace to load the list from
     * @param bookmark      the bookmark list to save
     * @throws AniException when an error occurred while saving the watchlist list data
     */
    public void saveBookmark(String workspaceName, Bookmark bookmark) throws AniException {
        bookmarkStorage.save(workspaceName, bookmark);
    }

    /**
     * Invokes the load method in bookmarkStorage to load the bookmark data.
     *
     * @param workspaceName the name of the workspace to load the list from
     * @param bookmark      the bookmark list to save
     * @return the load result message
     * @throws AniException when an error occurred while saving the watchlist list data
     */
    public String loadBookmark(String workspaceName, Bookmark bookmark) throws AniException {
        return bookmarkStorage.load(workspaceName, bookmark);
    }

    // ========================== Script Loading ==========================

    //@@author OngDeZhi
    /**
     * Loads the script file.
     *
     * @param workspaceName the name of the workspace where the script can be found
     * @param fileName      the file name of the script file
     * @return the content of the script file
     * @throws AniException when an error occurred while loading the script data
     */
    public String loadScript(String workspaceName, String fileName) throws AniException {
        return scriptStorage.readScript(workspaceName, fileName);
    }
}
