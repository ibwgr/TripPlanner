package model.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    DatabaseProxy databaseProxy;

    public DatabaseHandler(DatabaseProxy databaseProxy) {
        this.databaseProxy = databaseProxy;
    }

    public ArrayList<PoiCategory> getAllPoiCategories() {

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
        }

        return poiCategoryList;
    }

}
