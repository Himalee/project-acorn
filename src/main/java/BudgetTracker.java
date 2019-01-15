import java.sql.SQLException;

public class BudgetTracker {

    private Display display;
    private DatabaseCommunicator databaseCommunicator;
    public static final int ADD_NEW_OPPORTUNITY = 2;
    public static final int DISPLAY_ALL_OPPORTUNITIES = 3;

    public BudgetTracker(Display display, DatabaseCommunicator databaseCommunicator) {
        this.display = display;
        this.databaseCommunicator = databaseCommunicator;
    }

    public void start() throws SQLException, ClassNotFoundException {
       display.welcomeUser();
       display.menu();
       int menuChoice = getMenuChoice();
       if (menuChoice == ADD_NEW_OPPORTUNITY) {
           writeUserInputToDatabase();
       } else if (menuChoice == DISPLAY_ALL_OPPORTUNITIES) {
           display.listAllOpportunities(databaseCommunicator.readAllOpportunitiesFromDatabase());
       } else {
           display.goodbye();
       }
    }

    private int getMenuChoice() {
        return Integer.parseInt(display.getUserInput());
    }

    private void writeUserInputToDatabase() throws SQLException, ClassNotFoundException {
        display.getOpportunityName();
        String opportunityName = display.getUserInput();
        String sqlQuery = databaseCommunicator.convertUserInputToInsertSqlQuery(opportunityName);
        databaseCommunicator.writeToDatabase(sqlQuery);
        display.opportunityWrittenToDatabase();
    }
}
