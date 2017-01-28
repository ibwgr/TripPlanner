package model.common;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class PoiTest {

    @Test
    public void searchCityByNameReturnsCorrectCity() throws SQLException {
        ArrayList<Poi> poi = Poi.searchCityByName("Wien");

        String resultId = poi.get(0).getId();
        Assert.assertEquals("N17328659",resultId);
        String resultLongitude = poi.get(0).getLongitude();
        Assert.assertEquals("16.3725042",resultLongitude);
        String resultLatitude = poi.get(0).getLatitude();
        Assert.assertEquals("48.2083537",resultLatitude);
        String resultPoiCategory = poi.get(0).getPoiCategory().getId();
        Assert.assertEquals("66",resultPoiCategory);
        String resultName = poi.get(0).getName();
        Assert.assertEquals("Wien",resultName);
    }

}