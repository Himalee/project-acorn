import java.sql.SQLException;

public class BudgetTracker {

    public Display display;
    public DatabaseCommunicator databaseCommunicator;
    public static final int ADD_NEW_OPPORTUNITY = 2;

    public BudgetTracker(Display display, DatabaseCommunicator databaseCommunicator) {
        this.display = display;
        this.databaseCommunicator = databaseCommunicator;
    }

    public void start() throws SQLException, ClassNotFoundException {
       display.welcomeUser();
       display.menu();
       if (getMenuChoice() == ADD_NEW_OPPORTUNITY) {
           writeUserInputToDatabase();
       }
       display.goodbye();
    }

    private int getMenuChoice() {
        return Integer.parseInt(display.getUserInput());
    }

    private void writeUserInputToDatabase() throws SQLException, ClassNotFoundException {
        display.getOpportunityName();
        String opportunityName = display.getUserInput();
        String sqlQuery = databaseCommunicator.convertUserInputToSqlQuery(opportunityName);
        databaseCommunicator.writeToDatabase(sqlQuery);
        display.opportunityWrittenToDatabase();
    }
}
