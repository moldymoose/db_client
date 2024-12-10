package Utilities;

import java.sql.*;

public class DBConnector {
    private static final String URL = "jdbc:mysql://139.62.210.180:3306/cop3703_13";
    private static final String USER = "cop3703_13";
    private static final String PASSWORD = "password13";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}