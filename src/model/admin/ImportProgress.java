package model.admin;

import controller.admin.ImportController;

import java.util.concurrent.BlockingQueue;

/**
 * Created by dieterbiedermann on 04.01.17.
 */
public class ImportProgress extends Thread {

    private ImportController importController;

    public ImportProgress(ImportController importController) {
        this.importController = importController;
    }

    public void run() {

        while (!importController.allRowsProcessed()) {
            importController.showStatus();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        importController.showStatus();
    }

}