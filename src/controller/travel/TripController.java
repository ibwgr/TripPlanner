package controller.travel;

import controller.common.MainController;
import model.common.DatabaseProxy;
import model.common.User;
import model.common.UserTypeEnum;
import view.common.LoginView;
import view.travel.TripView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by user on 08.01.2017.
 */
public class TripController implements ActionListener {

    DatabaseProxy databaseProxy = new DatabaseProxy();
    TripView tripView;
    MainController mainController;

    public TripController(TripView tripView, MainController mainController) {
        this.tripView = tripView;
        this.mainController = mainController;
    }

        @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "detail":
                System.out.println("... Details ..."); // todo hier noch erkennen welche row
                break;

        }
    }
}
