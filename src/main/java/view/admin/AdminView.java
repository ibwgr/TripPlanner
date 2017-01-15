package view.admin;

import controller.admin.AdminController;
import controller.common.MainController;
import view.common.GridPanel;

import javax.swing.*;

public class AdminView extends GridPanel {

    AdminController adminController;
    MainController mainController;

    JLabel fileName;
    ButtonGroup fileTypeGroup;
    ButtonGroup fileDelimiterGroup;
    JRadioButton fileTypeCategory, fileTypePoi;
    JRadioButton fileDelimiterPipe, fileDelimiterComma, fileDelimiterSemicolon;
    JCheckBox fileHasHeader;

    public AdminView(MainController mainController) {
        /**
         * Anzahl Zeilen und Spalten für diese View
         */
        super(140,20);

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
        addComponentToPanel(fileTypeCategory = createRadioButton("Category", "category", true, fileTypeGroup));
        addComponentToPanel(fileTypePoi = createRadioButton("Point of interest", "poi", false, fileTypeGroup));
        addPanelWithLabel("Type:", true);

        /**
         * File Delimiter Optionen
         */
        fileDelimiterGroup = new ButtonGroup();
        addComponentToPanel(fileDelimiterPipe = createRadioButton("|", "|", true, fileDelimiterGroup));
        addComponentToPanel(fileDelimiterComma = createRadioButton(",", ",", false, fileDelimiterGroup));
        addComponentToPanel(fileDelimiterSemicolon = createRadioButton(";", ";", false, fileDelimiterGroup));
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
