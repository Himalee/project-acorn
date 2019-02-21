import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

class PostgresCommunicator implements DatabaseCommunicator {

    private String databaseURL;

    public PostgresCommunicator(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public void writeToDatabase(Opportunity opportunity) throws SQLException, ClassNotFoundException {
        String sqlQuery = String.format("INSERT INTO OPPORTUNITIES (name, description, proposed_cost, user_name, stage, opportunity_uuid, opportunity_date) VALUES ('%s', '%s', %d, '%s', '%s', '%s', TO_DATE('%s', 'dd/mm/yyyy'));", opportunity.getName(), opportunity.getDescription(), opportunity.getProposedCost(), opportunity.getUserName(), opportunity.getStage(), opportunity.getUuid(), opportunity.getDate());
        executeQuery(sqlQuery);
    }

    public List<Opportunity> readAllOpportunitiesFromDatabase() throws SQLException, ClassNotFoundException {
        List<Opportunity> opportunities = new ArrayList<>();
        String readSqlQuery = "SELECT * FROM opportunities";
        Connection database = getConnection();
        Statement statement = database.createStatement();
        ResultSet resultSet = statement.executeQuery(readSqlQuery);
        while (resultSet.next()) {
            Opportunity opportunity = readOpportunity(resultSet);
            setOpportunityId(opportunity, resultSet);
            opportunities.add(opportunity);
        }
        statement.close();
        database.close();
        return opportunities;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(databaseURL);
    }

    public void updateOpportunityStringField(Opportunity opportunity, String columnName, String update) throws SQLException, ClassNotFoundException {
        StringBuilder sqlQuery = new StringBuilder();
        if (isColumnDate(columnName)) {
            sqlQuery.append(String.format("UPDATE opportunities SET %s = TO_DATE('%s', 'dd/mm/yyyy') WHERE id = %d;", columnName, update, opportunity.getId()));
        } else {
            sqlQuery.append(String.format("UPDATE opportunities SET %s = '%s' WHERE id = %d;", columnName, update, opportunity.getId()));
        }
        executeQuery(sqlQuery.toString());
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

    public boolean doesRowExist(String uuid) throws SQLException, ClassNotFoundException {
        Connection database = getConnection();
        String sqlQuery = String.format("SELECT name FROM opportunities WHERE opportunity_uuid = '%s';", uuid);
        Statement statement = database.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        return resultSet.next();
    }

    private void executeQuery(String query) throws SQLException, ClassNotFoundException {
        Connection database = getConnection();
        Statement statement = null;
        statement = database.createStatement();
        statement.executeUpdate(query);
        statement.close();
        database.close();
    }

    private Opportunity readOpportunity(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(TableColumns.NAME.getColumnName());
        String description = resultSet.getString(TableColumns.DESCRIPTION.getColumnName());
        int proposedCost = resultSet.getInt(TableColumns.COST.getColumnName());
        String userName = resultSet.getString(TableColumns.USER_NAME.getColumnName());
        String stage = resultSet.getString(TableColumns.STAGE.getColumnName());
        String uuid = resultSet.getString(TableColumns.OPPORTUNITY_UUID.getColumnName());
        String date = formatDate(resultSet);
        return new Opportunity(name, description, proposedCost, userName, stage, uuid, date);
    }

    private void setOpportunityId(Opportunity opportunity, ResultSet resultSet) throws SQLException {
        opportunity.setId(Integer.parseInt(resultSet.getString(TableColumns.ID.getColumnName())));
    }

    private String formatDate(ResultSet resultSet) throws SQLException {
        Date date = resultSet.getDate(TableColumns.DATE.getColumnName());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    private boolean isColumnDate(String columnName) {
       return columnName.equals(TableColumns.DATE.getColumnName());
    }
}

