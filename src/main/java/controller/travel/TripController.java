package controller.travel;

import controller.common.MainController;
import model.common.DatabaseProxy;
import model.travel.Trip;
import view.travel.TripView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller Class fuer TripVew
 *
 * Diese Klasse steuert alle Aktionen
 * Bindeglied zwischen TripView und Trip Model
 *
 * @author  Dieter Biedermann
 * @author  Reto Kaufmann
 */
public class TripController extends MouseAdapter implements ActionListener, ListSelectionListener {

    TripView tripView;
    MainController mainController;

    public TripController(TripView tripView, MainController mainController) {
        this.tripView = tripView;
        this.mainController = mainController;
    }

    public ArrayList<Trip> getTripList() {
        // TripListe (from Model)
        System.out.println("TripController.getTripList ...");
        return Trip.searchByUser(mainController.getUser());
    }

    public void setCurrentTrip(Long currentTripId) {
        // dem MainController mitteilen welche Reise fixiert werden soll (fuer nachfolgende Aktionen)
        Trip t = Trip.searchById(currentTripId);
        mainController.setTrip(t);
    }

    //----------------------------------------------------
    // aus dem ActionListener
    //----------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "detail":
                executeActionDetail();
                break;
            case "delete":
                executeActionDelete();
                break;
            case "newActivty":
                executeActionNewAcitivy();
                break;
            case "saveNewTrip":
                executeActionSaveNewTrip();
                break;
        }
    }

    private void executeActionSaveNewTrip() {
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
    }

    private void executeActionNewAcitivy() {
        if (mainController.getTrip() != null) {
            mainController.openCitySearchView();
        } else {
            mainController.showErrorMessage("Please select a trip");
        }
    }

    private void executeActionDelete() {
        System.out.println("delete button");
        if (mainController.getTrip() != null) {
            int reply = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete trip \"" +mainController.getTrip().getName() +"\""
                   +"with all activities ?", "Delete?",  JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                // LOESCHEN und REFRESH
                try {
                    mainController.getTrip().delete();
                    mainController.setTrip(null);
                    tripView.refreshTable();
                } catch (SQLException e1) {
                    mainController.showErrorMessage("Error on deleting trip!");
                    e1.printStackTrace();
                }
            }
        } else {
            mainController.showErrorMessage("Please select a trip");
        }
    }

    private void executeActionDetail() {
        if (mainController.getTrip() != null) {
            // Activity view
            mainController.openActivityOverview();
        } else {
            mainController.showErrorMessage("Please select a trip");
        }
    }



    //----------------------------------------------------
    // aus dem ListSelectionListener
    //----------------------------------------------------
    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        executeListSelection();
    }

    private void executeListSelection() {
        try {
            System.out.println("JTABLE Row | " +tripView.getTable().getValueAt(tripView.getTable().getSelectedRow(), 0).toString());
            Long tripId = (Long) tripView.getTable().getValueAt(tripView.getTable().getSelectedRow(), 0);
            setCurrentTrip(tripId);
        } catch (IndexOutOfBoundsException e) {
            //index out of bound, only after delete, no problem!
        }
    }

    //----------------------------------------------------
    // aus dem MouseListener
    //----------------------------------------------------
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount()==2){
            // doppelklick ist dasselbe wie wenn man auf den Detail Button druecken wuerde!
            executeActionDetail();
        }
    }

}
