package controller.common;

import model.common.Poi;
import model.common.User;
import model.common.ViewInfo;
import model.travel.Activity;
import model.travel.Trip;
import view.admin.AdminView;
import view.admin.ProgressView;
import view.common.LoginView;
import view.common.TripPlannerMain;
import view.travel.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Controller Class für TripPlannerMain
 *
 */
public class MainController implements ActionListener {

    TripPlannerMain tripPlannerMain;
    User user;
    Trip trip;
    Activity activity;
    AdminView adminView;
    ProgressView progressView;
    LoginView loginView;
    CitySearchView citySearchView;
    PoiSearchView poiSearchView;
    ActivityView activityView;
    TripView tripView;
    BigMapView bigMapView;
    ArrayList<ViewInfo> viewList = new ArrayList<>();
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
            case "view_selected":
                /*
                 * öffnet die selektierte View
                 */
                currentViewNo = tripPlannerMain.getViewListComboBoxSelectedIndex();
                openView(currentViewNo);
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
        if (trip != null) {
            for (ViewInfo viewInfo:viewList) {
                if (viewInfo.getNeedsRefresh()) {
                    viewInfo.setHasBeenRefreshed(false);
                }
            }
            setSubTitle("Current Trip: " + trip.getName());
        }
    }

    public void setSubTitle(String subTitle) {
        tripPlannerMain.setSubTitle(subTitle);
    }

    public void setSubTitleVisible(Boolean visible) {
        tripPlannerMain.setSubTitleVisible(visible);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
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
        ViewInfo viewInfo = viewList.get(i - 1);
        String title = viewInfo.getTitle();
        Component compo = viewInfo.getCompo();
        tripPlannerMain.removeAllViews();

        tripPlannerMain.setSubTitleVisible(viewInfo.getShowSubTitle());

        if (viewInfo.getNeedsRefresh() && !viewInfo.getHasBeenRefreshed()) {
            // reload class
            try {
                compo = compo.getClass().getConstructor(MainController.class).newInstance(this);
                viewInfo.setCompo(compo);
                viewInfo.setHasBeenRefreshed(true);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        tripPlannerMain.addView(title, compo);

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
        tripPlannerMain.setViewListComboBoxSelectedIndex(currentViewNo);
    }

    public void clearViewList() {
        viewList.clear();
        currentViewNo = viewList.size();
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

    private void setNewView(String title, Component compo, Boolean showSubTitle, Boolean needsRefresh) {
        if (currentViewNo < viewList.size()) {
            for (int i = viewList.size(); i > currentViewNo; i--) {
                viewList.remove(i - 1);
            }
        }
        viewList.add(new ViewInfo(title, compo, showSubTitle, needsRefresh));
        tripPlannerMain.setViewListComboBox(viewList);
        currentViewNo = viewList.size();
    }

    public void openLogin() {
        // Immer die gleiche View Instanz öffnen
        if (loginView == null) {
            loginView = new LoginView(this);
        }
        setNewView("Login", loginView, false, false);
        openView(currentViewNo);
    }

    public void openAdmin() {
        // Immer die gleiche View Instanz öffnen
        if (adminView == null) {
            adminView = new AdminView(this);
        }
        setNewView("Administration - Import", adminView,false,false);
        openView(currentViewNo);
    }

    public void openTripOverview() {
        // Immer die gleiche View Instanz öffnen
        if (tripView == null) {
            tripView = new TripView(this);
        }
        setNewView("Trip Overview", tripView,true,false);
        openView(currentViewNo);
    }

    public void openActivityOverview() {
        // Immer eine neue View Instanz erstellen
        activityView = new ActivityView(this);
        setNewView("Activity Overview", activityView, true,true);
        openView(currentViewNo);
    }

    public ProgressView openProgressView() {
        // Immer eine neue View Instanz erstellen
        progressView = new ProgressView(this);
        setNewView("Administration - Import Processing", progressView, false,false);
        openView(currentViewNo);
        return progressView;
    }

    public void openCitySearchView() {
        // Immer eine neue View Instanz erstellen
        citySearchView = new CitySearchView(this);
        setNewView("City Search", citySearchView, true,false);
        openView(currentViewNo);
    }

    public void openPoiSearchView(Poi city) {
        // Immer eine neue View Instanz erstellen
        poiSearchView = new PoiSearchView(this, city);
        setNewView("Point of interest Search", poiSearchView, true, false);
        openView(currentViewNo);
    }

    public void openBigMapView() {
        // Immer eine neue View Instanz erstellen
        bigMapView = new BigMapView(this);
        setNewView("Big Map View", bigMapView, true,true);
        openView(currentViewNo);
    }

    public void showErrorMessage(String message) {
        tripPlannerMain.showErrorMessage(message);
    }

}
