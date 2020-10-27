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
 * Manages the storage of AniChan's data.
 */
public class StorageManager {
    private static final Logger LOGGER = AniLogger.getAniLogger(StorageManager.class.getName());

    private final String storageDirectory;
    private final UserStorage userStorage;
    private final WatchlistStorage watchlistStorage;
    private final BookmarkStorage bookmarkStorage;
    private final ScriptStorage scriptStorage;

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
     * Retrieves the list of workspace found in the storage directory.
     *
     * @return the list of workspace found in the storage directory
     */
    public String[] retrieveWorkspaceList() {
        File file = new File(storageDirectory);
        String[] workspaceList = file.list((current, name) -> new File(current, name).isDirectory());
        if (workspaceList == null) {
            LOGGER.log(Level.INFO, "Found 0 workspace.");
            return new String[0];
        }

        LOGGER.log(Level.INFO, "Found " + workspaceList.length + " workspace(s).");
        return workspaceList;
    }

    // ========================== User Saving and Loading ==========================

    /**
     * Invokes the save method in UserStorage to save the user data.
     *
     * @param user the user object to be saved
     * @throws AniException when an error occurred while saving the user data
     */
    public void saveUser(User user) throws AniException {
        userStorage.save(user);
    }

    /**
     * Invokes the load method in UserStorage to load the user data.
     *
     * @return the user object that was loaded
     * @throws AniException when an error occurred while loading the user data
     */
    public User loadUser() throws AniException {
        return userStorage.load();
    }

    // ========================== Workspace Saving ==========================

    public void saveWorkspace(Workspace workspace) throws AniException {
        new File(storageDirectory + workspace.getName()).mkdirs();
        watchlistStorage.save(workspace.getName(), workspace.getWatchlistList());
    }

    // ========================== Workspace Deletion ==========================

    public void deleteWorkspace(String name) throws AniException {
        String deletePathString = storageDirectory + name;
        Path deletePath = Paths.get(deletePathString);

        try {
            Files.walk(deletePath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new AniException("Failed to delete workspace folder, you can try deleting manually.");
        }
    }

    // ========================== Watchlist Saving and Loading ==========================

    /**
     * Invokes the save method in UserStorage to save the watchlist data.
     *
     * @param workspaceName name of the workspace to save the list under
     * @param watchlistList the watchlist list to save
     * @throws AniException when an error occurred while saving the watchlist list data
     */
    public void saveWatchlistList(String workspaceName, ArrayList<Watchlist> watchlistList) throws AniException {
        watchlistStorage.save(workspaceName, watchlistList);
    }

    /**
     * Invokes the load method in UserStorage to save the watchlist data.
     *
     * @param workspaceName name of the workspace to load the list from
     * @param watchlistList the watchlist list to load the data into
     * @return the load result message
     * @throws AniException when an error occurred while loading the watchlist list data
     */
    public String loadWatchlistList(String workspaceName, ArrayList<Watchlist> watchlistList) throws AniException {
        return watchlistStorage.load(workspaceName, watchlistList);
    }

    // ========================== Bookmark Saving and Loading ==========================

    public void saveBookmark(String workspaceName, Bookmark bookmark) throws AniException {
        bookmarkStorage.save(workspaceName, bookmark);
    }

    public String loadBookmark(String workspaceName, Bookmark bookmark) throws AniException {
        return bookmarkStorage.load(workspaceName, bookmark);
    }

    // ========================== Script Loading ==========================

    /**
     * Loads the script file.
     *
     * @param workspaceName name of the workspace where the script can be found
     * @param fileName file name of the script file
     * @return the content of the script file
     * @throws AniException when an error occurred while loading the script data
     */
    public String loadScript(String workspaceName, String fileName) throws AniException {
        return scriptStorage.readScript(workspaceName, fileName);
    }
}
