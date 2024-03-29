package model.admin;

import controller.admin.ImportController;
import controller.common.MainController;
import model.common.DatabaseProxy;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import testFramework.UnitTest;
import view.admin.AdminView;
import view.admin.ProgressView;
import view.common.TripPlannerMain;

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

    @Category({ UnitTest.class })
    @Test
    public void runFillsRowQueue() throws IOException {
        final File tempFile = tempFolder.newFile("tempFile.txt");

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("abc,cde,efg\n123,345,111");
        writer.close();

        MainController mainController = Mockito.mock(MainController.class);
        AdminView adminView = Mockito.mock(AdminView.class);
        ProgressView progressView = Mockito.mock(ProgressView.class);
        DatabaseProxy databaseProxy = Mockito.mock(DatabaseProxy.class);
        ImportController importController = new ImportController(tempFile, adminView, progressView, mainController, databaseProxy);

        FileReader fileReader = new FileReader(tempFile, importController, false);
        fileReader.run();

        String result = importController.getRow();
        Assert.assertEquals("abc,cde,efg", result);
    }

    @Category({ UnitTest.class })
    @Test
    public void runFillsRowQueueWithoutHeader() throws IOException {
        final File tempFile = tempFolder.newFile("tempFile.txt");

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("abc,cde,efg\n123,345,111");
        writer.close();

        MainController mainController = Mockito.mock(MainController.class);
        AdminView adminView = Mockito.mock(AdminView.class);
        ProgressView progressView = Mockito.mock(ProgressView.class);
        DatabaseProxy databaseProxy = Mockito.mock(DatabaseProxy.class);
        ImportController importController = new ImportController(tempFile, adminView, progressView, mainController, databaseProxy);

        FileReader fileReader = new FileReader(tempFile, importController, true);
        fileReader.run();

        String result = importController.getRow();
        Assert.assertEquals("123,345,111", result);
    }

}