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

    public Map<Integer, ArrayList> readAllOpportunitiesFromDatabase() throws SQLException, ClassNotFoundException {
        String readSqlQuery = readOpportunitiesSqlQuery("id, name, description, proposed_cost, user_name");
        HashMap<Integer, ArrayList> opportunities = new HashMap<>();
        Connection db = getConnection();
        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery(readSqlQuery);
        while (rs.next()) {
            ArrayList<String> opportunityDetails = new ArrayList<>();
            opportunityDetails.add(rs.getString("name"));
            opportunityDetails.add(rs.getString("description"));
            opportunityDetails.add(Integer.toString(rs.getInt("proposed_cost")));
            opportunityDetails.add(rs.getString("user_name"));
            opportunities.put(Integer.parseInt(rs.getString("id")), opportunityDetails);
        }
        return opportunities;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        return DriverManager.getConnection(databaseURL);
    }

    public String convertUserInputToInsertSqlQuery(Opportunity opportunity) {
        return String.format("INSERT INTO OPPORTUNITIES (name, description, proposed_cost, user_name) VALUES ('%s', '%s', %d, '%s');", opportunity.name, opportunity.description, opportunity.proposedCost, opportunity.userName);
    }

    public String readOpportunitiesSqlQuery(String columnNames) {
        return String.format("SELECT %s FROM opportunities;", columnNames);
    }
}

