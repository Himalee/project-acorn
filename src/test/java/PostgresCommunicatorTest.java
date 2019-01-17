import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class PostgresCommunicatorTest {

    private DatabaseCommunicator databaseCommunicator;

    @Before
    public void setUp() {
        String databaseURL = System.getenv("TESTDBURL");
        databaseCommunicator = new PostgresCommunicator(databaseURL);
    }

    public void writeToDatabase(String newOpportunityName) throws SQLException, ClassNotFoundException {
        String sqlQuery = String.format("INSERT INTO OPPORTUNITIES (name) VALUES ('%s');", newOpportunityName);
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

    @Test
    public void input_convertToInsertSqlQuery() {
        String userInput = "Host code retreat at office";

        Assert.assertEquals("INSERT INTO OPPORTUNITIES (name) VALUES ('Host code retreat at office');", databaseCommunicator.convertUserInputToInsertSqlQuery(userInput));
    }

    @Test
    public void newOpportunity_writeToDatabase() throws SQLException, ClassNotFoundException {
        String newOpportunityName = "AWS Training - 2019 conference";
        writeToDatabase(newOpportunityName);
        ResultSet rs = getLastSavedOpportunity();
        rs.next();
        String lastSavedOpportunityName = rs.getString("name");

        Assert.assertEquals(newOpportunityName, lastSavedOpportunityName);

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
        String newOpportunityName = "Socrates 2019 travel expenses";
        writeToDatabase(newOpportunityName);
        HashMap<String, List> opportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        ResultSet rs = getLastSavedOpportunity();
        rs.next();
        String lastSavedOpportunityID = rs.getString("id");
        List<String> opportunityDetails = opportunities.get(String.format("%s", lastSavedOpportunityID));

        Assert.assertTrue(opportunityDetails.contains(newOpportunityName));

        deleteLastSavedOpportunity(getUUIDofLastSavedOpportunity(rs));
    }
}
