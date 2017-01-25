package model.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Diese Klasse speichert Category Daten aus der Tabelle POI_CATEGORY.
 *
 * Zur Suche von Category stehen folgende statischen Methoden zur Verfügung:
 * - getAllPoiCategories
 * - getAllPoiCategoriesForComboBox
 * - searchById
 *
 * @author  Reto Kaufmann
 * @author  Dieter Biedermann
 */
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

    public static ArrayList<Pair<String, PoiCategory>> getAllPoiCategoriesForComboBox() {
        DatabaseProxy databaseProxy = new DatabaseProxy();

        PreparedStatement preparedStatement = databaseProxy.prepareStatement("select id, name from poi_category");

        ArrayList<Pair<String, PoiCategory>> poiCategoryList = new ArrayList<>();
        poiCategoryList.add(new Pair<String, PoiCategory>("All", null));

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                poiCategoryList.add(
                        new Pair<String, PoiCategory>(
                                resultSet.getString(2)
                                ,new PoiCategory(
                                        resultSet.getString(1)
                                        ,resultSet.getString(2)
                                )
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

    public static PoiCategory searchById(String id) {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        String query = "select id, name from poi_category where id = ?";
        PoiCategory poiCategory = null;

        PreparedStatement preparedStatement = databaseProxy.prepareStatement(query);

        ArrayList<PoiCategory> poiCategoryList = new ArrayList<>();

        try {
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                poiCategory = new PoiCategory(
                        resultSet.getString(1)
                        ,resultSet.getString(2)
                );
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseProxy.close();
        }

        return poiCategory;
    }

    @Override
    public String toString() {
        return name;
    }

}
