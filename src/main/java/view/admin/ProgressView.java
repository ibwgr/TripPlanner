package view.admin;

import controller.admin.ImportController;
import controller.admin.ProgressController;
import controller.common.MainController;
import view.common.FormPanel;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * View für den Admin Bereich. Hier wird der File Import Fortschritt angezeigt.
 *
 * @author  Dieter Biedermann
 */
public class ProgressView extends FormPanel {

    ProgressController progressController;

    JLabel statusTotalRows, statusRowsProcessed, statusRowsError, statusRowsCategoryError, statusElapsedTime;
    JButton buttonNewUpload, cancelButton;
    JProgressBar progressBar;

    DecimalFormat timeFormat = new DecimalFormat("00");

    public ProgressView(MainController mainController) {
        // Label Grösse diese View
        super(145, 20);

        progressController = new ProgressController(this, mainController);

        addComponentToPanel(statusTotalRows = new JLabel());
        addPanelWithLabel("Total rows from file:", true);

        addComponentToPanel(statusRowsProcessed = new JLabel());
        addPanelWithLabel("Processed rows:", true);

        addComponentToPanel(statusRowsError = new JLabel());
        addPanelWithLabel("Error rows:", true);

        addComponentToPanel(statusRowsCategoryError = new JLabel());
        addPanelWithLabel("No Category found:", true);

        addComponentToPanel(statusElapsedTime = new JLabel());
        addPanelWithLabel("Elapsed time:", true);

        addComponentDirect(progressBar = new JProgressBar());
        progressBar.setMinimum(0);

        addComponentDirect(cancelButton = createButton("cancel", "cancel", progressController, false));
        addComponentDirect(buttonNewUpload = createButton("import new file", "start_new", progressController, false));

    }

    public void setImportController(ImportController importController) {
        progressController.setImportController(importController);
    }

    public void setCancelButtonEnabled(Boolean enabled) {
        cancelButton.setEnabled(enabled);
    }

    public void importIsFinished() {
        buttonNewUpload.setEnabled(true);
    }

    public void setProgressStatus(long rowQueueCount, long processedCount, long errorCount, long errorCategoryCount, long estimatedTime) {
        statusTotalRows.setText(String.valueOf(rowQueueCount));
        statusRowsProcessed.setText(String.valueOf(processedCount));
        statusRowsError.setText(String.valueOf(errorCount));
        statusRowsCategoryError.setText(String.valueOf(errorCategoryCount));
        long seconds = TimeUnit.NANOSECONDS.toSeconds(estimatedTime);
        long minutes = seconds / 60;
        seconds = seconds % 60;
        statusElapsedTime.setText(timeFormat.format(minutes) + ":" + timeFormat.format(seconds));
        progressBar.setMaximum((int) rowQueueCount);
        progressBar.setValue((int) (processedCount + errorCount + errorCategoryCount));
    }


}