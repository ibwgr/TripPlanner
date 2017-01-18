package model.travel;

import model.common.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Activity {

    private Long id;
    private Trip trip;
    private Poi poi;
    private Date date;
    private String comment;

    public Activity(Long id, Trip trip, Poi poi, Date date, String comment) {
        this.id = id;
        this.trip = trip;
        this.poi = poi;
        this.date = date;
        this.comment = comment;
    }

    public Activity(Trip trip, Poi poi, Date date, String comment) {
        this.trip = trip;
        this.poi = poi;
        this.date = date;
        this.comment = comment;
    }

    /**
     * Speichert ein Activity Objekt auf die Datenbank. Tabelle: tp_activity
     * - wenn ID leer ist, wird ein Insert gemacht
     * - wenn ID vorhanden ist, wird ein Update gemacht
     */
    public void save() throws SQLException {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        String query = null;
        if (id == null) {
            query = "insert into tp_activity (trip_id, poi_id, date, comment) "
                    + "values (?, ?, ?, ?)";
        } else {
            query = "update tp_activity set trip_id = ?, poi_id = ?, date = ?, comment = ? "
                    + "where id = ?";
        }

        PreparedStatement preparedStatement = databaseProxy.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        try {
            preparedStatement.setLong(1, trip.getId());
            preparedStatement.setString(2, poi.getId());
            preparedStatement.setDate(3, new java.sql.Date(date.getTime()));
            preparedStatement.setString(4, comment);
            if (id != null) {
                preparedStatement.setLong(5, id);
            }

            System.out.println("save Activity query: " + preparedStatement.toString());

            preparedStatement.executeUpdate();
            // falls INSERT, sind wir am Resultat (Primary Key: ID) interessiert
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) {
                int incrementId = rs.getInt(1);
                if (incrementId > 0) {
                    System.out.println("inserted with primary key ID: " +incrementId);
                    this.setId((long) incrementId);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            databaseProxy.close();
        }
    }

    public void delete() throws SQLException {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        String query = null;
        if (id == null) {
            throw new IllegalArgumentException("Cannot delete Activity (not yet saved)");
        }
        query = "delete from tp_activity where id = ?";

        PreparedStatement preparedStatement = databaseProxy.prepareStatement(query);

        try {
            preparedStatement.setLong(1, id);

            System.out.println("delete Activity query: " + preparedStatement.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            databaseProxy.close();
        }
    }


    // Zur Info: Der eingeloggte Benutzer kann diese Methode nur im Kontext
    // einer SEINER Reisen Aufrufen.
    // Somit besteht keine Gefahr dass er unberechtigt Reisen anderer Personen sieht.
    public static ArrayList<Activity> searchByTrip(Trip trip){
        DatabaseProxy databaseProxy = new DatabaseProxy();
        PreparedStatement preparedStatement;
        ResultSet resultset = null;
        ArrayList<Activity> activityList = new ArrayList<Activity>();
        try {
            preparedStatement = databaseProxy.prepareStatement(
                    "select trip_id, activity_id, date, comment, poi_id, longitude, latitude, poi_name, category_id, poi_category_name, longitude, latitude, category_id, poi_category_name\n" +
                            "from tp_trip_full_v " +
                            "where trip_id = ? order by date asc");
            preparedStatement.setLong(1, trip.getId());
            resultset = preparedStatement.executeQuery();
            while (resultset.next()){
                System.out.println("DB, TRIP_ID   : " +resultset.getLong("trip_id"));
                System.out.println("DB, ACT_ID    : " +resultset.getLong("activity_id"));
                System.out.println("DB, DATE      : " +resultset.getDate("date"));
                System.out.println("DB, COMMENT   : " +resultset.getString("comment"));
                System.out.println("DB, POI_ID    : " +resultset.getString("poi_id"));
                System.out.println("DB, POI_NAME  : " +resultset.getString("poi_name"));
                System.out.println("DB, longitude : " +resultset.getString("longitude"));
                System.out.println("DB, latitude  : " +resultset.getString("latitude"));
                System.out.println("DB, category_id : " +resultset.getString("category_id"));
                System.out.println("DB, poi_category_name  : " +resultset.getString("poi_category_name"));
                // neues Activity Objekt
                PoiCategory poiCategory = new PoiCategory(resultset.getString("category_id"),resultset.getString("poi_category_name"));
                Poi poi = new Poi(resultset.getString("poi_id"),resultset.getString("poi_name"),poiCategory ,resultset.getString("longitude"),resultset.getString("latitude"));
                Activity activity = new Activity(resultset.getLong("activity_id"), trip, poi, resultset.getDate("date"), resultset.getString("comment"));
                // zur ACTIVITY-LISTE hinzufuegen
                activityList.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close anyway
            try {
                resultset.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("DB [activity], size()    : " +activityList.size());
        return activityList;
    }

    // Zur Info: Der eingeloggte Benutzer kann diese Methode nur im Kontext
    // einer SEINER Reisen Aufrufen.
    // Somit besteht keine Gefahr dass er unberechtigt Reisen anderer Personen sieht.
    public static Activity searchById(Long currentActivityId) {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        PreparedStatement preparedStatement;
        ResultSet resultset = null;
        Activity activity = null;
        try {
            preparedStatement = databaseProxy.prepareStatement(
                    "select trip_id, trip_name, user_id, activity_id, date, comment, poi_id, longitude, latitude, poi_name, category_id, poi_category_name, longitude, latitude, category_id, poi_category_name\n" +
                            "from tp_trip_full_v " +
                            "where activity_id = ? ");
            preparedStatement.setLong(1, currentActivityId);
            resultset = preparedStatement.executeQuery();
            while (resultset.next()){
                System.out.println("DB, TRIP_ID   : " +resultset.getLong("trip_id"));
                System.out.println("DB, ACT_ID    : " +resultset.getLong("activity_id"));
                System.out.println("DB, DATE      : " +resultset.getDate("date"));
                System.out.println("DB, COMMENT   : " +resultset.getString("comment"));
                System.out.println("DB, POI_ID    : " +resultset.getString("poi_id"));
                System.out.println("DB, POI_NAME  : " +resultset.getString("poi_name"));
                System.out.println("DB, longitude : " +resultset.getString("longitude"));
                System.out.println("DB, latitude  : " +resultset.getString("latitude"));
                System.out.println("DB, category_id : " +resultset.getString("category_id"));
                System.out.println("DB, poi_category_name  : " +resultset.getString("poi_category_name"));
                // neues Activity Objekt
                User user = User.searchById(databaseProxy, resultset.getLong("user_id"));
                Trip trip = new Trip(resultset.getLong("trip_id"), user, resultset.getString("trip_name"));
                PoiCategory poiCategory = new PoiCategory(resultset.getString("category_id"),resultset.getString("poi_category_name"));
                Poi poi = new Poi(resultset.getString("poi_id"),resultset.getString("poi_name"),poiCategory ,resultset.getString("longitude"),resultset.getString("latitude"));
                activity = new Activity(resultset.getLong("activity_id"), trip, poi, resultset.getDate("date"), resultset.getString("comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close anyway
            try {
                resultset.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("DB [activity] ID : " +activity.getId());
        return activity;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Poi getPoi() {
        return poi;
    }

    public void setPoi(Poi poi) {
        this.poi = poi;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    // Entweder Plus oder Minus (z.B. +1, +5, -1, -5)
    public void moveDays(int days) throws SQLException {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        String query = null;
        if (days != 0) {
            query = "update tp_activity set date = date + ? where id = ?";
        } else {
            query = "update tp_activity set date = date where id = ? AND 1=2"; // kein Update ausfuehren
        }
        PreparedStatement preparedStatement = databaseProxy.prepareStatement(query);
        try {
            preparedStatement.setInt(1, days);
            preparedStatement.setLong(2, this.id);
            System.out.println("Update Activity Query: " + preparedStatement.toString());
            preparedStatement.executeUpdate();
            //
            this.setDate(Util.addDays(this.getDate(),days));
            //
        } catch (SQLException e) {
            throw e;
        } finally {
            databaseProxy.close();
        }
    }

    public void setActivityDateBefore() throws SQLException {
        moveDays(1);
    }

    public void setActivityDateAfter() throws SQLException {
        moveDays(-1);
    }
}
