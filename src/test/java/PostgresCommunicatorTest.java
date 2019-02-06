import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PostgresCommunicatorTest {

    private DatabaseCommunicator databaseCommunicator;

    @Before
    public void setUp() {
        String databaseURL = System.getenv("TESTDBURL");
        databaseCommunicator = new PostgresCommunicator(databaseURL);
    }

    public void writeToDatabase(Opportunity opportunity) throws SQLException, ClassNotFoundException {
        databaseCommunicator.writeToDatabase(opportunity);
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
        int cost = 12000;
        String userName = "Himalee";
        String stage = "Approved";
        return new Opportunity(name, description, cost, userName, stage);
    }

    public Opportunity getOpportunity(List<Opportunity> opportunities, int lastSavedOpportunityId) {
        for (Opportunity opportunity : opportunities) {
            int id = opportunity.getId();
            if (lastSavedOpportunityId == id) {
                return opportunity;
            }
        }
        return null;
    }

    @Test
    public void newOpportunity_writeToDatabase() throws SQLException, ClassNotFoundException {
        Opportunity opportunity = exampleOpportunity();
        writeToDatabase(opportunity);
        ResultSet rs = getLastSavedOpportunity();
        rs.next();
        String lastSavedOpportunityName = rs.getString("name");

        Assert.assertEquals(opportunity.getName(), lastSavedOpportunityName);

        deleteLastSavedOpportunity(getUUIDofLastSavedOpportunity(rs));
    }

    @Test
    public void allOpportunities_readFromDatabase() throws SQLException, ClassNotFoundException {
        Opportunity opportunity = exampleOpportunity();
        writeToDatabase(opportunity);
        List<Opportunity> opportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        ResultSet rs = getLastSavedOpportunity();
        rs.next();
        int lastSavedOpportunityID = Integer.parseInt(rs.getString("id"));

        Opportunity lastSavedOpportunity = getOpportunity(opportunities, lastSavedOpportunityID);

        Assert.assertEquals(opportunity.getName(), lastSavedOpportunity.getName());
        Assert.assertEquals(opportunity.getDescription(), lastSavedOpportunity.getDescription());
        Assert.assertEquals(opportunity.getProposedCost(), lastSavedOpportunity.getProposedCost());
        Assert.assertEquals(opportunity.getUserName(), lastSavedOpportunity.getUserName());
        Assert.assertEquals(opportunity.getStage(), lastSavedOpportunity.getStage());

        deleteLastSavedOpportunity(getUUIDofLastSavedOpportunity(rs));
    }
}
