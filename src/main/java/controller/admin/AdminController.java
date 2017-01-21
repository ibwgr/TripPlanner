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
 */
public class AdminController implements ActionListener {

    private AdminView adminView;
    private MainController mainController;
    private File file;

    public AdminController(AdminView adminView, MainController mainController) {
        this.adminView = adminView;
        this.mainController = mainController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "open_file":
                /*
                 * Öffne den File Chooser Dialog und zeige den Filenamen in der View an
                 */
                file = getFile();
                if (file != null) {
                    adminView.setFileName(file.getName());
                }
                break;
            case "import_file":
                if (file == null) {
                    /*
                     * Fehlermeldung zurückgeben
                     */
                    mainController.showErrorMessage("Please choose a file first.");
                } else {
                    /*
                     * Importiere das File
                     */
                    ProgressView progressView = mainController.openProgressView();
                    ImportController importController = new ImportController(file, adminView, progressView, mainController, new DatabaseProxy());
                    progressView.setImportController(importController);
                    importController.start();
                }
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
