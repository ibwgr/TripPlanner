package controller.travel;

import controller.common.MainController;
import model.common.Geo;
import model.common.Poi;
import model.travel.Activity;
import view.travel.SearchView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Controller Class für SearchViews
 *
 * Diese Klasse behandelt alle Views die das Interface SearchView implementieren.
 * - Suche nach City
 * - Suche nach Point of interest
 * - Hinzufügen einer neuen Activity
 *
 * @author  Dieter Biedermann
 */
public class SearchController implements ActionListener {

    SearchView searchView;
    MainController mainController;

    public SearchController(SearchView searchView, MainController mainController) {
        this.searchView = searchView;
        this.mainController = mainController;
    }

    public void searchCity() {
        if (searchView.getSearchText().length() < 3) {
            mainController.showErrorMessage("Search text is too short. Minimum length is 3 characters.");
            return;
        }
        searchView.setSearchResult(Poi.searchCityByName(searchView.getSearchText()));
    }

    public void searchPoi() {
        // Geo Umkreissuche
        double[] boundingBox = Geo.getBoundingBox(searchView.getCity().getLatitudeDouble()
                ,searchView.getCity().getLongitudeDouble()
                ,searchView.getRadius()
        );

        if (!searchView.getSearchText().isEmpty()) {
            // Suche nach Namen
            if (searchView.getSearchText().length() < 3) {
                mainController.showErrorMessage("Search text minimum length is 3 characters.");
                return;
            }
            searchView.setSearchResult(Poi.searchPoiByNameAndRadius(searchView.getSearchText(), boundingBox));
        } else if (searchView.getPoiCategory() == null) {
            // Suche nur nach Radius (Category = All)
            searchView.setSearchResult(Poi.searchPoiByRadius(boundingBox));
        } else {
            // Suche nach Kategorie
            searchView.setSearchResult(Poi.searchPoiByCategoryAndRadius(searchView.getPoiCategory(), boundingBox));
        }
    }

    private void addActivity() {

        if (searchView.getPoi() == null
                || searchView.getDate() == null
                || searchView.getComment().isEmpty()
                ) {
            mainController.showErrorMessage("City/Point of interest, Date or Comment is missing");
            return;
        }

        Activity activity = new Activity(
                mainController.getTrip()
                ,searchView.getPoi()
                ,searchView.getDate()
                ,searchView.getComment()
                ,searchView.getCity() != null ? searchView.getCity().getName() : searchView.getPoi().getName()
        );

        try {
            activity.save();
        } catch (SQLException e) {
            mainController.showErrorMessage("Could not save Activity (" + e.getMessage() + ") (Search)");
        }

        mainController.openActivityOverview();
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
