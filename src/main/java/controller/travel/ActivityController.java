package controller.travel;

import controller.common.MainController;
import model.travel.Activity;
import view.travel.ActivityView;
import view.travel.MapPolyline;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class ActivityController extends MouseAdapter implements ActionListener, ListSelectionListener {

    ActivityView activityView;
    MainController mainController;
    MapPolyline mapView;

    public ActivityController(ActivityView ActivityView, MainController mainController, MapPolyline mapView) {
        this.activityView = ActivityView;
        this.mainController = mainController;
        this.mapView = mapView;
    }

    // TODO, diese liste koennten wir uns auf einer Instanzvariable zwischenspeichern
    public ArrayList<Activity> getActivityList() {
        // ActivityListe (from Model)
        return Activity.searchByTrip(mainController.getTrip());
    }

    public void setCurrentActivity(Long currentActivityId) {
        // dem MainController mitteilen welche Activity fixiert werden soll (fuer nachfolgende Aktionen)
        // TODO die muessen wir hier eigentlich nicht nochmals lesen wenn wir sie schon haben
        Activity a = Activity.searchById(currentActivityId);
        mapView.setWindow(a);
        mainController.setActivity(a);
        activityView.setDate(a.getDate());
        activityView.setComment(a.getComment());
    }

    //----------------------------------------------------
    // aus dem ActionListener
    //----------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "move_up":
                executeActionMoveUp();
                break;
            case "move_down":
                executeActionMoveDown();
                break;
            case "show_map":
                executeActionShowMap();
                break;
            case "newActivty":
                executeActionNewActivty();
                break;
            case "update_activity":
                updateActivity();
                break;
            case "delete_activity":
                deleteActivity();
                break;
        }
    }

    private void refresh() {
        activityView.refreshTable();
        mapView.refresh();
        activityView.setActivityInList(mainController.getActivity());
    }

    private void executeActionNewActivty() {
        mainController.openCitySearchView();
    }

    private void executeActionShowMap() {
        mainController.openBigMapView();
    }

    private void executeActionMoveUp() {
        if (mainController.getActivity() != null) {
            System.out.println("moving up");
            // UPDATE (Reorder) und REFRESH
            try {
                mainController.getActivity().setActivityDateAfter();
                refresh();
            } catch (SQLException e1) {
                mainController.showErrorMessage("Error on deleting trip!");
                e1.printStackTrace();
            }
        } else {
            mainController.showErrorMessage("Please select an activity");
        }
    }

    private void executeActionMoveDown() {
        if (mainController.getActivity() != null) {
            System.out.println("moving down");
            // UPDATE (Reorder) und REFRESH
            try {
                mainController.getActivity().setActivityDateBefore();
                refresh();
            } catch (SQLException e1) {
                mainController.showErrorMessage("Error on deleting trip!");
                e1.printStackTrace();
            }
        } else {
            mainController.showErrorMessage("Please select an activity");
        }
    }

    private void updateActivity() {

        if (activityView.getDate() == null
                || activityView.getComment() == null
                ) {
            mainController.showErrorMessage("Date or Comment is missing");
            return;
        }

        Activity activity = mainController.getActivity();
        activity.setDate(activityView.getDate());
        activity.setComment(activityView.getComment());

        try {
            activity.save();
        } catch (SQLException e) {
            mainController.showErrorMessage("Could not save Activity (" + e.getMessage() + ") (Act)");
        }

        refresh();
    }

    private void deleteActivity() {

        int reply = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete activity \"" +mainController.getActivity() +" ?"
                ,"Delete?",  JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                mainController.getActivity().delete();
            } catch (SQLException e) {
                mainController.showErrorMessage("Could not delete Activity (" + e.getMessage() + ")");
            }
            refresh();
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
            System.out.println("JTABLE Row | " +activityView.getTable().getValueAt(activityView.getTable().getSelectedRow(), 0).toString());
            Long tripId = (Long) activityView.getTable().getValueAt(activityView.getTable().getSelectedRow(), 0);
            setCurrentActivity(tripId);
        } catch (IndexOutOfBoundsException e) {
            //index out of bound, only after delete, no problem!
        }
    }

    //----------------------------------------------------
    // aus dem MouseListener
    //----------------------------------------------------
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println("Maus gecklickt!");

        if(mouseEvent.getClickCount()==2){
            System.out.println("Maus Doppelklick!");
            // doppelklick ist dasselbe wie wenn man auf den Detail Button druecken wuerde!
            //executeActionDetail();
        }
    }

//    public Object getFullTripActivityPath() {
//        ArrayList<Activity> getActivityList();
//        return null;
//    }
}
