package anichan.anime;

import anichan.exception.AniException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a single anime series.
 */
public class Anime {
    private static final String SET_RELEASE_DATE_PARSE_ERROR_MESSAGE = "Tried to parse an invalid date format!";
    private static final String DATE_PATTERN_PARSED = "yyyy-MM-dd";
    private static final String DASH_DELIMIT = "-";
    private static final String DATE_PATTERN_PRINT = "dd/MMM/yyyy";
    private static final int ZERO_VALUE = 0;
    private static final int MAX_RATING = 100;
    private static final int MIN_RATING = ZERO_VALUE;
    private static final String EMPTY_STRING = "";
    private static int totalAnime = ZERO_VALUE;

    private int animeID;
    private String animeName;
    private Date releaseDate;
    private int rating;
    private String[] genre;
    private int avgEpisodeLength;
    private int totalEpisodes;

    /**
     * Creates a default instance of an Anime object.
     */
    public Anime() {
        this.animeID = ZERO_VALUE;
        this.animeName = EMPTY_STRING;
        this.rating = ZERO_VALUE;
        this.totalEpisodes = ZERO_VALUE;
        this.genre = null;
        animeID = totalAnime;
    }

    /**
     * Creates an instance of an Anime object.
     *
     * @param animeName title of the anime
     * @param releaseDate release date of the anime
     * @param rating rating of the anime
     * @param genre array of genres that the anime is
     * @param avgEpisodeLength is the average episode length
     * @param totalEpisodes is the total episode that the anime has
     * @throws AniException if there is an error setting any of its parameters
     */
    public Anime(String animeName, String[] releaseDate, int rating, String[] genre,
                 int avgEpisodeLength, int totalEpisodes) throws AniException {
        setAnimeName(animeName);
        setReleaseDate(releaseDate);
        setRating(rating);
        setGenre(genre);
        setAvgEpisodeLength(avgEpisodeLength);
        setTotalEpisodes(totalEpisodes);
        incrementTotalAnime();
        setAnimeID(totalAnime);
    }

    /**
     * Gets the Anime ID.
     *
     * @return Anime ID
     */
    public int getAnimeID() {
        return animeID;
    }

    /**
     * Sets the Anime ID.
     *
     * @param animeID indicates the ID to set for this Anime
     */
    public void setAnimeID(int animeID) {
        this.animeID = animeID;
    }

    /**
     * Sets the total episode.
     *
     * @param totalEpisodes indicates the total episode this Anime has
     */
    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    /**
     * Sets the average episode length.
     *
     * @param avgEpisodeLength indicates the average length of each episode in this Anime
     */
    public void setAvgEpisodeLength(int avgEpisodeLength) {
        this.avgEpisodeLength = avgEpisodeLength;
    }

    /**
     * Gets the Anime total episode.
     *
     * @return the total episode of this Anime
     */
    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    /**
     * Gets the Anime name.
     *
     * @return name of the Anime
     */
    public String getAnimeName() {
        return animeName;
    }

    /**
     * Sets the name.
     *
     * @param animeName indicates the name of this Anime
     */
    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }

    /**
     * Gets the Anime release date.
     *
     * @return the release date of this Anime as a Date object
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Gets the Anime release date as a String.
     *
     * @return the release date of this Anime as a String
     */
    public String getReleaseDateInString() {
        SimpleDateFormat newDateFormat = new SimpleDateFormat(DATE_PATTERN_PRINT);
        return newDateFormat.format(releaseDate);
    }

    /**
     * Gets the Anime rating.
     *
     * @return the rating of this Anime
     */
    public int getRating() {
        return rating;
    }

    /**
     * Gets the Anime genre.
     *
     * @return a String Array of this Anime's genre
     */
    public String[] getGenre() {
        return genre;
    }

    /**
     * Sets the genres of this Anime.
     *
     * @param genre indicates all the genre this anime has
     */
    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    /**
     * Sets the class-level variable of totalAnime.
     *
     * @param totalAnime indicates the total number of Anime objects
     */
    public static void setTotalAnime(int totalAnime) {
        Anime.totalAnime = totalAnime;
    }

    /**
     * Sets the rating of anime, by performing a check to ensure that it is within a specified range.
     *
     * @param rating is the rating to set it to
     */
    public void setRating(int rating) {
        if (rating > MAX_RATING || rating < MIN_RATING) {
            this.rating = ZERO_VALUE;
        } else {
            this.rating = rating;
        }
    }

    /**
     * Increments the class-level variable by 1.
     */
    private void incrementTotalAnime() {
        totalAnime++;
    }

    /**
     * Sets the release date of this anime. Will parse in a String array and set it as a Date object.
     *
     * @param releaseDate is a String Array containing the date information.
     */
    public void setReleaseDate(String[] releaseDate) throws AniException {
        try {
            String dateInString = releaseDate[0] + DASH_DELIMIT + releaseDate[1] + DASH_DELIMIT + releaseDate[2];
            SimpleDateFormat stringToDate = new SimpleDateFormat(DATE_PATTERN_PARSED);
            this.releaseDate = stringToDate.parse(dateInString);
        } catch (java.text.ParseException invalidDateFormat) {
            throw new AniException(SET_RELEASE_DATE_PARSE_ERROR_MESSAGE);
        }
    }

    /**
     * Overrides toString() to return Anime name instead.
     *
     * @return the name of the Anime
     */
    @Override
    public String toString() {
        return getAnimeName();
    }
}
