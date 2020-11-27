package anichan;

import anichan.anime.AnimeData;
import anichan.bookmark.Bookmark;
import anichan.commands.Command;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.human.Workspace;
import anichan.logger.AniLogger;
import anichan.parser.Parser;
import anichan.ui.Ui;
import anichan.watchlist.Watchlist;
import anichan.storage.StorageManager;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author
public class Main {
    private static final String ANICHAN_STORAGE_DIRECTORY = "data" + File.separator;
    private static final Logger LOGGER = AniLogger.getAniLogger(Main.class.getName());
    private static final String DEFAULT_NAME = "Default";

    private final Ui ui;
    private final Parser parser;
    private final StorageManager storageManager;

    private AnimeData animeData;
    private User user;

    public Main() {
        ui = new Ui();
        parser = new Parser();
        storageManager = new StorageManager(ANICHAN_STORAGE_DIRECTORY);

        displayWelcome();
        userSetup();
        animeDataSetup();
    }

    public void run() {
        while (ui.hasNextLine(user)) {
            try {
                String userInput = ui.readUserInput();
                Command command = parser.getCommand(userInput);
                String commandOutput = command.execute(animeData, storageManager, user);
                ui.printMessage(commandOutput);

                if (command.getShouldExit()) {
                    ui.printGoodbyeMessage(user.getHonorificName());
                    break;
                }
            } catch (AniException exception) {
                ui.printErrorMessage(exception.getMessage());
            }
        }
    }

    /**
     * The starting point of AniChan.
     *
     * @param args are the arguments parsed in (if any)
     */
    public static void main(String[] args) {
        new Main().run();
    }

    /**
     * Prints the welcome message.
     */
    private void displayWelcome() {
        ui.printWelcomeMessage();
        LOGGER.log(Level.INFO, "AniChan started! Initializing..");
        ui.printHorizontalLine();
    }

    /**
     * Calls the relevant methods to load or setup the user and his workspaces.
     */
    private void userSetup() {
        loadUserData();
        ArrayList<Workspace> workspaceList = loadWorkspaceData();

        if (user == null) {
            newUserSetup();
        }
        
        workspaceSetup(workspaceList);
        ui.printHorizontalLine();
    }

    /**
     * Performs the loading of the source data, AnimeData.
     */
    private void animeDataSetup() {
        try {
            animeData = new AnimeData();
        } catch (AniException exception) {
            ui.printMessage("\tAnimeData: " + exception.getMessage());
            LOGGER.log(Level.WARNING, "Exception: " + exception.getMessage());
        }
    }

    /**
     * Loads existing user data (if any).
     */
    private void loadUserData() {
        try {
            user = storageManager.loadUser();
            ui.printMessage("User: Loaded successfully.");
            LOGGER.log(Level.INFO, "Loaded " + user.getName() + " from storage");
        } catch (AniException exception) {
            ui.printMessage("User: " + exception.getMessage());
            LOGGER.log(Level.WARNING, "Exception: " + exception.getMessage());
        }
    }

    /**
     * Loads existing workspace data (if any).
     *
     * @return list of Workspace objects
     */
    private ArrayList<Workspace> loadWorkspaceData() {
        ArrayList<Workspace> workspaceList = new ArrayList<>();
        String[] workspaceNameList = storageManager.retrieveWorkspaceList();
        for (String workspaceName : workspaceNameList) {
            ui.printMessage("Workspace \"" + workspaceName + "\":");

            ArrayList<Watchlist> watchlistList = loadWatchlistData(workspaceName);
            Bookmark bookmark = loadBookmarkData(workspaceName);

            if (watchlistList.size() == 0) {
                watchlistList.add(new Watchlist("Default"));
            }

            Workspace workspace = new Workspace(workspaceName, watchlistList, bookmark);
            workspaceList.add(workspace);
        }

        return workspaceList;
    }

    /**
     * Loads existing watchlist data for the workspace (if any).
     *
     * @param workspaceName the workspace to retrieve watchlist data from
     * @return all watchlist created for that workspace
     */
    private ArrayList<Watchlist> loadWatchlistData(String workspaceName) {
        ArrayList<Watchlist> watchlistList = new ArrayList<>();
        try {
            String loadWatchlistResult = storageManager.loadWatchlistList(workspaceName, watchlistList);
            ui.printMessage("\tWatchlist: " + loadWatchlistResult);
            LOGGER.log(Level.INFO, "Loaded watchlist(s) " + loadWatchlistResult + " from storage.");
        } catch (AniException exception) {
            ui.printMessage("\tWatchlist: " + exception.getMessage());
            LOGGER.log(Level.WARNING, "Exception: " + exception.getMessage());
        }

        return watchlistList;
    }

    /**
     * Loads existing bookmark data for the workspace (if any).
     *
     * @param workspaceName the workspace to retrieve watchlist data from
     * @return all bookmark created for that workspace
     */
    private Bookmark loadBookmarkData(String workspaceName) {
        Bookmark bookmark = new Bookmark();
        try {
            String loadBookmarkResult = storageManager.loadBookmark(workspaceName, bookmark);
            ui.printMessage("\tBookmark:  " + loadBookmarkResult);
            LOGGER.log(Level.INFO, "Loaded bookmark(s) " + loadBookmarkResult + " from storage.");
        } catch (AniException exception) {
            ui.printMessage("\tBookmark:  " + exception.getMessage());
            LOGGER.log(Level.WARNING, "Exception: " + exception.getMessage());
        }

        return bookmark;
    }

    /**
     * Setups the Workspace for User.
     *
     * @param workspaceList ArrayList of Workspaces the User manages
     */
    private void workspaceSetup(ArrayList<Workspace> workspaceList) {
        LOGGER.log(Level.INFO, "Workspace setup..");

        user.setWorkspaceList(workspaceList);

        if (user.getTotalWorkspaces() == 0) {
            try {
                Workspace newWorkspace = user.addWorkspace(DEFAULT_NAME);
                ArrayList<Watchlist> watchlistList = new ArrayList<>();
                watchlistList.add(new Watchlist(DEFAULT_NAME));
                newWorkspace.setWatchlistList(watchlistList);
                user.setActiveWorkspace(newWorkspace);
                LOGGER.log(Level.INFO, "Workspace created: " + newWorkspace);

                storageManager.saveWorkspace(newWorkspace);
                LOGGER.log(Level.INFO, "Workspace saved to storage: " + newWorkspace.getName());
            } catch (AniException exception) {
                ui.printErrorMessage(exception.getMessage());
                LOGGER.log(Level.SEVERE, "Exception: " + exception.getMessage());
            }
        }

        Workspace activeWorkspace = user.getActiveWorkspace();
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();
        activeWorkspace.setActiveWatchlist(watchlistList.get(0));
    }

    /**
     * Setups a new User for program.
     */
    private void newUserSetup() {
        LOGGER.log(Level.INFO, "Creating new user..");

        while (true) {
            try {
                String[] userDialogueInput = ui.createUserDialogue();
                user = new User(userDialogueInput[0], userDialogueInput[1]);
                LOGGER.log(Level.INFO, "Created: " + user);
                storageManager.saveUser(user);
                break;
            } catch (AniException exception) {
                ui.printErrorMessage(exception.getMessage());
                LOGGER.log(Level.WARNING, "Exception: " + exception.getMessage());
            }
        }
    }
}
