import org.junit.Assert;
import org.junit.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class PostgresCommunicatorTest {

    private DatabaseCommunicator databaseCommunicator = new PostgresCommunicator(System.getenv("TESTDBURL"));
    private TestHelper testHelper = new TestHelper();

    public Opportunity exampleOpportunity() {
        String name = "Host code retreat at office";
        String description = "To be held on annual code retreat day";
        int cost = 12000;
        String userName = "Himalee";
        String stage = "Approved";
        String uuid = "1234";
        return new Opportunity(name, description, cost, userName, stage, uuid);
    }

    private Opportunity updatedOpportunityStringField(String columnName, String update) throws SQLException, ClassNotFoundException {
        Opportunity opportunity = exampleOpportunity();
        testHelper.writeToDatabase(opportunity);
        List<Opportunity> opportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        ResultSet resultSet = testHelper.getResultSetForLastSavedOpportunity();
        resultSet.next();
        int lastSavedOpportunityID = Integer.parseInt(resultSet.getString(TableColumns.ID.getColumnName()));
        Opportunity lastSavedOpportunity = testHelper.getOpportunity(opportunities, lastSavedOpportunityID);
        databaseCommunicator.updateOpportunityStringField(lastSavedOpportunity, columnName, update);
        List<Opportunity> updatedOpportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        return testHelper.getOpportunity(updatedOpportunities, lastSavedOpportunityID);
    }

    @Test
    public void newOpportunity_writeToDatabase() throws SQLException, ClassNotFoundException {
        Opportunity opportunity = exampleOpportunity();
        testHelper.writeToDatabase(opportunity);
        ResultSet resultSet = testHelper.getResultSetForLastSavedOpportunity();
        resultSet.next();
        String lastSavedOpportunityName = resultSet.getString(TableColumns.NAME.getColumnName());

        Assert.assertEquals(opportunity.getName(), lastSavedOpportunityName);

        tearDown();
    }

    @Test
    public void allOpportunities_readFromDatabase() throws SQLException, ClassNotFoundException {
        Opportunity opportunity = exampleOpportunity();
        testHelper.writeToDatabase(opportunity);
        List<Opportunity> opportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        ResultSet resultSet = testHelper.getResultSetForLastSavedOpportunity();
        resultSet.next();
        int lastSavedOpportunityID = Integer.parseInt(resultSet.getString(TableColumns.ID.getColumnName()));

        Opportunity lastSavedOpportunity = testHelper.getOpportunity(opportunities, lastSavedOpportunityID);

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
        testHelper.writeToDatabase(opportunity);
        List<Opportunity> opportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        ResultSet resultSet = testHelper.getResultSetForLastSavedOpportunity();
        resultSet.next();
        int lastSavedOpportunityID = Integer.parseInt(resultSet.getString(TableColumns.ID.getColumnName()));
        Opportunity lastSavedOpportunity = testHelper.getOpportunity(opportunities, lastSavedOpportunityID);
        int update = 54000;

        databaseCommunicator.updateOpportunityNumericField(lastSavedOpportunity, TableColumns.COST.getColumnName(), update);
        List<Opportunity> updatedOpportunities = databaseCommunicator.readAllOpportunitiesFromDatabase();
        Opportunity updatedOpportunity = testHelper.getOpportunity(updatedOpportunities, lastSavedOpportunityID);

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

    @Test
    public void newOpportunity_writeAndDeleteFromDatabase() throws SQLException, ClassNotFoundException {
        Opportunity opportunity = exampleOpportunity();
        testHelper.writeToDatabase(opportunity);
        String uuid = opportunity.getUuid();
        databaseCommunicator.deleteOpportunity(opportunity);

        assertFalse(databaseCommunicator.doesRowExist(uuid));
    }

    public void tearDown() throws SQLException, ClassNotFoundException {
        Connection connection = databaseCommunicator.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM opportunities ORDER BY ID DESC LIMIT 1");
        resultSet.next();
        String lastSavedOpportunityUUID = resultSet.getString(TableColumns.UUID.getColumnName());
        statement.executeUpdate(String.format("DELETE FROM opportunities WHERE uuid='%s'", lastSavedOpportunityUUID));
        connection.close();
    }
}
