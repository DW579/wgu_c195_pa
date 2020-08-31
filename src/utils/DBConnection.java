package utils;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/U05Aey?autoReconnect=true&useSSL=false";

    // JDBC URL
    private static final String jdbURL = protocol + vendorName + ipAddress;

    // Driver Interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U05Aey"; // Username
    private static final String password = "53688444155"; // Password


    public static Connection startConnection() {
        try{
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbURL, username, password);
            System.out.println("Connection successful!");
        }
        catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public static void closeConnection() {
        try{
            conn.close();
            System.out.println("Connection closed!");
        }
        catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
