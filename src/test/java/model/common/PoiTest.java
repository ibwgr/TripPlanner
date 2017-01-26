package model.common;

import org.junit.Test;

public class PoiTest {

    @Test
    public void searchCityByNameReturnsCorrectCity() {

        // Damit der DatabaseProxy injected werden kann, darf die Methode nicht statisch sein!

        Poi.searchCityByName("Schellenberg");

        // TODO
        //Assert.assertNotNull(null);
    }


}