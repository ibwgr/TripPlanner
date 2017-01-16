package controller.travel;

import controller.common.MainController;
import model.common.DatabaseProxy;
import model.travel.Activity;
import view.travel.ActivityView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by user on 08.01.2017.
 */
public class ActivityController implements ActionListener {

    DatabaseProxy databaseProxy = new DatabaseProxy();
    ActivityView activityView;
    MainController mainController;
    //Long currentTripId; // nur zuer Uebergaben von View zu Controller

    public ActivityController(ActivityView ActivityView, MainController mainController) {
        this.activityView = ActivityView;
        this.mainController = mainController;
    }

    public ArrayList<Activity> getActivityList() {
        // ActivityListe (from Model)
        return Activity.searchByTrip(mainController.getTrip());
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "detail":
                if (1==1) {
                    // dem MainController mitteilen welche Reise fixiert werden soll (fuer nachfolgende Aktionen)
                    //Trip t = Trip.searchByUserAndId(mainController.getUser(), this.currentTripId);
                    //mainController.setTrip(t);
                } else {
                    mainController.showErrorMessage("Please select a trip");
                }
                break;

        }
    }
}
