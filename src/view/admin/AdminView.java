package view.admin;

import controller.admin.AdminController;
import controller.common.MainController;
import view.common.GridPanel;
import view.common.TripPlannerMain;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdminView extends GridPanel {

    AdminController adminController;
    MainController mainController;

    JButton chooseFileButton, startImportButton;
    JLabel fileName;
    ButtonGroup fileTypeGroup;
    ButtonGroup fileDelimiterGroup;
    JCheckBox fileHasHeader;
    JPanel inputPanel, progressPanel, inputPanelBorder, progressPanelBorder;

    DecimalFormat timeFormat = new DecimalFormat("00");

    public AdminView(MainController mainController) {
        /**
         * Anzahl Zeilen und Spalten für diese View
         */
        super(6,1,140,20);

        adminController = new AdminController(this, mainController);

        /**
         * Anzeige des Filenamens und File Öffnen Button
         */
        addComponentToPanel(fileName = new JLabel("Please select file"));
        addComponentToPanel(createButton("Open file", "open_file", adminController));
        addPanelWithLabel("File:", true);

        /**
         * File Type: PoiCategory oder Point of interest
         */
        fileTypeGroup = new ButtonGroup();
        addComponentToPanel(createRadioButton("Category", "category", true, fileTypeGroup));
        addComponentToPanel(createRadioButton("Point of interest", "poi", false, fileTypeGroup));
        addPanelWithLabel("Type:", true);

        /**
         * File Delimiter Optionen
         */
        fileDelimiterGroup = new ButtonGroup();
        addComponentToPanel(createRadioButton("|", "|", true, fileDelimiterGroup));
        addComponentToPanel(createRadioButton(",", ",", false, fileDelimiterGroup));
        addComponentToPanel(createRadioButton(";", ";", false, fileDelimiterGroup));
        addPanelWithLabel("Delimiter:", true);

        /**
         * Checkbox für erste Zeile als Header
         */
        addComponentToPanel(fileHasHeader = new JCheckBox());
        addPanelWithLabel("First row is header:", true);

        /**
         * Start des Imports Button
         */
        addComponentToPanel(createButton("Import file", "import_file", adminController));
        addPanelWithLabel("", true);

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

/*
    public void showProgressView() {
        this.replaceView(progressPanelBorder, 0);
    }

    public void showInputView() {
        this.replaceView(inputPanelBorder, 0);
    }
*/

}
