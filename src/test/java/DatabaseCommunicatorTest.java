import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCommunicatorTest {

    @Test
    public void newOpportunity_writeToDatabase() throws SQLException, ClassNotFoundException {
        String databaseURL = System.getenv("DBURL");
        DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator(databaseURL);
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
}
