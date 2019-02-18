import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TestHelper {

    private DatabaseCommunicator databaseCommunicator = new PostgresCommunicator(System.getenv("TESTDBURL"));

    public void writeToDatabase(Opportunity opportunity) throws SQLException, ClassNotFoundException {
        databaseCommunicator.writeToDatabase(opportunity);
    }

    public ResultSet getResultSetForLastSavedOpportunity() throws SQLException, ClassNotFoundException {
        Connection connection = databaseCommunicator.getConnection();
        Statement stmt = connection.createStatement();
        return stmt.executeQuery("SELECT * FROM opportunities ORDER BY ID DESC LIMIT 1");
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
}
