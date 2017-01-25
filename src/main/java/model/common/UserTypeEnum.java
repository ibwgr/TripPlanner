package model.common;

/**
 * Enumeration for User Class
 * 1 = User, 2 = Administrator
 *
 * @author  Reto Kaufmann
 */
public enum UserTypeEnum  {
    USER, ADMIN;

    final static Long long0 = 0L;
    final static Long long1 = 1L;
    final static Long long2 = 2L;

    //1 = User, 2 = Administrator
    public Long getId(){
        if (this==USER) {
            return long1;
        }
        else if (this==ADMIN) {
            return long2;
        }
        else {
            return long0;
        }
    }

    //1 = User, 2 = Administrator
    public static UserTypeEnum getEnum(Long id){
        if (id.equals(long1)) {
            return USER;
        } else if (id.equals(long2)) {
            return ADMIN;
        }
        return null;
    }

}
