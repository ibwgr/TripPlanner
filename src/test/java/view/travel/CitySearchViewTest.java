package view.travel;

import controller.common.MainController;
import org.junit.Assert;
import org.junit.Test;
import view.common.TripPlannerMain;

public class CitySearchViewTest {

    /**
     * citySearchView Konstruktor erstellt alle Komponenten
     */
    @Test
    public void citySearchViewCreatesAllComponentsInConstructor() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        CitySearchView citySearchView = new CitySearchView(mainController);

        Assert.assertNotNull(citySearchView.searchResult);
        Assert.assertNotNull(citySearchView.searchText);
        Assert.assertNotNull(citySearchView.commentText);
        Assert.assertNotNull(citySearchView.datePicker);
        Assert.assertNotNull(citySearchView.mapView);
    }

}