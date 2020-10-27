package anichan.parser;

import anichan.command.BookmarkCommand;
import org.junit.jupiter.api.Test;
import anichan.exception.AniException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookmarkParserTest {
    private static final String INVALID_FIRST_PARAMETERS_ADD_TEST1 = "1 -a 123";
    private static final String INVALID_FIRST_PARAMETERS_ADD_TEST2 = "a -a 123";
    private static final String INVALID_FIRST_PARAMETERS_DELETE_TEST1 = "1 -d 123";
    private static final String INVALID_FIRST_PARAMETERS_DELETE_TEST2 = "a -d 123";
    private static final String INVALID_FIELD_ADD_TEST1 = "-a   ";
    private static final String INVALID_FIELD_ADD_TEST2 = "-a beepboopbeep";
    private static final String INVALID_FIELD_ADD_TEST3 = "-a 123 123";
    private static final String INVALID_FIELD_DELETE_TEST1 = "-d   ";
    private static final String INVALID_FIELD_DELETE_TEST2 = "-d beepboopbeep";
    private static final String INVALID_FIELD_DELETE_TEST3 = "-d 123 123";
    private static final String INVALID_FIELD_EDIT_TEST1 = "1 -e   ";
    private static final String INVALID_FIELD_EDIT_TEST2 = "1 -e beepboopbeep";
    private static final String INVALID_FIELD_EDIT_TEST3 = "1 -e 123 123";
    private static final String INVALID_FIELD_EDIT_TEST4 = "a -e 1";
    private static final String NEGATIVE_BOOKMARKID_DELETE_TEST = "-d -1";
    private static final String NEGATIVE_ANIMEID_ADD_TEST = "-a -3";
    private static final String NEGATIVE_BOOKMARKID_EDIT_TEST = "-1 -e 1";
    private static final String INVALID_FIELD_NOTE_TEST1 = "1 -n  ";
    private static final String INVALID_FIELD_NOTE_TEST2 = "1 -n  -afds";
    private static final String INVALID_FIELD_LIST = "-l test";
    private static final String INVALID_SINGLE_INPUT_TEST = "a";
    private static final String UNKNOWN_PARAMETER = "-h";

    private static final String VALID_SINGLE_INPUT_TEST = "1";
    private static final String VALID_SINGLE_INPUT_TEST2 = "10";
    private static final String VALID_ADD_TEST = "-a 1";
    private static final String VALID_DELETE_TEST = "-d 1";
    private static final String VALID_NOTE_TEST = "1 -n test";
    private static final String VALID_LIST_TEST = "-l";
    private static final String VALID_EDIT_TEST = "1 -e 1";

    //==================== Valid Input Test ===========================

    @Test
    void execute_validSingleParameter_Successful() throws AniException {
        BookmarkParser testInfo1 = new BookmarkParser();
        BookmarkCommand testCommand = testInfo1.parse(VALID_SINGLE_INPUT_TEST);
        BookmarkParser testInfo2 = new BookmarkParser();
        BookmarkCommand testCommand2 = testInfo2.parse(VALID_SINGLE_INPUT_TEST2);
        assertEquals(testCommand.getBookmarkAction(), "i");
        assertEquals(testCommand2.getBookmarkAction(), "i");
    }

    @Test
    void execute_validFieldForNote_Successful() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        BookmarkCommand testCommand = test1.parse(VALID_NOTE_TEST);
        assertEquals(testCommand.getBookmarkAction(), "n");
    }

    @Test
    void execute_validList_Successful() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        BookmarkCommand testCommand = test1.parse(VALID_LIST_TEST);
        assertEquals(testCommand.getBookmarkAction(), "l");
    }

    @Test
    void execute_validEdit_Successful() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        BookmarkCommand testCommand = test1.parse(VALID_EDIT_TEST);
        assertEquals(testCommand.getBookmarkAction(), "e");
    }

    @Test
    void execute_validAdd_Successful() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        BookmarkCommand testCommand = test1.parse(VALID_ADD_TEST);
        assertEquals(testCommand.getBookmarkAction(), "a");
    }

    @Test
    void execute_validDelete_Successful() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        BookmarkCommand testCommand = test1.parse(VALID_DELETE_TEST);
        assertEquals(testCommand.getBookmarkAction(), "d");
    }

    //==================== Invalid Input Test ===========================

    @Test
    void execute_invalidSingleParameter_ThrowsAniException() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(INVALID_SINGLE_INPUT_TEST);
        });
    }


    @Test
    void execute_unknownParameter_ThrowsAniException() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(UNKNOWN_PARAMETER);
        });
    }


    @Test
    void execute_invalidListParameter_ThrowsAniException() throws AniException {
        BookmarkParser testAdd1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            testAdd1.parse(INVALID_FIELD_LIST);
        });
    }

    @Test
    void execute_invalidFirstParameter_ThrowsAniException() throws AniException {
        BookmarkParser testAdd1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            testAdd1.parse(INVALID_FIRST_PARAMETERS_ADD_TEST1);
        });

        BookmarkParser testAdd2 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            testAdd2.parse(INVALID_FIRST_PARAMETERS_ADD_TEST2);
        });

        BookmarkParser testDelete1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            testDelete1.parse(INVALID_FIRST_PARAMETERS_DELETE_TEST1);
        });

        BookmarkParser testDelete2 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            testDelete2.parse(INVALID_FIRST_PARAMETERS_DELETE_TEST2);
        });
    }

    @Test
    void execute_invalidFieldForAdd_ThrowsAniException() {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(INVALID_FIELD_ADD_TEST1);
        });

        BookmarkParser test2 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test2.parse(INVALID_FIELD_ADD_TEST2);
        });

        BookmarkParser test3 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test3.parse(INVALID_FIELD_ADD_TEST3);
        });
    }

    @Test
    void execute_invalidFieldForDelete_ThrowsAniException() {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(INVALID_FIELD_DELETE_TEST1);
        });

        BookmarkParser test2 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test2.parse(INVALID_FIELD_DELETE_TEST2);
        });

        BookmarkParser test3 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test3.parse(INVALID_FIELD_DELETE_TEST3);
        });
    }

    @Test
    void execute_invalidFieldForEdit_ThrowsAniException() {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(INVALID_FIELD_EDIT_TEST1);
        });

        BookmarkParser test2 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test2.parse(INVALID_FIELD_EDIT_TEST2);
        });

        BookmarkParser test3 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test3.parse(INVALID_FIELD_EDIT_TEST3);
        });

        BookmarkParser test4 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test4.parse(INVALID_FIELD_EDIT_TEST4);
        });

    }

    @Test
    void execute_invalidFieldForNote_ThrowsAniException() {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(INVALID_FIELD_NOTE_TEST1);
        });

        BookmarkParser test2 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test2.parse(INVALID_FIELD_NOTE_TEST2);
        });
    }


    @Test
    void execute_negativeBookmarkIdForDelete_ThrowsAniException() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(NEGATIVE_BOOKMARKID_DELETE_TEST);
        });
    }

    @Test
    void execute_negativeBookmarkIdForEdit_ThrowsAniException() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(NEGATIVE_BOOKMARKID_EDIT_TEST);
        });
    }

    @Test
    void execute_negativeAnimeIdForAdd_ThrowsAniException() throws AniException {
        BookmarkParser test1 = new BookmarkParser();
        assertThrows(AniException.class, () -> {
            test1.parse(NEGATIVE_ANIMEID_ADD_TEST);
        });
    }
}
