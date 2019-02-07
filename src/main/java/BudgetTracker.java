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
          display.opportunities(databaseCommunicator.readAllOpportunitiesFromDatabase());
       } else if (option == MenuOptions.SEARCH_BY_ID) {
          List<Opportunity> filteredList = searchBy(userChoiceId());
          display.opportunities(filteredList);
       } else if (option == MenuOptions.UPDATE_OPP) {
          updateOpportunity();
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

    private List<Opportunity> searchBy(int userChoiceId) throws SQLException, ClassNotFoundException {
        List<Opportunity> opportunityList = new ArrayList<>();
        List<Opportunity> opportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        for (Opportunity opportunity : opportunities) {
            int id = opportunity.getId();
            if (id != userChoiceId) {
                continue;
            }
            opportunityList.add(opportunity);
        }
        return opportunityList;
    }

    private int userChoiceId() {
        display.getId();
        return display.getOnlyNumbersInput();
    }

    private void updateOpportunity() throws SQLException, ClassNotFoundException {
        List<Opportunity> filteredList = searchBy(userChoiceId());
        display.opportunities(filteredList);
        Opportunity oldOpportunity = filteredList.get(0);
        display.updateOpportunityMenu();
        String menuChoice = display.getUpdateOpportunityChoice();
        UpdateOpportunityOptions option = UpdateOpportunityOptions.findChoice(menuChoice);
        if (option == UpdateOpportunityOptions.NAME) {
            String newName = name();
            databaseCommunicator.updateName(oldOpportunity, newName);
        }
        List<Opportunity> updatedList = searchBy(oldOpportunity.getId());
        display.opportunities(updatedList);
    }
}


