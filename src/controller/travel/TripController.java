package controller.travel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.common.MainController;
import model.common.DatabaseProxy;
import model.travel.Trip;
import view.travel.TripView;

import javax.swing.*;

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
        System.out.println("TripController.getTripList ...");
        return Trip.searchByUser(mainController.getUser());
    }

    // Getters/Setters
    public Long getCurrentTripId() {
        return currentTripId;
    }
    public void setCurrentTripId(Long currentTripId) {
        System.out.println("TripController.setCurrentTripId: "+currentTripId);
        this.currentTripId = currentTripId;
        // auch dem MainController mitteilen welche Reise fixiert werden soll (fuer nachfolgende Aktionen)
        Trip t = Trip.searchByUserAndId(mainController.getUser(), this.currentTripId);
        mainController.setTrip(t);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            //-----------------------------------
            case "detail":
                if (this.currentTripId != null) {
                    // Activity view
                    mainController.openActivityOverview();
                } else {
                    mainController.showErrorMessage("Please select a trip");
                }
                break;
            //-----------------------------------
            case "delete":
                System.out.println("delete button");
                if (mainController.getTrip() != null) {
                    int reply = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete trip \"" +mainController.getTrip().getName() +"\""
                           +"with all activities ?", "Delete?",  JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        // LOESCHEN
                        try {
                            mainController.getTrip().delete();
                            tripView.refreshTable();
                        } catch (SQLException e1) {
                            mainController.showErrorMessage("Error on deleting trip!");
                            e1.printStackTrace();
                        }
                    }
                } else {
                    mainController.showErrorMessage("Please select a trip");
                }
                //tableModel.fireTableDataChanged()
                break;
            //-----------------------------------
            case "newActivty":
                mainController.openCitySearchView();
                break;
            //-----------------------------------
            case "saveNewTrip":
                Trip t = new Trip(null, mainController.getUser(), tripView.getNewTripNameField().getText());
                System.out.println("New Trip :" +t.getName());
                try {
                    t.save();
                    mainController.setTrip(t);
                    tripView.refreshTable();
                } catch (SQLException e1) {
                    mainController.showErrorMessage("Error on saving trip!");
                    e1.printStackTrace();
                }
                break;

        }
    }
}
