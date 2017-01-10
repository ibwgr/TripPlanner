package view.common;

import controller.common.LoginController;
import controller.common.MainController;

import javax.swing.*;

public class LoginView extends GridPanel {

    JTextField userField, passwordField;
    LoginController loginController;
    MainController mainController;

    public LoginView(MainController mainController) {
        super(3,1,100, 20);

        this.mainController = mainController;
        loginController = new LoginController(this, mainController);

        addComponentToPanel(userField = new JTextField(10));
        addPanelWithLabel("Username:", true);

        addComponentToPanel(passwordField = new JTextField(10));
        addPanelWithLabel("Password:", true);

        addComponentToPanel(createButton("Login", "login", loginController));
        addPanelWithLabel("", true);

/*
        this.setViewTitle("Administration - File Upload");
        this.addView(inputPanelBorder);
*/
    }

    public String getUsername() {
        return userField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }
}
