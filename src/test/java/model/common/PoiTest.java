package model.common;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class PoiTest {

    // Integrationstest. Voraussetzung: austria-latest.csv ist geladen

    @Test
    public void searchCityByNameReturnsCorrectCity() throws SQLException {
        ArrayList<Poi> poiList = Poi.searchCityByName("Wien");
        // da es Wien mehrmals gibt, muessen wir uns auf eine Row fixieren
        for (Poi poi : poiList) {
            if (poi.getPoiCategory().equals(new PoiCategory("N17328659","Wien"))) {
                String resultId = poi.getId();
                Assert.assertEquals("N17328659", resultId);
                String resultLongitude = poi.getLongitude();
                Assert.assertEquals("16.3725042", resultLongitude);
                String resultLatitude = poi.getLatitude();
                Assert.assertEquals("48.2083537", resultLatitude);
                String resultPoiCategory = poi.getPoiCategory().getId();
                Assert.assertEquals("66", resultPoiCategory);
                String resultName = poi.getName();
                Assert.assertEquals("Wien", resultName);
            }
        }
    }

}