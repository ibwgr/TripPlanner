package view.travel;

import controller.common.MainController;
import model.common.Poi;
import model.common.PoiCategory;
import org.junit.Assert;
import org.junit.Test;
import view.common.TripPlannerMain;

public class PoiSearchViewTest {

    /**
     * citySearchView Konstruktor erstellt alle Komponenten
     */
    @Test
    public void poiSearchViewCreatesAllComponentsInConstructor() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        PoiSearchView poiSearchView = new PoiSearchView(mainController, new Poi("N17328659"
                , "Wien"
                , new PoiCategory("66", "City")
                , "16.3725042"
                , "48.2083537"
        ));

        Assert.assertNotNull(poiSearchView.searchResult);
        Assert.assertNotNull(poiSearchView.searchText);
        Assert.assertNotNull(poiSearchView.commentText);
        Assert.assertNotNull(poiSearchView.datePicker);
        Assert.assertNotNull(poiSearchView.mapView);
        Assert.assertNotNull(poiSearchView.poiCategoryCombo);
        Assert.assertNotNull(poiSearchView.radiusCombo);
    }

}