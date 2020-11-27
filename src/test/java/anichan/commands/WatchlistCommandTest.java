package anichan.commands;

import anichan.human.User;
import anichan.human.Workspace;
import anichan.parser.WatchlistParser;
import anichan.watchlist.Watchlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.storage.StorageManager;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author OngDeZhi
class WatchlistCommandTest {
    private static final String STORAGE_DIRECTORY = "src" + File.separator + "test"
                                                    + File.separator + "data" + File.separator;

    private WatchlistParser watchlistParser;
    private AnimeData animeData;
    private StorageManager storageManager;
    private User user;
    private Workspace activeWorkspace;

    @BeforeEach
    void setUp() throws AniException {
        watchlistParser = new WatchlistParser();
        animeData = new AnimeData(new ArrayList<>());
        storageManager = new StorageManager(STORAGE_DIRECTORY);
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
        user.setActiveWorkspace(newWorkspace);
        activeWorkspace = user.getActiveWorkspace();
    }

    // ========================== Create ==========================

    @Test
    void execute_validParametersForCreateWatchlist_success() throws AniException {
        WatchlistCommand createWatchlist = watchlistParser.parse("-n Test");
        createWatchlist.execute(animeData, storageManager, user);
        assertEquals(4, activeWorkspace.getWatchlistList().size());
    }

    @Test
    void execute_notUniqueWatchlistNameForCreateWatchlist_throwsAniException() {
        assertThrows(AniException.class, () -> {
            WatchlistCommand createWatchlist = watchlistParser.parse("-n First");
            createWatchlist.execute(animeData, storageManager, user);
        });
    }

    // ========================== List ==========================

    @Test
    void execute_validParametersForListAllWatchlist_success() throws AniException {
        String expectedListResult = "Currently, you have 3 watchlist(s):";
        expectedListResult += System.lineSeparator();
        expectedListResult += "\t1. First";
        expectedListResult += System.lineSeparator();
        expectedListResult += "\t2. Second";
        expectedListResult += System.lineSeparator();
        expectedListResult += "\t3. Third";

        WatchlistCommand listAllWatchlist = watchlistParser.parse("-l");
        assertEquals(expectedListResult, listAllWatchlist.execute(animeData, storageManager, user));
    }

    // ========================== Select ==========================

    @Test
    void execute_validParametersForSelectWatchlistList_success() throws AniException {
        WatchlistCommand selectWatchlist = watchlistParser.parse("-s 3");
        selectWatchlist.execute(animeData, storageManager, user);
        Watchlist activeWatchlist = activeWorkspace.getActiveWatchlist();
        Watchlist selectedWatchlist = activeWorkspace.getWatchlistList().get(2);
        assertEquals(activeWatchlist, selectedWatchlist);
    }

    @Test
    void execute_invalidWatchlistIndexForSelectWatchlist_throwsAniException() {
        assertThrows(AniException.class, () -> {
            WatchlistCommand selectWithOutOfRangeIndex = watchlistParser.parse("-s 999");
            selectWithOutOfRangeIndex.execute(animeData, storageManager, user);
        });
    }

    @Test
    void execute_onlyOneWatchlistForSelectWatchlist_throwsAniException() {
        ArrayList<Watchlist> watchlistList = new ArrayList<>();
        watchlistList.add(new Watchlist("Only One"));
        activeWorkspace.setWatchlistList(watchlistList);
        activeWorkspace.setActiveWatchlist(watchlistList.get(0));

        assertThrows(AniException.class, () -> {
            WatchlistCommand selectWatchlist = watchlistParser.parse("-s 1");
            selectWatchlist.execute(animeData, storageManager, user);
        });
    }

    // ========================== Delete ==========================

    @Test
    void execute_validParametersForDeleteWatchlistList_success() throws AniException {
        // Delete active watchlist
        activeWorkspace.setActiveWatchlist(activeWorkspace.getWatchlistList().get(2));
        WatchlistCommand deleteActiveWatchlist = watchlistParser.parse("-d 3");
        deleteActiveWatchlist.execute(animeData, storageManager, user);
        assertEquals(activeWorkspace.getWatchlistList().get(0), activeWorkspace.getActiveWatchlist());

        // Delete non-active watchlist
        WatchlistCommand deleteWatchlist = watchlistParser.parse("-d 2");
        deleteWatchlist.execute(animeData, storageManager, user);
        assertEquals(1, activeWorkspace.getWatchlistList().size());
    }

    @Test
    void execute_invalidWatchlistIndexForDeleteWatchlist_throwsAniException() {
        assertThrows(AniException.class, () -> {
            WatchlistCommand deleteWithOutOfRangeIndex = watchlistParser.parse("-d 999");
            deleteWithOutOfRangeIndex.execute(animeData, storageManager, user);
        });
    }

    @Test
    void execute_onlyOneWatchlistForDeleteWatchlist_throwsAniException() {
        ArrayList<Watchlist> watchlistList = new ArrayList<>();
        watchlistList.add(new Watchlist("Only One"));
        activeWorkspace.setWatchlistList(watchlistList);
        activeWorkspace.setActiveWatchlist(watchlistList.get(0));

        assertThrows(AniException.class, () -> {
            WatchlistCommand deleteWatchlist = watchlistParser.parse("-d 1");
            deleteWatchlist.execute(animeData, storageManager, user);
        });
    }
}
