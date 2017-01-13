package model.travel;

import model.common.DatabaseProxy;
import model.common.Poi;
import model.common.PoiCategory;
import model.common.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        PreparedStatement preparedStatement = databaseProxy.prepareStatement(query);

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

        } catch (SQLException e) {
            throw e;
        } finally {
            databaseProxy.close();
        }
    }


    // todo woher sonst user nehmen? dann natuerlich nur noch nach trip suchen
    public static ArrayList<Activity> searchByUserAndTrip(User user, Trip trip){
        DatabaseProxy databaseProxy = new DatabaseProxy();
        PreparedStatement preparedStatement;
        ResultSet resultset = null;
        ArrayList<Activity> activityList = new ArrayList<Activity>();
        try {
            preparedStatement = databaseProxy.prepareStatement(
                    "select trip_id, activity_id, date, comment, poi_id, longitude, latitude, poi_name, category_id, poi_category_name, longitude, latitude, category_id, poi_category_name\n" +
                            "from tp_trip_full_v " +
                            "where user_id = ? and trip_id = ? ");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, trip.getId());
            resultset = preparedStatement.executeQuery();
            while (resultset.next()){
                System.out.println("DB, TRIP_ID   : " +resultset.getLong("trip_id"));
                System.out.println("DB, ACT_ID    : " +resultset.getInt("activity_id"));
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
                Activity activity = new Activity(trip, poi, resultset.getDate("date"), resultset.getString("comment"));
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
}
