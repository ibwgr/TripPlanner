package model.travel;

import model.common.DatabaseProxy;
import model.common.Poi;
import model.common.PoiCategory;

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
