package model.admin;

import controller.admin.ImportController;

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

        importController.importIsFinished();

    }

}