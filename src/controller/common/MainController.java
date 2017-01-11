package controller.common;

import model.common.User;
import view.admin.AdminView;
import view.admin.ProgressView;
import view.common.LoginView;
import view.common.TripPlannerMain;
import view.travel.TripView;

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
        }
    }

    public User getUser() {
        return user;
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

    public void setUser(User user) {
        this.user = user;
        tripPlannerMain.setUsername(user.getUsername());
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

    public void showErrorMessage(String message) {
        tripPlannerMain.showErrorMessage(message);
    }

}
