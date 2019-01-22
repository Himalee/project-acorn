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
}
