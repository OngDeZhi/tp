package anichan.human;

import anichan.exception.AniException;
import static anichan.logger.AniLogger.getAniLogger;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the User.
 */
public class User extends Human {
    private static final String GENDER_MALE = "male";
    private static final String GENDER_FEMALE = "female";
    private static final String GENDER_OTHER = "other";
    private static final String HONORIFIC_FEMALE = "-chan";
    private static final String HONORIFIC_NEUTRAL = "-san";
    private static final Logger LOGGER = getAniLogger(User.class.getName());
    private static final String EXCEPTION_WORKPLACE_NOT_FOUND = "Workspace does not exist!";
    private static final String EXCEPTION_SIMILAR_WORKPLACE_FOUND = "Workspace with similar name found!";
    private static final String EXCEPTION_WORKSPACE_ALREADY_EXIST = "Workspace already exist!";
    private static final String ASSERTION_INVALID_MESSAGE = "Input invalid.";

    private Gender gender;
    private Workspace activeWorkspace;
    private ArrayList<Workspace> workspaceList = new ArrayList<>();

    // ========================== User related methods ==========================

    /**
     * Creates an instance of a User.
     *
     * @param name   name of User
     * @param gender gender of User
     * @throws AniException if an error occurred while creating User
     */
    public User(String name, String gender) throws AniException {
        super(name);

        setGender(gender);
        activeWorkspace = null;
    }

    /**
     * Sets gender of user using Gender enum.
     *
     * @param genderString gender input provided by user
     * @throws AniException if gender string is invalid
     */
    public void setGender(String genderString) throws AniException {
        assert genderString != null : ASSERTION_INVALID_MESSAGE;
        String genderStringLowered = genderString.toLowerCase(Locale.ROOT);

        switch (genderStringLowered) {
        case GENDER_MALE:
            gender = Gender.Male;
            break;
        case GENDER_FEMALE:
            gender = Gender.Female;
            break;
        case GENDER_OTHER:
            gender = Gender.Other;
            break;
        default:
            throw new AniException("Unexpected gender: " + genderString);
        }
    }

    /**
     * Returns gender of User.
     *
     * @return Gender of User.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Provides the name of the User with Japanese honorifics depending on gender.
     *
     * @return name of User with honorifics.
     */
    public String getHonorificName() {
        if (gender == Gender.Female) {
            return name + HONORIFIC_FEMALE;
        } else {
            return name + HONORIFIC_NEUTRAL;
        }
    }

    @Override
    public String toString() {
        return " Name: " + getHonorificName() + " | Gender: " + getGender();
    }

    // ========================== User's workspace related methods ==========================

    /**
     * Initialises the ArrayList of Workspace for User to manage.
     *
     * @param workspaceList Workspace ArrayList
     */
    public void setWorkspaceList(ArrayList<Workspace> workspaceList) {
        this.workspaceList = workspaceList;
        if (workspaceList.size() != 0) {
            activeWorkspace = workspaceList.get(0);
        }
    }

    /**
     * Gets Workspace ArrayList which the User manages.
     *
     * @return Workspace ArrayList
     */
    public ArrayList<Workspace> getWorkspaceList() {
        return workspaceList;
    }

    /**
     * Sets the active Workspace of user to the specified Workspace.
     *
     * @param inputWorkspace workspace to switch to
     * @throws AniException if unable to switch to current Workspace
     */
    public void setActiveWorkspace(Workspace inputWorkspace) throws AniException {
        assert inputWorkspace != null : ASSERTION_INVALID_MESSAGE;

        try {
            //Set the first watchlist to be the active watchlist
            inputWorkspace.setActiveWatchlist(inputWorkspace.getWatchlistList().get(0));
            LOGGER.log(Level.INFO, "Workspace switched: " + inputWorkspace.getName());

            activeWorkspace = inputWorkspace;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, EXCEPTION_WORKPLACE_NOT_FOUND);
            throw new AniException(EXCEPTION_WORKPLACE_NOT_FOUND);
        }
    }

    /**
     * Gets the current Workspace User is working on.
     *
     * @return active Workspace which the User is using
     */
    public Workspace getActiveWorkspace() {
        return activeWorkspace;
    }

    /**
     * Finds the workplace that matches the string parameter to switch to.
     *
     * @param switchToThisWorkspace the requested workplace to switch to
     * @throws AniException if the workplace is not found
     */
    public void switchActiveWorkspace(String switchToThisWorkspace) throws AniException {
        assert switchToThisWorkspace != null : ASSERTION_INVALID_MESSAGE;

        for (Workspace existingWorkspace : workspaceList) {
            if (existingWorkspace.getName().equals(switchToThisWorkspace)) {
                setActiveWorkspace(existingWorkspace);
                return;
            }
        }

        LOGGER.log(Level.WARNING, "Workspace " + switchToThisWorkspace + " does not exist!");
        throw new AniException("Workspace " + switchToThisWorkspace + " does not exist!");
    }

    /**
     * Gets total number of workspace(s) the User owns.
     *
     * @return size of Workspace(s) the User owns
     */
    public int getTotalWorkspaces() {
        return workspaceList.size();
    }

    /**
     * Adds a new Workspace to the User.
     *
     * @param name of the new Workspace
     * @return the newly created Workspace
     * @throws AniException if unable to make a new Workspace
     */
    public Workspace addWorkspace(String name) throws AniException {
        assert name != null : ASSERTION_INVALID_MESSAGE;

        if (findWorkspace(name) != null) {
            throw new AniException(EXCEPTION_WORKSPACE_ALREADY_EXIST);
        } else {
            checkWorkspaceName(name.toLowerCase());
            Workspace newWorkspace = new Workspace(name);

            workspaceList.add(newWorkspace);
            LOGGER.log(Level.INFO, "Workspace created: " + name);
            return newWorkspace;

        }
    }

    /**
     * Deletes an existing Workspace.
     *
     * @param toDeleteWorkspace name of Workspace to be deleted
     * @throws AniException if Workspace is unable to be deleted
     */
    public void deleteWorkspace(String toDeleteWorkspace) throws AniException {
        assert toDeleteWorkspace != null : ASSERTION_INVALID_MESSAGE;

        Workspace targetWorkspace = findWorkspace(toDeleteWorkspace);

        if (targetWorkspace != null) {
            workspaceList.remove(targetWorkspace);
        } else {
            LOGGER.log(Level.WARNING, EXCEPTION_WORKPLACE_NOT_FOUND);
            throw new AniException(EXCEPTION_WORKPLACE_NOT_FOUND);
        }
    }

    /**
     * Finds and return the Workspace if it exists.
     *
     * @param findString name of Workspace to search for
     * @return Workspace object is found, else null
     */
    private Workspace findWorkspace(String findString) {
        assert findString != null : ASSERTION_INVALID_MESSAGE;

        for (Workspace tempWorkspace : workspaceList) {
            if (tempWorkspace.getName().equals(findString)) {
                return tempWorkspace;
            }
        }

        return null;
    }

    /**
     * Checks if there exist a workspace with same name (regardless of case sensitivity).
     *
     * @param name of new workspace to be checked
     * @throws AniException if a workspace with same name is found
     */
    private void checkWorkspaceName(String name) throws AniException {
        assert name != null : ASSERTION_INVALID_MESSAGE;

        for (Workspace tempWorkspace : workspaceList) {
            if (tempWorkspace.getName().equalsIgnoreCase(name)) {
                throw new AniException(EXCEPTION_SIMILAR_WORKPLACE_FOUND);
            }
        }
    }

}
