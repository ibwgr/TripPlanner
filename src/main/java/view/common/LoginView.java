package view.common;

import controller.common.LoginController;
import controller.common.MainController;
import model.common.DatabaseProxy;

import javax.swing.*;

public class LoginView extends GridPanel {

    JTextField userField;
    JPasswordField passwordField;
    JButton loginButton;
    LoginController loginController;
    MainController mainController;

    public LoginView(MainController mainController) {
        super(100, 20);

        this.mainController = mainController;
        loginController = new LoginController(this, mainController, new DatabaseProxy());

        addComponentToPanel(userField = new JTextField(10));
        addPanelWithLabel("Username:", true);
        addComponentToPanel(passwordField = new JPasswordField(10));
        addPanelWithLabel("Password:", true);
        addComponentToPanel(loginButton = createButton("Login", "login", loginController));
        addPanelWithLabel("", true);

        // set additional attributes to user field
        userField.setText("benutzer"); // todo, temporaer damit zum testen einfacher
        userField.setActionCommand("login");
        userField.addActionListener(loginController);

        // set additional attributes to password field
        passwordField.setText("benutzer"); // todo, temporaer damit zum testen einfacher
        passwordField.setActionCommand("login");
        passwordField.addActionListener(loginController);

    }

    public String getUsername() {
        return userField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

}
