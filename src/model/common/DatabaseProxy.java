package model.common;

import org.postgresql.jdbc2.optional.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseProxy {

    private Connection conn;

    public DatabaseProxy() {
        try {
            conn = DBConnection.getConnection();

/*
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/trip_planner_db", "postgres1", "postgres1");
*/
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