import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface DatabaseCommunicator {
    void writeToDatabase(String sqlQuery) throws SQLException, ClassNotFoundException;
    Connection getConnection() throws ClassNotFoundException, SQLException;
    String convertUserInputToInsertSqlQuery(String userInput);
    String readOpportunitiesSqlQuery(String columnNames);
    HashMap<String, ArrayList> readAllOpportunitiesFromDatabase() throws SQLException, ClassNotFoundException;
}
