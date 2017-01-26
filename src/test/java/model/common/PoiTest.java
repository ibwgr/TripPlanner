package model.common;

import org.junit.Test;

import java.util.ArrayList;

public class PoiTest {

    /*
     * Die Tests der Such-Methoden wird nur mit einem echtem Datenbankzugriff durchgeführt.
     * Damit der Datenbankzugriff gemocked werden kann, müssten die statischen Search-Methoden
     * auf nicht statische Methoden umgebaut werden. Das Design mit den statischen Methoden
     * möchten wir aber nicht anpassen.
     *
     */

    @Test
    public void searchCityByNameReturnsCorrectCity() {

        ArrayList<Poi> poi = Poi.searchCityByName("Schellenberg");



        // TODO
        //Assert.assertNotNull(null);
    }


}