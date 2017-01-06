package controller.admin;

import model.admin.*;
import view.admin.AdminView;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by dieterbiedermann on 04.01.17.
 */
public class ImportController {

    private File file;
    private AdminView adminView;
    private LinkedList<String> rowQueue = new LinkedList<String>();
    private long rowQueueCount = -1;
    private long errorCount = 0;
    private long counter = 0;

    public ImportController(File file, AdminView adminView) {
        this.file = file;
        this.adminView = adminView;
    }

    public void start() {

        FileReader fileReader = new FileReader(file, this, adminView.getFileHasHeader());
        fileReader.start();

        DatabaseProxy databaseProxy = new DatabaseProxy();

        if ("poi".equals(adminView.getFileType())) {
            PoiConsumer poiConsumer = new PoiConsumer(this, databaseProxy, adminView.getFileDelimiter());
            poiConsumer.start();
        } else {
            CategoryConsumer categoryConsumer = new CategoryConsumer(this, databaseProxy, adminView.getFileDelimiter());
            categoryConsumer.start();
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

    }

    public void increaseErrorCount() {
        errorCount++;
    }
}
