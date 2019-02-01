import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface DatabaseCommunicator {
    void writeToDatabase(String sqlQuery) throws SQLException, ClassNotFoundException;
    Connection getConnection() throws ClassNotFoundException, SQLException;
    String convertUserInputToInsertSqlQuery(Opportunity opportunity);
    String readOpportunitiesSqlQuery(String columnNames);
    Map<Integer, ArrayList> readAllOpportunitiesFromDatabase() throws SQLException, ClassNotFoundException;
}
