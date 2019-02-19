import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BudgetTracker {

    final int GET_CHOSEN_OPPORTUNITY = 0;

    private Display display;
    private DatabaseCommunicator databaseCommunicator;

    public BudgetTracker(Display display, DatabaseCommunicator databaseCommunicator) {
        this.display = display;
        this.databaseCommunicator = databaseCommunicator;
    }

    public void start() throws SQLException, ClassNotFoundException {
       display.welcomeUser();
       AllMenuOptions option = getMenuOption(Menus.STARTING.getMenu());
       if (option == AllMenuOptions.ADD_NEW_OPPORTUNITY) {
           writeUserInputToDatabase();
       } else if (option == AllMenuOptions.DISPLAY_ALL_OPPORTUNITY) {
          display.opportunities(databaseCommunicator.readAllOpportunitiesFromDatabase());
       } else if (option == AllMenuOptions.SEARCH_BY_ID) {
          List<Opportunity> filteredList = searchBy(getUserChoiceId());
          display.opportunities(filteredList);
       } else if (option == AllMenuOptions.UPDATE_OPPORTUNITY) {
          updateOpportunity();
       } else if (option == AllMenuOptions.DELETE_OPPORTUNITY) {
           Opportunity opportunity = getChosenOpportunity();
           deleteOpportunityWithConfirmation(opportunity);
       }
       else {
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
        return new Opportunity(getName(), getDescription(), getProposedCost(), getUserName(), getStage(), generateUuid());
    }

    private String getUserName(){
        display.getUserName();
        return display.getOnlyLettersInput();
    }

    private String getName() {
        display.getOpportunityName();
        return display.getNonEmptyInput();
    }

    private String getDescription() {
        display.getOpportunityDescription();
        return display.getNonEmptyInput();
    }

    private int getProposedCost() {
        display.getOpportunityProposedCost();
        return display.getCost();
    }

    private String getStage() {
        display.getStage();
        AllMenuOptions stage = getMenuOption(Menus.OPPORTUNITY_STAGES.getMenu());
        return stage.getDescription();
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
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

    private int getUserChoiceId() {
        display.getId();
        return display.getOnlyNumbersInput();
    }

    private void updateOpportunity() throws SQLException, ClassNotFoundException {
        List<Opportunity> filteredList = searchBy(getUserChoiceId());
        display.opportunities(filteredList);
        Opportunity oldOpportunity = filteredList.get(GET_CHOSEN_OPPORTUNITY);
        AllMenuOptions updateOppOption = getMenuOption(Menus.UPDATE_OPPORTUNITY.getMenu());
        switch (updateOppOption) {
            case NAME:
                String name = getName();
                updateStringField(oldOpportunity, TableColumns.NAME.getColumnName(), name);
                break;
            case DESCRIPTION:
                String description = getDescription();
                updateStringField(oldOpportunity, TableColumns.DESCRIPTION.getColumnName(), description);
                break;
            case COST:
                int cost = getProposedCost();
                updateNumericField(oldOpportunity, TableColumns.COST.getColumnName(), cost);
                break;
            case USER_NAME:
                String userName = getUserName();
                updateStringField(oldOpportunity, TableColumns.USER_NAME.getColumnName(), userName);
                break;
            case STAGE:
                String stage = getStage();
                updateStringField(oldOpportunity, TableColumns.STAGE.getColumnName(), stage);
                break;
        }
        List<Opportunity> updatedList = searchBy(oldOpportunity.getId());
        display.opportunities(updatedList);
    }

    private Opportunity getChosenOpportunity() throws SQLException, ClassNotFoundException {
        List<Opportunity> filteredList = searchBy(getUserChoiceId());
        display.opportunities(filteredList);
        return filteredList.get(GET_CHOSEN_OPPORTUNITY);
    }

    private void deleteOpportunity(Opportunity opportunity) throws SQLException, ClassNotFoundException {
        databaseCommunicator.deleteOpportunity(opportunity);
        display.opportunityDeleted();
    }

    private boolean getUserConfirmation() {
        AllMenuOptions confirmationOption = getMenuOption(Menus.CONFIRMATION.getMenu());
        return confirmationOption == AllMenuOptions.YES;
    }

    private void updateStringField(Opportunity opportunity, String columnName, String updatedField) throws SQLException, ClassNotFoundException {
        display.areYouSure();
        if (getUserConfirmation()) {
            databaseCommunicator.updateOpportunityStringField(opportunity, columnName, updatedField);
        }
    }

    private void updateNumericField(Opportunity opportunity, String columnName, int updatedField) throws SQLException, ClassNotFoundException {
        display.areYouSure();
        if (getUserConfirmation()) {
            databaseCommunicator.updateOpportunityNumericField(opportunity, columnName, updatedField);
        }
    }

    private void deleteOpportunityWithConfirmation(Opportunity opportunity) throws SQLException, ClassNotFoundException {
        display.areYouSure();
        if (getUserConfirmation()) {
            deleteOpportunity(opportunity);
        }
    }
}


