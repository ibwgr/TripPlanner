package model.travel;

import model.common.DatabaseProxy;
import model.common.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
      // Neuester Trip zuoberst, da dies der aktuellste und wichtigste ist
      // (wird wahrscheinlich vom User bearbeitet)
      // Bei New Trip ist es wichtig dass dieser zuoberst in der Liste erscheint,
      // sonst verlieren wir den Focus, bzw. der Focus liegt auf einer
      // nicht-relevanten Reise
      preparedStatement = databaseProxy.prepareStatement(
              "select trip_id, user_id, trip_name, max_date, min_date, count_acitvities " +
              "from tp_trip_aggr_v " +
              "where user_id = ? " +
              "order by trip_id desc");
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

  /**
   * Speichert ein Trip Objekt auf die Datenbank. Tabelle: tp_trip
   * - wenn ID leer ist, wird ein Insert gemacht
   * - wenn ID vorhanden ist, wird ein Update gemacht
   */
  public void save() throws SQLException {
    DatabaseProxy databaseProxy = new DatabaseProxy();
    String query = null;
    if (id == null) {
      query = "insert into tp_trip (user_id, name) "
              + "values (?, ?)";
    } else {
      query = "update tp_trip set user_id = ?, name = ? "
              + "where id = ?";
    }

    PreparedStatement preparedStatement = databaseProxy.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

    try {
      preparedStatement.setLong(1, user.getId());
      preparedStatement.setString(2, this.name);
      if (id != null) {
        preparedStatement.setLong(3, id);
      }

      System.out.println("save Trip query: " + preparedStatement.toString());

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


  /**
   * Loescht ein Trip Objekt auf die Datenbank. Tabelle: tp_trip
   * Falls bereits Activitied
   */
  public void delete() throws SQLException {
    DatabaseProxy databaseProxy = new DatabaseProxy();
    String query = null;
    query = "delete from tp_trip where id = ?";

    PreparedStatement preparedStatement = databaseProxy.prepareStatement(query);
    preparedStatement.setLong(1, id);
    System.out.println("delete Trip query: " + preparedStatement.toString());

    try {
        preparedStatement.executeUpdate();
    } catch(SQLException e)   {
        throw e;
    } finally {
        databaseProxy.close();
    }
}



}


