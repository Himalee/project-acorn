import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BudgetTracker {

    private Display display;
    private DatabaseCommunicator databaseCommunicator;

    public BudgetTracker(Display display, DatabaseCommunicator databaseCommunicator) {
        this.display = display;
        this.databaseCommunicator = databaseCommunicator;
    }

    public void start() throws SQLException, ClassNotFoundException {
       display.welcomeUser();
       display.startingMenu();
       String menuChoice = display.getMenuChoice();
       MenuOptions option = MenuOptions.findChoice(menuChoice);
       if (option == MenuOptions.ADD_NEW_OPP) {
           writeUserInputToDatabase();
       } else if (option == MenuOptions.DISPLAY_ALL_OPP) {
          display.formatOpportunities(databaseCommunicator.readAllOpportunitiesFromDatabase());
       } else if(option == MenuOptions.SEARCH_BY_ID) {
          List<Opportunity> filteredList = searchById();
          display.formatOpportunities(filteredList);
       } else {
           display.goodbye();
       }
    }

    private void writeUserInputToDatabase() throws SQLException, ClassNotFoundException {
        databaseCommunicator.writeToDatabase(createNewOpportunity());
        display.opportunityWrittenToDatabase();
    }

    private Opportunity createNewOpportunity() {
        return new Opportunity(name(), description(), proposedCost(), userName(), stage());
    }

    private String userName(){
        display.getUserName();
        return display.getOnlyLettersInput();
    }

    private String name() {
        display.getOpportunityName();
        return display.getNonEmptyInput();
    }

    private String description() {
        display.getOpportunityDescription();
        return display.getNonEmptyInput();
    }

    private int proposedCost() {
        display.getOpportunityProposedCost();
        return display.getCost();
    }

    private String stage() {
        display.getStage();
        display.opportunityStagesMenu();
        String stageChoice = display.getOpportunityStage();
        OpportunityStages stage = OpportunityStages.findStage(stageChoice);
        return stage.getName();
    }

    private List<Opportunity> searchById() throws SQLException, ClassNotFoundException {
        List<Opportunity> opportunityList = new ArrayList<>();
        display.getId();
        int userChoiceId = display.getOnlyNumbersInput();
        List<Opportunity> opportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        for (Opportunity opp : opportunities) {
            int id = opp.getId();
            if (id == userChoiceId) {
                opportunityList.add(opp);
            }
        }
        return opportunityList;
    }
}


