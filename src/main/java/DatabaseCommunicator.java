import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DatabaseCommunicator {
    void writeToDatabase(Opportunity opportunity) throws SQLException, ClassNotFoundException;
    Connection getConnection() throws ClassNotFoundException, SQLException;
    List<Opportunity> readAllOpportunitiesFromDatabase() throws SQLException, ClassNotFoundException;
    void updateOpportunityStringField(Opportunity opportunity, String columnName, String newName) throws SQLException, ClassNotFoundException;
    void updateOpportunityNumericField(Opportunity opportunity, String columnName, int update) throws SQLException, ClassNotFoundException;
}
