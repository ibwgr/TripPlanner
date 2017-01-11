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
import java.util.concurrent.TimeUnit;

public class ImportController {

    private File file;
    private AdminView adminView;
    private ProgressView progressView;
    private MainController mainController;
    private DatabaseProxy databaseProxy;
    LinkedList<String> rowQueue = new LinkedList<String>();
    long rowQueueCount = -1;
    long errorCount = 0;
    long errorCategoryCount = 0;
    long processedCount = 0;
    private final int threadNo = 3;
    private long startTime = System.nanoTime();
    private ArrayList<PoiCategory> poiCategories;
    public Thread[] consumers = new Thread[threadNo];
    public FileReader fileReader;
    public ImportProgress importProgress;
//    private ExecutorService executorService = Executors.newFixedThreadPool(threadNo+2);

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
            /**
             * Wenn keine Kategorien vorhanden sind, eine Fehlermeldung anzeigen und zurück zu der Input Form
             */
            poiCategories = PoiCategory.getAllPoiCategories();
            if (poiCategories.isEmpty()) {
                mainController.openAdmin();
                mainController.showErrorMessage("POI Categories are empty. Cannot import POI file.");
                return;
            }

            for (int i = 0; i < threadNo; i++) {
                consumers[i] = new PoiConsumer(this, databaseProxy, adminView.getFileDelimiter());
                consumers[i].start();
/*
                executorService.execute(new PoiConsumer(this, databaseProxy, adminView.getFileDelimiter()));
*/
            }
        } else {
            for (int i = 0; i < threadNo; i++) {
                consumers[i] = new CategoryConsumer(this, databaseProxy, adminView.getFileDelimiter());
                consumers[i].start();
/*
                executorService.execute(new CategoryConsumer(this, databaseProxy, adminView.getFileDelimiter()));
*/
            }
        }

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

    public synchronized void increaseErrorCategoryCount() {
        errorCategoryCount++;
    }

    public synchronized void increaseProcessedCount() {
        processedCount++;
    }

}
