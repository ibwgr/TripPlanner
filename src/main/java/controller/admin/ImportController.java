package controller.admin;

import controller.common.MainController;
import model.admin.*;
import model.common.DatabaseProxy;
import model.common.PoiCategory;
import view.admin.AdminView;
import view.admin.ProgressView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Controller Class für den Import eines CSV Files
 * - erstellt einen ImportProgress Thread
 * - erstellt einen FileReader Thread
 * - erstellt drei PoiConsumer oder einen CategoryConsumer Thread
 */
public class ImportController {

    private File file;
    private AdminView adminView;
    private ProgressView progressView;
    private MainController mainController;
    private DatabaseProxy databaseProxy;
    public LinkedList<String> rowQueue = new LinkedList<String>();
    public long rowQueueCount = -1;
    public long errorCount = 0;
    public long errorCategoryCount = 0;
    public long processedCount = 0;
    private final int threadNo = 3;
    private long startTime = System.nanoTime();
    public ArrayList<PoiCategory> poiCategories = new ArrayList<>();
    public Thread[] consumers = new Thread[threadNo];
    public FileReader fileReader;
    public ImportProgress importProgress;

    public ImportController(File file, AdminView adminView, ProgressView progressView, MainController mainController) {
        this.file = file;
        this.adminView = adminView;
        this.progressView = progressView;
        this.mainController = mainController;
        databaseProxy = new DatabaseProxy();
    }

    public void start() {

        importProgress = new ImportProgress(this);
        importProgress.start();

        fileReader = new FileReader(file, this, adminView.getFileHasHeader());
        fileReader.start();

        if ("poi".equals(adminView.getFileType())) {
            /*
             * Wenn keine Kategorien vorhanden sind, eine Fehlermeldung anzeigen und zurück zu der Input Form
             */
            poiCategories = PoiCategory.getAllPoiCategories();
            if (poiCategories.isEmpty()) {
                mainController.openAdmin();
                mainController.showErrorMessage("POI Categories are empty. Cannot import POI file.");
                return;
            }

            for (int i = 0; i < threadNo; i++) {
                DatabaseImport databaseImport = new DatabaseImport(this, databaseProxy);
                consumers[i] = new PoiConsumer(this, databaseImport, adminView.getFileDelimiter());
                consumers[i].start();
            }
        } else {
            DatabaseImport databaseImport = new DatabaseImport(this, databaseProxy);
            consumers[0] = new CategoryConsumer(this, databaseImport, adminView.getFileDelimiter());
            consumers[0].start();
        }

    }

    public void stopImport() {
        fileReader.interrupt();
        for (Thread thread :consumers) {
            thread.interrupt();
        }
        importProgress.interrupt();
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
        return row;
    }

    public synchronized boolean queueIsEmpty() {
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

    public synchronized boolean allRowsProcessed() {
        return (processedCount + errorCount + errorCategoryCount) == rowQueueCount;
    }

    public void showStatus() {
        long estimatedTime = System.nanoTime() - startTime;
        progressView.setProgressStatus(rowQueueCount, processedCount, errorCount, errorCategoryCount, estimatedTime);
//        adminView.setStatusText(counter + " / " + rowQueueCount + " (elapsed time: " + String.valueOf(TimeUnit.NANOSECONDS.toSeconds(estimatedTime)) + ")");
    }

    public synchronized void increaseErrorCount() {
        errorCount++;
    }

    public synchronized void increaseErrorCount(long number) {
        errorCount = errorCount + number;
    }

    public void importIsFinished() {
        processedCount = 0;
        errorCount = 0;
        errorCategoryCount = 0;
        rowQueueCount = -1;
        progressView.importIsFinished();
    }

    public synchronized Boolean poiCategoryExists(String id) {
        if (poiCategories != null) {
            for (PoiCategory poiCategory : poiCategories) {
                if (id.equals(poiCategory.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized void increaseErrorCategoryCount() {
        errorCategoryCount++;
    }

    public synchronized void increaseProcessedCount() {
        processedCount++;
    }

}
