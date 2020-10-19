package seedu.duke.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.human.Workspace;
import seedu.duke.storage.StorageManager;
import seedu.duke.watchlist.Watchlist;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WatchlistCommandTest {
    private AnimeData animeData;
    private StorageManager storageManager;
    private User user;
    private Workspace activeWorkspace;

    @BeforeEach
    void setUp() throws AniException {
        animeData = new AnimeData(new ArrayList<>());
        storageManager = new StorageManager("src" + File.separator + "test"
                                            + File.separator + "data" + File.separator);
        user = new User("Testing", "Male");

        Watchlist secondWatchlist = new Watchlist("Second");
        secondWatchlist.addAnimeToList(1);
        secondWatchlist.addAnimeToList(2);

        Watchlist thirdWatchlist = new Watchlist("Third");
        thirdWatchlist.addAnimeToList(3);

        ArrayList<Watchlist> watchlistList = new ArrayList<>();
        watchlistList.add(new Watchlist("First"));
        watchlistList.add(secondWatchlist);
        watchlistList.add(thirdWatchlist);

        Workspace newWorkspace = user.addWorkspace("Default");
        newWorkspace.setWatchlistList(watchlistList);
        newWorkspace.setActiveWatchlist(newWorkspace.getWatchlistList().get(0));
        user.setActiveWorkspace(newWorkspace);
        activeWorkspace = user.getActiveWorkspace();
    }

    // ========================== General ==========================

    @Test
    void execute_invalidParameters_throwsAniException() {
        // Blanks
        WatchlistCommand blankOption = new WatchlistCommand("", "");
        assertThrows(AniException.class, () -> blankOption.execute(animeData, storageManager, user));

        // Unknown Option
        WatchlistCommand unknownOption = new WatchlistCommand("UNKNOWN", "TEST");
        assertThrows(AniException.class, () -> unknownOption.execute(animeData, storageManager, user));
    }

    @Test
    void execute_nullParameters_throwsAssertionError() {
        // Null Option and Option Information
        WatchlistCommand nullOption = new WatchlistCommand(null, null);
        assertThrows(AssertionError.class, () -> nullOption.execute(animeData, storageManager, user));

        // Null WatchlistList
        WatchlistCommand nullWorkspace = new WatchlistCommand("n", "First");
        activeWorkspace.setWatchlistList(null);
        assertThrows(AssertionError.class, () -> nullWorkspace.execute(animeData, storageManager, user));
    }

    // ========================== Create ==========================

    @Test
    void execute_validParametersForCreateWatchlist_success() throws AniException {
        WatchlistCommand watchlistCommand = new WatchlistCommand("n", "Test");
        watchlistCommand.execute(animeData, storageManager, user);
        assertEquals(4, activeWorkspace.getWatchlistList().size());
    }

    @Test
    void execute_duplicateWatchlistNameForCreateWatchlist_throwsAniException() {
        WatchlistCommand watchlistCommand = new WatchlistCommand("n", "First");
        assertThrows(AniException.class, () -> watchlistCommand.execute(animeData, storageManager, user));
    }

    // ========================== List ==========================

    @Test
    void execute_validParametersForListWatchlist_success() throws AniException {
        WatchlistCommand watchlistCommand = new WatchlistCommand("l", "");
        String emptyListMessage = "Uhh.. You have no watchlist to list..";
        assertNotEquals(emptyListMessage, watchlistCommand.execute(animeData, storageManager, user));
    }

    @Test
    void execute_emptyWatchlistListForListAllWatchlist_emptyListMessage() throws AniException {
        WatchlistCommand watchlistCommand = new WatchlistCommand("l", "");
        activeWorkspace.setWatchlistList(new ArrayList<>());
        String expected = "Uhh.. You have no watchlist to list..";
        assertEquals(expected, watchlistCommand.execute(animeData, storageManager, user));
    }

    // ========================== Select ==========================

    @Test
    void execute_validParametersForSelectWatchlist_success() throws AniException {
        WatchlistCommand watchlistCommand = new WatchlistCommand("s", "3");
        watchlistCommand.execute(animeData, storageManager, user);
        Watchlist activeWatchlist = activeWorkspace.getActiveWatchlist();
        Watchlist selectedWatchlist = activeWorkspace.getWatchlistList().get(2);
        assertEquals(activeWatchlist, selectedWatchlist);
    }

    // ========================== Delete ==========================

    @Test
    void execute_validParametersForDeleteWatchlist_success() throws AniException {
        // Delete non-active watchlist
        WatchlistCommand deleteWatchlist = new WatchlistCommand("d", "2");
        deleteWatchlist.execute(animeData, storageManager, user);
        assertEquals(2, activeWorkspace.getWatchlistList().size());

        // Delete active watchlist
        WatchlistCommand deleteActiveWatchlist = new WatchlistCommand("d", "2");
        activeWorkspace.setActiveWatchlist(activeWorkspace.getWatchlistList().get(1));
        deleteActiveWatchlist.execute(animeData, storageManager, user);
        assertEquals(activeWorkspace.getWatchlistList().get(0), activeWorkspace.getActiveWatchlist());
    }

    // ========================== Select and Delete ==========================

    @Test
    void execute_emptyWatchlistListForSelectAndDeleteWatchlist_throwsAniException() {
        activeWorkspace.setWatchlistList(new ArrayList<>());
        WatchlistCommand selectWatchlist = new WatchlistCommand("s", "1");
        assertThrows(AniException.class, () -> selectWatchlist.execute(animeData, storageManager, user));

        WatchlistCommand deleteWatchlist = new WatchlistCommand("d", "1");
        assertThrows(AniException.class, () -> deleteWatchlist.execute(animeData, storageManager, user));
    }

    @Test
    void execute_notIntegerStringForSelectAndDeleteWatchlist_throwsAniException() {
        WatchlistCommand selectWatchlist = new WatchlistCommand("s", "one");
        assertThrows(AniException.class, () -> selectWatchlist.execute(animeData, storageManager, user));

        WatchlistCommand deleteWatchlist = new WatchlistCommand("d", "one");
        assertThrows(AniException.class, () -> deleteWatchlist.execute(animeData, storageManager, user));
    }

    @Test
    void execute_negativeWatchlistIndexForSelectAndDeleteWatchlist_throwsAniException() {
        WatchlistCommand selectWatchlist = new WatchlistCommand("s", "-2");
        assertThrows(AniException.class, () -> selectWatchlist.execute(animeData, storageManager, user));

        WatchlistCommand deleteWatchlist = new WatchlistCommand("d", "-2");
        assertThrows(AniException.class, () -> deleteWatchlist.execute(animeData, storageManager, user));
    }

    @Test
    void execute_invalidWatchlistIndexForSelectAndDeleteWatchlist_throwsAniException() {
        WatchlistCommand selectWatchlist = new WatchlistCommand("d", "999");
        assertThrows(AniException.class, () -> selectWatchlist.execute(animeData, storageManager, user));

        WatchlistCommand deleteWatchlist = new WatchlistCommand("s", "999");
        assertThrows(AniException.class, () -> deleteWatchlist.execute(animeData, storageManager, user));
    }

    @Test
    void execute_oneWatchlistInWatchlistListForSelectAndDeleteWatchlist_throwsAniException() {
        ArrayList<Watchlist> watchlistList = new ArrayList<>();
        watchlistList.add(new Watchlist("Only One"));
        activeWorkspace.setWatchlistList(watchlistList);
        activeWorkspace.setActiveWatchlist(watchlistList.get(0));

        WatchlistCommand selectWatchlist = new WatchlistCommand("s", "1");
        assertThrows(AniException.class, () -> selectWatchlist.execute(animeData, storageManager, user));

        WatchlistCommand deleteWatchlist = new WatchlistCommand("d", "1");
        assertThrows(AniException.class, () -> deleteWatchlist.execute(animeData, storageManager, user));
    }
}
