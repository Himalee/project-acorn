import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class PostgresCommunicator implements DatabaseCommunicator {

    private String databaseURL;

    public PostgresCommunicator(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public void writeToDatabase(Opportunity opportunity) throws SQLException, ClassNotFoundException {
        String sqlQuery = String.format("INSERT INTO OPPORTUNITIES (name, description, proposed_cost, user_name, stage, opportunity_uuid) VALUES ('%s', '%s', %d, '%s', '%s', '%s');", opportunity.getName(), opportunity.getDescription(), opportunity.getProposedCost(), opportunity.getUserName(), opportunity.getStage(), opportunity.getUuid());
        executeQuery(sqlQuery);
    }

    public List<Opportunity> readAllOpportunitiesFromDatabase() throws SQLException, ClassNotFoundException {
        List<Opportunity> opportunities = new ArrayList<>();
        String readSqlQuery = "SELECT * FROM opportunities";
        Connection db = getConnection();
        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery(readSqlQuery);
        while (rs.next()) {
            Opportunity opportunity = readOpportunity(rs);
            setOpportunityId(opportunity, rs);
            opportunities.add(opportunity);
        }
        stmt.close();
        db.close();
        return opportunities;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(databaseURL);
    }

    public void updateOpportunityStringField(Opportunity opportunity, String columnName, String update) throws SQLException, ClassNotFoundException {
        String sqlQuery = String.format("UPDATE opportunities SET %s = '%s' WHERE id = %d;", columnName, update, opportunity.getId());
        executeQuery(sqlQuery);
    }

    public void updateOpportunityNumericField(Opportunity opportunity, String columnName, int update) throws SQLException, ClassNotFoundException {
        String sqlQuery = String.format("UPDATE opportunities SET %s = %d WHERE id = %d;", columnName, update, opportunity.getId());
        executeQuery(sqlQuery);
    }

    public void deleteOpportunity(Opportunity opportunity) throws SQLException, ClassNotFoundException {
        String uuid = opportunity.getUuid();
        String sqlQuery = String.format("DELETE FROM opportunities WHERE opportunity_uuid = '%s';", uuid);
        executeQuery(sqlQuery);

    }

    public boolean rowExists(String uuid) throws SQLException, ClassNotFoundException {
        Connection db = getConnection();
        String sqlQuery = String.format("SELECT name FROM opportunities WHERE opportunity_uuid = '%s';", uuid);
        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery);
        return rs.next();
    }

    private void executeQuery(String query) throws SQLException, ClassNotFoundException {
        Connection database = getConnection();
        Statement stmt = null;
        stmt = database.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
        database.close();
    }

    private Opportunity readOpportunity(ResultSet rs) throws SQLException {
        String name = rs.getString(TableColumns.NAME.getColumnName());
        String description = rs.getString(TableColumns.DESCRIPTION.getColumnName());
        int proposedCost = rs.getInt(TableColumns.COST.getColumnName());
        String userName = rs.getString(TableColumns.USER_NAME.getColumnName());
        String stage = rs.getString(TableColumns.STAGE.getColumnName());
        String uuid = rs.getString(TableColumns.OPPORTUNITY_UUID.getColumnName());
        return new Opportunity(name, description, proposedCost, userName, stage, uuid);
    }

    private void setOpportunityId(Opportunity opportunity, ResultSet rs) throws SQLException {
        opportunity.setId(Integer.parseInt(rs.getString(TableColumns.ID.getColumnName())));
    }
}

