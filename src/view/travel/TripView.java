package view.travel;

import javax.swing.*;

import controller.common.MainController;

public class TripView extends JPanel {

    private JLabel testLabel;

    //JTextField userField;
    //JPasswordField passwordField;
    //JButton loginButton;


    //LoginController loginController;   ... muss ein TripController sein!
    MainController mainController;

    public TripView(MainController mainController) {

        testLabel = new JLabel("test trip view");

        this.add(testLabel);

    }

}
