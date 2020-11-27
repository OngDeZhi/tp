package anichan.commands;

import anichan.human.User;
import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.storage.StorageManager;

public abstract class Command {
    private static final String IMPLEMENT_CHILD_CLASS = "This method should be implemented in the child class!";
    private boolean shouldExit = false;

    public boolean getShouldExit() {
        return shouldExit;
    }

    public void setShouldExit() {
        this.shouldExit = true;
    }

    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        throw new AniException(IMPLEMENT_CHILD_CLASS);
    }
}
