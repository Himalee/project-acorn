import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

import static org.hamcrest.CoreMatchers.containsString;

public class BudgetTrackerTest {

    private BudgetTracker budgetTracker;
    private DatabaseCommunicator databaseCommunicator;


    @Before
    public void setUp() {
        String databaseURL = System.getenv("DBURL");
        databaseCommunicator = new PostgresCommunicator(databaseURL);
    }

    @Test
    public void createNewBudgetTracker_welcomesUser() throws SQLException, ClassNotFoundException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayInputStream userInput = new ByteArrayInputStream("1".getBytes());
        CommandLineInterface cli = new CommandLineInterface(new PrintStream(outContent), userInput);
        Display display = new Display(cli);
        budgetTracker = new BudgetTracker(display, databaseCommunicator);

        budgetTracker.start();

        String output = outContent.toString();
        Assert.assertThat(output, containsString("Welcome"));
    }

    @Test
    public void createNewBudgetTracker_saveNewOpportunityToDatabase() throws SQLException, ClassNotFoundException {
        String newOpportunityName = "Black girl tech sponsorship";
        String simulatedUserInput = String.format("2\n%s\n", newOpportunityName);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayInputStream userInput = new ByteArrayInputStream(simulatedUserInput.getBytes());
        CommandLineInterface cli = new CommandLineInterface(new PrintStream(outContent), userInput);
        Display display = new Display(cli);
        budgetTracker = new BudgetTracker(display, databaseCommunicator);

        budgetTracker.start();

        String output = outContent.toString();
        Assert.assertThat(output, containsString("saved"));

        Connection connection = databaseCommunicator.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM opportunities ORDER BY ID DESC LIMIT 1");
        rs.next();
        String lastSavedOpportunityUUID = rs.getString("uuid");
        stmt.executeUpdate(String.format("DELETE FROM opportunities WHERE uuid='%s'", lastSavedOpportunityUUID));
        connection.close();
    }
}

