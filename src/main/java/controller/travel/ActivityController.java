package controller.travel;

import controller.common.MainController;
import model.common.DatabaseProxy;
import model.travel.Activity;
import view.travel.ActivityView;

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

    DatabaseProxy databaseProxy = new DatabaseProxy();
    ActivityView activityView;
    MainController mainController;

    public ActivityController(ActivityView ActivityView, MainController mainController) {
        this.activityView = ActivityView;
        this.mainController = mainController;
    }

    public ArrayList<Activity> getActivityList() {
        // ActivityListe (from Model)
        return Activity.searchByTrip(mainController.getTrip());
    }

    public void setCurrentActivity(Long currentActivityId) {
        // dem MainController mitteilen welche Reise fixiert werden soll (fuer nachfolgende Aktionen)
        Activity a = Activity.searchById(currentActivityId);
        mainController.setActivity(a);
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
        }
    }

    private void executeActionShowMap() {
        mainController.openCompleteTripView();
    }

    private void executeActionMoveUp() {
        if (mainController.getActivity() != null) {
            System.out.println("moving up");
            // UPDATE (Reorder) und REFRESH
            try {
                mainController.getActivity().setActivityDateAfter();
                activityView.refreshTable();
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
                activityView.refreshTable();
            } catch (SQLException e1) {
                mainController.showErrorMessage("Error on deleting trip!");
                e1.printStackTrace();
            }
        } else {
            mainController.showErrorMessage("Please select an activity");
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

}
