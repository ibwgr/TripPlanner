package model.travel;

import model.common.DatabaseProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
  public User(DatabaseProxy databaseProxy) {
    try {
      // Databaseproxy wird via Constructor Injection verwendet
      this.databaseProxy = databaseProxy;
      this.databaseProxy.setAutoCommit(false);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  public User(){
  };

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

//wird nie direkt gesetzt!
//wird innerhalb Java mit UserTypeEnum verwendet, gegen DB jedoch gemapped: 1 = User, 2 = Administrator
//  public void setType(Long type) {
//    this.type = type;
//  }

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
      User tempUser = new User(databaseProxy);
      tempUser = searchByCredentials(this.username, this.password);
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
      return searchByCredentials(this.username, this.password);
    } else {
      return null;
    }
  }

  // Search by Credentials (Username AND Password)
  public User searchByCredentials(String username, String password) {
    System.out.println("searchByCredentials(String username, String password)");
    User tempUser = new User();
    try {
      preparedStatement = databaseProxy.prepareStatement("SELECT id, username, password, email, name, type FROM tp_user where username = ? and password = ? ");
      preparedStatement.setString(1, this.username);
      preparedStatement.setString(2, this.password);
      resultset = preparedStatement.executeQuery();
      while (resultset.next()){
        System.out.println("DB Zugriff, ID: " +resultset.getString("id"));
        System.out.println("DB Zugriff, EMAIL: " +resultset.getString("email"));
        tempUser.setId(resultset.getLong("id"));
        tempUser.setEmail(resultset.getString("email"));
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
    return tempUser;
  }

  public void save() {
  }


}
