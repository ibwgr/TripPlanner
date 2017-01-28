package model.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Diese Klasse speichert Point of interest Daten aus der Tabelle POI.
 *
 * Zur Suche von Point of interests stehen folgende statischen Methoden zur Verfügung:
 * - searchCityByName
 * - searchPoiByNameAndRadius
 * - searchPoiByCategoryAndRadius
 * - searchPoiByRadius
 *
 * @author  Reto Kaufmann
 * @author  Dieter Biedermann
 */
public class Poi {

    // Instanzvariablen
    private String id;
    private String name;
    private PoiCategory poiCategory;
    private String longitude;
    private String latitude;

    public Poi(String id, String name, PoiCategory poiCategory, String longitude, String latitude) {
        this.id = id;
        this.name = name;
        this.poiCategory = poiCategory;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static ArrayList<Poi> searchCityByName(String name) {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        ArrayList<Poi> poiList = new ArrayList<>();
        String query = "select id, name, category_id, longitude, latitude from poi "
                        + "where category_id in ('66','69','70') and lower(name) like lower(?)";

        PreparedStatement preparedStatement = databaseProxy.prepareStatement(query);

        try {
            preparedStatement.setString(1, "%"+name+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                poiList.add(
                        new Poi(
                                resultSet.getString(1)
                                ,resultSet.getString(2)
                                ,PoiCategory.searchById(resultSet.getString(3))
                                ,resultSet.getString(4)
                                ,resultSet.getString(5)
                        )
                );

            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseProxy.close();
        }

        return poiList;
    }

    public static ArrayList<Poi> searchPoiByNameAndRadius(String name, double[] boundingBox) {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        ArrayList<Poi> poiList = new ArrayList<>();
        String query = "select id, name, category_id, longitude, latitude from poi " +
                "where category_id not in ('66','69','70') " +
                "and latitude between ? and ? " +
                "and longitude between ? and ? " +
                "and lower(name) like lower(?)"
                ;

        PreparedStatement preparedStatement = databaseProxy.prepareStatement(query);

        try {
            // boundingBox Array: maxLat, minLat, maxLon, minLon
            // Suche: latitude
            preparedStatement.setString(1, String.valueOf(boundingBox[1]));
            preparedStatement.setString(2, String.valueOf(boundingBox[0]));
            // Suche: longitude
            preparedStatement.setString(3, String.valueOf(boundingBox[3]));
            preparedStatement.setString(4, String.valueOf(boundingBox[2]));
            // Suche: name
            preparedStatement.setString(5, "%"+name+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                poiList.add(
                        new Poi(
                                resultSet.getString(1)
                                ,resultSet.getString(2)
                                ,PoiCategory.searchById(resultSet.getString(3))
                                ,resultSet.getString(4)
                                ,resultSet.getString(5)
                        )
                );

            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseProxy.close();
        }

        return poiList;
    }

    public static ArrayList<Poi> searchPoiByCategoryAndRadius(PoiCategory poiCategory, double[] boundingBox) {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        ArrayList<Poi> poiList = new ArrayList<>();
        String query = "select id, name, category_id, longitude, latitude from poi " +
                "where category_id not in ('66','69','70') " +
                "and latitude between ? and ? " +
                "and longitude between ? and ? " +
                "and category_id = ?"
                ;

        PreparedStatement preparedStatement = databaseProxy.prepareStatement(query);

        try {
            // boundingBox Array: maxLat, minLat, maxLon, minLon
            // Suche: latitude
            preparedStatement.setString(1, String.valueOf(boundingBox[1]));
            preparedStatement.setString(2, String.valueOf(boundingBox[0]));
            // Suche: longitude
            preparedStatement.setString(3, String.valueOf(boundingBox[3]));
            preparedStatement.setString(4, String.valueOf(boundingBox[2]));
            // Suche: name
            preparedStatement.setString(5, poiCategory.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                poiList.add(
                        new Poi(
                                resultSet.getString(1)
                                ,resultSet.getString(2)
                                ,PoiCategory.searchById(resultSet.getString(3))
                                ,resultSet.getString(4)
                                ,resultSet.getString(5)
                        )
                );

            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseProxy.close();
        }

        return poiList;
    }

    public static ArrayList<Poi> searchPoiByRadius(double[] boundingBox) {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        ArrayList<Poi> poiList = new ArrayList<>();
        String query = "select id, name, category_id, longitude, latitude from poi " +
                "where category_id not in ('66','69','70') " +
                "and latitude between ? and ? " +
                "and longitude between ? and ? "
                ;

        PreparedStatement preparedStatement = databaseProxy.prepareStatement(query);

        try {
            // boundingBox Array: maxLat, minLat, maxLon, minLon
            // Suche: latitude
            preparedStatement.setString(1, String.valueOf(boundingBox[1]));
            preparedStatement.setString(2, String.valueOf(boundingBox[0]));
            // Suche: longitude
            preparedStatement.setString(3, String.valueOf(boundingBox[3]));
            preparedStatement.setString(4, String.valueOf(boundingBox[2]));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                poiList.add(
                        new Poi(
                                resultSet.getString(1)
                                ,resultSet.getString(2)
                                ,PoiCategory.searchById(resultSet.getString(3))
                                ,resultSet.getString(4)
                                ,resultSet.getString(5)
                        )
                );

            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseProxy.close();
        }

        return poiList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PoiCategory getPoiCategory() {
        return poiCategory;
    }

    public void setPoiCategory(PoiCategory poiCategory) {
        this.poiCategory = poiCategory;
    }

    public String getLongitude() {
        return longitude;
    }

    public Double getLongitudeDouble() {
        return Double.valueOf(longitude);
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public Double getLatitudeDouble() {
        return Double.valueOf(latitude);
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return name + " (" + poiCategory + ")";
    }

    public String getShortName() {
        int maxLength = 16;
        return name.substring(0,name.length() < maxLength ? name.length() : maxLength);
    }
}
