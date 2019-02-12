import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class PostgresCommunicator implements DatabaseCommunicator {

    private String databaseURL;

    public PostgresCommunicator(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public void writeToDatabase(Opportunity opportunity) throws SQLException, ClassNotFoundException {
        Connection db = getConnection();
        String sqlQuery = String.format("INSERT INTO OPPORTUNITIES (name, description, proposed_cost, user_name, stage) VALUES ('%s', '%s', %d, '%s', '%s');", opportunity.getName(), opportunity.getDescription(), opportunity.getProposedCost(), opportunity.getUserName(), opportunity.getStage());
        executeQuery(db, sqlQuery);
        closeDatabaseConnection(db);
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

    public void updateOpportunity(Opportunity opportunity, String columnName, String newName) throws SQLException, ClassNotFoundException {
        Connection db = getConnection();
        int id = opportunity.getId();
        String sqlQuery = String.format("UPDATE opportunities SET %s = '%s' WHERE id = %d;", columnName, newName, id);
        executeQuery(db, sqlQuery);
        closeDatabaseConnection(db);
    }

    private void executeQuery(Connection database, String query) throws SQLException {
        Statement stmt = null;
        stmt = database.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
    }

    private void closeDatabaseConnection(Connection database) throws SQLException {
        database.close();
    }
}

