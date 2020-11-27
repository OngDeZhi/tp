package anichan.anime;

import anichan.exception.AniException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import static anichan.logger.AniLogger.getAniLogger;

/**
 * Manages the storage of anime data.
 */
public class AnimeStorage {

    //Constant Param
    private static final int FIRST_FILE_INDEX = 1;
    private static final int LAST_FILE_INDEX = 6;
    private static final String EMPTY_STRING = "";
    private static final int DEFAULT_PARAM = 0;

    //Resource stream folder and extension
    private static final String ANIME_STORAGE_FILE_EXTENSION = ".json";
    private static final String ANIME_STORAGE_FILE_HEADER = "/AniListData/AniList-Data";

    //Logger messages
    private static final String ANIME_STORAGE_EXTRACTION_HEADER = "Extraction of " + ANIME_STORAGE_FILE_HEADER;
    private static final String ANIME_STORAGE_EXTRACTION_TRAILER = ANIME_STORAGE_FILE_EXTENSION + " successful";
    private static final String ANIME_STORAGE_PARSE_MESSAGE = "Parsing Json data.";
    private static final String ANIME_STORAGE_PARSE_SUCCESSFUL = "Parse Successful.";
    private static final String ANIME_STORAGE_PARSING_FAILED = "Parsing file failed!";
    private static final String ANIME_STORAGE_RETRIEVE_MESSAGE = "Retrieving information from DataSource.";
    private static final String ANIME_STORAGE_CURRENT_HEADER = "Currently extracting from " + ANIME_STORAGE_FILE_HEADER;
    public static final String ANIME_STORAGE_SUCCESSFUL_MESSAGE = "Retrieval and Parsing for anime object"
            + " in DataSource Successful.";

    //JSON field title
    private static final String DATA_JSON_FIELD = "data";
    private static final String MEDIA_JSON_FIELD = "Media";
    private static final String ENGLISH_JSON_FIELD = "english";
    private static final String ROMAJI_JSON_FIELD = "romaji";
    private static final String EPISODES_JSON_FIELD = "episodes";
    private static final String START_DATE_JSON_FIELD = "startDate";
    private static final String YEAR_JSON_FIELD = "year";
    private static final String MONTH_JSON_FIELD = "month";
    private static final String DAY_JSON_FIELD = "day";
    private static final String AVERAGE_SCORE_JSON_FIELD = "averageScore";
    private static final String GENRES_JSON_FIELD = "genres";
    private static final String DURATION_JSON_FIELD = "duration";
    private static final String TITLE_JSON_FIELD = "title";

    //Error messages
    private static final String GENRE_NULL_ERROR = "Genre should not be null.";
    private static final String RELEASE_DATE_NULL_ERROR = "Release date should not be null.";
    private static final String ANIME_NAME_NULL_ERROR = "Anime Name should not be null.";
    private static final String FILENAME_NULL_ERROR = "Filename should not be null.";
    private static final String FILE_RESOURCE_NULL_ERROR = "File within resource stream could not be found!";

    private static final Logger LOGGER = getAniLogger(Anime.class.getName());


    /**
     * Read anime data from offline database.
     *
     * @return ArrayList of anime object
     * @throws AniException if have error reading file
     */
    public ArrayList<Anime> readAnimeDatabase() throws AniException {
        LOGGER.log(Level.INFO, ANIME_STORAGE_RETRIEVE_MESSAGE);
        ArrayList<Anime> animeDataList = new ArrayList<>();
        for (int i = FIRST_FILE_INDEX; i < LAST_FILE_INDEX; i++) {
            LOGGER.log(Level.INFO, ANIME_STORAGE_CURRENT_HEADER + i + ANIME_STORAGE_FILE_EXTENSION);
            String fileData = getDataFromJarFile(ANIME_STORAGE_FILE_HEADER + i + ANIME_STORAGE_FILE_EXTENSION);
            LOGGER.log(Level.INFO, ANIME_STORAGE_EXTRACTION_HEADER + i + ANIME_STORAGE_EXTRACTION_TRAILER);
            LOGGER.log(Level.INFO, ANIME_STORAGE_PARSE_MESSAGE);
            parseJson(animeDataList, fileData);
            LOGGER.log(Level.INFO, ANIME_STORAGE_PARSE_SUCCESSFUL);
        }
        LOGGER.log(Level.INFO, ANIME_STORAGE_SUCCESSFUL_MESSAGE);
        return animeDataList;
    }

