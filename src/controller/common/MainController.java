package controller.common;

import model.travel.User;
import view.admin.AdminView;
import view.admin.ProgressView;
import view.common.LoginView;
import view.common.TripPlannerMain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {

    TripPlannerMain tripPlannerMain;
    User user;
    AdminView adminView;
    ProgressView progressView;
    LoginView loginView;

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
        tripPlannerMain.removeAllViews();
        if (loginView == null) {
            loginView = new LoginView(this);
        }
        tripPlannerMain.addView("Login", loginView);
    }

    public void setUser(User user) {
        this.user = user;
        tripPlannerMain.setUsername(user.getUsername());
    }

    public void openAdmin() {
        tripPlannerMain.removeAllViews();
        if (adminView == null) {
            adminView = new AdminView(this);
        }
        tripPlannerMain.addView("Administration", adminView);
    }

    public void openTripOverview() {
//        tripPlannerMain.removeAllViews();
//        tripPlannerMain.addView("Trip Overview", new TripView(this));
    }

    public ProgressView openProgressView() {
        tripPlannerMain.removeAllViews();
        if (progressView == null) {
            progressView = new ProgressView(this);
        }
        tripPlannerMain.addView("Trip Overview", progressView);
        return progressView;
    }

    public void showErrorMessage(String message) {
        tripPlannerMain.showErrorMessage(message);
    }

}
