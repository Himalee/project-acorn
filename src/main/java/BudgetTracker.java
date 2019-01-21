import java.sql.SQLException;

public class BudgetTracker {

    private Display display;
    private DatabaseCommunicator databaseCommunicator;

    public BudgetTracker(Display display, DatabaseCommunicator databaseCommunicator) {
        this.display = display;
        this.databaseCommunicator = databaseCommunicator;
    }

    public void start() throws SQLException, ClassNotFoundException {
       display.welcomeUser();
       display.formatMenu();
       String menuChoice = display.getUserInput();
       MenuOptions option = MenuOptions.findChoice(menuChoice);
       if (option == MenuOptions.ADD_NEW_OPP) {
           writeUserInputToDatabase();
       } else if (option == MenuOptions.DISPLAY_ALL_OPP) {
          display.formatOpportunities(databaseCommunicator.readAllOpportunitiesFromDatabase());
       } else {
           display.goodbye();
       }
    }

    private int getMenuChoice() {
        return Integer.parseInt(display.getUserInputString());
    }

    private void writeUserInputToDatabase() throws SQLException, ClassNotFoundException {
        String sqlQuery = databaseCommunicator.convertUserInputToInsertSqlQuery(createNewOpportunity());
        databaseCommunicator.writeToDatabase(sqlQuery);
        display.opportunityWrittenToDatabase();
    }

    private Opportunity createNewOpportunity() {
        display.getOpportunityName();
        String name = display.getUserInputString();
        display.getOpportunityDescription();
        String description = display.getUserInputString();
        display.getOpportunityProposedCost();
        int cost = display.getUserInputInteger();
        return new Opportunity(name, description, cost);
    }
}


