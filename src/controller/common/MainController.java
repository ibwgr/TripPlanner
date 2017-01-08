package controller.common;

import view.common.TripPlannerMain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {

    TripPlannerMain tripPlannerMain;

    public MainController(TripPlannerMain tripPlannerMain) {
        this.tripPlannerMain = tripPlannerMain;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "close_error":
                /**
                 * schliesst das Error Panel
                 */
                tripPlannerMain.closeErrorPanel();
                break;

        }
    }

}
