package controller.travel;

import controller.common.MainController;
import model.common.Geo;
import model.common.Poi;
import model.travel.Activity;
import view.travel.SearchView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SearchController implements ActionListener {

    SearchView searchView;
    MainController mainController;

    public SearchController(SearchView searchView, MainController mainController) {
        this.searchView = searchView;
        this.mainController = mainController;
    }

    public void searchCity() {
        if (searchView.getSearchText().length() < 4) {
            mainController.showErrorMessage("Search text is too short. Minimum length is 4 characters.");
            return;
        }
        searchView.setSearchResult(Poi.searchCityByName(searchView.getSearchText()));
    }

    public void searchPoi() {
        if (searchView.getSearchText().length() < 3 && searchView.getPoiCategory() == null) {
            mainController.showErrorMessage("Search text minimum length is 3 characters.");
            return;
        }

        // Geo Umkreissuche
        double[] boundingBox = Geo.getBoundingBox(searchView.getCity().getLatitudeDouble()
                ,searchView.getCity().getLongitudeDouble()
                ,searchView.getRadius()
        );

        //

        // ToDO: Wie soll die Suche gemacht werden? Alle Varianten in einer Methode?
        searchView.setSearchResult(Poi.searchPoiByName(searchView.getSearchText(), boundingBox));
    }

    private void addActivity() {

        if (searchView.getPoi() == null
                || searchView.getDate() == null
                || searchView.getComment() == null
                ) {
            mainController.showErrorMessage("City/Point of interest, Date or Comment is missing");
            return;
        }

        Activity activity = new Activity(
                mainController.getTrip()
                ,searchView.getPoi()
                ,searchView.getDate()
                ,searchView.getComment()
        );
        try {
            activity.save();
        } catch (SQLException e) {
            mainController.showErrorMessage("Could not save Activity (" + e.getMessage() + ")");
        }

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

    public void openPoiSearch() {
        if (searchView.getPoi() == null) {
            mainController.showErrorMessage("Please select a city first.");
            return;
        }
        mainController.openPoiSearchView(searchView.getPoi());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "search_city":
                searchCity();
                break;
            case "search_poi":
                searchPoi();
                break;
            case "open_poi_search":
                openPoiSearch();
                break;
            case "add_activity":
                addActivity();
                break;
        }
    }

}
