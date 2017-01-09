package model.travel;

import model.common.DatabaseProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Trip {

  // Instanzvariablen Transfer-Object
  private Long id;
  private Long user_id;
  private String name;

  // Instanzvariablen Hilfsobjekte
  DatabaseProxy databaseProxy;
  PreparedStatement preparedStatement;
  ResultSet resultset;

  // Constructor
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
  };

  // Getter/Setter Instanz-Methoden
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // Statische Klassen-Methoden
  public static ArrayList<Trip> searchByUser(User user){
    PreparedStatement preparedStatement;
    ResultSet resultset = null;
    DatabaseProxy databaseProxy = new DatabaseProxy();
    try {
      preparedStatement = databaseProxy.prepareStatement(
              "select trip_id, user_id, trip_name, max_date, min_date, count_acitvities " +
              "from tp_trip_aggr_v " +
              "where user_id = ? ");
      preparedStatement.setLong(1, user.getId());
      resultset = preparedStatement.executeQuery();
      while (resultset.next()){
        System.out.println("DB, TRIP_ID   : " +resultset.getInt("trip_id"));
        System.out.println("DB, TRIP_NAME : " +resultset.getString("trip_name"));
        /*
        tempUser.setId(resultset.getLong("id"));
        tempUser.setEmail(resultset.getString("email"));
        tempUser.setPassword(resultset.getString("password"));
        tempUser.setUsername(resultset.getString("username"));
      //tempUser.setTypeEnum(resultset.getLong("type"));
        tempUser.setName(resultset.getString("name"));
        */
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
    return null;
  }


}


