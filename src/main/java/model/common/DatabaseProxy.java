package model.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Diese Klasse wird verwendet um eine Datenbankverbindung zu bekommen.
 * Alle DB Zugriffe sind synchronized damit es durch den Import zu keinen Problemen auf der Datenbank führt.
 *
 * @author  Reto Kaufmann
 * @author  Dieter Biedermann
 */
public class DatabaseProxy {

    private Connection conn;

    public DatabaseProxy() {
        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized PreparedStatement prepareStatement(String str) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    // mit 2. Parameter, damit bei INSERT/UPDATE der PK zurueckgegeben werden kann
    // (Parameter z.b. Statement.RETURN_GENERATED_KEYS)
    public synchronized PreparedStatement prepareStatement(String str, int k) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(str, k);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public synchronized void setAutoCommit(Boolean autoCommit) {
        try {
            conn.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void commit() {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}