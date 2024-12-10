package DAO;

import Utilities.DBConnector;
import java.sql.*;

public abstract class BaseDAO {

    //Opens connection throws exceptions down the line
    protected Connection getConnection() throws SQLException {
        return DBConnector.getConnection();
    }
}