package controller.common;

import model.common.Pair;
import model.common.Poi;
import model.common.User;
import model.travel.Trip;
import view.admin.AdminView;
import view.admin.ProgressView;
import view.common.LoginView;
import view.common.TripPlannerMain;
import view.travel.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controller Class für TripPlannerMain
 *
 */
public class MainController implements ActionListener {

    TripPlannerMain tripPlannerMain;
    User user;
    Trip trip;
    AdminView adminView;
    ProgressView progressView;
    LoginView loginView;
    CitySearchView citySearchView;
    PoiSearchView poiSearchView;
    ActivityView activityView;
    TripView tripView;
    CompleteTripView completeTripView;
    ArrayList<Pair<String, Component>> viewList = new ArrayList<>();
    int currentViewNo = 0;

    public MainController(TripPlannerMain tripPlannerMain) {
        this.tripPlannerMain = tripPlannerMain;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "close_error":
                /*
                 * schliesst das Error Panel
                 */
                tripPlannerMain.closeErrorPanel();
                break;
            case "back":
                /*
                 * eine View zurück
                 */
                openLastView();
                break;
            case "forward":
                /*
                 * eine View nach vorne
                 */
                openNextView();
                break;
            case "close_view":
                /*
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
        setSubTitle("Current Trip: " + trip.getName());
    }

    public void setSubTitle(String subTitle) {
        tripPlannerMain.setSubTitle(subTitle);
    }

    public void setSubTitleVisible(Boolean visible) {
        tripPlannerMain.setSubTitleVisible(visible);
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
                viewList.get(i - 1).getKey()
                ,viewList.get(i - 1).getValue()
        );
        if (currentViewNo == 1) {
            tripPlannerMain.setBackButtonEnabled(false);
        } else {
            tripPlannerMain.setBackButtonEnabled(true);
        }
        if (currentViewNo == viewList.size()) {
            tripPlannerMain.setForwardButtonEnabled(false);
        } else {
            tripPlannerMain.setForwardButtonEnabled(true);
        }
    }

    public void closeCurrentView() {
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

    private void setNewView(String s, Component view) {
        if (currentViewNo < viewList.size()) {
            for (int i = viewList.size(); i > currentViewNo; i--) {
                viewList.remove(i - 1);
            }
        }
        viewList.add(new Pair<>(s, view));
        currentViewNo = viewList.size();
    }

    public void openLogin() {
        tripPlannerMain.removeAllViews();
        if (loginView == null) {
            loginView = new LoginView(this);
        }
        setNewView("Login", loginView);
        openView(currentViewNo);
//        tripPlannerMain.addView("Login", loginView);
    }

    public void openAdmin() {
        tripPlannerMain.removeAllViews();
        // Immer die gleiche View Instanz öffnen
        if (adminView == null) {
            adminView = new AdminView(this);
        }
        setNewView("Administration - Import", adminView);
        openView(currentViewNo);
//        tripPlannerMain.addView("Administration - Import", adminView);
    }

    public void openTripOverview() {
        tripPlannerMain.removeAllViews();
        tripView = new TripView(this);
        setNewView("Trip Overview", tripView);
        openView(currentViewNo);
//        tripPlannerMain.addView("Trip Overview", tripView);
    }

    public void openActivityOverview() {
        tripPlannerMain.removeAllViews();
        activityView = new ActivityView(this);
        setNewView("Activity Overview", activityView);
        openView(currentViewNo);
//        tripPlannerMain.addView("Activity Overview", activityView);
    }

    public ProgressView openProgressView() {
        tripPlannerMain.removeAllViews();
        if (progressView == null) {
            progressView = new ProgressView(this);
        }
        setNewView("Administration - Import Processing", progressView);
        openView(currentViewNo);
//        tripPlannerMain.addView("Administration - Import Processing", progressView);
        return progressView;
    }

    public void openCitySearchView() {
//        setTrip(Trip.searchByUserAndId(user, 1L)); // todo: nur für tests
        tripPlannerMain.removeAllViews();
        // Immer eine neue View Instanz erstellen
//        if (citySearchView == null) {
            citySearchView = new CitySearchView(this);
//        }
        setNewView("City Search", citySearchView);
        openView(currentViewNo);
//        tripPlannerMain.addView("City Search", citySearchView);
    }

    public void openPoiSearchView(Poi city) {
        tripPlannerMain.removeAllViews();
        // Immer eine neue View Instanz erstellen
//        if (poiSearchView == null) {
            poiSearchView = new PoiSearchView(this, city);
//        }
        setNewView("Point of interest Search", poiSearchView);
        openView(currentViewNo);
//        tripPlannerMain.addView("Point of interest Search", poiSearchView);
    }

    public void openCompleteTripView() {
        tripPlannerMain.removeAllViews();
        // Immer eine neue View Instanz erstellen
//        if (citySearchView == null) {
        completeTripView = new CompleteTripView(this);
//        }
        setNewView("Complete Trip", completeTripView);
        openView(currentViewNo);
//        tripPlannerMain.addView("City Search", citySearchView);
    }

    public void showErrorMessage(String message) {
        tripPlannerMain.showErrorMessage(message);
    }

}
