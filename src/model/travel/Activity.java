package model.travel;

import model.common.Poi;

import java.util.Date;

public class Activity {

    private int id;
    private Trip trip;
    private Poi poi;
    private Date date;
    private String comment;

    public Activity(int id, Trip trip, Poi poi, Date date, String comment) {
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
    public void save() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
