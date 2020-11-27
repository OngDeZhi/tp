package anichan.anime;

import anichan.exception.AniException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Manages the all anime data.
 */
public class AnimeData {
    private static final int RESET_ANIME_ID = 0;

    private static final String INDEX_HEADER = "Index: ";
    private static final String NAME_HEADER = "Name: ";
    private static final String EPISODES_HEADER = "Episodes: ";
    private static final String RELEASE_DATE_HEADER = "Release Date: ";
    private static final String RATING_HEADER = "Rating: ";
    private static final String GENRE_HEADER = "Genre: ";
    private ArrayList<Anime> animeDataList;


    /**
     * Creates an instance of AnimeData using animeDataList provided.
     *
     * @param animeDataList the animeDataList to load
     */
    public AnimeData(ArrayList<Anime> animeDataList) {
        Anime.setTotalAnime(RESET_ANIME_ID);
        this.animeDataList = animeDataList;
    }

    /**
     * Creates a new instance of AnimeData.
     * Loads the animeDataList from offline storage.
     *
     * @throws AniException if error loading animeDataList from offline storage
     */
    public AnimeData() throws AniException {
        Anime.setTotalAnime(RESET_ANIME_ID);
        AnimeStorage animeStorage = new AnimeStorage();
        loadAnimeData(animeStorage.readAnimeDatabase());
    }

    /**
     * Loads the animeDataList using an anime list provided.
     *
     * @param animeDataList anime data list to be loaded
     */
    public void loadAnimeData(ArrayList<Anime> animeDataList) {
        this.animeDataList = animeDataList;
    }

    /**
     * Gets the anime object using its anime id.
     *
     * @param animeIndex the anime id
     * @return the anime object of the anime id
     */
    public Anime getAnime(Integer animeIndex) {
        return animeDataList.get(animeIndex);
    }

    /**
     * Gets the size of anime data source.
     *
     * @return size of anime data list
     */
    public int getSize() {
        return animeDataList.size();
    }

    /**
     * Get the anime data list.
     *
     * @return anime data list, arraylist of anime object
     */
    public ArrayList<Anime> getAnimeDataList() {
        return animeDataList;
    }

    /**
     * Builds and return anime information in a String format.
     *
     * @param animeIndex the anime index to return
     * @return anime information in a printable String format.
     */
    public String returnAnimeInfo(int animeIndex) {
        Anime anime = animeDataList.get(animeIndex);
        StringBuilder result = new StringBuilder();

        result.append(INDEX_HEADER + (animeIndex + 1));
        result.append(System.lineSeparator());
        result.append(NAME_HEADER + anime.getAnimeName());
        result.append(System.lineSeparator());
        result.append(EPISODES_HEADER + anime.getTotalEpisodes());
        result.append(System.lineSeparator());
        result.append(RELEASE_DATE_HEADER + anime.getReleaseDateInString());
        result.append(System.lineSeparator());
        result.append(RATING_HEADER + anime.getRating());
        result.append(System.lineSeparator());
        result.append(GENRE_HEADER + Arrays.toString(anime.getGenre()));

        return result.toString();
    }

}
