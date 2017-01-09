package model.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PoiCategory {

    // Instanzvariablen
    private String id;
    private String name;

    public PoiCategory(String id, String name) {
        this.id = id;
        this.name = name;
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

    public static ArrayList<PoiCategory> getAllPoiCategories() {

        DatabaseProxy databaseProxy = new DatabaseProxy();

        PreparedStatement preparedStatement = databaseProxy.prepareStatement("select id, name from poi_category");
        ArrayList<PoiCategory> poiCategoryList = new ArrayList<>();

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                poiCategoryList.add(
                        new PoiCategory(
                                resultSet.getString(1)
                                ,resultSet.getString(2)
                        )
                );

            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseProxy.close();
        }

        return poiCategoryList;
    }
}
