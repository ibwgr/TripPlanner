package model.admin;

import controller.admin.ImportController;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * In dieser Klasse wird das übergebene CSV File eingelesen und alle Zeilen in die Queue geschrieben.
 *
 * @author  Dieter Biedermann
 */
public class FileReader extends Thread {

    private File file;
    private ImportController importController;
    private Boolean hasHeader, firstRow;

    public FileReader(File file, ImportController importController, Boolean hasHeader) {
        this.file = file;
        this.importController = importController;
        this.hasHeader = hasHeader;
    }

    public void run() {

        try {
            Stream<String> lines = Files.lines(file.toPath(), StandardCharsets.UTF_8);
            firstRow = true;
            for (String line : (Iterable<String>) lines::iterator) {
                if (!hasHeader || !firstRow) {
                    importController.putRow(line);
                    importController.increaseRowQueueCount();
                }
                firstRow = false;
            }
            lines.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}