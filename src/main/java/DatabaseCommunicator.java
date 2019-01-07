import java.sql.*;

class DatabaseCommunicator {

    public String databaseURL;

    public DatabaseCommunicator(String databaseURL) {
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
        String username = System.getenv("DBUSERNAME");
        String password = System.getenv("DBPASSWORD");

        return DriverManager.getConnection(databaseURL, username, password);
    }
}


