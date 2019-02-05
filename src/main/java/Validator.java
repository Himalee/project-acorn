import java.util.ArrayList;
import java.util.List;

public class Validator {

    public boolean startingMenuChoice(String choice) {
        List possibleMenuCommands = new ArrayList();
        MenuOptions[] menuOptions = MenuOptions.values();
        for (MenuOptions menuOption : menuOptions) {
            possibleMenuCommands.add(menuOption.getCommand());
        }
       return possibleMenuCommands.contains(choice);
    }

    public boolean empty(String userInput) {
        return userInput == null || userInput.trim().isEmpty();
    }

    public boolean cost(String userInput) {
        return userInput.matches("[0-9]+.[0-9][0-9]");
    }

    public boolean onlyLetters(String userInput) {
        return userInput.matches("[a-zA-Z]+") || userInput.length() == 0;
    }

    public boolean opportunityStageChoice(String choice) {
        List possibleMenuCommands = new ArrayList();
        OpportunityStages[] opportunityStages = OpportunityStages.values();
        for (OpportunityStages stage : opportunityStages) {
            possibleMenuCommands.add(stage.getCommand());
        }
        return possibleMenuCommands.contains(choice);
    }

    public boolean onlyNumbers(String userInput) {
        return userInput.matches("[0-9]+");
    }
}
