package anichan.parser;

import anichan.exception.AniException;

import java.math.BigInteger;

/**
 * Represents an abstract class which each command parser will inherit from.
 */
public abstract class CommandParser {
    //Shared Constants by Parsers
    protected static final String NAME_PARAM = "n";
    protected static final String GENRE_PARAM = "g";
    protected static final String DASH = "-";
    protected static final String WHITESPACE = " ";
    protected static final String INVALID_OPTION = " is not a valid option";
    protected static final String NOT_RECOGNISED = " is not recognised!";
    protected static final String PARAMETER_ERROR_HEADER = "Parameter : -";
    protected static final String REQUIRE_ADDITIONAL_FIELD = " requires an additional field.";
    protected static final String TOO_MUCH_FIELDS = " has too much fields.";
    protected static final String TOO_MUCH_PARAMETERS = " has too much parameters.";
    protected static final String NO_PARAMETER_PROVIDED = "No parameter provided.";
    protected static final String DESCRIPTION_CANNOT_BE_NULL = "description should not be null.";
    protected static final String NOT_INTEGER = "Please provide a integer instead!";
    protected static final String NOT_POSITIVE_INTEGER = "Please provide a positive integer instead!";
    protected static final String INTEGER_VALUE_OUTSIDE_OF_INTEGER_RANGE = "Please ensure the integer is not larger"
                                                                            + " than " + Integer.MAX_VALUE + ".";
    protected static final String DESCRIPTION_NOT_REQUIRED = "Command does not require additional parameters!";
    protected static final String REGEX_ALPHANUMERIC_WITH_SPACE = "^[a-zA-Z0-9\\s]*$";
    protected static final String REGEX_PARAMETER = "^.*-[a-zA-z]+.*$";
    protected static final String NON_INTEGER_PROVIDED = "Please specify an integer value for ";
    protected static final String PARAMETER_PARSED = "Parameter parsed properly";
    protected static final int FIELD_SPLIT_LIMIT = 2;

    private static final String REGEX_POSITIVE_INTEGER = "^\\d+$";
    private static final String REGEX_NEGATIVE_INTEGER = "^[-]\\d+$";

    /**
     * Splits the parameters into individual parts for parsing.
     *
     * @param parameter unprocessed parameter strings
     * @return parameters in parts
     */
    protected String[] parameterSplitter(String parameter) {
        return parameter.split(DASH);
    }

    /**
     * Checks if there is a parameter set.
     *
     * @param paramParts Array of parameters
     * @throws AniException if there is no parameter provided
     */

    protected void paramIsSetCheck(String[] paramParts) throws AniException {
        if (paramParts.length < 2) {
            throw new AniException(NO_PARAMETER_PROVIDED);
        }
    }

    /**
     * Checks if parameter has a single additional field or not.
     *
     * @param paramParts the param to check
     * @throws AniException if the parameter is missing the additional field
     */
    protected void paramFieldCheck(String[] paramParts) throws AniException {
        String invalidParameter = PARAMETER_ERROR_HEADER + paramParts[0] + REQUIRE_ADDITIONAL_FIELD;
        if (paramParts.length < 2) {
            throw new AniException(invalidParameter);
        }
        if (paramParts[1].trim().isEmpty()) {
            throw new AniException(invalidParameter);
        }
    }

    /**
     * Checks if parameter has more than one field.
     *
     * @param paramParts the param to check
     * @throws AniException if the parameter has too many fields
     */
    protected void paramExtraFieldCheck(String[] paramParts) throws AniException {
        if (paramParts.length > 2) {
            String invalidParameter = PARAMETER_ERROR_HEADER + paramParts[0] + TOO_MUCH_FIELDS;
            throw new AniException(invalidParameter);
        }
    }

    /**
     * Checks if the string is an integer.
     *
     * @param checkStr the string to check
     * @return true if it can be parsed into an integer
     */
    protected boolean isInteger(String checkStr) {
        return checkStr.matches(REGEX_POSITIVE_INTEGER);
    }

    /**
     * Check if the string is a negative integer.
     *
     * @param checkStr the string to check
     * @return {@code true} if it is a negative integer; {@code false} otherwise
     */
    protected boolean isNegativeInteger(String checkStr) {
        return checkStr.matches(REGEX_NEGATIVE_INTEGER);
    }

    //@@author OngDeZhi
    /**
     * Parses the string argument as a signed integer. It also checks if the {@code integer} is within
     * the range of java.lang.Integer.
     *
     * @param stringInteger {@code String} argument to be parsed to {@code integer}
     * @return the {@code integer} that was parsed successfully
     * @throws AniException when an error occurred while parsing the string to integer
     */
    protected int parseStringToInteger(String stringInteger) throws AniException {
        try {
            return Integer.parseInt(stringInteger);
        } catch (NumberFormatException exception) {
            try {
                new BigInteger(stringInteger);
            } catch (NumberFormatException sameException) {
                throw new AniException(NOT_INTEGER);
            }

            // Thrown when stringInteger represents a valid integer that is outside
            // of java.lang.Integer range (overflow).
            throw new AniException(INTEGER_VALUE_OUTSIDE_OF_INTEGER_RANGE);
        }
    }

    /**
     * Checks if the string is an integer, and throws an exception if not.
     * 
     * @param paramPart a string containing the field value
     * @param id a string indicating which ID it should use
     * @throws AniException when an error occurred while parsing the string to integer
     */
    protected void isIntegerCheck(String paramPart, String id) throws AniException {
        if (!isInteger(paramPart)) {
            throw new AniException(NON_INTEGER_PROVIDED + id);
        }
    }
}
