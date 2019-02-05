import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface DatabaseCommunicator {
    void writeToDatabase(Opportunity opportunity) throws SQLException, ClassNotFoundException;
    Connection getConnection() throws ClassNotFoundException, SQLException;
    List<Opportunity> readAllOpportunitiesFromDatabase() throws SQLException, ClassNotFoundException;
}
