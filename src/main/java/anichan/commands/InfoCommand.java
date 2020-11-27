package anichan.commands;

import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.logger.AniLogger;
import anichan.storage.StorageManager;

import java.util.logging.Level;
import java.util.logging.Logger;

//@@author michaeldinata
/**
 * Represents the command to view the information of an anime.
 */
public class InfoCommand extends Command {
    private static final String OUT_OF_BOUND_INDEX_ERROR = "Anime ID is invalid!";
    private static final String NULL_ANIME_INDEX = "Anime index should not be null";
    private static final String RESULT_BUILD_SUCCESSFUL = "Result built successfully";
    
    private Integer animeIndex;
    private static final Logger LOGGER = AniLogger.getAniLogger(InfoCommand.class.getName());

    /**
     * Creates a new instance of InfoCommand with the specified anime index.
     * 
     * @param animeIndex the specified anime index to show information
     */
    public InfoCommand(Integer animeIndex) {
        this.animeIndex = animeIndex - 1; // 1-based to 0-based numbering
    }
    
    /**
     * Builds and returns a string representation of the information of
     * the specified anime.
     *
     * @param animeData used to retrieve anime information
     * @param storageManager used to save or read AniChan data
     * @param user used to modify user data
     * @return result after executing the command
     * @throws AniException when an error occurred while executing the command
     */
    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        assert animeIndex != null : NULL_ANIME_INDEX;
        int indexSize = animeData.getSize();
        StringBuilder result = new StringBuilder();
        
        if (animeIndex < 0) {
            LOGGER.log(Level.WARNING, OUT_OF_BOUND_INDEX_ERROR);
            throw new AniException(OUT_OF_BOUND_INDEX_ERROR);
        } else if (animeIndex >= indexSize) {
            LOGGER.log(Level.WARNING, OUT_OF_BOUND_INDEX_ERROR);
            throw new AniException(OUT_OF_BOUND_INDEX_ERROR);
        }
        
        result.append("Here is the information for the anime:");
        result.append(System.lineSeparator());
        String animeInfo = animeData.returnAnimeInfo(animeIndex);
        result.append(animeInfo);
        LOGGER.log(Level.INFO, RESULT_BUILD_SUCCESSFUL);

        return result.toString();
    }
}
