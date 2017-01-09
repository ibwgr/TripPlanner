package controller.common;

import model.travel.User;
import view.common.LoginView;
import view.common.TripPlannerMain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {

    TripPlannerMain tripPlannerMain;
    User user;

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

    public void openLogin() {
        tripPlannerMain.addView("Login", new LoginView(this));
    }

    public void setUser(User user) {
        this.user = user;
        tripPlannerMain.setUsername(user.getUsername());
    }
}
