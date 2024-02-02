package DatabaseHandling;

import java.sql.*;

public class DatabaseConnectionTest {
    public static void main(String[] args) throws SQLException {
        System.setProperty("java.net.preferIPv6Addresses", "true");
        Connection c = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Employees;trustServerCertificate=true;"
                , "SA", "15&12&L1l2l3l4");
        c.prepareStatement("CREATE DATABASE Hello").executeQuery();
    }
}
