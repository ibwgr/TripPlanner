package view.admin;

import controller.admin.ProgressController;
import controller.common.MainController;
import view.common.GridPanel;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class ProgressView extends GridPanel {

    ProgressController progressController;

    JLabel statusTotalRows, statusRowsProcessed, statusRowsError, statusRowsCategoryError, statusElapsedTime;
    JButton buttonNewUpload;
    JProgressBar progressBar;

    DecimalFormat timeFormat = new DecimalFormat("00");

    public ProgressView(MainController mainController) {
        /**
         * Anzahl Zeilen und Spalten für diese View
         */
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

        addComponentToPanel(progressBar = new JProgressBar());
        progressBar.setMinimum(0);
        addComponentDirect(progressBar);

        addComponentToPanel(buttonNewUpload = createButton("import new file", "start_new", progressController, false));
        addComponentDirect(buttonNewUpload);

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