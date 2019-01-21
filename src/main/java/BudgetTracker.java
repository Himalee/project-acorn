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

    private void writeUserInputToDatabase() throws SQLException, ClassNotFoundException {
        display.getOpportunityName();
        String opportunityName = display.getUserInput();
        String sqlQuery = databaseCommunicator.convertUserInputToInsertSqlQuery(opportunityName);
        databaseCommunicator.writeToDatabase(sqlQuery);
        display.opportunityWrittenToDatabase();
    }
}
