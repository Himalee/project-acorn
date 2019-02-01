import java.util.ArrayList;
import java.util.List;

public class Validator {

    public boolean menuChoice(String choice) {
        List possibleMenuCommands = new ArrayList();
        MenuOptions[] menuOptions = MenuOptions.values();
        for (MenuOptions menuOption : menuOptions) {
            possibleMenuCommands.add(menuOption.getCommand());
        }
       if (possibleMenuCommands.contains(choice)) {
           return true;
       } else {
           return false;
       }
    }

    public boolean empty(String userInput) {
        return userInput == null || userInput.trim().isEmpty();
    }

    public boolean cost(String userInput) {
        if (userInput.matches("[0-9]+.[0-9][0-9]")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean onlyLetters(String userInput) {
        if (userInput.matches("[a-zA-Z]+") || userInput.length() == 0) {
            return true;

        } else {
            return false;
        }
    }
}
