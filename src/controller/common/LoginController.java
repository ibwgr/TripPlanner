package controller.common;

import model.common.DatabaseProxy;
import model.travel.User;

import javax.swing.*;

/**
 * Created by user on 08.01.2017.
 */
public class LoginController {

    DatabaseProxy databaseProxy = new DatabaseProxy();

    public void doLogin(String username, String password) {
        User user =  new User(databaseProxy);
        user.setUsername(username);
        user.setPassword(password);
        user.login();
    }
}
