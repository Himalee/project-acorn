import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresCommunicatorTest {

    private DatabaseCommunicator databaseCommunicator;

    @Before
    public void setUp() {
        String databaseURL = System.getenv("TESTDBURL");
        databaseCommunicator = new PostgresCommunicator(databaseURL);
    }

    @Test
    public void input_convertToInsertSqlQuery() {
        String userInput = "Host code retreat at office";

        Assert.assertEquals("INSERT INTO OPPORTUNITIES (name) VALUES ('Host code retreat at office');", databaseCommunicator.convertUserInputToInsertSqlQuery(userInput));
    }

    @Test
    public void newOpportunity_writeToDatabase() throws SQLException, ClassNotFoundException {
        String newOpportunityName = "AWS Training - 2019 conference";
        String sqlQuery = String.format("INSERT INTO OPPORTUNITIES (name) VALUES ('%s');", newOpportunityName);
        databaseCommunicator.writeToDatabase(sqlQuery);

        Connection connection = databaseCommunicator.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM opportunities ORDER BY ID DESC LIMIT 1");
        rs.next();
        String lastSavedOpportunityName = rs.getString("name");

        Assert.assertEquals(newOpportunityName, lastSavedOpportunityName);

        String lastSavedOpportunityUUID = rs.getString("uuid");
        stmt.executeUpdate(String.format("DELETE FROM opportunities WHERE uuid='%s'", lastSavedOpportunityUUID));
        connection.close();
    }

    @Test
    public void columnNames_convertToReadSqlQuery() {
        String columnNames = "id, name";
        String readSqlQuery = databaseCommunicator.readOpportunitiesSqlQuery(columnNames);

        Assert.assertEquals("SELECT id, name FROM opportunities;", readSqlQuery);
    }
}
