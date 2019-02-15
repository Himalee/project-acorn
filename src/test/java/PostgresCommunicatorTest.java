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

    private void writeToDatabase(Opportunity opportunity) throws SQLException, ClassNotFoundException {
        databaseCommunicator.writeToDatabase(opportunity);
    }

    private ResultSet getResultSetForLastSavedOpportunity() throws SQLException, ClassNotFoundException {
        Connection connection = databaseCommunicator.getConnection();
        Statement stmt = connection.createStatement();
        return stmt.executeQuery("SELECT * FROM opportunities ORDER BY ID DESC LIMIT 1");
    }

    private Opportunity exampleOpportunity() {
        String name = "Host code retreat at office";
        String description = "To be held on annual code retreat day";
        int cost = 12000;
        String userName = "Himalee";
        String stage = "Approved";
        return new Opportunity(name, description, cost, userName, stage);
    }

    private Opportunity getOpportunity(List<Opportunity> opportunities, int lastSavedOpportunityId) {
        for (Opportunity opportunity : opportunities) {
            int id = opportunity.getId();
            if (lastSavedOpportunityId == id) {
                return opportunity;
            }
        }
        return null;
    }

    private Opportunity updatedOpportunityStringField(String columnName, String update) throws SQLException, ClassNotFoundException {
        Opportunity opportunity = exampleOpportunity();
        writeToDatabase(opportunity);
        List<Opportunity> opportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        ResultSet rs = getResultSetForLastSavedOpportunity();
        rs.next();
        int lastSavedOpportunityID = Integer.parseInt(rs.getString(TableColumns.ID.getColumnName()));
        Opportunity lastSavedOpportunity = getOpportunity(opportunities, lastSavedOpportunityID);
        databaseCommunicator.updateOpportunityStringField(lastSavedOpportunity, columnName, update);
        List<Opportunity> updatedOpportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        return getOpportunity(updatedOpportunities, lastSavedOpportunityID);
    }

    @Test
    public void newOpportunity_writeToDatabase() throws SQLException, ClassNotFoundException {
        Opportunity opportunity = exampleOpportunity();
        writeToDatabase(opportunity);
        ResultSet rs = getResultSetForLastSavedOpportunity();
        rs.next();
        String lastSavedOpportunityName = rs.getString(TableColumns.NAME.getColumnName());

        Assert.assertEquals(opportunity.getName(), lastSavedOpportunityName);

        tearDown();
    }

    @Test
    public void allOpportunities_readFromDatabase() throws SQLException, ClassNotFoundException {
        Opportunity opportunity = exampleOpportunity();
        writeToDatabase(opportunity);
        List<Opportunity> opportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        ResultSet rs = getResultSetForLastSavedOpportunity();
        rs.next();
        int lastSavedOpportunityID = Integer.parseInt(rs.getString(TableColumns.ID.getColumnName()));

        Opportunity lastSavedOpportunity = getOpportunity(opportunities, lastSavedOpportunityID);

        Assert.assertEquals(opportunity.getName(), lastSavedOpportunity.getName());
        Assert.assertEquals(opportunity.getDescription(), lastSavedOpportunity.getDescription());
        Assert.assertEquals(opportunity.getProposedCost(), lastSavedOpportunity.getProposedCost());
        Assert.assertEquals(opportunity.getUserName(), lastSavedOpportunity.getUserName());
        Assert.assertEquals(opportunity.getStage(), lastSavedOpportunity.getStage());

        tearDown();
    }

    @Test
    public void getOpportunityFromDatabase_updateName() throws SQLException, ClassNotFoundException {
        String update = "Host GOL code retreat at office";
        Opportunity updatedOpportunity = updatedOpportunityStringField(TableColumns.NAME.getColumnName(), update);

        Assert.assertEquals(update, updatedOpportunity.getName());

        tearDown();
    }

    @Test
    public void getOpportunityFromDatabase_updateDescription() throws SQLException, ClassNotFoundException {
        String update = "Host GOL code retreat at office in April 2019";
        Opportunity updatedOpportunity = updatedOpportunityStringField(TableColumns.DESCRIPTION.getColumnName(), update);

        Assert.assertEquals(update, updatedOpportunity.getDescription());

        tearDown();
    }

    @Test
    public void getOpportunityFromDatabase_updateCost() throws SQLException, ClassNotFoundException {
        Opportunity opportunity = exampleOpportunity();
        writeToDatabase(opportunity);
        List<Opportunity> opportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        ResultSet rs = getResultSetForLastSavedOpportunity();
        rs.next();
        int lastSavedOpportunityID = Integer.parseInt(rs.getString(TableColumns.ID.getColumnName()));
        Opportunity lastSavedOpportunity = getOpportunity(opportunities, lastSavedOpportunityID);
        int update = 54000;

        databaseCommunicator.updateOpportunityNumericField(lastSavedOpportunity, TableColumns.COST.getColumnName(), update);
        List<Opportunity> updatedOpportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        Opportunity updatedOpportunity = getOpportunity(updatedOpportunities, lastSavedOpportunityID);

        Assert.assertEquals(update, updatedOpportunity.getProposedCost());

        tearDown();
    }

    @Test
    public void getOpportunityFromDatabase_updateUserName() throws SQLException, ClassNotFoundException {
        String update = "Ben Wyatt";
        Opportunity updatedOpportunity = updatedOpportunityStringField(TableColumns.USER_NAME.getColumnName(), update);

        Assert.assertEquals(update, updatedOpportunity.getUserName());

        tearDown();
    }

    @Test
    public void getOpportunityFromDatabase_updateStage() throws SQLException, ClassNotFoundException {
        String update = "Declined";
        Opportunity updatedOpportunity = updatedOpportunityStringField(TableColumns.STAGE.getColumnName(), update);

        Assert.assertEquals(update, updatedOpportunity.getStage());

        tearDown();
    }

    public void tearDown() throws SQLException, ClassNotFoundException {
        Connection connection = databaseCommunicator.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM opportunities ORDER BY ID DESC LIMIT 1");
        rs.next();
        String lastSavedOpportunityUUID = rs.getString(TableColumns.UUID.getColumnName());
        stmt.executeUpdate(String.format("DELETE FROM opportunities WHERE uuid='%s'", lastSavedOpportunityUUID));
        connection.close();
    }
}
