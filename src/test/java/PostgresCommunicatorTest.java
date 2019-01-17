import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostgresCommunicatorTest {

    private DatabaseCommunicator databaseCommunicator;

    @Before
    public void setUp() {
        String databaseURL = System.getenv("TESTDBURL");
        databaseCommunicator = new PostgresCommunicator(databaseURL);
    }

    public void writeToDatabase(Opportunity opportunity) throws SQLException, ClassNotFoundException {
        String sqlQuery = String.format("INSERT INTO OPPORTUNITIES (name, description) VALUES ('%s', '%s');", opportunity.name, opportunity.description);
        databaseCommunicator.writeToDatabase(sqlQuery);
    }

    public ResultSet getLastSavedOpportunity() throws SQLException, ClassNotFoundException {
        Connection connection = databaseCommunicator.getConnection();
        Statement stmt = connection.createStatement();
        return stmt.executeQuery("SELECT * FROM opportunities ORDER BY ID DESC LIMIT 1");
    }

    public String getUUIDofLastSavedOpportunity(ResultSet rs) throws SQLException {
        return rs.getString("uuid");
    }

    public void deleteLastSavedOpportunity(String lastSavedOpportunityUUID) throws SQLException, ClassNotFoundException {
        Connection connection = databaseCommunicator.getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(String.format("DELETE FROM opportunities WHERE uuid='%s'", lastSavedOpportunityUUID));
        connection.close();
    }

    public Opportunity exampleOpportunity() {
        String name = "Host code retreat at office";
        String description = "To be held on annual code retreat day";
        return new Opportunity(name, description);
    }

    @Test
    public void input_convertToInsertSqlQuery() {
        String expectedSqlQuery = "INSERT INTO OPPORTUNITIES (name, description) VALUES ('Host code retreat at office', 'To be held on annual code retreat day');";
        Assert.assertEquals(expectedSqlQuery, databaseCommunicator.convertUserInputToInsertSqlQuery(exampleOpportunity()));
    }

    @Test
    public void newOpportunity_writeToDatabase() throws SQLException, ClassNotFoundException {
        Opportunity opportunity = exampleOpportunity();
        writeToDatabase(opportunity);
        ResultSet rs = getLastSavedOpportunity();
        rs.next();
        String lastSavedOpportunityName = rs.getString("name");

        Assert.assertEquals(opportunity.name, lastSavedOpportunityName);

        deleteLastSavedOpportunity(getUUIDofLastSavedOpportunity(rs));
    }

    @Test
    public void columnNames_convertToReadSqlQuery() {
        String columnNames = "id, name";
        String readSqlQuery = databaseCommunicator.readOpportunitiesSqlQuery(columnNames);

        Assert.assertEquals("SELECT id, name FROM opportunities;", readSqlQuery);
    }

    @Test
    public void allOpportunities_readFromDatabase() throws SQLException, ClassNotFoundException {
        Opportunity opportunity = exampleOpportunity();
        writeToDatabase(opportunity);
        HashMap<String, ArrayList> allOpportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        ResultSet rs = getLastSavedOpportunity();
        rs.next();
        String lastSavedOpportunityID = rs.getString("id");
        List<String> opportunityDetails = allOpportunities.get(String.format("%s", lastSavedOpportunityID));

        Assert.assertTrue(opportunityDetails.contains(opportunity.name));

        deleteLastSavedOpportunity(getUUIDofLastSavedOpportunity(rs));
    }
}
