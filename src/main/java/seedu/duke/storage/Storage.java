package seedu.duke.storage;

import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserProfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Storage {
    private final String userProfileFilePath;
    private final String watchlistFilePath;
    private final String storageDirectory = "data" + File.separator + "AniChan" + File.separator;
    private static final String FILE_LINE_DELIMITER = " \\| ";

    public Storage(String userProfileFileName, String watchlistFileName) {
        userProfileFilePath = storageDirectory + userProfileFileName;
        watchlistFilePath = storageDirectory + watchlistFileName;
    }

    public UserProfile readUserProfileFile(Ui ui) {
        UserProfile userProfile = null;
        try {
            String fileString = readFile(ui, userProfileFilePath);
            if (fileString.isBlank()) {
                return userProfile;
            }

            String[] fileStringParts = fileString.split(FILE_LINE_DELIMITER);
            String name = fileStringParts[0];
            String birthDate = fileStringParts[1];
            String gender = fileStringParts[2];
            userProfile = new UserProfile(name, birthDate, gender);
        } catch (AniException | ParseException exception) {
            ui.printMessage("User profile object creation has failed.");
        }

        return userProfile;
    }

    public ArrayList<Watchlist> readWatchlistFile(Ui ui) {
        ArrayList<Watchlist> watchlists = new ArrayList<>();
        String fileString = readFile(ui, watchlistFilePath);
        if (fileString.isBlank()) {
            return watchlists;
        }

        String[] fileStringParts = fileString.split(System.lineSeparator());
        for (String line : fileStringParts) {
            String[] lineSplit = line.split(FILE_LINE_DELIMITER, 2);
            String watchlistName = lineSplit[0];
            String animeListString = lineSplit[1].substring(1, lineSplit[1].length() - 1);

            ArrayList<String> animeList = new ArrayList<>();
            if (!animeListString.isBlank()) {
                String[] animes = animeListString.split(", ");
                animeList = new ArrayList<>(Arrays.asList(animes));
            }

            Watchlist savedWatchList = new Watchlist(watchlistName, animeList);
            watchlists.add(savedWatchList);
        }

        return watchlists;
    }

    private String readFile(Ui ui, String filePath) {
        StringBuilder sbFileString = new StringBuilder();
        try {
            File fileToRead = new File(filePath);
            Scanner fileReader = new Scanner(fileToRead);

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                sbFileString.append(line);
                sbFileString.append(System.lineSeparator());
            }
        } catch (FileNotFoundException exception) {
            if (filePath.equals(userProfileFilePath)) {
                ui.printHorizontalLine();
                ui.printMessage("User profile file is not found.");
            } else {
                ui.printMessage("Watchlist file is not found.");
                ui.printHorizontalLine();
            }
        }

        return sbFileString.toString();
    }

    public void writeUserProfileFile(Ui ui, UserProfile userProfile) {
        String userProfileString = userProfile.toFileString();
        writeFile(ui, userProfileFilePath, userProfileString);
    }

    public void writeWatchlistFile(Ui ui, ArrayList<Watchlist> watchlists) {
        StringBuilder sbWatchlistString = new StringBuilder();
        for (Watchlist watchlist : watchlists) {
            sbWatchlistString.append(watchlist.toFileString());
            sbWatchlistString.append(System.lineSeparator());
        }
        writeFile(ui, watchlistFilePath, sbWatchlistString.toString());
    }

    private void writeFile(Ui ui, String filePath, String fileString) {
        new File(storageDirectory).mkdirs();
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(fileString);
            fileWriter.close();
        } catch (IOException exception) {
            ui.printErrorMessage("Error occurred while writing to file.");
        }
    }
}