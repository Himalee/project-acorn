import java.sql.SQLException;

public class ProjectAcorn {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        CommandLineInterface cli = new CommandLineInterface(System.out, System.in);
        Validator validator = new Validator();
        Display display = new Display(cli, validator);
        String databaseURL = System.getenv("PRODDBURL");
        DatabaseCommunicator databaseCommunicator = new PostgresCommunicator(databaseURL);
        BudgetTracker budgetTracker = new BudgetTracker(display, databaseCommunicator);
        budgetTracker.start();
    }
}
