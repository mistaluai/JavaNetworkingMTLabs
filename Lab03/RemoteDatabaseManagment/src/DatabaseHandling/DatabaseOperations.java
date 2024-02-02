package DatabaseHandling;

import java.sql.Connection;

public class DatabaseOperations {
    private Connection dbConnection;
    DatabaseOperations(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }
}
