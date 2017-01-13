package controller.travel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import controller.common.MainController;
import model.common.DatabaseProxy;
import model.travel.Trip;
import view.travel.TripView;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

/**
 * Created by user on 08.01.2017.
 */
public class TripController implements ActionListener {

    DatabaseProxy databaseProxy = new DatabaseProxy();
    TripView tripView;
    MainController mainController;
    Long currentTripId; // nur zuer Uebergaben von View zu Controller

    public TripController(TripView tripView, MainController mainController) {
        this.tripView = tripView;
        this.mainController = mainController;
    }

    public ArrayList<Trip> getTripList() {
        // TripListe (from Model)
        return Trip.searchByUser(mainController.getUser());
    }

    // Getters/Setters
    public Long getCurrentTripId() {
        return currentTripId;
    }
    public void setCurrentTripId(Long currentTripId) {
        System.out.println("TripController.setCurrentTripId: "+currentTripId);
        this.currentTripId = currentTripId;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "detail":
                if (this.currentTripId != null) {
                    // dem MainController mitteilen welche Reise fixiert werden soll (fuer nachfolgende Aktionen)
                    Trip t = Trip.searchByUserAndId(mainController.getUser(), this.currentTripId);
                    mainController.setTrip(t);
                } else {
                    mainController.showErrorMessage("Please select a trip");
                }
                break;

        }
    }
}
