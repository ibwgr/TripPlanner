package controller.travel;

import controller.common.MainController;
import model.common.Poi;
import model.travel.Activity;
import view.common.LoginView;
import view.travel.CitySearchView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CitySearchController implements ActionListener {

    CitySearchView citySearchView;
    MainController mainController;

    public CitySearchController(CitySearchView citySearchView, MainController mainController) {
        this.citySearchView = citySearchView;
        this.mainController = mainController;
    }

    private void addActivity() {

        Activity activity = new Activity(
                mainController.getTrip()
                ,citySearchView.getPoi()
                ,citySearchView.getDate()
                ,citySearchView.getComment()
        );
        activity.save();

        // oder

/*
        Activity.saveNewActivity(
                mainController.getTrip()
                ,citySearchView.getPoi()
                ,citySearchView.getDate()
                ,citySearchView.getComment()
        );
*/

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "searchCity":
                if (citySearchView.getSearchText().length() < 4) {
                    mainController.showErrorMessage("Search text is too short. Minimum length is 4 characters.");
                    return;
                }
                citySearchView.setSearchResult(Poi.searchCityByName(citySearchView.getSearchText()));
                break;
            case "add_activity":
                addActivity();
                break;
        }
    }

}
