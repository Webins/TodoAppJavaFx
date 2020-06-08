package todo.db;

import java.sql.*;
import java.util.ArrayList;

public class dbHandler extends Config {
    private static Connection dbConnection;
    public static Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database_name;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, user, password);
        return dbConnection;
    }

}
