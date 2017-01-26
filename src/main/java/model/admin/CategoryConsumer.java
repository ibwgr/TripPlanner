package model.admin;

import controller.admin.ImportController;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * In dieser Klasse werden die Rows für Categories in der Queue verarbeitet.
 *
 * - Die eingelesene Zeile wird validiert (2 Spalten müssen vorhanden sein)
 * - wenn 10 Zeilen eingelesen wurden, werden die Categories in die Datenbank geschrieben
 *
 * @author  Dieter Biedermann
 */
public class CategoryConsumer extends Thread {

    private ImportController importController;
    private DatabaseImport databaseImport;
    private ArrayList<String[]> categoryList;
    private String delimiter;

    public CategoryConsumer(ImportController importController, DatabaseImport databaseImport, String delimiter) {
        this.importController = importController;
        this.databaseImport = databaseImport;
        this.categoryList = new ArrayList<>();
        this.delimiter = delimiter;
    }

    public void run() {
        while (!importController.allRowsProcessed() && !isInterrupted()) {
            String row = importController.getRow();
            if (row != null) {
                String[] rowItem = row.split(Pattern.quote(delimiter));
                if (rowItem.length != 2 || rowItem[0].isEmpty()) {
                    importController.increaseErrorCount();
                } else {
                    categoryList.add(rowItem);
                    importController.increaseProcessedCount();

                    if (categoryList.size() >= 10) {
                        databaseImport.insertMultiValueCategories(categoryList);
                        categoryList.clear();
                    }
                }
            }
        }

        if (categoryList.size() > 0) {
            databaseImport.insertMultiValueCategories(categoryList);
            categoryList.clear();
        }

    }

}