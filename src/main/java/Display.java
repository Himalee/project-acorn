import java.util.*;

public class Display {

    private CommandLineInterface cli;
    private Validator validator;

    public Display(CommandLineInterface cli, Validator validator) {
        this.cli = cli;
        this.validator = validator;
    }

    public void welcomeUser() {
        cli.present(Message.welcome());
    }

    public String getUserInputString() {
        return cli.receiveString();
    }

    public void getOpportunityName() {
        cli.present(Message.enterOpportunityName());
    }

    public void opportunityWrittenToDatabase() {
        cli.present(Message.opportunitySaved());
    }

    public void goodbye() {
        cli.present(Message.closeApp());
    }

    public void formatOpportunities(List<Opportunity> opportunities) {
        StringBuilder presentableOpportunities = new StringBuilder();
        Map<Integer,ArrayList> sortedMap = new TreeMap<>(formattedOpportunitiesData(opportunities));
        for (Map.Entry<Integer, ArrayList> entry : sortedMap.entrySet()) {
            Integer key = entry.getKey();
            presentableOpportunities.append(key);
            presentableOpportunities.append(". ");
            ArrayList values = entry.getValue();
            values.stream().forEach(elem -> presentableOpportunities.append(elem + "\n"));
        }
        cli.present(presentableOpportunities.toString());
    }

    public HashMap<Integer, ArrayList> formattedOpportunitiesData(List<Opportunity> opportunities) {
        HashMap<Integer, ArrayList> formattedOpportunities = new HashMap<>();
        for (Opportunity opp : opportunities) {
            ArrayList<String> opportunityDetails = new ArrayList<>();
            opportunityDetails.add(opp.getName());
            opportunityDetails.add(opp.getDescription());
            opportunityDetails.add(Integer.toString(opp.getProposedCost()));
            opportunityDetails.add(opp.getUserName());
            formattedOpportunities.put(opp.getId(), opportunityDetails);
        }
        return formattedOpportunities;
    }

    public void formatMenu() {
        MenuOptions[] menuOptions = MenuOptions.values();
        StringBuilder formattedMenu = new StringBuilder();
        for (MenuOptions menuOption : menuOptions) {
            formattedMenu.append(menuOption.getName());
            formattedMenu.append(" (select ");
            formattedMenu.append(menuOption.getCommand());
            formattedMenu.append(")");
            formattedMenu.append("\n");

        }
        cli.present(formattedMenu.toString());
    }

    public void getOpportunityDescription() {
        cli.present(Message.enterOpportunityDescription());
    }

    public void getOpportunityProposedCost() {
        cli.present(Message.enterProposedOpportunityCost());
    }

    public String getMenuChoice() {
        String menuChoice = getUserInputString();
        while (!validator.menuChoice(menuChoice)) {
            cli.present(Message.invalidMenuChoice());
            menuChoice = getUserInputString();
        }
        return menuChoice;
    }

    public String getNonEmptyInput() {
        String userInput = getUserInputString();
        while (validator.empty(userInput)) {
            cli.present(Message.emptyInput());
            userInput = getUserInputString();
        }
        return userInput;
    }

    public int getCost() {
        String userInput = getUserInputString();
        while (!validator.cost(userInput)) {
            cli.present(Message.enterProposedOpportunityCost());
            userInput = getUserInputString();
        }
        String inputWithRemovedDecimal = userInput.replace(".", "");
        return Integer.parseInt(inputWithRemovedDecimal);
    }

    public String getOnlyLettersInput() {
        String userInput = getUserInputString();
        while (!validator.onlyLetters(userInput)) {
            cli.present(Message.enterUserName());
            userInput = getUserInputString();
        }
        return userInput;
    }

    public void getUserName() {
        cli.present(Message.enterUserName());
    }
}

