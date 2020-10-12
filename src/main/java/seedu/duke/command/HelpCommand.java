package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class HelpCommand extends Command {
    
    public HelpCommand() {
        
    }

    /**
     * Shows help function.
     */
    @Override
    public void execute(Ui ui, Storage storage, AnimeData animeData, Watchlist currentWatchlist,
                        ArrayList<Watchlist> watchlists, Bookmark bookmark) {
        // Code to be added

        // Print for testing
        System.out.println("Help showed");
    }
}