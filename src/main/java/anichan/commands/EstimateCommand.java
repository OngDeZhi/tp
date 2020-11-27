package anichan.commands;

import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.human.Workspace;
import anichan.logger.AniLogger;
import anichan.storage.StorageManager;

import java.util.logging.Level;
import java.util.logging.Logger;

//@@author OngDeZhi
/**
 * Represents the command to estimate the time needed to translate a script.
 */
public class EstimateCommand extends Command {
    private static final int DEFAULT_WORDS_PER_HOUR = -1;
    private static final int MINUTES_PER_HOUR = 60;
    private static final String SPLIT_WHITESPACE = " ";

    // The values 400, 500, and 600 refers to the amount of words an average translator
    // can translates in an hour.
    private static final int[] AVERAGE_TRANSLATOR_WORDS_PER_HOUR = {400, 500, 600};
    private static final Logger LOGGER = AniLogger.getAniLogger(EstimateCommand.class.getName());

    private static final String INVALID_WORDS_PER_HOUR = "Words per hour value cannot be zero!";

    private final String scriptFileName;
    private final int wordsPerHour;

    /**
     * Creates a new instance of EstimateCommand with the specified script file name and words per hour.
     *
     * @param scriptFileName specified script file name
     * @param wordsPerHour specified words per hour
     */
    public EstimateCommand(String scriptFileName, int wordsPerHour) {
        this.scriptFileName = scriptFileName;
        this.wordsPerHour = wordsPerHour;
        LOGGER.log(Level.INFO, "EstimateCommand object is created.");
    }

    /**
     * Depending on whether the optional parameter, words per hour (wph) is specified.
     * <ul>
     *     <li>Specified: Calculates using the value.</li>
     *     <li>Not specified: Calculates using the values defined in {@link #AVERAGE_TRANSLATOR_WORDS_PER_HOUR}.</li>
     * </ul>
     *
     * @param animeData used to retrieve anime information
     * @param storageManager used to save or read AniChan data
     * @param user used to modify user data
     * @return estimated timing generated after executing the command
     * @throws AniException when an error occurred while executing the command
     */
    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        Workspace activeWorkspace = user.getActiveWorkspace();
        String fileContent = storageManager.loadScript(activeWorkspace.getName(), scriptFileName);
        int wordCount = fileContent.split(SPLIT_WHITESPACE).length;
        LOGGER.log(Level.INFO, wordCount + " words in the script (" + scriptFileName + ").");

        assert (wordsPerHour > 0 || wordsPerHour == DEFAULT_WORDS_PER_HOUR) : INVALID_WORDS_PER_HOUR;

        StringBuilder commandResult = new StringBuilder();
        if (wordsPerHour != DEFAULT_WORDS_PER_HOUR) {
            double timeNeeded = wordCount / (double) wordsPerHour;
            commandResult.append("You would need ");
            commandResult.append(timeNeededToString(timeNeeded));
        } else {
            for (int averageWordsPerHour : AVERAGE_TRANSLATOR_WORDS_PER_HOUR) {
                double timeNeeded = wordCount / (double) averageWordsPerHour;
                commandResult.append("Average translator (");
                commandResult.append(averageWordsPerHour);
                commandResult.append(" words per hour) takes: ");
                commandResult.append(timeNeededToString(timeNeeded));
                commandResult.append(System.lineSeparator());
            }

            // Remove extra new line.
            commandResult.setLength(commandResult.length() - System.lineSeparator().length());
        }

        LOGGER.log(Level.INFO, "Returning estimate result for " + scriptFileName
                                    + " with " + wordsPerHour + "wph.");
        return commandResult.toString();
    }

    /**
     * Converts the estimated timing to a human-readable format.
     *
     * @param timeNeeded the estimated timing
     * @return a human-readable format of the estimated timing
     */
    private String timeNeededToString(double timeNeeded) {
        double hoursNeeded = Math.floor(timeNeeded);
        double minutesNeeded = (timeNeeded - hoursNeeded) * MINUTES_PER_HOUR;

        String hoursAndMinutesNeeded = (int) hoursNeeded + " hour(s)";
        if ((int)minutesNeeded != 0) {
            hoursAndMinutesNeeded += " " + (int) minutesNeeded + " minute(s).";
        } else {
            hoursAndMinutesNeeded += ".";
        }

        LOGGER.log(Level.INFO, "Converted " + timeNeeded + " to: " + hoursAndMinutesNeeded);
        return hoursAndMinutesNeeded;
    }
}
