import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BudgetTracker {

    final int GET_CHOSEN_OPP = 0;

    private Display display;
    private DatabaseCommunicator databaseCommunicator;

    public BudgetTracker(Display display, DatabaseCommunicator databaseCommunicator) {
        this.display = display;
        this.databaseCommunicator = databaseCommunicator;
    }

    public void start() throws SQLException, ClassNotFoundException {
       display.welcomeUser();
       AllMenuOptions option = getMenuOption(Menus.STARTING.getMenu());
       if (option == AllMenuOptions.ADD_NEW_OPP) {
           writeUserInputToDatabase();
       } else if (option == AllMenuOptions.DISPLAY_ALL_OPP) {
          display.opportunities(databaseCommunicator.readAllOpportunitiesFromDatabase());
       } else if (option == AllMenuOptions.SEARCH_BY_ID) {
          List<Opportunity> filteredList = searchBy(userChoiceId());
          display.opportunities(filteredList);
       } else if (option == AllMenuOptions.UPDATE_OPP) {
          updateOpportunity();
       } else {
           display.goodbye();
       }
    }

    private AllMenuOptions getMenuOption(String menuType) {
        OptionList startingMenuOptions = new OptionsBuilder().build(menuType);
        Menu startingMenu = new Menu(startingMenuOptions);
        display.menu(startingMenu);
        String choice = display.getMenuChoice(menuType);
        return startingMenu.findMenuOption(choice);
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
        AllMenuOptions stage = getMenuOption(Menus.OPPORTUNITY_STAGES.getMenu());
        return stage.getDescription();
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
        Opportunity oldOpportunity = filteredList.get(GET_CHOSEN_OPP);
        AllMenuOptions updateOppOption = getMenuOption(Menus.UPDATE_OPPORTUNITY.getMenu());
        if (updateOppOption == AllMenuOptions.NAME) {
            String newName = name();
            String columnName = "name";
            databaseCommunicator.updateOpportunity(oldOpportunity, columnName, newName);
        } else if (updateOppOption == AllMenuOptions.DESCRIPTION) {
            String newDescription = description();
            String columnName = "description";
            databaseCommunicator.updateOpportunity(oldOpportunity, columnName, newDescription);
        }
        List<Opportunity> updatedList = searchBy(oldOpportunity.getId());
        display.opportunities(updatedList);
    }
}


