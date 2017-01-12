package controller.common;

import model.common.User;
import model.travel.Trip;
import view.admin.AdminView;
import view.admin.ProgressView;
import view.common.LoginView;
import view.common.TripPlannerMain;
import view.travel.CitySearchView;
import view.travel.TripView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainController implements ActionListener {

    TripPlannerMain tripPlannerMain;
    User user;
    Trip trip;
    AdminView adminView;
    ProgressView progressView;
    LoginView loginView;
    CitySearchView citySearchView;
    ArrayList<Pair> viewList = new ArrayList<>();
    int currentViewNo = 0;

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
            case "back":
                /**
                 * eine View zurück
                 */
                openLastView();
                break;
            case "forward":
                /**
                 * eine View nach vorne
                 */
                openNextView();
                break;
            case "close_view":
                /**
                 * eine View nach vorne
                 */
                closeCurrentView();
                break;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        tripPlannerMain.setUsername(user.getUsername());
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
        tripPlannerMain.setSubTitle(trip.getName());
    }

    public void openLastView() {
        if (currentViewNo > 1) {
            openView(--currentViewNo);
        }
    }

    public void openNextView() {
        if (viewList.size() > currentViewNo) {
            openView(++currentViewNo);
        }
    }

    private void openView(int i) {
        tripPlannerMain.removeAllViews();
        tripPlannerMain.addView(
                viewList.get(i - 1).getStr()
                ,viewList.get(i - 1).getCompo()
        );
    }

    private void closeCurrentView() {
        if (currentViewNo == 0) {
            return;
        }
        viewList.remove(currentViewNo - 1);
        if (currentViewNo == 1 && viewList.size() == 0) {
            // wenn nur eine View vorhanden ist, die Login View öffnen
            currentViewNo = 0;
            openLogin();
        } else if (viewList.size() < currentViewNo) {
            // wenn die letzte View geschlossen wird, die vorherige View öffnen
            openView(--currentViewNo);
        } else {
            // wenn mehrere Views vorhanden sind und nicht die letzte geschlossen wird, öffne die nächste View
            openView(currentViewNo);
        }
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

    private void setNewView(String s, Component view) {
        if (currentViewNo < viewList.size()) {
            for (int i = viewList.size(); i > currentViewNo; i--) {
                viewList.remove(i - 1);
            }
        }
        viewList.add(new Pair(s, view));
        currentViewNo = viewList.size();
    }

    public void openLogin() {
        tripPlannerMain.removeAllViews();
        if (loginView == null) {
            loginView = new LoginView(this);
        }
        setNewView("Login", loginView);
        tripPlannerMain.addView("Login", loginView);
    }

    public void openAdmin() {
        tripPlannerMain.removeAllViews();
        if (adminView == null) {
            adminView = new AdminView(this);
        }
        setNewView("Administration - Import", adminView);
        tripPlannerMain.addView("Administration - Import", adminView);
    }

    public void openTripOverview() {
        tripPlannerMain.removeAllViews();
        tripPlannerMain.addView("Trip Overview", new TripView(this));
    }

    public ProgressView openProgressView() {
        tripPlannerMain.removeAllViews();
        if (progressView == null) {
            progressView = new ProgressView(this);
        }
        setNewView("Administration - Import Processing", progressView);
        tripPlannerMain.addView("Administration - Import Processing", progressView);
        return progressView;
    }

    public void openCitySearchView() {
        setTrip(Trip.searchById(1L)); // todo: nur für tests
        tripPlannerMain.removeAllViews();
        if (citySearchView == null) {
            citySearchView = new CitySearchView(this);
        }
        setNewView("City Search", citySearchView);
        tripPlannerMain.addView("City Search", citySearchView);
    }

    public void showErrorMessage(String message) {
        tripPlannerMain.showErrorMessage(message);
    }

}
