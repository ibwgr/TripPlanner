package model.admin;

import controller.admin.ImportController;
import model.common.DatabaseProxy;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * In dieser Klasse werden die Daten aus den Consumer Threads in die Datenbank geschrieben.
 * Es wird ein Multi Insert Statement als Prepared Statement erstellt.
 *
 * @author  Dieter Biedermann
 */
public class DatabaseImport {

    ImportController importController;
    DatabaseProxy databaseProxy;
    PreparedStatement preparedStatement;
    int counter = 0, rowCount = 0;

    public DatabaseImport(ImportController importController, DatabaseProxy databaseProxy) {
        try {
            this.databaseProxy = databaseProxy;
            this.importController = importController;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void insertMultiValuePois(ArrayList<String[]> poiList) {

        try {
            databaseProxy.setAutoCommit(false);

            StringBuilder sb;
            sb = new StringBuilder();
            sb.append("insert into poi (category_id, id, latitude, longitude, name) values ");

            rowCount = rowCount + poiList.size();
            for (int i = 1; i <= poiList.size(); i++) {
                sb.append("(?,?,?,?,?),");
            }
            sb.delete(sb.length()-1,sb.length());
            if (preparedStatement == null || preparedStatement.isClosed()) {
                preparedStatement = databaseProxy.prepareStatement(sb.toString());
            }

            int cnt = 0;
            for (String[] poi:poiList) {
                for (String item: poi) {
                    cnt++;
                    preparedStatement.setString(cnt, item);
                }
            }

            counter++;
            preparedStatement.addBatch();
            if (counter % 50 == 0) {
//                System.out.println(Thread.currentThread().getName() + " - " + preparedStatement.toString());
                preparedStatement.executeBatch();
                databaseProxy.commit();
                rowCount = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {

                    if (rowCount > 0) {
//                        System.out.println(Thread.currentThread().getName() + " - " + preparedStatement.toString());
                        preparedStatement.executeBatch();
                        databaseProxy.commit();
                        rowCount = 0;
                    }
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                importController.increaseErrorCount(rowCount);
            }
        }

    }

    public void insertMultiValueCategories(ArrayList<String[]> categoryList) {
        try {
            databaseProxy.setAutoCommit(false);

            StringBuilder sb;
            sb = new StringBuilder();
            sb.append("insert into poi_category (name, id) values ");

            rowCount = rowCount + categoryList.size();
            for (int i = 1; i <= categoryList.size(); i++) {
                sb.append("(?,?),");
            }
            sb.delete(sb.length()-1,sb.length());
            if (preparedStatement == null || preparedStatement.isClosed()) {
                preparedStatement = databaseProxy.prepareStatement(sb.toString());
            }

            int cnt = 0;
            for (String[] category:categoryList) {
                for (String item: category) {
                    cnt++;
                    preparedStatement.setString(cnt, item);
                }
            }

            counter++;
            preparedStatement.addBatch();
            if (counter % 50 == 0) {
//                System.out.println(Thread.currentThread().getName() + " - " + preparedStatement.toString());
                preparedStatement.executeBatch();
                databaseProxy.commit();
                rowCount = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {

                    if (rowCount > 0) {
//                        System.out.println(Thread.currentThread().getName() + " - " + preparedStatement.toString());
                        preparedStatement.executeBatch();
                        databaseProxy.commit();
                        rowCount = 0;
                    }
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                importController.increaseErrorCount(rowCount);
            }
        }
    }
}
