package DatabaseHandling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseManager {
    private Connection dbConnection;
    private String hostname;
    private int portNumber;
    private String dbName;
    private String[] creds;
    private String properties;
    private boolean hasProperties;
    private DatabaseOperations dbOperations;
    DatabaseManager(String hostname, String dbName, String username, String password) {
        portNumber = 1433;
        this.hostname = hostname;
        this.dbName = dbName;
        creds = new String[2];
        creds[0] = username;
        creds[1] = password;
    }
    DatabaseManager(String hostname, int portNumber, String dbName, String username, String password) {
        this(hostname, dbName, username, password);
        this.portNumber = portNumber;
    }
    DatabaseManager(String hostname, int portNumber, String dbName,
                    String username, String password, String properties) {
        this(hostname,portNumber, dbName, username, password);
        this.properties = properties;
        hasProperties = true;
    }
    /**
     * compileProperties is a method for fixing and organizing the parameters sent to
     * the database connection string.
     * @return a string of a well organized properties ready to be put in the url
     */
    private String compileProperties() {
        Pattern pattern = Pattern.compile("\\w*=\\w*", Pattern.CASE_INSENSITIVE);
        Matcher propertiesMatcher = pattern.matcher(properties);
        StringBuilder p = new StringBuilder();
        while (propertiesMatcher.find())
            p.append(propertiesMatcher.group() + ";");
        return p.toString();
    }

    /**
     * parseURL is a method that prepares the connection string of the database.
     * @return SQL database connection url
     */
    private String parseURL() {
        StringBuilder url = new StringBuilder("jdbc:sqlserver://" + hostname + ":" + portNumber + ";");
        url.append("databaseName=" + dbName + ";");
        if (hasProperties)
            url.append(compileProperties());
        return url.toString();
    }

    /**
     * initializeDatabase is a method that establishes the connection with the database.
     * @return true if the connection was established successfully, false if not.
     */
    private boolean initializeDatabase() {
        String url = parseURL();
        try {
            dbConnection = DriverManager.getConnection(url, creds[0], creds[1]);
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    /**
     * Connects the manager to the database and initializes the database operations object
     * @return true if the initialization and connection were done successfully, false if not.
     */
    public boolean establishConnection() {
        if (initializeDatabase()) {
            dbOperations = new DatabaseOperations(dbConnection);
            return true;
        }
        System.out.println("Failed to establish connection");
        return false;
    }

    /**
     * getter method for the operations handler of the database
     * @return returns object type of DatabaseOperations
     */
    public DatabaseOperations getDatabaseOperations() {
        return dbOperations;
    }
}
