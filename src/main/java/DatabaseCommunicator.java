import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface DatabaseCommunicator {
    void writeToDatabase(Opportunity opportunity) throws SQLException, ClassNotFoundException;
    Connection getConnection() throws ClassNotFoundException, SQLException;
    Map<Integer, ArrayList> readAllOpportunitiesFromDatabase() throws SQLException, ClassNotFoundException;
}
