package anichan.parser;

import org.junit.jupiter.api.Test;

import anichan.exception.AniException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WorkspaceParserTest {

    @Test
    void parse_invalidParameterSimple_throwsAniException() {
        WorkspaceParser testParse = new WorkspaceParser();

        assertThrows(AniException.class, () -> testParse.parse(""));
        assertThrows(AniException.class, () -> testParse.parse(" "));
        assertThrows(AniException.class, () -> testParse.parse(" -"));
        assertThrows(AniException.class, () -> testParse.parse(" -x"));
        assertThrows(AniException.class, () -> testParse.parse(" -n"));
        assertThrows(AniException.class, () -> testParse.parse(" - "));
        assertThrows(AniException.class, () -> testParse.parse(" -   ["));
        assertThrows(AniException.class, () -> testParse.parse(" ` 1"));
        assertThrows(AniException.class, () -> testParse.parse(" /"));
        assertThrows(AniException.class, () -> testParse.parse(" ./data"));
        assertThrows(AniException.class, () -> testParse.parse(" \\data"));
        assertThrows(AniException.class, () -> testParse.parse("\\data"));
        assertThrows(AniException.class, () -> testParse.parse(" .\\hello"));
        assertThrows(AniException.class, () -> testParse.parse("-100"));
        assertThrows(AniException.class, () -> testParse.parse(" - 100"));
        assertThrows(AniException.class, () -> testParse.parse("  -100"));
        assertThrows(AniException.class, () -> testParse.parse("%"));
        assertThrows(AniException.class, () -> testParse.parse("0"));
        assertThrows(AniException.class, () -> testParse.parse("%data"));
        assertThrows(AniException.class, () -> testParse.parse("888 -n"));
        assertThrows(AniException.class, () -> testParse.parse("888 -n "));
        assertThrows(AniException.class, () -> testParse.parse("888 -n ."));
    }

    @Test
    void parser_legitimateName_success() {
        WorkspaceParser testParse = new WorkspaceParser();

        assertDoesNotThrow(() -> testParse.parse("-n Crunchy"));
        assertDoesNotThrow(() -> testParse.parse("-n Crunchy 1234"));
    }

    @Test
    void parser_legitimateNameComplex_success() {
        WorkspaceParser testParse = new WorkspaceParser();

        assertDoesNotThrow(() -> testParse.parse("-n Crunchy OREO c00ki3s"));
        assertDoesNotThrow(() -> testParse.parse("-n CruNc6y  OREO   c00k155s"));
    }

    @Test
    void parser_illegalNameComplex_throwsAniException() {
        WorkspaceParser testParse = new WorkspaceParser();

        assertThrows(AniException.class, () -> testParse.parse("-n ."));
        assertThrows(AniException.class, () -> testParse.parse("-n /"));
        assertThrows(AniException.class, () -> testParse.parse("-n .hello"));
        assertThrows(AniException.class, () -> testParse.parse("-n /hello_world"));
        assertThrows(AniException.class, () -> testParse.parse("-n /test"));
        assertThrows(AniException.class, () -> testParse.parse("-n //test"));
        assertThrows(AniException.class, () -> testParse.parse("-n [test"));
        assertThrows(AniException.class, () -> testParse.parse("-n [[;test"));
        assertThrows(AniException.class, () -> testParse.parse("-n     _     test"));
        assertThrows(AniException.class, () -> testParse.parse("-n /more/tests"));
        assertThrows(AniException.class, () -> testParse.parse("-n !"));
        assertThrows(AniException.class, () -> testParse.parse("-n how about this?"));
        assertThrows(AniException.class, () -> testParse.parse("-n and this%"));
        assertThrows(AniException.class, () -> testParse.parse("-n ["));
        assertThrows(AniException.class, () -> testParse.parse("-n ;"));

        assertThrows(AniException.class, () -> testParse.parse("./abc"));
        assertThrows(AniException.class, () -> testParse.parse("./      "));
        assertThrows(AniException.class, () -> testParse.parse(".         "));
        assertThrows(AniException.class, () -> testParse.parse("       .         "));
        assertThrows(AniException.class, () -> testParse.parse("       ."));
        assertThrows(AniException.class, () -> testParse.parse(".         "));
        assertThrows(AniException.class, () -> testParse.parse(" -abcdefg12345!@#$%^*&(#)$%| "));
        assertThrows(AniException.class, () -> testParse.parse(" -h hello -m world -t confusion "));
    }

    @Test
    void parser_illegalLongName_throwsAniException() {
        WorkspaceParser testParse = new WorkspaceParser();

        // Test if name can be longer than 30 characters
        assertThrows(AniException.class, () -> testParse.parse("-n 45fx1JDGmVQfxLF0nnLAF5AUon1HOjS"));
        assertThrows(AniException.class, () -> testParse.parse("-n Wj1dHkUETeRU11EgJkb423bLNXsFDtR0X6sTnESc"));
        assertThrows(AniException.class, () -> testParse.parse("-n Wj1dHkUETeRU1"
                + "1EgJkb423bLNXsFDtWj1dHkUETeRU11EgJkb423bLNXsFDtR0X6sTnEScR0X6sTnESc"));

        // Test if 30 or less than
        assertDoesNotThrow(() -> testParse.parse("-n 45fx1JDGmVQfxLF0nnLAF5AUon1HOj"));
        assertDoesNotThrow(() -> testParse.parse("-n WezZJBpRg0"));
        assertDoesNotThrow(() -> testParse.parse("-n a"));
    }
}
