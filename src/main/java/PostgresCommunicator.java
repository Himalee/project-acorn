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
        String sqlQuery = String.format("INSERT INTO OPPORTUNITIES (name, description, proposed_cost, user_name, stage) VALUES ('%s', '%s', %d, '%s', '%s');", opportunity.getName(), opportunity.getDescription(), opportunity.getProposedCost(), opportunity.getUserName(), opportunity.getStage());
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
            String stage = rs.getString("stage");
            Opportunity opportunity = new Opportunity(name, description, proposedCost, userName, stage);
            opportunity.setId(Integer.parseInt(rs.getString("id")));
            opportunities.add(opportunity);
        }
        return opportunities;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        return DriverManager.getConnection(databaseURL);
    }

    public void updateName(Opportunity opportunity, String newName) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        Connection db = getConnection();
        stmt = db.createStatement();
        int id = opportunity.getId();
        String sqlQuery = String.format("UPDATE opportunities SET name = '%s' WHERE id = %d;", newName, id);
        stmt.executeUpdate(sqlQuery);
        stmt.close();
        db.close();
    }
}

