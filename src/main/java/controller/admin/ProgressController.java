package controller.admin;

import controller.common.MainController;
import view.admin.ProgressView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller Class für ProgressView
 */
public class ProgressController implements ActionListener {


    private ProgressView progressView;
    private MainController mainController;
    public ImportController importController;

    public ProgressController(ProgressView progressView, MainController mainController) {
        this.progressView = progressView;
        this.mainController = mainController;
    }

    public void setImportController(ImportController importController) {
        this.importController = importController;
        progressView.setCancelButtonEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "start_new":
                /*
                 * schliesst das Progress Panel und öffnet wieder das Input Panel
                 */
                mainController.openLastView();
                break;
            case "cancel":
                /*
                 * beendet alle Import Threads
                 */
                importController.stopImport();
                break;
        }

    }

}
