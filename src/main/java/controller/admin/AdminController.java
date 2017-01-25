package controller.admin;

import controller.common.MainController;
import model.common.DatabaseProxy;
import view.admin.AdminView;
import view.admin.ProgressView;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Controller Class für AdminView
 *
 * Diese Klasse öffnet einen File Dialog zum Auswählen einer Datei für den Import
 * und startet den File Import.
 *
 * @author  Dieter Biedermann
 */
public class AdminController implements ActionListener {

    private AdminView adminView;
    private MainController mainController;
    private File file;

    public AdminController(AdminView adminView, MainController mainController) {
        this.adminView = adminView;
        this.mainController = mainController;
    }

    /*
     * Öffne den File Chooser Dialog und zeige den Filenamen in der View an
     */
    public void openFile() {
        file = getFile();
        if (file != null) {
            adminView.setFileName(file.getName());
        }
    }

    /*
     * Importiere das File
     */
    public void importFile() {
        if (file == null) {
            // Fehlermeldung zurückgeben
            mainController.showErrorMessage("Please choose a file first.");
        } else {
            ProgressView progressView = mainController.openProgressView();
            ImportController importController = new ImportController(file, adminView, progressView, mainController, new DatabaseProxy());
            progressView.setImportController(importController);
            importController.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "open_file":
                openFile();
                break;
            case "import_file":
                importFile();
                break;
        }

    }

    public File getFile() {
        JFileChooser fileChooser = new JFileChooser();

        File workingDirectory = new File(System.getProperty("user.dir") + "/resources");
        fileChooser.setCurrentDirectory(workingDirectory);

        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().toLowerCase().endsWith("csv");
            }

            @Override
            public String getDescription() {
                return "CSV Datei";
            }
        };
        fileChooser.setFileFilter(filter);

        fileChooser.setMultiSelectionEnabled(false);
        int returnCode = fileChooser.showOpenDialog(adminView);
        if (returnCode == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }


}
