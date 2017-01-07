package controller.admin;

import model.admin.*;
import view.admin.AdminView;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Created by dieterbiedermann on 04.01.17.
 */
public class ImportController {

    private File file;
    private AdminView adminView;
    LinkedList<String> rowQueue = new LinkedList<String>();
    long rowQueueCount = -1;
    long errorCount = 0;
    long counter = 0;
    private final int threadNo = 4;
    private long startTime = System.nanoTime();

    public ImportController(File file, AdminView adminView) {
        this.file = file;
        this.adminView = adminView;
    }

    public void start() {

        FileReader fileReader = new FileReader(file, this, adminView.getFileHasHeader());
        fileReader.start();

        DatabaseProxy databaseProxy = new DatabaseProxy();

        if ("poi".equals(adminView.getFileType())) {
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
        counter++;
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
        return counter == rowQueueCount;
    }

    public void showStatus() {
        long estimatedTime = System.nanoTime() - startTime;
        adminView.setStatusText(counter + " / " + rowQueueCount + " (elapsed time: " + String.valueOf(TimeUnit.NANOSECONDS.toSeconds(estimatedTime)) + ")");
    }

    public void increaseErrorCount() {
        errorCount++;
    }

    public void importIsFinished() {
        adminView.importIsFinished();
    }
}
