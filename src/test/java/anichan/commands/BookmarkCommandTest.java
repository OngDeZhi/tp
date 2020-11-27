package anichan.commands;

import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.human.Workspace;
import anichan.parser.BookmarkParser;
import anichan.storage.StorageManager;
import anichan.watchlist.Watchlist;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;


import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookmarkCommandTest {
    private static AnimeData animeData;
    private static final String STORAGE_DIRECTORY = "src" + File.separator + "test"
            + File.separator + "data" + File.separator;
    private static StorageManager storageManager;
    private static User user;

    private static final String VALID_ADD_TEST = "-a 1";
    private static final String VALID_ADD_TEST2 = "-a 2";
    private static final String VALID_ADD_TEST3 = "-a 3";
    private static final String VALID_ADD_TEST4 = "-a 4";
    private static final String VALID_LIST_TEST = "-l";
    private static final String VALID_DELETE_TEST = "-d 2";
    private static final String VALID_EDIT_TEST = "1 -e 1";
    private static final String VALID_NOTE_TEST = "1 -n test";
    private static final String VALID_NOTE_TEST2 = "1 -n test2";
    private static final String VALID_NOTE_TEST3 = "1 -n test3";
    private static final String VALID_SINGLE_INPUT_TEST = "1";
    private static final String VALID_SINGLE_INPUT_TEST2 = "2";
    private static final String VALID_SINGLE_INPUT_TEST3 = "3";
    private static final String VALID_REMOVE_NOTE_TEST = "1 -r 2";
    private static final String INVALID_ADD_ANIME_INDEX_TEST = "-a 600";
    private static final String INVALID_DELETE_BOOKMARK_INDEX_TEST = "-d 5";
    private static final String INVALID_ADD_DUPLICATE_ANIME_INDEX_TEST = "-a 1";
    private static final String INVALID_EDIT_BOOKMARK_INDEX_TEST = "5 -e 5";
    private static final String INVALID_INFO_BOOKMARK_INDEX_TEST = "5";
    private static final String INVALID_NOTE_BOOKMARK_INDEX_TEST = "5 -n test";
    private static final String INVALID_REMOVE_NOTE_BOOKMARK_INDEX_TEST = "5 -r 5";
    private static final String INVALID_REMOVE_NOTE_INDEX_TEST = "1 -r 5";
    private static final String ZERO_ADD_ANIME_INDEX_TEST = "-a 0";
    private static final String ZERO_DELETE_BOOKMARK_INDEX_TEST = "-d 0";
    private static final String ZERO_EDIT_BOOKMARK_INDEX_TEST = "0 -e 1";
    private static final String ZERO_REMOVE_NOTE_INDEX_TEST = "1 -r 0";
    private static final String ZERO_REMOVE_BOOKMARK_INDEX_TEST = "0 -r 1";
    private static final String INVALID_EDIT_EPISODE_TEST = "1 -e 100";
    private static final String INVALID_NOTE_FORBIDDEN_CHAR_TEST = "1 -n testing~";

    private static final String OUTPUT_ADD_TEST = "Saving 1. Cowboy Bebop to bookmark.";
    private static final String OUTPUT_ADD_TEST2 = "Saving 2. Cowboy Bebop: The Movie - Knockin' on Heaven's Door"
            + " to bookmark.";
    private static final String OUTPUT_ADD_TEST3 = "Saving 3. Trigun to bookmark.";
    private static final String OUTPUT_ADD_TEST4 = "Saving 4. Witch Hunter Robin to bookmark.";
    private static final String OUTPUT_LIST_TEST = "Listing all anime in bookmark:"
            + System.lineSeparator()
            + "\t1. Cowboy Bebop" + System.lineSeparator()
            + "\t2. Cowboy Bebop: The Movie - Knockin' on Heaven's Door" + System.lineSeparator()
            + "\t3. Trigun" + System.lineSeparator()
            + "\t4. Witch Hunter Robin" + System.lineSeparator();
    private static final String OUTPUT_DELETE_TEST = "Removing Cowboy Bebop: The Movie - Knockin' on Heaven's Door! :(";
    private static final String OUTPUT_EDIT_TEST = "Editing the current episode for Cowboy Bebop to episode(s) 1.";
    private static final String OUTPUT_NOTE_TEST = "Adding note:\"test\" to Cowboy Bebop!";
    private static final String OUTPUT_NOTE_TEST2 = "Adding note:\"test2\" to Cowboy Bebop!";
    private static final String OUTPUT_NOTE_TEST3 = "Adding note:\"test3\" to Cowboy Bebop!";
    private static final String OUTPUT_SINGLE_INPUT_TEST = "Here is the information for that anime."
            + System.lineSeparator()
            + "Index: 1" + System.lineSeparator()
            + "Name: Cowboy Bebop" + System.lineSeparator()
            + "Episodes: 26" + System.lineSeparator()
            + "Release Date: 03/Apr/1998" + System.lineSeparator()
            + "Rating: 86" + System.lineSeparator()
            + "Genre: [Action, Adventure, Drama, Sci-Fi]" + System.lineSeparator()
            + System.lineSeparator()
            + "Current Episode: 1" + System.lineSeparator()
            + System.lineSeparator()
            + "Notes for anime:" + System.lineSeparator()
            + "1. test" + System.lineSeparator()
            + "2. test2" + System.lineSeparator()
            + "3. test3" + System.lineSeparator();
    private static final String OUTPUT_SINGLE_INPUT_TEST2 = "Here is the information for that anime."
            + System.lineSeparator()
            + "Index: 3" + System.lineSeparator()
            + "Name: Trigun" + System.lineSeparator()
            + "Episodes: 26" + System.lineSeparator()
            + "Release Date: 01/Apr/1998" + System.lineSeparator()
            + "Rating: 79" + System.lineSeparator()
            + "Genre: [Action, Adventure, Comedy, Drama, Sci-Fi]" + System.lineSeparator()
            + System.lineSeparator()
            + "Notes for anime:" + System.lineSeparator()
            + "\tNotes is empty.. :(" + System.lineSeparator();
    private static final String OUTPUT_SINGLE_INPUT_TEST3 = "Here is the information for that anime."
            + System.lineSeparator()
            + "Index: 4" + System.lineSeparator()
            + "Name: Witch Hunter Robin" + System.lineSeparator()
            + "Episodes: 26" + System.lineSeparator()
            + "Release Date: 02/Jul/2002" + System.lineSeparator()
            + "Rating: 68" + System.lineSeparator()
            + "Genre: [Action, Drama, Mystery, Supernatural]" + System.lineSeparator()
            + System.lineSeparator()
            + "Notes for anime:" + System.lineSeparator()
            + "\tNotes is empty.. :(" + System.lineSeparator();
    private static final String OUTPUT_REMOVE_NOTE_TEST = "Deleting note: test2" + System.lineSeparator()
            + "From: Cowboy Bebop.";
    private static final String OUTPUT_SINGLE_INPUT_TEST_AFTER_REMOVE = "Here is the information for that anime."
            + System.lineSeparator()
            + "Index: 1" + System.lineSeparator()
            + "Name: Cowboy Bebop" + System.lineSeparator()
            + "Episodes: 26" + System.lineSeparator()
            + "Release Date: 03/Apr/1998" + System.lineSeparator()
            + "Rating: 86" + System.lineSeparator()
            + "Genre: [Action, Adventure, Drama, Sci-Fi]" + System.lineSeparator()
            + System.lineSeparator()
            + "Current Episode: 1" + System.lineSeparator()
            + System.lineSeparator()
            + "Notes for anime:" + System.lineSeparator()
            + "1. test" + System.lineSeparator()
            + "2. test3" + System.lineSeparator();


    @BeforeAll
    static void setUp() throws AniException {
        animeData = new AnimeData();
        storageManager = new StorageManager(STORAGE_DIRECTORY);
        user = new User("Mr Test", "Male");

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
    }

    @Test
    @Order(1)
    void execute_validAddAnimeIndex_successfulOutput() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(VALID_ADD_TEST);
        String result = testBookmarkCommand.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_ADD_TEST, result);
    }

    @Test
    @Order(2)
    void execute_validAddAnimeIndex2_successfulOutput() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(VALID_ADD_TEST2);
        String result = testBookmarkCommand.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_ADD_TEST2, result);


        BookmarkParser testParse2 = new BookmarkParser();
        BookmarkCommand testBookmarkCommand2 = testParse2.parse(VALID_ADD_TEST3);
        String result2 = testBookmarkCommand2.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_ADD_TEST3, result2);


        BookmarkParser testParse3 = new BookmarkParser();
        BookmarkCommand testBookmarkCommand3 = testParse3.parse(VALID_ADD_TEST4);
        String result3 = testBookmarkCommand3.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_ADD_TEST4, result3);
    }

    @Test
    @Order(3)
    void execute_validList_successfulOutput() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(VALID_LIST_TEST);
        String result = testBookmarkCommand.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_LIST_TEST, result);
    }

    @Test
    @Order(4)
    void execute_validDelete_successfulOutput() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(VALID_DELETE_TEST);
        String result = testBookmarkCommand.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_DELETE_TEST, result);
        System.out.println(result);
    }

    @Test
    @Order(5)
    void execute_validEdit_successfulOutput() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(VALID_EDIT_TEST);
        String result = testBookmarkCommand.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_EDIT_TEST, result);
        System.out.println(result);
    }

    @Test
    @Order(6)
    void execute_validNote_successfulOutput() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(VALID_NOTE_TEST);
        String result = testBookmarkCommand.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_NOTE_TEST, result);
        System.out.println(result);

        BookmarkParser testParse2 = new BookmarkParser();
        BookmarkCommand testBookmarkCommand2 = testParse2.parse(VALID_NOTE_TEST2);
        String result2 = testBookmarkCommand2.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_NOTE_TEST2, result2);
        System.out.println(result2);

        BookmarkParser testParse3 = new BookmarkParser();
        BookmarkCommand testBookmarkCommand3 = testParse3.parse(VALID_NOTE_TEST3);
        String result3 = testBookmarkCommand3.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_NOTE_TEST3, result3);
        System.out.println(result);

    }

    @Test
    @Order(7)
    void execute_validInfo_successfulOutput() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(VALID_SINGLE_INPUT_TEST);
        String result = testBookmarkCommand.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_SINGLE_INPUT_TEST, result);

        BookmarkParser testParse2 = new BookmarkParser();
        BookmarkCommand testBookmarkCommand2 = testParse2.parse(VALID_SINGLE_INPUT_TEST2);
        String result2 = testBookmarkCommand2.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_SINGLE_INPUT_TEST2, result2);

        BookmarkParser testParse3 = new BookmarkParser();
        BookmarkCommand testBookmarkCommand3 = testParse3.parse(VALID_SINGLE_INPUT_TEST3);
        String result3 = testBookmarkCommand3.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_SINGLE_INPUT_TEST3, result3);
    }

    @Test
    @Order(8)
    void execute_validRemoveNote_successfulOutput() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(VALID_REMOVE_NOTE_TEST);
        String result = testBookmarkCommand.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_REMOVE_NOTE_TEST, result);
        System.out.println(result);

        BookmarkParser testParse2 = new BookmarkParser();
        BookmarkCommand testBookmarkCommand2 = testParse2.parse(VALID_SINGLE_INPUT_TEST);
        String result2 = testBookmarkCommand2.execute(animeData, storageManager, user);
        assertEquals(OUTPUT_SINGLE_INPUT_TEST_AFTER_REMOVE, result2);
    }


    @Test
    @Order(9)
    void execute_invalidAnimeIndexAdd_ThrowsAniException() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(INVALID_ADD_ANIME_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand.execute(animeData, storageManager, user);
        });
    }

    @Test
    @Order(10)
    void execute_invalidBookmarkIndexDelete_ThrowsAniException() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(INVALID_DELETE_BOOKMARK_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand.execute(animeData, storageManager, user);
        });
    }

    @Test
    @Order(11)
    void execute_invalidDuplicateAnimeIndexAdd_ThrowsAniException() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(INVALID_ADD_DUPLICATE_ANIME_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand.execute(animeData, storageManager, user);
        });
    }

    @Test
    @Order(12)
    void execute_invalidBookmarkIndexEdit_ThrowsAniException() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(INVALID_EDIT_BOOKMARK_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand.execute(animeData, storageManager, user);
        });
    }

    @Test
    @Order(13)
    void execute_invalidBookmarkIndexInfo_ThrowsAniException() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(INVALID_INFO_BOOKMARK_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand.execute(animeData, storageManager, user);
        });
    }

    @Test
    @Order(14)
    void execute_invalidBookmarkIndexNote_ThrowsAniException() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(INVALID_NOTE_BOOKMARK_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand.execute(animeData, storageManager, user);
        });
    }

    @Test
    @Order(15)
    void execute_invalidBookmarkIndexNoteRemove_ThrowsAniException() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(INVALID_REMOVE_NOTE_BOOKMARK_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand.execute(animeData, storageManager, user);
        });
    }

    @Test
    @Order(16)
    void execute_invalidNoteIndexRemoveNote_ThrowsAniException() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(INVALID_REMOVE_NOTE_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand.execute(animeData, storageManager, user);
        });
    }

    @Test
    @Order(17)
    void execute_zeroIndex_ThrowsAniException() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(ZERO_ADD_ANIME_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand.execute(animeData, storageManager, user);
        });

        BookmarkParser testParse2 = new BookmarkParser();
        BookmarkCommand testBookmarkCommand2 = testParse2.parse(ZERO_DELETE_BOOKMARK_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand2.execute(animeData, storageManager, user);
        });

        BookmarkParser testParse3 = new BookmarkParser();
        BookmarkCommand testBookmarkCommand3 = testParse3.parse(ZERO_EDIT_BOOKMARK_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand3.execute(animeData, storageManager, user);
        });

        BookmarkParser testParse4 = new BookmarkParser();
        BookmarkCommand testBookmarkCommand4 = testParse4.parse(ZERO_REMOVE_NOTE_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand4.execute(animeData, storageManager, user);
        });

        BookmarkParser testParse5 = new BookmarkParser();
        BookmarkCommand testBookmarkCommand5 = testParse5.parse(ZERO_REMOVE_BOOKMARK_INDEX_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand5.execute(animeData, storageManager, user);
        });
    }

    @Test
    @Order(18)
    void execute_invalidEpisodeEdit_ThrowsAniException() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(INVALID_EDIT_EPISODE_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand.execute(animeData, storageManager, user);
        });
    }

    @Test
    @Order(19)
    void execute_invalidForbiddenCharNote_ThrowsAniException() throws AniException {
        BookmarkParser testParse = new BookmarkParser();
        BookmarkCommand testBookmarkCommand = testParse.parse(INVALID_NOTE_FORBIDDEN_CHAR_TEST);

        assertThrows(AniException.class, () -> {
            testBookmarkCommand.execute(animeData, storageManager, user);
        });
    }
}
