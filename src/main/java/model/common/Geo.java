package model.common;

/**
 * Ermittelt ob sich zwei Koordinaten innerhalb eines gewissens (quadratischen)
 * Radius befindet.
 * Beispiel: Um das IBW Gebaeude in Chur legen wir ein 10km Quadrat, genannt BoundingBox.
 * Die Abfrage ob sich der Hauptbahnhof in Zuerich innerhalb dieser Boundingbox befindet,
 * wird verneint. Hingegen waere die Abfrage mit dem Bahnhof in Chur mit ja zu beantworten.
 *
 * @author  Reto Kaufmann
 */
public class Geo {

    public static double[] getBoundingBox(double lat, double lon, double radius) {

        // BoundingBox (unser Quadrat um lat/long Position)
        double[] boundingBoxArray = new double[4];
        //
        double earth_radius = 6371; // Erd Radius in KM
        double maxLat = lat + Math.toDegrees(radius / earth_radius);
        double minLat = lat - Math.toDegrees(radius / earth_radius);
        double maxLon = lon + Math.toDegrees(radius / earth_radius / Math.cos(Math.toRadians(lat)));
        double minLon = lon - Math.toDegrees(radius / earth_radius / Math.cos(Math.toRadians(lat)));
        //
        // returnwert
        boundingBoxArray[0] = maxLat;
        boundingBoxArray[1] = minLat;
        boundingBoxArray[2] = maxLon;
        boundingBoxArray[3] = minLon;
        //
        return boundingBoxArray;
    }

    public boolean isInnerhalbBoundingBox(double[] boundingBoxArray, double pruefpunktLat, double pruefpunktLon) {
        //
        double maxLat = boundingBoxArray[0];
        double minLat = boundingBoxArray[1];
        double maxLon = boundingBoxArray[2];
        double minLon = boundingBoxArray[3];
        //
        if (pruefpunktLat >= minLat && pruefpunktLat <= maxLat) {
            if (pruefpunktLon >= minLon && pruefpunktLon <= maxLon) {
                //System.out.println("innerhalb BoundingBox!");
                return true;
            }
        }
        // andernfalls
        return false;
    }

    public boolean isInnerhalbBoundingBox(double mittelpunktLat, double mittelpunktLon, double radius, double pruefpunktLat, double pruefpunktLon) {
        double[] boundingBoxArray = getBoundingBox(mittelpunktLat,mittelpunktLon,radius);
        // aufruf obere methode
        return isInnerhalbBoundingBox(
                boundingBoxArray
                ,pruefpunktLat,pruefpunktLon);
    }


}
