package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.String.format;

public class DBUtil {

    public static Connection getConnection() {
        return connection;
    }

    static Connection connection;
    static private Statement statement = null;
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:requests.db";

    static final String COLUMN_PRODUCT_NAME = "REQUEST_NAME";
    static final String COLUMN_COUNTER = "REQUEST_COUNTER";
    static final String TABLE_NAME = "REQUEST";

    public static Connection createConnection() {
        if(connection == null) {
            try {
                try {
                    Class.forName(DRIVER);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                connection = DriverManager.getConnection(DB_URL);
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            createTable();
        }
        return connection;
    }

    public static void createTable() {
        String createRequestsTable = format("CREATE TABLE IF NOT EXISTS %s" +
                        " (%s VARCHAR(255), %s INTEGER)",
                TABLE_NAME, COLUMN_PRODUCT_NAME, COLUMN_COUNTER);

        try {
            statement.execute("DROP table REQUEST");
            statement.execute(createRequestsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
