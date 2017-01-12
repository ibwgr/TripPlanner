package model.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return name + " (" + poiCategory + ")";
    }
}
