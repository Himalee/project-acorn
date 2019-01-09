import java.sql.SQLException;

public class ProjectAcorn {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        CommandLineInterface cli = new CommandLineInterface(System.out, System.in);
        Display display = new Display(cli);
        String databaseURL = System.getenv("DBURL");
        DatabaseCommunicator databaseCommunicator = new PostgresCommunicator(databaseURL);
        BudgetTracker budgetTracker = new BudgetTracker(display, databaseCommunicator);
        budgetTracker.start();
    }
}
