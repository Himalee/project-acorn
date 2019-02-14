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
    private static final String UPDATE_OPPORTUNITY = "u";
    private static final String UPDATE_NAME = "n";
    private static final String UPDATE_DESCRIPTION = "d";
    private static final String UPDATE_COST = "c";
    private static final String UPDATE_USER_NAME = "r";
    private static final String UPDATE_STAGE = "s";
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
        Opportunity opportunity = new Opportunity("Hello", "World", 1400, "HelloWorld", "Approved");
        databaseCommunicator.writeToDatabase(opportunity);

        Opportunity savedOpportunity = getOpportunityByUserName("HelloWorld");
        int id = savedOpportunity.getId();

        String simulatedUserInput = String.format("%s\n%d\n", SEARCH_BY_ID, id);

        Display display = createNewDisplay(outContent, simulatedUserInput);
        budgetTracker = new BudgetTracker(display, databaseCommunicator);

        budgetTracker.start();

        String output = outContent.toString();
        Assert.assertThat(output, containsString("Hello\nWorld\n1400\nHelloWorld\nApproved"));

        tearDown();
    }

    @Test
    public void createNewBudgetTracker_updateOpportunityName() throws SQLException, ClassNotFoundException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        Opportunity opportunity = new Opportunity("Foo", "Bar", 1200, "FooBar", "Expired");
        databaseCommunicator.writeToDatabase(opportunity);
        Opportunity savedOpportunity = getOpportunityByUserName("FooBar");
        int id = savedOpportunity.getId();
        String newName = "GoodbyeWorld";
        String simulatedUserInput = String.format("%s\n%d\n%s\n%s\n", UPDATE_OPPORTUNITY, id, UPDATE_NAME, newName);
        Display display = createNewDisplay(outContent, simulatedUserInput);
        budgetTracker = new BudgetTracker(display, databaseCommunicator);

        budgetTracker.start();

        String output = outContent.toString();
        String expectedOutput = String.format("%d. GoodbyeWorld\nBar\n1200\nFooBar\nExpired", id);

        Assert.assertThat(output, containsString(expectedOutput));

        tearDown();
    }

    @Test
    public void createNewBudgetTracker_updateOpportunityDescription() throws SQLException, ClassNotFoundException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        Opportunity opportunity = new Opportunity("Host meet up", "In Spring 2019", 1400, "Burt Macklin", "Approved");
        databaseCommunicator.writeToDatabase(opportunity);
        Opportunity savedOpportunity = getOpportunityByUserName("Burt Macklin");
        int id = savedOpportunity.getId();
        String newDescription = "In Winter 2019";
        String simulatedUserInput = String.format("%s\n%d\n%s\n%s\n", UPDATE_OPPORTUNITY, id, UPDATE_DESCRIPTION, newDescription);
        Display display = createNewDisplay(outContent, simulatedUserInput);
        budgetTracker = new BudgetTracker(display, databaseCommunicator);

        budgetTracker.start();

        String output = outContent.toString();
        String expectedOutput = String.format("%d. Host meet up\nIn Spring 2019\n1400\nBurt Macklin\nApproved", id);

        Assert.assertThat(output, containsString(expectedOutput));

        tearDown();
    }

    @Test
    public void createNewBudgetTracker_updateOpportunityCost() throws SQLException, ClassNotFoundException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        Opportunity opportunity = new Opportunity("Host Code First Girls", "8 week course", 12000, "Leslie K", "Approved");
        databaseCommunicator.writeToDatabase(opportunity);
        Opportunity savedOpportunity = getOpportunityByUserName("Leslie K");
        int id = savedOpportunity.getId();
        String simulatedUserInput = String.format("%s\n%d\n%s\n130.00\n", UPDATE_OPPORTUNITY, id, UPDATE_COST);
        Display display = createNewDisplay(outContent, simulatedUserInput);
        budgetTracker = new BudgetTracker(display, databaseCommunicator);

        budgetTracker.start();

        String output = outContent.toString();
        String expectedOutput = String.format("%d. Host Code First Girls\n8 week course\n13000\nLeslie K\nApproved", id);

        Assert.assertThat(output, containsString(expectedOutput));

        tearDown();
    }

    @Test
    public void createNewBudgetTracker_updateUserName() throws SQLException, ClassNotFoundException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        Opportunity opportunity = new Opportunity("Code retreat", "Winter 2019", 15500, "Ben", "Approved");
        databaseCommunicator.writeToDatabase(opportunity);
        Opportunity savedOpportunity = getOpportunityByUserName("Ben");
        int id = savedOpportunity.getId();
        String newUserName = "April Ludgate";
        String simulatedUserInput = String.format("%s\n%d\n%s\n%s\n", UPDATE_OPPORTUNITY, id, UPDATE_USER_NAME, newUserName);
        Display display = createNewDisplay(outContent, simulatedUserInput);
        budgetTracker = new BudgetTracker(display, databaseCommunicator);

        budgetTracker.start();

        String output = outContent.toString();
        String expectedOutput = String.format("%d. Code retreat\nWinter 2019\n15500\nApril Ludgate\nApproved", id);

        Assert.assertThat(output, containsString(expectedOutput));

        tearDown();
    }

    @Test
    public void createNewBudgetTracker_updateStage() throws SQLException, ClassNotFoundException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        Opportunity opportunity = new Opportunity("Code retreat", "Winter 2019", 15500, "Tom", "Approved");
        databaseCommunicator.writeToDatabase(opportunity);
        Opportunity savedOpportunity = getOpportunityByUserName("Tom");
        int id = savedOpportunity.getId();
        String newStageChoice = "x";
        String simulatedUserInput = String.format("%s\n%d\n%s\n%s\n", UPDATE_OPPORTUNITY, id, UPDATE_STAGE, newStageChoice);
        Display display = createNewDisplay(outContent, simulatedUserInput);
        budgetTracker = new BudgetTracker(display, databaseCommunicator);

        budgetTracker.start();

        String output = outContent.toString();
        String expectedOutput = String.format("%d. Code retreat\nWinter 2019\n15500\nTom\nExpired", id);

        Assert.assertThat(output, containsString(expectedOutput));

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


