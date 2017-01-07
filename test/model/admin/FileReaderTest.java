package model.admin;

import controller.admin.ImportController;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import view.admin.AdminView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dieterbiedermann on 06.01.17.
 */
public class FileReaderTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void runFillsRowQueue() throws IOException {
        final File tempFile = tempFolder.newFile("tempFile.txt");

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("abc,cde,efg\n123,345,111");
        writer.close();

        ImportController importController = new ImportController(tempFile, new AdminView());

        FileReader fileReader = new FileReader(tempFile, importController, false);
        fileReader.run();

        String result = importController.getRow();
        Assert.assertEquals("abc,cde,efg", result);
    }

    @Test
    public void runFillsRowQueueWithoutHeader() throws IOException {
        final File tempFile = tempFolder.newFile("tempFile.txt");

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("abc,cde,efg\n123,345,111");
        writer.close();

        ImportController importController = new ImportController(tempFile, new AdminView());

        FileReader fileReader = new FileReader(tempFile, importController, true);
        fileReader.run();

        String result = importController.getRow();
        Assert.assertEquals("123,345,111", result);
    }

}