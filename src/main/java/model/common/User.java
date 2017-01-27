package model.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 *
 * @author  Reto Kaufmann
 */
public class User {

  // Instanzvariablen Transfer-Object
  private Long id;
  private String username;
  private String password;
  private String email;  // todo email soll als hashwert abgelegt sein
  private String name;
  private Long type;

  // Instanzvariablen zusaetzlich
  private UserTypeEnum typeEnum;
  private boolean isLoggedIn;

  // Instanzvariablen Hilfsobjekte
  DatabaseProxy databaseProxy;
  PreparedStatement preparedStatement;
  ResultSet resultset;

  // Constructor
  public User(Long id, String username, String password, String email, String name, Long type) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.name = name;
    setType(type);
  }
  public User(){
  }

  // Methoden
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getType() {
    return type;
  }

  public void setType(Long type) {
    this.type = type;
    this.typeEnum = UserTypeEnum.getEnum(type);
  }

  public UserTypeEnum getTypeEnum() {
    return typeEnum;
  }

  public void setTypeEnum(UserTypeEnum typeEnum) {
    this.typeEnum = typeEnum;
    // gleichzeitig auch den type mit long id setzten (fuer DB)
    this.type = typeEnum.getId();
  }

  public boolean isLoggedIn() {
    return isLoggedIn;
  }

  public void setLoggedIn(boolean loggedIn) {
    isLoggedIn = loggedIn;
  }

  // login function, checks if user exists and checks user credentials
  public boolean login() {
    // Check 1: Required fields are set
    // Required for login:
    //  - username
    //  - password
    if (this.username != null  && this.password != null ) {
      // Check 2: Does User in DB exist?
      User tempUser = new User();
      tempUser = searchByCredentials(databaseProxy, this.username, this.password);
      if (tempUser.getId() != null) {
        System.out.println("User.login(): Info: User has logged in. Username="+tempUser.getUsername() +", id="+tempUser.getId());
        this.isLoggedIn = true;
        return true;
      }
      else {
        System.out.println("User.login(): Info: Login error. No User found in Database. Username="+tempUser.getUsername() +", id="+tempUser.getId());
        this.isLoggedIn = false;
        return false;
      }
    }
    else {
      System.out.println("User.login(): Info: Login error. No username/password has been provided");
      this.isLoggedIn = false;
      return false;
    }
  }

  public String getLoginInformation() {
    return "x";
  }

  // DB Methoden
  public ArrayList<User> search()  {
    return null;
  }


  // Search by Credentials (Username AND Password)
  public User searchByCredentials() {
    System.out.println("searchByCredentials()");
    if (this.username != null && this.password != null) {
      return searchByCredentials(databaseProxy, this.username, this.password);
    } else {
      return null;
    }
  }

  // Search by Credentials (Username AND Password)
  public static User searchByCredentials(DatabaseProxy databaseProxy, String username, String password) {
    System.out.println("searchByCredentials username="+username +", password="+password);
    ResultSet resultSet = null;
    User user = null;
    try {
      PreparedStatement preparedStatement = databaseProxy.prepareStatement("SELECT id, username, password, email, name, type FROM tp_user where username = ? and password = md5(?) ");
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()){
        user = new User(
                resultSet.getLong(1)
                ,resultSet.getString(2)
                ,resultSet.getString(3)
                ,resultSet.getString(4)
                ,resultSet.getString(5)
                ,resultSet.getLong(6)
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // close anyway
      try {
        if (resultSet != null) {
          resultSet.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return user;
  }

  // Search by Credentials (Username AND Password)
  public static User searchById(DatabaseProxy databaseProxy, Long id) {
    System.out.println("searchById()");
    ResultSet resultSet = null;
    User user = null;
    try {
      PreparedStatement preparedStatement = databaseProxy.prepareStatement(
              "SELECT id, username, password, email, name, type " +
              "FROM tp_user where id = ? ");
      preparedStatement.setLong(1, id);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()){
        user = new User(
                 resultSet.getLong(1)
                ,resultSet.getString(2)
                ,resultSet.getString(3)
                ,resultSet.getString(4)
                ,resultSet.getString(5)
                ,resultSet.getLong(6)
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      // close anyway
      try {
        if (resultSet != null) {
          resultSet.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return user;
  }

  public void save() {
  }


}
