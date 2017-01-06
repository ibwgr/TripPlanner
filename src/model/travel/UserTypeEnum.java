package model.travel;

/**
 * Enumeration for User Class
 * 1 = User, 2 = Administrator
 */

public enum UserTypeEnum  {
    USER, ADMIN;

    //1 = User, 2 = Administrator
    public Long getId(){
        if (this==USER) {
            return new Long(1);
        }
        else if (this==ADMIN) {
            return new Long(2);
        }
        else {
            return new Long(0);
        }
    }

}
