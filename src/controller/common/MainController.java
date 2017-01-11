package controller.common;

import model.travel.User;
import view.admin.AdminView;
import view.admin.ProgressView;
import view.common.LoginView;
import view.common.TripPlannerMain;
import view.travel.CitySearchView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainController implements ActionListener {

    TripPlannerMain tripPlannerMain;
    User user;
    AdminView adminView;
    ProgressView progressView;
    LoginView loginView;
    ArrayList<Pair> viewList = new ArrayList<>();

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

    public User getUser() {
        return user;
    }

    class Pair {
        private String str;
        private Component compo;
        Pair(String str, Component compo) {
            this.str = str;
            this.compo = compo;
        }

        public String getStr() {
            return str;
        }

        public Component getCompo() {
            return compo;
        }
    }

    public void openLogin() {
        tripPlannerMain.removeAllViews();
        if (loginView == null) {
            loginView = new LoginView(this);
        }
        viewList.add(new Pair("Login", loginView));
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
        viewList.add(new Pair("Administration", adminView));
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
        viewList.add(new Pair("Administration", progressView));
        tripPlannerMain.addView("Administration", progressView);
        return progressView;
    }

    public void openCitySearchView() {
        tripPlannerMain.addView("City Search", new CitySearchView(this));
    }

    public void showErrorMessage(String message) {
        tripPlannerMain.showErrorMessage(message);
    }

}
