package controller.admin;

import controller.common.MainController;
import model.common.DatabaseProxy;
import view.admin.AdminView;
import view.admin.ProgressView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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

    /**
     * Öffne den File Chooser Dialog und zeige den Filenamen und einen Preview in der View an
     */
    public void openFile() {
        file = adminView.getFile();
        if (file != null) {
            adminView.setFileName(file.getName());
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new FileReader(file));
                for (int i = 0; i < 5 || i < br.lines().count(); i++) {
                    String line = br.readLine();
                    if (line != null) {
                        sb.append(line);
                        sb.append("\n");
                    }
                }
                sb.deleteCharAt(sb.length()-1);
                adminView.setFilePreview(String.valueOf(sb));
            } catch (FileNotFoundException e) {
                mainController.showErrorMessage("File not found: " + e.getMessage());
            } catch (IOException e) {
                mainController.showErrorMessage("File reading error: " + e.getMessage());
            }
        }
    }

    /**
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



}
