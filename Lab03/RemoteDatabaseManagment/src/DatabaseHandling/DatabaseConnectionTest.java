package DatabaseHandling;

import java.sql.SQLException;

public class DatabaseConnectionTest {
    public static void main(String[] args) throws SQLException {
        System.setProperty("java.net.preferIPv6Addresses", "true");
        DatabaseManager dbm =
                new DatabaseManager("localhost", 1433, "Employees", "SA", "15&12&L1l2l3l4", "trustServerCertificate=true");
        if (dbm.establishConnection()) {
            DatabaseOperations dbo = dbm.getDatabaseOperations();

        }
    }
}
