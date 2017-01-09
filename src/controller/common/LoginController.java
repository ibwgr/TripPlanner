package controller.common;

import model.common.DatabaseProxy;
import model.travel.User;
import model.travel.UserTypeEnum;
import view.common.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by user on 08.01.2017.
 */
public class LoginController implements ActionListener {

    DatabaseProxy databaseProxy = new DatabaseProxy();
    LoginView loginView;
    MainController mainController;

    public LoginController(LoginView loginView, MainController mainController) {
        this.loginView = loginView;
        this.mainController = mainController;
    }

    public void doLogin(String username, String password) {
        User user =  User.searchByCredentials(databaseProxy, username, password);
        mainController.setUser(user);
        if (user.getTypeEnum().equals(UserTypeEnum.ADMIN)) {
            mainController.openAdmin();
        } else if (user.getTypeEnum().equals(UserTypeEnum.USER)) {
            mainController.openTripOverview();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "login":
                doLogin(loginView.getUsername(), loginView.getPassword());
                break;

        }
    }
}
