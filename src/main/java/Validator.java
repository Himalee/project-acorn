import java.util.ArrayList;
import java.util.List;

public class Validator {

    public boolean menuChoice(String choice, String menuType) {
        List possibleMenuCommands = new ArrayList();
        AllMenuOptions[] allMenuOptions = AllMenuOptions.values();
        for (AllMenuOptions option : allMenuOptions) {
            if (option.getType().equals(menuType)) {
                possibleMenuCommands.add(option.getCommand());
            }
        }
        return possibleMenuCommands.contains(choice);
    }

    public boolean empty(String userInput) {
        return userInput == null || userInput.trim().isEmpty();
    }

    public boolean cost(String userInput) {
        return userInput.matches("[0-9]+\\.[0-9][0-9]");
    }

    public boolean onlyLetters(String userInput) {
        return userInput.matches("[a-zA-Z ]+") || userInput.length() == 0;
    }

    public boolean onlyNumbers(String userInput) {
        return userInput.matches("[0-9]+");
    }

    public boolean yesOrNo(String userInput) {
        return userInput.equals("y") || userInput.equals("n");
    }
}
