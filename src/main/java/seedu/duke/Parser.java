package seedu.duke;

public class Parser {

    public String[] parseUserInput(String input) throws DukeException {
        if (input == null || input.isEmpty()) {
            throw new DukeException("Input is empty");
        }

        String[] inputSplit = input.split(" ", 2);
        return inputSplit;
    }
}
