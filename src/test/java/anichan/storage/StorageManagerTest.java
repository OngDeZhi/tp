package anichan.storage;

import anichan.bookmark.Bookmark;
import anichan.human.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import anichan.exception.AniException;
import anichan.watchlist.Watchlist;

import java.io.File;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author OngDeZhi
class StorageManagerTest {
    private static final String TEST_WORKSPACE_NAME = "Test";
    private static final String VALID_WORKSPACE = "ValidWorkspace";
    private static final String EMPTY_FILE_WORKSPACE = "EmptyFileWorkspace";
    private static final String EMPTY_WORKSPACE = "EmptyWorkspace";
    private static final String SOME_INVALID_WORKSPACE = "SomeInvalidWorkspace";
    private static final String ALL_INVALID_WORKSPACE = "AllInvalidWorkspace";
    private static final String SCRIPT_FILE_NAME = "script.txt";
    private static final String INVALID_TEST_DIRECTORY = "a" + File.separator + "b" + File.separator;
    private static final String VALID_TEST_DIRECTORY = "src" + File.separator + "test" + File.separator
            + "data" + File.separator + "StorageManagerTest"
            + File.separator;
    private static final String VALID_FILE_DIRECTORY = VALID_TEST_DIRECTORY + "DirectoryWithValidFile"
            + File.separator;
    private static final String EMPTY_FILE_DIRECTORY = VALID_TEST_DIRECTORY + "DirectoryWithEmptyFileAndDirectory"
            + File.separator;
    private static final String INVALID_FILE_DIRECTORY = VALID_TEST_DIRECTORY + "DirectoryWithInvalidFile"
            + File.separator;
    private static final String INVALID_USER_DATA_DIRECTORY = VALID_TEST_DIRECTORY + "InvalidUserDataDirectory"
            + File.separator;

    private static final String BOOKMARK_LOAD_TEST = "Loaded successfully.";
    private static final String BOOKMARK_LOAD_FAIL_TEST = "Not loaded successfully.";

    private StorageManager validFileSM;
    private StorageManager invalidFileSM;
    private StorageManager emptySM;
    private StorageManager invalidDirectorySM;
    private StorageManager invalidUserDataDirectorySM;

    private User userToLoad;
    private Bookmark bookmarkToLoad;
    private ArrayList<Watchlist> watchlistListForLoad;

    @BeforeEach
    public void setUp() {
        validFileSM = new StorageManager(VALID_FILE_DIRECTORY);
        invalidFileSM = new StorageManager(INVALID_FILE_DIRECTORY);
        emptySM = new StorageManager(EMPTY_FILE_DIRECTORY);
        invalidDirectorySM = new StorageManager(INVALID_TEST_DIRECTORY);
        invalidUserDataDirectorySM = new StorageManager(INVALID_USER_DATA_DIRECTORY);

        userToLoad = null;
        bookmarkToLoad = null;
        watchlistListForLoad = new ArrayList<>();

        Watchlist firstWatchlist = new Watchlist("a");
        firstWatchlist.addAnimeToList(1);
        firstWatchlist.addAnimeToList(2);
        firstWatchlist.addAnimeToList(3);

        Watchlist secondWatchlist = new Watchlist("b");
        secondWatchlist.addAnimeToList(2);
        secondWatchlist.addAnimeToList(3);
        secondWatchlist.addAnimeToList(4);
    }

    @Test
    void retrieveWorkspaceList() {
        // Valid Workspace Directory
        String[] validWorkspaceList = invalidFileSM.retrieveWorkspaceList();
        assertEquals(2, validWorkspaceList.length);

        // Invalid Workspace Directory
        String[] invalidWorkspaceList = invalidDirectorySM.retrieveWorkspaceList();
        assertNotNull(invalidWorkspaceList);
    }

    // ========================== User Saving and Loading ==========================

    @Test
    void loadUser() throws AniException {
        // Invalid Directory
        assertThrows(AniException.class, () -> userToLoad = invalidDirectorySM.loadUser());

        // Valid Directory (Use result from testSaveUser())
        userToLoad = validFileSM.loadUser();
        User expectedUser = new User("Testing", "Male");
        assertEquals(userToLoad.getName(), expectedUser.getName());
        assertEquals(userToLoad.getGender(), expectedUser.getGender());
    }

    @Test
    void loadUser_emptyUserFile_throwsAniException() {
        assertThrows(AniException.class, () -> userToLoad = emptySM.loadUser());
    }

