package model.common;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class GeoTest {

    //Ap Daun Chur
    private static final double kletterzentrumApDaunChurLat = 46.852338;
    private static final double kletterzentrumApDaunChurLon = 9.518351;

    //Hotel Ibis, Chur (ca. 1 KM)
    private static final double hotelIbisMcDonaldsChurLat = 46.846138;
    private static final double hotelIbisMcDonaldsChurLon = 9.512531;

    //Hotel Krone, Churwalden  (ca. 8 KM)
    private static final double hotelKroneChurwaldentLat = 46.777204;
    private static final double hotelKroneChurwaldenLon = 9.545394;

    //Waldorf Astoria, New York (ca. 6800km)
    private static final double hotelWaldorfAstoriaNewYorkLat = 40.756482;
    private static final double hotelWaldorfAstoriaNewYorkLon = -73.973305;


    /**
     * ------------------------------------------------------------------------------
     *  BoundingBox
     * ------------------------------------------------------------------------------
     */
    @Test
    public void testBoundingBoxKletterzentrumMitRadiusEinKm(){

        //Ap Daun Chur
        double mittelpunktLat = kletterzentrumApDaunChurLat;
        double mittelpunktLon = kletterzentrumApDaunChurLon;

        double radius = 1.0;

        Geo g = new Geo();
        double[] boundingBoxArray = g.getBoundingBox(mittelpunktLat,mittelpunktLon,radius);
        double maxLat = boundingBoxArray[0];
        double minLat = boundingBoxArray[1];
        double maxLon = boundingBoxArray[2];
        double minLon = boundingBoxArray[3];

        //Ausgabe fuer test-google-maps-1km.html, soll so aussehen: ['Bondi Beach', -33.890542, 151.274856, 4],
        System.out.println("testBoundingBoxKletterzentrumMitRadiusEinKm");
        System.out.println(" ['center' , " + mittelpunktLat + ", " + mittelpunktLon + "],");
        System.out.println(" ['nw    ' , " + maxLat + ", " + minLon + "],");
        System.out.println(" ['ne    ' , " + maxLat + ", " + maxLon + "],");
        System.out.println(" ['sw    ' , " + minLat + ", " + minLon + "],");
        System.out.println(" ['se    ' , " + minLat + ", " + maxLon + "] ");
        /*
         ['center' , 46.852338, 9.518351],
         ['nw    ' , 46.86133121605919, 9.505200734104253],
         ['ne    ' , 46.86133121605919, 9.531501265895745],
         ['sw    ' , 46.843344783940815, 9.505200734104253],
         ['se    ' , 46.843344783940815, 9.531501265895745]
        */
        Assert.assertEquals(46.843344783940815,minLat,0.0000000000000000000000);
        Assert.assertEquals(9.531501265895745,maxLon,0.0000000000000000000000);
    }

    @Test
    public void testBoundingBoxKletterzentrumMitRadiusZehnKm(){

        //Ap Daun Chur
        double mittelpunktLat = kletterzentrumApDaunChurLat;
        double mittelpunktLon = kletterzentrumApDaunChurLon;

        double radius = 10.0;

        Geo g = new Geo();
        double[] boundingBoxArray = g.getBoundingBox(mittelpunktLat,mittelpunktLon,radius);
        double maxLat = boundingBoxArray[0];
        double minLat = boundingBoxArray[1];
        double maxLon = boundingBoxArray[2];
        double minLon = boundingBoxArray[3];

        //Ausgabe fuer test-google-maps-1km.html, soll so aussehen: ['Bondi Beach', -33.890542, 151.274856, 4],
        System.out.println("testBoundingBoxKletterzentrumMitRadiusZehnKm");
        System.out.println(" ['center' , " + mittelpunktLat + ", " + mittelpunktLon + "],");
        System.out.println(" ['nw    ' , " + maxLat + ", " + minLon + "],");
        System.out.println(" ['ne    ' , " + maxLat + ", " + maxLon + "],");
        System.out.println(" ['sw    ' , " + minLat + ", " + minLon + "],");
        System.out.println(" ['se    ' , " + minLat + ", " + maxLon + "] ");
        /*
         ['center' , 46.852338, 9.518351],
         ['nw    ' , 46.942270160591875, 9.386848341042548],
         ['ne    ' , 46.942270160591875, 9.64985365895745],
         ['sw    ' , 46.76240583940813, 9.386848341042548],
         ['se    ' , 46.76240583940813, 9.64985365895745]
        */
        Assert.assertEquals(46.76240583940813,minLat,0.0000000000000000000000);
        Assert.assertEquals(9.64985365895745,maxLon,0.0000000000000000000000);
    }

    /**
     * ------------------------------------------------------------------------------
     *  Einzeln
     * ------------------------------------------------------------------------------
     */
    @Test
    public void testKoordinatenInnerhalbBoundingBoxMitArray(){

        //Ap Daun Chur
        double mittelpunktLat = kletterzentrumApDaunChurLat;
        double mittelpunktLon = kletterzentrumApDaunChurLon;

        //Hotel Ibis, Chur (ca. 1 KM)
        double pruefpunktLat = hotelIbisMcDonaldsChurLat;
        double pruefpunktLon = hotelIbisMcDonaldsChurLon;

        double radius = 1.0;

        Geo g = new Geo();
        double[] boundingBoxArray = g.getBoundingBox(mittelpunktLat,mittelpunktLon,radius);
        boolean isInnerhalbBoundingBox = g.isInnerhalbBoundingBox(
                boundingBoxArray
                ,pruefpunktLat,pruefpunktLon);

        Assert.assertEquals(isInnerhalbBoundingBox,true);
    }

    @Test
    public void testKoordinatenInnerhalbBoundingBoxIbisInnerhalb(){

        //Ap Daun Chur
        double mittelpunktLat = kletterzentrumApDaunChurLat;
        double mittelpunktLon = kletterzentrumApDaunChurLon;

        //Hotel Ibis, Chur (ca. 1 KM)
        double pruefpunktLat = hotelIbisMcDonaldsChurLat;
        double pruefpunktLon = hotelIbisMcDonaldsChurLon;

        double radius = 1.0;

        Geo g = new Geo();
        boolean isInnerhalbBoundingBox = g.isInnerhalbBoundingBox(
                mittelpunktLat,mittelpunktLon
                ,radius
                ,pruefpunktLat,pruefpunktLon);

        Assert.assertEquals(isInnerhalbBoundingBox,true);
    }

    @Test
    public void testKoordinatenInnerhalbBoundingBoxIbisAusserhalb(){

        //Ap Daun Chur
        double mittelpunktLat = kletterzentrumApDaunChurLat;
        double mittelpunktLon = kletterzentrumApDaunChurLon;

        //Hotel Ibis, Chur (ca. 1 KM)
        double pruefpunktLat = hotelIbisMcDonaldsChurLat;
        double pruefpunktLon = hotelIbisMcDonaldsChurLon;

        double radius = 0.2;

        Geo g = new Geo();
        boolean isInnerhalbBoundingBox = g.isInnerhalbBoundingBox(
                mittelpunktLat,mittelpunktLon
                ,radius
                ,pruefpunktLat,pruefpunktLon);

        Assert.assertEquals(isInnerhalbBoundingBox,false);
    }

    @Test
    public void testKoordinatenInnerhalbBoundingBoxChurwaldenInnerhalb(){

        //Ap Daun Chur
        double mittelpunktLat = kletterzentrumApDaunChurLat;
        double mittelpunktLon = kletterzentrumApDaunChurLon;

        //Hotel Krone, Churwalden  (ca. 10 KM)
        double pruefpunktLat = hotelKroneChurwaldentLat;
        double pruefpunktLon = hotelKroneChurwaldenLon;

        double radius = 15;

        Geo g = new Geo();
        boolean isInnerhalbBoundingBox = g.isInnerhalbBoundingBox(
                mittelpunktLat,mittelpunktLon
                ,radius
                ,pruefpunktLat,pruefpunktLon);

        Assert.assertEquals(isInnerhalbBoundingBox,true);
    }

    @Test
    public void testKoordinatenInnerhalbBoundingBoxChurwaldenAusserhalb(){

        //Ap Daun Chur
        double mittelpunktLat = kletterzentrumApDaunChurLat;
        double mittelpunktLon = kletterzentrumApDaunChurLon;

        //Hotel Krone, Churwalden  (ca. 10 KM)
        double pruefpunktLat = hotelKroneChurwaldentLat;
        double pruefpunktLon = hotelKroneChurwaldenLon;

        double radius = 8;

        Geo g = new Geo();
        boolean isInnerhalbBoundingBox = g.isInnerhalbBoundingBox(
                mittelpunktLat,mittelpunktLon
                ,radius
                ,pruefpunktLat,pruefpunktLon);

        Assert.assertEquals(isInnerhalbBoundingBox,false);
    }

    @Test
    public void testKoordinatenInnerhalbBoundingBoxWaldorfNewYorkInnerhalb(){

        //Ap Daun Chur
        double mittelpunktLat = kletterzentrumApDaunChurLat;
        double mittelpunktLon = kletterzentrumApDaunChurLon;

        //Waldorf Astoria, New York
        double pruefpunktLat = hotelWaldorfAstoriaNewYorkLat;
        double pruefpunktLon = hotelWaldorfAstoriaNewYorkLon;

        double radius = 8000;

        Geo g = new Geo();
        boolean isInnerhalbBoundingBox = g.isInnerhalbBoundingBox(
                mittelpunktLat,mittelpunktLon
                ,radius
                ,pruefpunktLat,pruefpunktLon);

        Assert.assertEquals(isInnerhalbBoundingBox,true);
    }

    @Test
    public void testKoordinatenInnerhalbBoundingBoxWaldorfNewYorkAusserhalb(){

        //Ap Daun Chur
        double mittelpunktLat = kletterzentrumApDaunChurLat;
        double mittelpunktLon = kletterzentrumApDaunChurLon;

        //Waldorf Astoria, New York
        double pruefpunktLat = hotelWaldorfAstoriaNewYorkLat;
        double pruefpunktLon = hotelWaldorfAstoriaNewYorkLon;

        double radius = 6000;

        Geo g = new Geo();
        boolean isInnerhalbBoundingBox = g.isInnerhalbBoundingBox(
                mittelpunktLat,mittelpunktLon
                ,radius
                ,pruefpunktLat,pruefpunktLon);

        Assert.assertEquals(isInnerhalbBoundingBox,false);
    }


    /**
     * ------------------------------------------------------------------------------
     *  Abarbeitung Liste
     * ------------------------------------------------------------------------------
     */
    @Test
    public void testKoordinatenInnerhalbBoundingBoxMitListeZwanzigKm(){

        //Ap Daun Chur
        double mittelpunktLat = kletterzentrumApDaunChurLat;
        double mittelpunktLon = kletterzentrumApDaunChurLon;

        double[][] hotelListe = new double[3][2];
        hotelListe[0][0] = hotelIbisMcDonaldsChurLat;
        hotelListe[0][1] = hotelIbisMcDonaldsChurLon;
        hotelListe[1][0] = hotelKroneChurwaldentLat;
        hotelListe[1][1] = hotelKroneChurwaldenLon;
        hotelListe[2][0] = hotelWaldorfAstoriaNewYorkLat;
        hotelListe[2][1] = hotelWaldorfAstoriaNewYorkLon;

        double radius = 20.0;

        Geo g = new Geo();
        for (int i = 0; i < hotelListe.length; i++ ){
            //
            boolean isInnerhalbBoundingBox = g.isInnerhalbBoundingBox(
                    mittelpunktLat, mittelpunktLon
                    , radius
                    , hotelListe[i][0], hotelListe[i][1]);
            //
            if (isInnerhalbBoundingBox) {
                System.out.println("ist innerhalb");
            } else {
                System.out.println("ist ausserhalb");
            }
        }
    }
}