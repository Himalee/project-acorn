import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

public class BudgetTrackerTest {

    private BudgetTracker budgetTracker;
    private DatabaseCommunicator databaseCommunicator;
    private static final String QUIT_APP = "q";
    private static final String ADD_NEW_OPP_TO_DB = "a";
    private static final String TO_BE_DISCUSSED = "t";
    private static final String SEARCH_BY_ID = "i";
    private Validator validator;

    @Before
    public void setUp() {
        String databaseURL = System.getenv("TESTDBURL");
        databaseCommunicator = new PostgresCommunicator(databaseURL);
    }

    public Display createNewDisplay(ByteArrayOutputStream outContent, String simulatedUserInput) {
        ByteArrayInputStream userInput = new ByteArrayInputStream(simulatedUserInput.getBytes());
        CommandLineInterface cli = new CommandLineInterface(new PrintStream(outContent), userInput);
        validator = new Validator();
        return new Display(cli, validator);
    }

    public Opportunity getOpportunityByUserName(String userName) throws SQLException, ClassNotFoundException {
        List<Opportunity> opportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        for (Opportunity opp : opportunities) {
            if (userName.equals(opp.getUserName())) {
                return opp;
            }
        }
        return null;
    }

    @Test
    public void createNewBudgetTracker_welcomesUser() throws SQLException, ClassNotFoundException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        String simulatedUserInput = String.format("%s", QUIT_APP);
        Display display = createNewDisplay(outContent, simulatedUserInput);

        budgetTracker = new BudgetTracker(display, databaseCommunicator);

        budgetTracker.start();

        String output = outContent.toString();
        Assert.assertThat(output, containsString("Welcome"));
    }

    @Test
    public void createNewBudgetTracker_saveNewOpportunityToDatabase() throws SQLException, ClassNotFoundException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        String newUserName = "Himalee";
        String newOpportunityName = "Black girl tech sponsorship";
        String newOpportunityDescription = "Offer paid internships";
        String simulatedUserInput = String.format("%s\n%s\n%s\n123.45\n%s\n%s\n", ADD_NEW_OPP_TO_DB, newOpportunityName, newOpportunityDescription, newUserName, TO_BE_DISCUSSED);
        Display display = createNewDisplay(outContent, simulatedUserInput);

        budgetTracker = new BudgetTracker(display, databaseCommunicator);

        budgetTracker.start();

        String output = outContent.toString();
        Assert.assertThat(output, containsString("saved"));

        tearDown();
    }

    @Test
    public void createNewBudgetTracker_displayOpportunityBasedOnId() throws SQLException, ClassNotFoundException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        Opportunity opportunity = new Opportunity("Hello", "World", 1400, "HimaleeTailor", "Approved");
        databaseCommunicator.writeToDatabase(opportunity);

        Opportunity savedOpportunity = getOpportunityByUserName("HimaleeTailor");
        int id = savedOpportunity.getId();

        String simulatedUserInput = String.format("%s\n%d\n", SEARCH_BY_ID, id);

        Display display = createNewDisplay(outContent, simulatedUserInput);
        budgetTracker = new BudgetTracker(display, databaseCommunicator);

        budgetTracker.start();

        String output = outContent.toString();
        Assert.assertThat(output, containsString("Hello\nWorld\n1400\nHimaleeTailor\nApproved"));

        tearDown();
    }

    public void tearDown() throws SQLException, ClassNotFoundException {
        Connection connection = databaseCommunicator.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM opportunities ORDER BY ID DESC LIMIT 1");
        rs.next();
        String lastSavedOpportunityUUID = rs.getString("uuid");
        stmt.executeUpdate(String.format("DELETE FROM opportunities WHERE uuid='%s'", lastSavedOpportunityUUID));
        connection.close();
    }
}


