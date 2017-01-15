package model.admin;

import controller.admin.ImportController;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class PoiConsumer extends Thread {

    private ImportController importController;
    private DatabaseImport databaseImport;
    private ArrayList<String[]> poiList = new ArrayList<>();
    private String delimiter;

    public PoiConsumer(ImportController importController, DatabaseImport databaseImport, String delimiter) {
        this.importController = importController;
        this.databaseImport = databaseImport;
        this.poiList = new ArrayList<>();
        this.delimiter = delimiter;
    }

    public void run() {

/*
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/

        while (!importController.allRowsProcessed()) {
            String row = importController.getRow();
//            System.out.println(Thread.currentThread().getName() + " - after getRow");
            if (row != null) {
                String[] rowItem = row.split(Pattern.quote(delimiter));
//                System.out.println(Thread.currentThread().getName() + " - after split");
                if (rowItem.length != 5 || rowItem[0].isEmpty()) {
//                    System.out.println("Error -> wrong row");
                    /**
                     * increase error count
                     */
                    importController.increaseErrorCount();
                } else if (!importController.poiCategoryExists(rowItem[0])) {
//                    System.out.println("Error -> category does not exist");
//                    System.out.println(this.getName() + " -> " + row);
                    importController.increaseErrorCategoryCount();
                } else {

                    poiList.add(rowItem);
                    importController.increaseProcessedCount();
                    // mit println werden alle Daten in die DB geschrieben !!!
//                    System.out.println(Thread.currentThread().getName() + " - after increase - poiList.size:" + poiList.size());

                    if (poiList.size() >= 100) {
//                        System.out.println(Thread.currentThread().getName() + " - 1 - " + poiList.size());
                        databaseImport.insertMultiValuePois(poiList);
//                        conn.commit();
                        poiList.clear();
                    }
                }
            }
        }

//        System.out.println(Thread.currentThread().getName() + " - 2 - " + poiList.size());
        if (poiList.size() > 0) {
//            System.out.println(Thread.currentThread().getName() + " - 2 - " + poiList.size());
            databaseImport.insertMultiValuePois(poiList);
            poiList.clear();
        }

    }

}