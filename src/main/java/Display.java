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

    public void opportunities(List<Opportunity> opportunities) {
        cli.present(formatOpportunities(opportunities));
    }

    public String formatOpportunities(List<Opportunity> opportunities) {
        Collections.sort(opportunities);
        StringBuilder presentableOpportunities = new StringBuilder();
        for (Opportunity opportunity : opportunities) {
            presentableOpportunities.append(opportunity.getId());
            presentableOpportunities.append(". ");
            presentableOpportunities.append(opportunity.getName() + "\n");
            presentableOpportunities.append(opportunity.getDescription() + "\n");
            presentableOpportunities.append(opportunity.getProposedCost() + "\n");
            presentableOpportunities.append(opportunity.getUserName() + "\n");
            presentableOpportunities.append(opportunity.getStage() + "\n");

        }
        return presentableOpportunities.toString();
    }

    public void getOpportunityDescription() {
        cli.present(Message.enterOpportunityDescription());
    }

    public void getOpportunityProposedCost() {
        cli.present(Message.enterProposedOpportunityCost());
    }

    public String getMenuChoice(String menuType) {
        String menuChoice = getUserInputString();
        while (!validator.menuChoice(menuChoice, menuType)) {
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

    public void menu(Menu menu) {
        StringBuilder formattedMenu = new StringBuilder();
        ArrayList<AllMenuOptions> options = menu.getOptionList();
        for (AllMenuOptions option : options) {
            formattedMenu.append(option.getDescription());
            formattedMenu.append(" (select ");
            formattedMenu.append(option.getCommand());
            formattedMenu.append(")");
            formattedMenu.append("\n");

        }
        cli.present(formattedMenu.toString());
    }

    public void opportunityDeleted() {
        cli.present(Message.opportunityHasBeenDeleted());
    }

    public void areYouSure() {
        cli.present(Message.confirm());
    }

    public String getDate() {
        String userInput = getUserInputString();
        while (!validator.date(userInput)) {
            cli.present(Message.enterOpportunityDate());
            userInput = getUserInputString();
        }
        return userInput;
    }

    public void getOpportunityDate() {
        cli.present(Message.enterOpportunityDate());
    }
}

