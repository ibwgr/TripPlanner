package view.common;

import controller.common.LoginController;
import controller.common.MainController;

import javax.swing.*;

public class LoginView extends GridPanel {

    JTextField userField;
    JPasswordField passwordField;
    JButton loginButton;
    LoginController loginController;
    MainController mainController;

    public LoginView(MainController mainController) {
        super(3,1,100, 20);

        this.mainController = mainController;
        loginController = new LoginController(this, mainController);

        userField = new JTextField(10);
        userField.setText("benutzer"); // todo, temporaer damit zum testen einfacher
        addComponentToPanel(userField);
      //addComponentToPanel(userField = new JTextField(10));
        addPanelWithLabel("Username:", true);

        passwordField = new JPasswordField(10);
        passwordField.setText("benutzer");
        addComponentToPanel(passwordField); // todo, temporaer damit zum testen einfacher
      //addComponentToPanel(passwordField = new JPasswordField(10));
        addPanelWithLabel("Password:", true);

        addComponentToPanel(loginButton = createButton("Login", "login", loginController));
        addPanelWithLabel("", true);

        userField.setActionCommand("login");
        userField.addActionListener(loginController);
        passwordField.setActionCommand("login");
        passwordField.addActionListener(loginController);

        //setDefaultButton(loginButton);
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
