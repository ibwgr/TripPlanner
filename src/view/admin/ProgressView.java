package view.admin;

import controller.admin.AdminController;
import controller.admin.ImportController;
import controller.admin.ProgressController;
import controller.common.MainController;
import view.common.GridPanel;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class ProgressView extends GridPanel {

    ProgressController progressController;

    JLabel statusTotalRows, statusRowsProcessed, statusRowsError, statusRowsCategoryError, statusElapsedTime;
    JButton buttonNewUpload, buttonCancel;

    DecimalFormat timeFormat = new DecimalFormat("00");

    public ProgressView(MainController mainController) {
        /**
         * Anzahl Zeilen und Spalten für diese View
         */
        super(6, 1, 140, 20);

        progressController = new ProgressController(this, mainController);

        addComponentToPanel(statusTotalRows = new JLabel());
        addPanelWithLabel("Total rows:", true);

        addComponentToPanel(statusRowsProcessed = new JLabel());
        addPanelWithLabel("Processed rows:", true);

        addComponentToPanel(statusRowsError = new JLabel());
        addPanelWithLabel("Error rows:", true);

        addComponentToPanel(statusRowsCategoryError = new JLabel());
        addPanelWithLabel("Without rows:", true);

        addComponentToPanel(statusElapsedTime = new JLabel());
        addPanelWithLabel("Elapsed time:", true);

        addComponentToPanel(buttonNewUpload = createButton("import new file", "start_new", progressController, false));
        addComponentDirect(buttonNewUpload);

        addComponentToPanel(buttonCancel = createButton("cancel", "cancel", progressController));
        addComponentDirect(buttonCancel);
    }

    public void importIsFinished() {
        buttonNewUpload.setEnabled(true);
    }

    public void setProgressStatus(long rowQueueCount, long processedCount, long errorCount, long errorCategoryCount, long estimatedTime) {
        statusTotalRows.setText(String.valueOf(rowQueueCount));
        statusRowsProcessed.setText(String.valueOf(processedCount));
        statusRowsError.setText(String.valueOf(errorCount));
        statusRowsCategoryError.setText(String.valueOf(errorCategoryCount));
        if (errorCategoryCount == 0) {
            statusRowsCategoryError.setVisible(false);
        } else {
            statusRowsCategoryError.setVisible(true);
        }
        long seconds = TimeUnit.NANOSECONDS.toSeconds(estimatedTime);
        long minutes = seconds / 60;
        seconds = seconds % 60;
        statusElapsedTime.setText(timeFormat.format(minutes) + ":" + timeFormat.format(seconds));
    }


}