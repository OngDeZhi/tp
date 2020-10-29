package anichan.commands;

import anichan.human.User;
import anichan.human.Workspace;
import anichan.logger.AniLogger;
import anichan.watchlist.Watchlist;
import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.storage.StorageManager;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command to remove an anime from the active Watchlist.
 */
public class RemoveCommand extends Command {
    protected static final String OUT_OF_BOUND_INDEX_ERROR = "Invalid Watchlist Index!";
    protected static final String EMPTY_WATCHLIST_ERROR = "Watchlist is empty!";

    private Integer animeIndexInWatchlist;
    private Integer animeIndex;
    private static final Logger LOGGER = AniLogger.getAniLogger(RemoveCommand.class.getName());

    /**
     * Creates a new instance of RemoveCommand.
     */
    public RemoveCommand() {
        // LOGGER.setLevel(Level.WARNING);
    }

    /**
     * Executes removing of anime from active Watchlist.
     *
     * @param animeData used to retrieve anime information
     * @param storageManager used to save or read AniChan data
     * @param user used to modify user data
     * @return result after executing the command
     * @throws AniException when an error occurred while executing the command
     */
    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        Workspace activeWorkspace = user.getActiveWorkspace();
        
        removeFromWatchlist(storageManager, activeWorkspace);
        Anime anime = animeData.getAnime(animeIndex);
        String animeName = anime.getAnimeName();

        return animeName + " successfully removed from watchlist!";
    }

    /**
     * Removes selected anime from active Watchlist.
     *
     * @param storageManager used to save watchlist data
     * @param activeWorkspace used to update watchlist list and save watchlist data to correct folder
     * @throws AniException when an error occurred while creating the watchlist
     */
    private void removeFromWatchlist(StorageManager storageManager, Workspace activeWorkspace) throws AniException {
        Watchlist activeWatchlist = activeWorkspace.getActiveWatchlist();
        
        if (activeWatchlist.getWatchlistSize() == 0) {
            LOGGER.log(Level.WARNING, EMPTY_WATCHLIST_ERROR);
            throw new AniException(EMPTY_WATCHLIST_ERROR);
        } else if (activeWatchlist.getWatchlistSize() <= animeIndexInWatchlist) {
            LOGGER.log(Level.WARNING, OUT_OF_BOUND_INDEX_ERROR);
            throw new AniException(OUT_OF_BOUND_INDEX_ERROR);
        } else if (animeIndexInWatchlist < 0) {
            LOGGER.log(Level.WARNING, OUT_OF_BOUND_INDEX_ERROR);
            throw new AniException(OUT_OF_BOUND_INDEX_ERROR);
        } 
        
        assert this.animeIndexInWatchlist >= 0 : "anime index has to be valid";
        animeIndex = activeWatchlist.getWatchlistListAnimeIndex(animeIndexInWatchlist);
        activeWatchlist.removeAnimeFromList(animeIndexInWatchlist);

        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();
        storageManager.saveWatchlistList(activeWorkspace.getName(), watchlistList);
        LOGGER.log(Level.INFO, "Successfully removed anime from active watchlist");
    }

    /**
     * Sets the index of anime in the Watchlist to be removed.
     * 
     * @param animeIndexInWatchlist the specified anime index in the Watchlist to be removed.
     */
    public void setWatchlistListIndex(Integer animeIndexInWatchlist) {
        this.animeIndexInWatchlist = animeIndexInWatchlist - 1;
    }
}
