package anichan.anime;

import anichan.exception.AniException;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnimeTest {
    private static final int LARGE_RATING = 9999;
    private static final int ZERO_RATING = 0;
    private static final int NEGATIVE_RATING = -1;
    private static final String TEST_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TO_MATCH = "2020-05-01";
    private static final String[] INVALID_DATE = {"Not", "Valid", "Date"};
    private static final String[] VALID_DATE = {"2020", "5", "1"};

    @Test
    void setReleaseDate_singleDigits_usableDateFormat() throws java.text.ParseException, AniException {
        Anime testAnime = new Anime();
        String[] inputDate = VALID_DATE;
        testAnime.setReleaseDate(inputDate);
        SimpleDateFormat stringToDate = new SimpleDateFormat(TEST_DATE_FORMAT);
        Date testDate = stringToDate.parse(DATE_TO_MATCH);
        assertEquals(testAnime.getReleaseDate(), testDate);
    }

    @Test
    void setRating_greaterThan100_ratingWillBeZero() {
        Anime testAnime = new Anime();
        testAnime.setRating(LARGE_RATING);
        assertEquals(testAnime.getRating(), ZERO_RATING);
    }

    @Test
    void setRating_negativeNumbers_ratingWillBeZero() {
        Anime testAnime = new Anime();
        testAnime.setRating(NEGATIVE_RATING);
        assertEquals(testAnime.getRating(), ZERO_RATING);
    }

    @Test
    void setReleaseDate_invalidDate_throwAniException() {
        Anime testAnime = new Anime();
        String[] invalidDate = INVALID_DATE;
        assertThrows(AniException.class, () -> {
            testAnime.setReleaseDate(invalidDate);
        });
    }
}
