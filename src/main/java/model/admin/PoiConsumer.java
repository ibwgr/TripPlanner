package model.admin;

import controller.admin.ImportController;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * In dieser Klasse werden die Rows für Point of interest in der Queue verarbeitet.
 *
 * - die eingelesene Zeile wird validiert (5 Spalten müssen vorhanden sein)
 * - die Category zu dem Point of Interest muss vorhanden sein
 * - wenn 100 Zeilen eingelesen wurden, werden die Categories in die Datenbank geschrieben
 *
 * @author  Dieter Biedermann
 */
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

        while (!importController.allRowsProcessed() && !isInterrupted()) {
            String row = importController.getRow();
            if (row != null) {
                String[] rowItem = row.split(Pattern.quote(delimiter));
                if (rowItem.length != 5 || rowItem[0].isEmpty()) {
                    // increase error count
                    importController.increaseErrorCount();
                } else if (!importController.poiCategoryExists(rowItem[0])) {
                    // increase error count
                    importController.increaseErrorCategoryCount();
                } else {
                    poiList.add(rowItem);
                    importController.increaseProcessedCount();
                    if (poiList.size() >= 100) {
                        databaseImport.insertMultiValuePois(poiList);
                        poiList.clear();
                    }
                }
            }
        }

        if (poiList.size() > 0) {
            databaseImport.insertMultiValuePois(poiList);
            poiList.clear();
        }

    }

}