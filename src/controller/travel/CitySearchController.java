package controller.travel;

import controller.common.MainController;
import model.common.Poi;
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

        }
    }

}
