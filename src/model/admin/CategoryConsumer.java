package model.admin;

import controller.admin.ImportController;
import model.common.DatabaseProxy;

import java.util.ArrayList;
import java.util.regex.Pattern;

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
        while (!importController.allRowsProcessed()) {
            String row = importController.getRow();
            if (row != null) {
                String[] rowItem = row.split(Pattern.quote(delimiter));
                if (rowItem.length != 2 || rowItem[0].isEmpty()) {
//                    System.out.println("Error -> wrong row -> " + row);
                    importController.increaseErrorCount();
                } else {

//                    System.out.println(this.getName() + " -> " + row);
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