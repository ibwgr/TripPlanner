package model.admin;

import controller.admin.ImportController;

/**
 * Diese Klasse aktualisiert die Fortschrittsanzeige. Anzahl verarbeitete Zeilen und den Fortschrittsbalken.
 *
 * @author  Dieter Biedermann
 */
public class ImportProgress extends Thread {

    private ImportController importController;

    public ImportProgress(ImportController importController) {
        this.importController = importController;
    }

    public void run() {

        while (!importController.allRowsProcessed() && !isInterrupted()) {
            importController.showStatus();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

        importController.showStatus();

        importController.importIsFinished();

    }

}