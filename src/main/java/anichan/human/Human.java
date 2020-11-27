package anichan.human;

import anichan.exception.AniException;

/**
 * Abstract class to represent a Human.
 */
public abstract class Human {
    private static final int MAX_NAME_LENGTH = 200;
    private static final String EXCEPTION_INVALID_NAME = "Invalid name!";
    private static final String REGEX_ALPHANUMERIC_WITH_SPACE = "^[a-zA-Z0-9\\s]*$";
    protected String name;

    /**
     * Creates an instance of a Human object.
     *
     * @param name of the Human
     * @throws AniException if name is empty, beyond max length, or contain non alphanumerical characters
     */
    public Human(String name) throws AniException {
        checkName(name);
        setName(name);
    }

    /**
     * Checks if name is legal.
     *
     * @param name of the Human
     * @throws AniException if name is empty, beyond max length, or contain non alphanumerical characters
     */
    private void checkName(String name) throws AniException {
        if (name.isEmpty() || name.isBlank()) {
            throw new AniException(EXCEPTION_INVALID_NAME);
        } else if (name.length() > MAX_NAME_LENGTH) {
            throw new AniException(EXCEPTION_INVALID_NAME);
        } else if (!name.matches(REGEX_ALPHANUMERIC_WITH_SPACE)) {
            throw new AniException(EXCEPTION_INVALID_NAME);
        }
    }

    /**
     * Returns name of the Human.
     *
     * @return name of Human
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of the Human.
     *
     * @param name of Human
     */
    public void setName(String name) {
        this.name = name;
    }

}
