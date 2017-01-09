package view.admin;

import controller.admin.AdminController;
import view.common.TripPlannerMain;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdminView extends TripPlannerMain {

    AdminController adminController;

    JButton chooseFileButton, startImportButton;
    JLabel fileName;
    ButtonGroup fileTypeGroup;
    ButtonGroup fileDelimiterGroup;
    JCheckBox fileHasHeader;
    JPanel inputPanel, progressPanel, inputPanelBorder, progressPanelBorder;
    JLabel statusTotalRows, statusRowsProcessed, statusRowsError, statusRowsCategoryError, statusElapsedTime;
    JButton buttonNewUpload;

    ArrayList<Component> compoList;
    Dimension labelDimension = new Dimension(140,20);

    DecimalFormat timeFormat = new DecimalFormat("00");

    public AdminView() {
        /**
         * Anzahl Zeilen und Spalten für diese View
         */
        super(1,1);

        adminController = new AdminController(this);


        /**
         * Input View
         *
         */

        /**
         * Layout Panels
         */
//        this.setLayout(new BorderLayout());
        inputPanelBorder = new JPanel();
        inputPanel = new JPanel(new GridLayout(6,1));
        inputPanelBorder.add(inputPanel, BorderLayout.NORTH);

        /**
         * Anzeige des Filenamens und File Öffnen Button
         */
        compoList = new ArrayList<>();
        compoList.add(fileName = new JLabel("Please select file"));
        compoList.add(createButton("Open file", "open_file"));
        inputPanel.add(createPanelWithLabel("File:", compoList));

        /**
         * File Type: PoiCategory oder Point of interest
         */
        compoList = new ArrayList<>();
        fileTypeGroup = new ButtonGroup();
        compoList.add(createRadioButton("Category", "category", true, fileTypeGroup));
        compoList.add(createRadioButton("Point of interest", "poi", false, fileTypeGroup));
        inputPanel.add(createPanelWithLabel("Type:", compoList));

        /**
         * File Delimiter Optionen
         */
        compoList = new ArrayList<>();
        fileDelimiterGroup = new ButtonGroup();
        compoList.add(createRadioButton("|", "|", true, fileDelimiterGroup));
        compoList.add(createRadioButton(",", ",", false, fileDelimiterGroup));
        compoList.add(createRadioButton(";", ";", false, fileDelimiterGroup));
        inputPanel.add(createPanelWithLabel("Delimiter:", compoList));

        /**
         * Checkbox für erste Zeile als Header
         */
        compoList = new ArrayList<>();
        compoList.add(fileHasHeader = new JCheckBox());
        inputPanel.add(createPanelWithLabel("First row is header:", compoList));

        /**
         * Start des Imports Button
         */
        compoList = new ArrayList<>();
        compoList.add(createButton("Import file", "import_file"));
        inputPanel.add(createPanelWithLabel("", compoList));

        /**
         * Progress View
         */
        progressPanelBorder = new JPanel();
        progressPanel = new JPanel(new GridLayout(6,1));
        progressPanelBorder.add(progressPanel, BorderLayout.NORTH);

        compoList = new ArrayList<>();
        compoList.add(statusTotalRows = new JLabel());
        progressPanel.add(createPanelWithLabel("Total rows:", compoList));

        compoList = new ArrayList<>();
        compoList.add(statusRowsProcessed = new JLabel());
        progressPanel.add(createPanelWithLabel("Processed rows:", compoList));

        compoList = new ArrayList<>();
        compoList.add(statusRowsError = new JLabel());
        progressPanel.add(createPanelWithLabel("Error rows:", compoList));

        compoList = new ArrayList<>();
        compoList.add(statusRowsCategoryError = new JLabel());
        progressPanel.add(createPanelWithLabel("Without category:", compoList));

        compoList = new ArrayList<>();
        compoList.add(statusElapsedTime = new JLabel());
        progressPanel.add(createPanelWithLabel("Elapsed time:", compoList));

        buttonNewUpload = new JButton("import new file");
        buttonNewUpload.setActionCommand("start_new");
        buttonNewUpload.addActionListener(adminController);
        buttonNewUpload.setEnabled(false);
        progressPanel.add(buttonNewUpload);


        /**
         * zweite Row des Main GridLayout abfüllen und Parameter für das Fenster
         */
        this.setViewTitle("Administration - File Upload");
        this.addView(inputPanelBorder);
        this.setSize(new Dimension(500,300));
    }

    private JRadioButton createRadioButton(String text, String cmd, boolean selected, ButtonGroup buttonGroup) {
        JRadioButton radioButton = new JRadioButton(text, selected);
        radioButton.setActionCommand(cmd);
        buttonGroup.add(radioButton);
        return radioButton;
    }

    private JButton createButton(String text, String cmd) {
        JButton button = new JButton(text);
        button.setActionCommand(cmd);
        button.addActionListener(adminController);
        return button;
    }

    private JPanel createPanelWithLabel(String text, ArrayList<Component> compoList) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(labelDimension);
        JPanel jpanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        jpanel.add(label);
        for (Component compo:compoList) {
            jpanel.add(compo);
        }
        return jpanel;
    }

    public static void main(String[] args) {
        AdminView adminView = new AdminView();
        adminView.setVisible(true);
    }

    public void setFileName(String fileName) {
        this.fileName.setText(fileName);
    }

    public String getFileType() {
        return fileTypeGroup.getSelection().getActionCommand();
    }

    public String getFileDelimiter() {
        return fileDelimiterGroup.getSelection().getActionCommand();
    }

    public Boolean getFileHasHeader() {
        return fileHasHeader.isSelected();
    }

    public void showProgressView() {
        this.replaceView(progressPanelBorder, 0);
    }

    public void showInputView() {
        this.replaceView(inputPanelBorder, 0);
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
