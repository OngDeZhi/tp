package anichan.commands;

import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.human.Workspace;
import anichan.parser.EstimateParser;
import anichan.watchlist.Watchlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import anichan.storage.StorageManager;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author OngDeZhi
class EstimateCommandTest {
    private static final String VALID_WORKSPACE = "ValidWorkspace";
    private static final String INVALID_TEST_DIRECTORY = "a" + File.separator + "b" + File.separator + "c"
                                                         + File.separator;
    private static final String VALID_TEST_DIRECTORY = "src" + File.separator + "test" + File.separator
                                                       + "data" + File.separator + "StorageManagerTest"
                                                       + File.separator;
    private static final String VALID_FILE_DIRECTORY = VALID_TEST_DIRECTORY + "DirectoryWithValidFile"
                                                       + File.separator;
    private static final String EMPTY_FILE_DIRECTORY = VALID_TEST_DIRECTORY + "DirectoryWithEmptyFileAndDirectory"
                                                       + File.separator;

    private StorageManager validSM;
    private StorageManager emptySM;
    private StorageManager invalidDirectorySM;

    private EstimateParser estimateParser;
    private AnimeData animeData;
    private User user;

    @BeforeEach
    void setUp() throws AniException {
        estimateParser = new EstimateParser();
        animeData = new AnimeData(new ArrayList<>());
        validSM = new StorageManager(VALID_FILE_DIRECTORY);
        emptySM = new StorageManager(EMPTY_FILE_DIRECTORY);
        invalidDirectorySM = new StorageManager(INVALID_TEST_DIRECTORY);
        user = new User("Testing", "Male");

        ArrayList<Watchlist> watchlistList = new ArrayList<>();
        watchlistList.add(new Watchlist("First"));

        Workspace newWorkspace = user.addWorkspace(VALID_WORKSPACE);
        newWorkspace.setWatchlistList(watchlistList);
        user.setActiveWorkspace(newWorkspace);
    }

    @Test
    void execute_validScriptFileNameWithNoWordsPerHour_success() throws AniException {
        // Words per hour (wph) not specified.
        String expectedNoWphResult = "Average translator (400 words per hour) takes: 8 hour(s) 1 minute(s).";
        expectedNoWphResult += System.lineSeparator();
        expectedNoWphResult += "Average translator (500 words per hour) takes: 6 hour(s) 25 minute(s).";
        expectedNoWphResult += System.lineSeparator();
        expectedNoWphResult += "Average translator (600 words per hour) takes: 5 hour(s) 20 minute(s).";

        EstimateCommand noWph = estimateParser.parse("script.txt");
        String noWphResult = noWph.execute(animeData, validSM, user);
        assertEquals(expectedNoWphResult, noWphResult);
    }

    @Test
    void execute_validScriptFileNameWithWordsPerHour_success() throws AniException {
        // Words per hour specified (with hours and minutes).
        EstimateCommand wphWithHoursAndMinutes = estimateParser.parse("script.txt -wph 777");
        String wphWithHoursAndMinutesResult = wphWithHoursAndMinutes.execute(animeData, validSM, user);
        assertEquals("You would need 4 hour(s) 7 minute(s).", wphWithHoursAndMinutesResult);

        // Words per hour specified (with only hours).
        EstimateCommand wphWithOnlyHours = estimateParser.parse("script.txt -wph 3205");
        String wphWithOnlyHoursResult = wphWithOnlyHours.execute(animeData, validSM, user);
        assertEquals("You would need 1 hour(s).", wphWithOnlyHoursResult);
    }

    @Test
    void execute_invalidDirectory_throwsAniException() {
        assertThrows(AniException.class, () -> {
            EstimateCommand estimateCommand = estimateParser.parse("script.txt");
            estimateCommand.execute(animeData, invalidDirectorySM, user);
        });
    }

    @Test
    void execute_emptyFile_throwsAniException() {
        assertThrows(AniException.class, () -> {
            EstimateCommand estimateCommand = estimateParser.parse("script.txt");
            estimateCommand.execute(animeData, emptySM, user);
        });
    }
}
