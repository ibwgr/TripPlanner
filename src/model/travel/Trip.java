package model.travel;

import model.common.DatabaseProxy;
import model.common.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Trip {

  // Instanzvariablen Transfer-Object
  private Long id;
  private User user;
  private String name;

  // Instanzvariablen zusaetzlich
  private Date maxDate;
  private Date minDate;
  private int countActivities;  // todo diesen hier spaeter noch auslagern

  // Instanzvariablen Hilfsobjekte
  DatabaseProxy databaseProxy;
  PreparedStatement preparedStatement;
  ResultSet resultset;

  // Constructor   todo braucht es den databaseproxy hier ueberhaupt?
  public Trip(DatabaseProxy databaseProxy) {
    try {
      // Databaseproxy wird via Constructor Injection verwendet
      this.databaseProxy = databaseProxy;
      this.databaseProxy.setAutoCommit(false);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  public Trip(){
  }
  public Trip(Long id, User user, String name){
    this.id = id;
    this.user = user;
    this.name = name;
  };

  // Getter/Setter Instanz-Methoden
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getMaxDate() {
    return maxDate;
  }

  public void setMaxDate(Date maxDate) {
    this.maxDate = maxDate;
  }

  public Date getMinDate() {
    return minDate;
  }

  public void setMinDate(Date minDate) {
    this.minDate = minDate;
  }

  public int getCountActivities() {
    return countActivities;
  }

  public void setCountActivities(int countActivities) {
    this.countActivities = countActivities;
  }

  // Statische Klassen-Methoden
  public static ArrayList<Trip> searchByUser(User user){
    DatabaseProxy databaseProxy = new DatabaseProxy();
    PreparedStatement preparedStatement;
    ResultSet resultset = null;
    ArrayList<Trip> tripList = new ArrayList<Trip>();
    try {
      preparedStatement = databaseProxy.prepareStatement(
              "select trip_id, user_id, trip_name, max_date, min_date, count_acitvities " +
              "from tp_trip_aggr_v " +
              "where user_id = ? ");
      preparedStatement.setLong(1, user.getId());
      resultset = preparedStatement.executeQuery();
      while (resultset.next()){
        System.out.println("DB, TRIP_ID   : " +resultset.getLong("trip_id"));
        System.out.println("DB, TRIP_NAME : " +resultset.getString("trip_name"));
        System.out.println("DB, ACTIVITIES: " +resultset.getInt("count_acitvities"));
        System.out.println("DB, MIN DATE  : " +resultset.getDate("min_date"));
        System.out.println("DB, MAX DATE  : " +resultset.getDate("max_date"));
        // neues TRIP Objekt
        Trip trip = new Trip(resultset.getLong("trip_id"), user, resultset.getString("trip_name"));
        //trip.setId(resultset.getLong("trip_id"));
        //trip.setName(resultset.getString("trip_name"));
        //trip.setUser_id(1L);
        trip.setCountActivities(resultset.getInt("count_acitvities"));
        trip.setMinDate(resultset.getDate("min_date"));
        trip.setMaxDate(resultset.getDate("max_date"));
        // zur TRIP-LISTE hinzufuegen
        tripList.add(trip);
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
    System.out.println("DB [trip], size()    : " +tripList.size());
    return tripList;
  }

  // todo woher sonst den user nehmen?  dann natuerlich nur noch nach id suchen
  public static Trip searchByUserAndId(User user, Long id){
    DatabaseProxy databaseProxy = new DatabaseProxy();
    PreparedStatement preparedStatement;
    ResultSet resultset = null;
    Trip trip = null;
    try {
      preparedStatement = databaseProxy.prepareStatement(
              "select trip_id, user_id, trip_name, max_date, min_date, count_acitvities " +
                      "from tp_trip_aggr_v " +
                      "where trip_id = ? ");
      preparedStatement.setLong(1, id);
      resultset = preparedStatement.executeQuery();
      while (resultset.next()){
        System.out.println("DB, TRIP_ID   : " +resultset.getLong("trip_id"));
        System.out.println("DB, TRIP_NAME : " +resultset.getString("trip_name"));
        System.out.println("DB, ACTIVITIES: " +resultset.getInt("count_acitvities"));
        System.out.println("DB, MIN DATE  : " +resultset.getDate("min_date"));
        System.out.println("DB, MAX DATE  : " +resultset.getDate("max_date"));
        // neues TRIP Objekt
        trip = new Trip(resultset.getLong("trip_id"), user, resultset.getString("trip_name"));
        //trip.setId(resultset.getLong("trip_id"));
        //trip.setName(resultset.getString("trip_name"));
        //trip.setUser_id(1L);
        trip.setCountActivities(resultset.getInt("count_acitvities"));
        trip.setMinDate(resultset.getDate("min_date"));
        trip.setMaxDate(resultset.getDate("max_date"));
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
    return trip;
  }
}


