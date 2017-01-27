package model.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import testFramework.UnitTest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PoiTest {

    @Category({ UnitTest.class })
    @Test
    public void searchCityByNameReturnsCorrectCity() throws SQLException {

        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(databaseProxy.prepareStatement(anyString())).thenReturn(preparedStatement);

        // return fake poi in resultSet
        // first next() method call returns true, the second returns false
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString(1)).thenReturn("N17328659");
        when(resultSet.getString(2)).thenReturn("Wien");
        when(resultSet.getString(3)).thenReturn("66");
        when(resultSet.getString(4)).thenReturn("16.3725042");
        when(resultSet.getString(5)).thenReturn("48.2083537");
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        ArrayList<Poi> poi = Poi.searchCityByName("Wien", databaseProxy);

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