    /**
     * Parse the json file into anime object.
     *
     * @param animeDataList the list to keep anime object data
     * @param fileData      the json filedata to be parse into anime object
     * @throws AniException if error parsing string into json object
     */
    private void parseJson(ArrayList<Anime> animeDataList, String fileData) throws AniException {
        JSONParser parser = new JSONParser();
        JSONArray jsonList = new JSONArray();
        try {
            jsonList = (JSONArray) parser.parse(fileData);

        } catch (ParseException e) {
            LOGGER.log(Level.WARNING, ANIME_STORAGE_PARSING_FAILED);
            e.printStackTrace();

        }

        Iterator iterator = jsonList.iterator();
        JSONObject jsonObject;
        while (iterator.hasNext()) {
            String animeName;
            int animeEpisode = DEFAULT_PARAM;
            int animeRating = DEFAULT_PARAM;
            int animeDuration = DEFAULT_PARAM;

            //Advance the Iterator
            jsonObject = (JSONObject) iterator.next();
            jsonObject = (JSONObject) jsonObject.get(DATA_JSON_FIELD);
            jsonObject = (JSONObject) jsonObject.get(MEDIA_JSON_FIELD);

            //Getting anime name
            JSONObject jsonTitle = (JSONObject) jsonObject.get(TITLE_JSON_FIELD);
            if (jsonTitle.get(ENGLISH_JSON_FIELD) == null) {
                animeName = (String) jsonTitle.get(ROMAJI_JSON_FIELD);
            } else {
                animeName = (String) jsonTitle.get(ENGLISH_JSON_FIELD);
            }
            assert animeName != null : ANIME_NAME_NULL_ERROR;
            //getting anime episode

            if (jsonObject.get(EPISODES_JSON_FIELD) != null) {
                animeEpisode = (int) (long) jsonObject.get(EPISODES_JSON_FIELD);
            }

            //getting start date
            JSONObject jsonDate = (JSONObject) jsonObject.get(START_DATE_JSON_FIELD);
            String[] animeReleaseDate;
            animeReleaseDate = new String[]{String.valueOf((long) jsonDate.get(YEAR_JSON_FIELD)),
                    String.valueOf((long) jsonDate.get(MONTH_JSON_FIELD)),
                    String.valueOf((long) jsonDate.get(DAY_JSON_FIELD))};
            assert animeReleaseDate != null : RELEASE_DATE_NULL_ERROR;

            //getting rating
            if (jsonObject.get(AVERAGE_SCORE_JSON_FIELD) != null) {
                animeRating = (int) (long) jsonObject.get(AVERAGE_SCORE_JSON_FIELD);
            }

            //getting genre
            ArrayList<String> animeGenre;
            animeGenre = new ArrayList<>();
            JSONArray jsonGenre = (JSONArray) jsonObject.get(GENRES_JSON_FIELD);
            for (Object genre : jsonGenre) {
                animeGenre.add((String) genre);
            }
            String[] animeGenreArray;
            animeGenreArray = new String[animeGenre.size()];
            animeGenreArray = animeGenre.toArray(animeGenreArray);
            assert animeGenreArray != null : GENRE_NULL_ERROR;

            //getting duration
            if (jsonObject.get(DURATION_JSON_FIELD) != null) {
                animeDuration = (int) (long) jsonObject.get(DURATION_JSON_FIELD);
            }

            Anime anime = new Anime(animeName, animeReleaseDate, animeRating, animeGenreArray, animeDuration,
                    animeEpisode);
            animeDataList.add(anime);
        }
    }

    /**
     * Read file from resource stream. (Files embedded within the program).
     *
     * @param filename name of the resource stream file
     * @return file read in string
     * @throws AniException if error reading resource stream file
     */
    public String getDataFromJarFile(String filename) throws AniException {
        assert filename != null : FILENAME_NULL_ERROR;
        try {
            InputStream inputStream = AnimeStorage.class.getResourceAsStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String fileLine;
            String fileData = EMPTY_STRING;

            while ((fileLine = bufferedReader.readLine()) != null) {
                fileData += fileLine;
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            return fileData;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, FILE_RESOURCE_NULL_ERROR);
            throw new AniException(FILE_RESOURCE_NULL_ERROR);
        }

    }
}
