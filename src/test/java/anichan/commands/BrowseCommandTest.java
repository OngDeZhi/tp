package anichan.commands;

import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.parser.BrowseParser;
import anichan.storage.StorageManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BrowseCommandTest {
    private static final String NORM_PAGE_NUM = "-p 1";
    private static final int RESET_SORT = 3;
    private static final int TWO_ANIME_PER_PAGE = 2;
    private User user;
    private static AnimeData animeData;
    private static StorageManager storageManager;

    private static final String NAME_SORT_ASC_2 = "-s name -o asc -p 2";
    private static final String NAME_SORT_DSC_3 = "-s name -o dsc -p 3";
    private static final String RATING_SORT_ASC_3 = "-s rating -o asc -p 3";
    private static final String RATING_SORT_DSC_3 = "-s rating -o dsc -p 3";
    private static final String LARGE_PAGE_NUM = "-p 9999";
    private static final String NEGATIVE_PAGE_NUM = "-p -1";
    private static final String ZERO_PAGE_NUM = "-p 0";
    private static final String LAST_PAGE = "-p 26";
    private static final String ID_SORT_DSC = "-o dsc";

    private static final int LARGE_NUM = 9999;
    private static final int ONE_ANIME_PER_PAGE = 1;
    private static final int NEGATIVE_NUM = -ONE_ANIME_PER_PAGE;
    private static final int ZERO_NUM = 0;

    private static final String EMPTY_STRING = "";
    private static final String FIRST_ANIME = "1.   Cowboy Bebop                                        [Id: 1  ]";
    private static final String FIRST_ANIME_2 = "501. Wind: A Breath of Heart OVA";
    private static final String OUTPUT_PAGE_1 = "Browsing Page: 1";
    private static final String OUTPUT_PAGE_2 = "Browsing Page: 2";
    private static final String OUTPUT_PAGE_3 = "Browsing Page: 3";
    private static final String LAST_ANIME = "1.   Major S2                                            [Id: 511]";
    private static final String ASC_ANIME =  "21.  Akane Maniax                                        [Id: 250]";
    private static final String DSC_ANIME = "41.  Trinity Blood                                       [Id: 18 ]";
    private static final String ASC_RATING = "41.  Psychic Academy                                     [Id: 304]";
    private static final String DSC_RATING = "41.  Beck: Mongolian Chop Squad                          [Id: 39 ]";
    private static final String LONG_RESULT = "1.   Cowboy Bebop                                        [Id: 1  ]"
            + System.lineSeparator() + "2.   Cowboy Bebop: The Movie - Knockin' on Heaven's D... [Id: 2  ]";

    @BeforeAll
    static void setUp() throws AniException {
        animeData = new AnimeData();
        storageManager = new StorageManager(EMPTY_STRING);
    }

    @Test
    void execute_printLongSeries_correctlyFormattedOutput() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(NORM_PAGE_NUM);
        testBrowse.setAnimePerPage(TWO_ANIME_PER_PAGE);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(LONG_RESULT + System.lineSeparator() + OUTPUT_PAGE_1, result);
    }

    @Test
    void execute_printLastSeries_correctOutput() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(LAST_PAGE);
        String result = testBrowse.execute(animeData, storageManager, user);
        //Not able to prematurely halt the browse print, as the full browse output needs to be obtained here.
        result = result.substring(0,32);
        assertEquals(FIRST_ANIME_2, result);
    }

    @Test
    void execute_assertTest_assertionThrow() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(EMPTY_STRING);
        testBrowse.setSortType(LARGE_NUM);
        assertThrows(AssertionError.class, () -> {
            testBrowse.execute(animeData, storageManager, user);
        });

        testBrowse.setSortType(ZERO_NUM);
        testBrowse.setOrder(LARGE_NUM);
        testBrowse.setSortType(NEGATIVE_NUM);
        assertThrows(AssertionError.class, () -> {
            testBrowse.execute(animeData, storageManager, user);
        });
    }

    @Test
    void execute_browseWithNoParam_correctOutput() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(EMPTY_STRING);
        testBrowse.setAnimePerPage(ONE_ANIME_PER_PAGE);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(FIRST_ANIME + System.lineSeparator() + OUTPUT_PAGE_1, result);
    }

    @Test
    void execute_browseByDscOnly_correctOutput() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(ID_SORT_DSC);
        testBrowse.setAnimePerPage(ONE_ANIME_PER_PAGE);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(LAST_ANIME + System.lineSeparator() + OUTPUT_PAGE_1, result);
    }

    @Test
    void execute_browseByNameAsc_correctOutput() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(NAME_SORT_ASC_2);
        testBrowse.setAnimePerPage(ONE_ANIME_PER_PAGE);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(ASC_ANIME + System.lineSeparator() + OUTPUT_PAGE_2, result);
    }

    @Test
    void execute_browseByNameDsc_correctOutput() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(NAME_SORT_DSC_3);
        testBrowse.setAnimePerPage(ONE_ANIME_PER_PAGE);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(DSC_ANIME + System.lineSeparator() + OUTPUT_PAGE_3, result);
    }

    @Test
    void execute_browseByRatingAsc_correctOutput() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(RATING_SORT_ASC_3);
        testBrowse.setAnimePerPage(ONE_ANIME_PER_PAGE);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(ASC_RATING + System.lineSeparator() + OUTPUT_PAGE_3, result);
    }

    @Test
    void execute_browseByRatingDsc_correctOutput() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(RATING_SORT_DSC_3);
        testBrowse.setAnimePerPage(ONE_ANIME_PER_PAGE);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(DSC_RATING + System.lineSeparator() + OUTPUT_PAGE_3, result);
    }

    @Test
    void execute_resetSort_correctOutput() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(EMPTY_STRING);
        testBrowse.setAnimePerPage(ONE_ANIME_PER_PAGE);
        testBrowse.setSortType(RESET_SORT);
        String result = testBrowse.execute(animeData, storageManager, user);
        assertEquals(FIRST_ANIME + System.lineSeparator() + OUTPUT_PAGE_1, result);
    }


    @Test
    void execute_invalidPageNum_throwsAniException() throws AniException {
        BrowseParser testParse = new BrowseParser();
        BrowseCommand testBrowse = testParse.parse(LARGE_PAGE_NUM);
        assertThrows(AniException.class, () -> {
            testBrowse.execute(animeData, storageManager, user);
        });

        assertThrows(AniException.class, () -> {
            testParse.parse(NEGATIVE_PAGE_NUM);
        });

        BrowseParser testParse2 = new BrowseParser();
        assertThrows(AniException.class, () -> {
            testParse2.parse(ZERO_PAGE_NUM);
        });
    }
}
