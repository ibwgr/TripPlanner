package controller.admin;

import model.admin.*;
import model.common.DatabaseProxy;
import model.common.PoiCategory;
import view.admin.AdminView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class ImportController {

    private File file;
    private AdminView adminView;
    private DatabaseProxy databaseProxy;
    LinkedList<String> rowQueue = new LinkedList<String>();
    long rowQueueCount = -1;
    long errorCount = 0;
    long errorCategoryCount = 0;
    long processedCount = 0;
    private final int threadNo = 4;
    private long startTime = System.nanoTime();
    private ArrayList<PoiCategory> poiCategories;

    public ImportController(File file, AdminView adminView) {
        this.file = file;
        this.adminView = adminView;
        databaseProxy = new DatabaseProxy();
    }

    public void start() {

        FileReader fileReader = new FileReader(file, this, adminView.getFileHasHeader());
        fileReader.start();

        if ("poi".equals(adminView.getFileType())) {
            /**
             * Wenn keine Kategorien vorhanden sind, eine Fehlermeldung anzeigen und zurück zu der Input Form
             */
            poiCategories = PoiCategory.getAllPoiCategories();
            if (poiCategories.isEmpty()) {
                adminView.showInputView();
                adminView.showErrorMessage("POI Categories are empty. Cannot import POI file.");
                return;
            }
            PoiConsumer[] poiConsumer = new PoiConsumer[threadNo];
            for (int i = 0; i < threadNo; i++) {
                poiConsumer[i] = new PoiConsumer(this, databaseProxy, adminView.getFileDelimiter());
                poiConsumer[i].start();
            }
        } else {
            CategoryConsumer[] categoryConsumer = new CategoryConsumer[threadNo];
            for (int i = 0; i < threadNo; i++) {
                categoryConsumer[i] = new CategoryConsumer(this, databaseProxy, adminView.getFileDelimiter());
                categoryConsumer[i].start();
            }
        }

        ImportProgress importProgress = new ImportProgress(this);
        importProgress.start();

    }

    public synchronized void putRow(String s) {
        rowQueue.addLast(s);
        notify();
    }

    public synchronized String getRow() {
        while (queueIsEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notify();
        String row = rowQueue.getFirst();
        rowQueue.removeFirst();
//        counter++;
        return row;
    }

    public boolean queueIsEmpty() {
        return rowQueue.isEmpty();
    }

    /**
     * Der Total Row Count muss in einem eigenem Attribut gespeichert werden.
     * Weil die Einträge in der Queue immer wieder gelöscht werden, wenn etwas gelesen wurde.
     */
    public void increaseRowQueueCount() {
        if (rowQueueCount < 0) {
            rowQueueCount = 0;
        }
        rowQueueCount++;
    }

    public boolean allRowsProcessed() {
        return (processedCount + errorCount + errorCategoryCount) == rowQueueCount;
    }

    public void showStatus() {
        long estimatedTime = System.nanoTime() - startTime;
        adminView.setProgressStatus(rowQueueCount, processedCount, errorCount, errorCategoryCount, estimatedTime);
//        adminView.setStatusText(counter + " / " + rowQueueCount + " (elapsed time: " + String.valueOf(TimeUnit.NANOSECONDS.toSeconds(estimatedTime)) + ")");
    }

    public void increaseErrorCount() {
        errorCount++;
    }

    public void importIsFinished() {
        adminView.importIsFinished();
    }

    public Boolean poiCategoryExists(String id) {
        if (poiCategories != null) {
            for (PoiCategory poiCategory : poiCategories) {
                if (id.equals(poiCategory.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void increaseErrorCategoryCount() {
        errorCategoryCount++;
    }

    public void increaseProcessedCount() {
        processedCount++;
    }
}
