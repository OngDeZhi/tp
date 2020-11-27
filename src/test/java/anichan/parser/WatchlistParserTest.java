package anichan.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import anichan.exception.AniException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author OngDeZhi
class WatchlistParserTest {
    private WatchlistParser watchlistParser;

    @BeforeEach
    void setUp() {
        watchlistParser = new WatchlistParser();
    }

    @Test
    void parse_validParameters_success() {
        assertDoesNotThrow(() -> {
            watchlistParser.parse("-n correct");
        });

        assertDoesNotThrow(() -> {
            watchlistParser.parse("-l");
        });

        assertDoesNotThrow(() -> {
            watchlistParser.parse("-s 1");
        });

        assertDoesNotThrow(() -> {
            watchlistParser.parse("-d 1");
        });

        assertDoesNotThrow(() -> {
            watchlistParser.parse("        -l        ");
        });
    }

    @Test
    void parse_noParameter_throwsAniException() {
        assertThrows(AniException.class, () -> watchlistParser.parse(""));
    }

    @Test
    void parse_invalidParameter_throwsAniException() {
        assertThrows(AniException.class, () -> watchlistParser.parse("-invalid"));
        assertThrows(AniException.class, () -> watchlistParser.parse("-nnewWatchlistName"));
        assertThrows(AniException.class, () -> watchlistParser.parse("- n newWatchlistName"));
    }

    @Test
    void parse_tooManyParameter_throwsAniException() {
        assertThrows(AniException.class, () -> watchlistParser.parse("-n one -n two"));
    }

    @Test
    void parse_fieldBeforeParameter_throwsAniException() {
        assertThrows(AniException.class, () -> watchlistParser.parse("hello -n testing"));
    }

    @Test
    void parse_emptyParameterValue_throwsAniException() {
        assertThrows(AniException.class, () -> watchlistParser.parse("-n"));
        assertThrows(AniException.class, () -> watchlistParser.parse("-s"));
        assertThrows(AniException.class, () -> watchlistParser.parse("-d"));
    }

    @Test
    void parse_tooManyFieldsForList_throwsAniException() {
        assertThrows(AniException.class, () -> watchlistParser.parse("-l a"));
    }

    @Test
    void parse_invalidWatchlistName_throwsAniException() {
        assertThrows(AniException.class, () -> watchlistParser.parse("-n                 "));
        assertThrows(AniException.class, () -> watchlistParser.parse("-n alongwatchlistnamethatwouldfail"));
        assertThrows(AniException.class, () -> watchlistParser.parse("-n *-*"));
    }

    @Test
    void parse_invalidWatchlistIndex_throwsAniException() {
        assertThrows(AniException.class, () -> watchlistParser.parse("-s 0"));
        assertThrows(AniException.class, () -> watchlistParser.parse("-d -1"));
        assertThrows(AniException.class, () -> watchlistParser.parse("-s one"));
        assertThrows(AniException.class, () -> watchlistParser.parse("-d 1 2 3"));
    }
}
