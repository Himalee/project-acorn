import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class PostgresCommunicator implements DatabaseCommunicator {

    private String databaseURL;

    public PostgresCommunicator(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public void writeToDatabase(Opportunity opportunity) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        Connection db = getConnection();
        stmt = db.createStatement();
        String sqlQuery = String.format("INSERT INTO OPPORTUNITIES (name, description, proposed_cost, user_name) VALUES ('%s', '%s', %d, '%s');", opportunity.name, opportunity.description, opportunity.proposedCost, opportunity.userName);
        stmt.executeUpdate(sqlQuery);
        stmt.close();
        db.close();
    }

    public List<Opportunity> readAllOpportunitiesFromDatabase() throws SQLException, ClassNotFoundException {
        List<Opportunity> opportunities = new ArrayList<>();
        String readSqlQuery = "SELECT * FROM opportunities";
        Connection db = getConnection();
        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery(readSqlQuery);
        while (rs.next()) {
            String name = rs.getString("name");
            String description = rs.getString("description");
            int proposedCost = rs.getInt("proposed_cost");
            String userName = rs.getString("user_name");
            Opportunity opportunity = new Opportunity(name, description, proposedCost, userName);
            opportunity.setId(Integer.parseInt(rs.getString("id")));
            opportunities.add(opportunity);
        }
        return opportunities;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        return DriverManager.getConnection(databaseURL);
    }
}

