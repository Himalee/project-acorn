import java.sql.*;

class PostgresCommunicator implements DatabaseCommunicator {

    private String databaseURL;

    public PostgresCommunicator(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public void writeToDatabase(String sqlQuery) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        Connection db = getConnection();
        stmt = db.createStatement();
        stmt.executeUpdate(sqlQuery);
        stmt.close();
        db.close();
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        return DriverManager.getConnection(databaseURL);
    }

    public String convertUserInputToInsertSqlQuery(String userInput) {
        return String.format("INSERT INTO OPPORTUNITIES (name) VALUES ('%s');", userInput);
    }

    public String readOpportunitiesSqlQuery(String columnNames) {
        return String.format("SELECT %s FROM opportunities;", columnNames);
    }
}


