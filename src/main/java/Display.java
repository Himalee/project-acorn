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


    public void startingMenu() {
        MenuOptions[] menuOptions = MenuOptions.values();
        cli.present(formatMenu(menuOptions));
    }

    public void opportunityStagesMenu() {
        OpportunityStages[] opportunityStages = OpportunityStages.values();
        cli.present(formatMenu(opportunityStages));
    }


    public void getOpportunityDescription() {
        cli.present(Message.enterOpportunityDescription());
    }

    public void getOpportunityProposedCost() {
        cli.present(Message.enterProposedOpportunityCost());
    }

    public String getMenuChoice() {
        String menuChoice = getUserInputString();
        while (!validator.startingMenuChoice(menuChoice)) {
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

    public void getStage() {
        cli.present(Message.enterOpportunityStage());
    }

    public String getOpportunityStage() {
        String stageChoice = getUserInputString();
        while (!validator.opportunityStageChoice(stageChoice)) {
            cli.present(Message.invalidMenuChoice());
            stageChoice = getUserInputString();
        }
        return stageChoice;
    }

    public void getId() {
        cli.present(Message.enterOpportunityId());
    }

    public int getOnlyNumbersInput() {
        String userInput = getUserInputString();
        while(!validator.onlyNumbers(userInput)) {
            cli.present(Message.enterOpportunityId());
            userInput = getUserInputString();
        }
        return Integer.parseInt(userInput);
    }

    private HashMap<Integer, ArrayList> formattedOpportunitiesData(List<Opportunity> opportunities) {
        HashMap<Integer, ArrayList> formattedOpportunities = new HashMap<>();
        for (Opportunity opp : opportunities) {
            ArrayList<String> opportunityDetails = new ArrayList<>();
            opportunityDetails.add(opp.getName());
            opportunityDetails.add(opp.getDescription());
            opportunityDetails.add(Integer.toString(opp.getProposedCost()));
            opportunityDetails.add(opp.getUserName());
            opportunityDetails.add(opp.getStage());
            formattedOpportunities.put(opp.getId(), opportunityDetails);
        }
        return formattedOpportunities;
    }

    private String formatMenu(Menu[] menu) {
        StringBuilder formattedMenu = new StringBuilder();
        for (Menu menuOption : menu) {
            formattedMenu.append(menuOption.getName());
            formattedMenu.append(" (select ");
            formattedMenu.append(menuOption.getCommand());
            formattedMenu.append(")");
            formattedMenu.append("\n");

        }
        return formattedMenu.toString();
    }
}

