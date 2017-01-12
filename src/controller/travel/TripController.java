package controller.travel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import controller.common.MainController;
import model.common.DatabaseProxy;
import model.travel.Trip;
import view.travel.TripView;

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

    public ArrayList<Trip> getTripList() {
        // TripListe (from Model)
        return Trip.searchByUser(mainController.getUser());
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
