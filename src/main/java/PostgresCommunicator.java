import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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

    public HashMap<String, ArrayList> readAllOpportunitiesFromDatabase() throws SQLException, ClassNotFoundException {
        String readSqlQuery = readOpportunitiesSqlQuery("id, name, description");
        HashMap<String, ArrayList> opportunities = new HashMap<>();
        Connection db = getConnection();
        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery(readSqlQuery);
        while (rs.next()) {
            ArrayList<String> opportunityDetails = new ArrayList<>();
            opportunityDetails.add(rs.getString("name"));
            opportunityDetails.add(rs.getString("description"));
            opportunities.put(rs.getString("id"), opportunityDetails);
        }
        return opportunities;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        return DriverManager.getConnection(databaseURL);
    }

    public String convertUserInputToInsertSqlQuery(Opportunity opportunity) {
        return String.format("INSERT INTO OPPORTUNITIES (name, description) VALUES ('%s', '%s');", opportunity.name, opportunity.description);
    }

    public String readOpportunitiesSqlQuery(String columnNames) {
        return String.format("SELECT %s FROM opportunities;", columnNames);
    }
}

