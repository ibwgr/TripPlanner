package controller.common;

import model.common.DatabaseProxy;
import model.common.User;
import model.common.UserTypeEnum;
import view.common.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller Class für LoginView
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
        if (username.isEmpty() || password.isEmpty()) {
            mainController.showErrorMessage("Username and Password must be filled.");
            return;
        }

        User user =  User.searchByCredentials(databaseProxy, username, password);

        if (user == null) {
            mainController.showErrorMessage("Username not found or Password incorrect.");
            return;
        }

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
