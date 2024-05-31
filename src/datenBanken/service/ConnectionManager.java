package datenBanken.service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String CONNECTIONSTRING="jdbc:sqlite:C:/Users/Fabian/IdeaProjects/codeNight/src/datenBanken/codeNight";
    private static final String CLASSNAME = "org.sqlite.JDBC";

    public static Connection creatConnection()  {
        try {
            return DriverManager.getConnection(CONNECTIONSTRING);
        } catch (SQLException e) {
         e.printStackTrace();
        }
        return null;
    }

    public static boolean classConnect()  {
        try {
            Class.forName(CLASSNAME);
            return true;
        } catch (ClassNotFoundException e) {
        e.printStackTrace();
        }
        return false;
    }
}
