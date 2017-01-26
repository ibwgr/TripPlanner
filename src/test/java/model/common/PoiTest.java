package model.common;

import org.junit.Test;

public class PoiTest {

    /*
     * Die Tests der Such-Methoden wird nur mit einem echtem Datenbankzugriff durchgeführt.
     * Damit der Datenbankzugriff gemocked werden kann, müssten die statischen Search-Methoden
     * auf nicht statische Methoden umgebaut werden. Das Design mit den statischen Methoden
     * möchten wir aber nicht anpassen.
     *
     *
     */

    @Test
    public void searchCityByNameReturnsCorrectCity() {

        // Damit der DatabaseProxy injected werden kann, darf die Methode nicht statisch sein!

        Poi.searchCityByName("Schellenberg");

        // TODO
        //Assert.assertNotNull(null);
    }


}