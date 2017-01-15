package model.common;

import controller.common.MainController;
import org.junit.Assert;
import org.junit.Test;
import view.admin.AdminView;
import view.common.TripPlannerMain;

import static org.junit.Assert.*;

public class PoiTest {

    @Test
    public void searchCityByNameReturnsCorrectCity() {
        Poi.searchCityByName("Schellenberg");

        // TODO
        //Assert.assertNotNull(null);
    }


}