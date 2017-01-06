package model.travel;

public class User {
  private Long id;
  private String username;
  private String password;
  private String email;  // todo email soll als hashwert abgelegt sein
  private String name;
  private Long type;
  private UserTypeEnum typeEnum;

  public Long getId() {
    return id;
  }

//wird db-seitig durch sequence jeweils erhoeht
//public void setId(Long id) {
//  this.id = id;
//}

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
}