    @Test
    void loadUser_invalidUserFile_throwsAniException() {
        assertThrows(AniException.class, () -> userToLoad = invalidFileSM.loadUser());
        assertThrows(AniException.class, () -> userToLoad = invalidUserDataDirectorySM.loadUser());
    }

    // ========================== Watchlist Saving and Loading ==========================

    @Test
    void loadWatchlistList() throws AniException {
        // Valid Watchlist File
        String validResult = validFileSM.loadWatchlistList(VALID_WORKSPACE, watchlistListForLoad);
        String expectedValidResult = "Loaded successfully.";
        assertEquals(expectedValidResult, validResult);

        // Invalid Directory
        assertThrows(AniException.class, () -> {
            invalidDirectorySM.loadWatchlistList(VALID_WORKSPACE, watchlistListForLoad);
        });

        // Empty Watchlist File
        String emptyResult = emptySM.loadWatchlistList(EMPTY_FILE_WORKSPACE, watchlistListForLoad);
        String expectedEmptyFileResult = "Empty watchlist file.";
        assertEquals(expectedEmptyFileResult, emptyResult);

        // No Watchlist File
        assertThrows(AniException.class, () -> {
            emptySM.loadWatchlistList(EMPTY_WORKSPACE, watchlistListForLoad);
        });
    }

    @Test
    void loadWatchlistList_someInvalidWatchlist_failToLoadSome() throws AniException {
        String someInvalidResult = invalidFileSM.loadWatchlistList(SOME_INVALID_WORKSPACE, watchlistListForLoad);
        String expectedSomeInvalidResult = "Not all loaded successfully (some invalid).";
        assertEquals(3, watchlistListForLoad.size());
        assertEquals(someInvalidResult, expectedSomeInvalidResult);
    }

    @Test
    void loadWatchlistList_allInvalidWatchlist_failToLoadAll() throws AniException {
        String allInvalidResult = invalidFileSM.loadWatchlistList(ALL_INVALID_WORKSPACE, watchlistListForLoad);
        String expectedAllInvalidResult = "No watchlist loaded successfully (all invalid).";
        assertEquals(0, watchlistListForLoad.size());
        assertEquals(allInvalidResult, expectedAllInvalidResult);
    }

    // ========================== Script Reading ==========================

    @Test
    void readScriptFile() throws AniException {
        // Valid Script File
        String fileString = validFileSM.loadScript(VALID_WORKSPACE, SCRIPT_FILE_NAME);
        assertNotNull(fileString);

        // Invalid Directory
        assertThrows(AniException.class, () -> {
            invalidDirectorySM.loadScript(VALID_WORKSPACE, SCRIPT_FILE_NAME);
        });

        // Empty Script File
        assertThrows(AniException.class, () -> {
            emptySM.loadScript(EMPTY_FILE_WORKSPACE, SCRIPT_FILE_NAME);
        });
    }

    //@@author Ong Xin Bin
    // ========================== Bookmark Saving and Loading ==========================
    @Test
    void loadBookmark() throws AniException {
        Bookmark bookmark = new Bookmark();
        String loadBookmarkResult = validFileSM.loadBookmark(VALID_WORKSPACE, bookmark);
        assertEquals(BOOKMARK_LOAD_TEST, loadBookmarkResult);
    }

    @Test
    void loadBookmark_invalidBookmarkFormat_unsuccessfulOutput() throws AniException {
        Bookmark bookmark = new Bookmark();
        String loadBookmarkResult = invalidFileSM.loadBookmark(ALL_INVALID_WORKSPACE, bookmark);
        assertEquals(BOOKMARK_LOAD_FAIL_TEST, loadBookmarkResult);
    }


    @Test
    void loadBookmark_invalidDirectorySM_throwsAniException() throws AniException {
        // Invalid Directory
        assertThrows(AniException.class, () -> invalidDirectorySM.loadBookmark(TEST_WORKSPACE_NAME, bookmarkToLoad));
    }

    @Test
    void loadBookmark_emptyBookmarkFile_throwsAniException() {

        assertThrows(AniException.class, () -> emptySM.loadBookmark(TEST_WORKSPACE_NAME, bookmarkToLoad));
    }

    @Test
    void loadBookmark_invalidBookmarkFile_throwsAniException() {
        assertThrows(AniException.class, () -> invalidFileSM.loadBookmark(TEST_WORKSPACE_NAME, bookmarkToLoad));
    }

}
