import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseCommunicator {
    void writeToDatabase(String sqlQuery) throws SQLException, ClassNotFoundException;
    Connection getConnection() throws ClassNotFoundException, SQLException;
    String convertUserInputToSqlQuery(String userInput);
}
