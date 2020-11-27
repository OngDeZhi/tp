package anichan.human;

import anichan.exception.AniException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Test
    void testUserCreation() throws AniException {
        User user = new User("Jessica Williams", "feMale");

        assertEquals(" Name: Jessica Williams-chan | Gender: Female", user.toString());
    }

    @Test
    void testCreateUser_emptyName_throwsAniException() {
        assertThrows(AniException.class, () -> new User("", "Male"));
        assertThrows(AniException.class, () -> new User(" ", "Male"));
        assertThrows(AniException.class, () -> new User("   ", "Male"));
        assertThrows(AniException.class, () -> new User("                   ", "female"));
    }

    @Test
    void testCreateUser_illegalCharacter_throwsAniException() {
        assertThrows(AniException.class, () -> new User(".", "Male"));
        assertThrows(AniException.class, () -> new User(". ", "Male"));
        assertThrows(AniException.class, () -> new User(" /", "Male"));
        assertThrows(AniException.class, () -> new User("/", "female"));
        assertThrows(AniException.class, () -> new User("/   ", "female"));
        assertThrows(AniException.class, () -> new User("^      ", "female"));
        assertThrows(AniException.class, () -> new User("(/)   ", "female"));
        assertThrows(AniException.class, () -> new User("abc(/)  def ", "female"));
        assertThrows(AniException.class, () -> new User("abc.  def ", "female"));
        assertThrows(AniException.class, () -> new User("abc.def", "female"));
        assertThrows(AniException.class, () -> new User("abc_def", "female"));
        assertThrows(AniException.class, () -> new User("Cake/", "female"));
        assertThrows(AniException.class, () -> new User("/Cheese/", "female"));
        assertThrows(AniException.class, () -> new User("/Yogurt", "female"));
        assertThrows(AniException.class, () -> new User("Chocolate Caramel Cluster   '", "female"));
    }

    @Test
    void testCreateUser_longName_throwsAniException() {
        assertThrows(AniException.class, () -> new User("Chocolate Fudge Brownie Strawberry e"
                + "Strawberry cheesecake ice cream with strawberries and a graham cracker swirl. If you can think of a"
                + "more strawberrily perfect flavour combination, let us know, because we think this is tough to beat."
                + "Think of your favourite creamy cheesecake, topped with sweet fruit (but not too sweet), backed by a"
                + "light crust. Now, that’s a heavenly dessert. And guess what? For strawberry cheesecake lovers "
                + "who’ve always wanted to have their cheesecake and scoop it, too, we’ve created a ", "Male"));
    }

    @Test
    void changeName_TommytoJohnny_Johnny() throws AniException {
        User user = new User("Tommy", "Male");
        user.setName("Johnny");

        assertEquals("Johnny", user.getName());
    }

    @Test
    void setGender_Female_returnFemale() throws AniException {
        User user = new User("A changeGender", "MaLE");

        // Notice how case is not sensitive
        user.setGender("FEMAle");

        assertEquals("Female", user.getGender().toString());
    }

    @Test
    void setGender_Monster_throwsAniException() throws AniException {
        User user = new User("Monster", "MaLE");

        assertThrows(AniException.class, () -> user.setGender("Monster"));
    }

    @Test
    void getHonorificName_testGender_nameWithHonorific() throws AniException {
        User maleUser = new User("Robinson", "MaLE");
        assertEquals("Robinson-san", maleUser.getHonorificName());

        User femaleUser = new User("Bella", "Female");
        assertEquals("Bella-chan", femaleUser.getHonorificName());

        User otherUser = new User("Thomas", "Other");
        assertEquals("Thomas-san", otherUser.getHonorificName());
    }

    @Test
    void getTotalWorkspace_noWorkspace_sizeZero() throws AniException {
        User user = new User("User getTotalWorkspace", "Male");
        assertEquals(0, user.getTotalWorkspaces());
    }

    @Test
    void getTotalWorkspace_twoWorkspaces() throws AniException {
        User user = new User("A test workspace", "Male");

        user.addWorkspace("Test Workspace");
        user.addWorkspace("Test Workspace 2");

        assertEquals(2, user.getTotalWorkspaces());
    }

    @Test
    void setWorkspace_emptyWorkspaceList_setEmpty() throws AniException {
        User user = new User("new User", "femaLe");

        ArrayList<Workspace> workspaceList = new ArrayList<>();

        assertDoesNotThrow(() -> user.setWorkspaceList(workspaceList));
    }

    @Test
    void setWorkspace_filledWorkspaceList_useFirst() throws AniException {
        User user = new User("new User", "femaLe");

        ArrayList<Workspace> workspaceList = new ArrayList<>();
        Workspace newWorkspace = user.addWorkspace("Default");

        workspaceList.add(newWorkspace);
        assertDoesNotThrow(() -> user.setWorkspaceList(workspaceList));
    }

    @Test
    void setActiveWorkspace_empty_useFirst() throws AniException {
        User user = new User("new User", "femaLe");
        Workspace newWorkspace = user.addWorkspace("Default");

        assertThrows(AniException.class, () -> user.setActiveWorkspace(newWorkspace));
    }

    @Test
    void switchActiveWorkspace_emptyWorkspaceList_throwsAniException() throws AniException {
        User user = new User("new User", "femaLe");

        assertThrows(AniException.class, () -> user.switchActiveWorkspace("I want to switch!"));
    }

    @Test
    void addWorkspace_nameExist_throwsAniException() throws AniException {
        User user = new User("new User", "femaLe");
        user.addWorkspace("Default");

        assertThrows(AniException.class, () -> user.addWorkspace("Default"));
    }

    @Test
    void addWorkspace_similarNameExist_throwsAniException() throws AniException {
        User user = new User("new User", "femaLe");
        user.addWorkspace("Default");

        assertThrows(AniException.class, () -> user.addWorkspace("default"));
    }

    @Test
    void deleteWorkspace_invalidNames_throwsAniException() throws AniException {
        User user = new User("new User", "femaLe");
        user.addWorkspace("Default");

        assertThrows(AniException.class, () -> {
            user.deleteWorkspace("");
            user.deleteWorkspace(" ");
            user.deleteWorkspace(" -l");
            user.deleteWorkspace("-99");
            user.deleteWorkspace("123");
            user.deleteWorkspace("abc");
            user.deleteWorkspace("abc123");
            user.deleteWorkspace("abc 123");
        });
    }


}
