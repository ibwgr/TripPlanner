package model.admin;

import controller.admin.ImportController;
import controller.common.MainController;
import model.common.DatabaseProxy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import testFramework.UnitTest;
import view.admin.AdminView;
import view.admin.ProgressView;
import view.common.TripPlannerMain;

import java.io.File;
import java.io.IOException;

/**
 * Created by dieterbiedermann on 06.01.17.
 */
public class CategoryConsumerTest {

    @Category({ UnitTest.class })
    @Test
    public void runFillsReadsQueueWithoutErrors() throws IOException {

        DatabaseImport databaseImport = Mockito.mock(DatabaseImport.class);

        File file = Mockito.mock(File.class);
        MainController mainController = Mockito.mock(MainController.class);
        AdminView adminView = Mockito.mock(AdminView.class);
        ProgressView progressView = Mockito.mock(ProgressView.class);
        DatabaseProxy databaseProxy = Mockito.mock(DatabaseProxy.class);
        ImportController importController = new ImportController(file, adminView, progressView, mainController, databaseProxy);

        // Row muss zwei Werte haben
        importController.rowQueue.add("abc,def");
        importController.rowQueue.add("123,456");
        importController.rowQueueCount = 2;

        CategoryConsumer categoryConsumer = new CategoryConsumer(importController, databaseImport, ",");
        categoryConsumer.run();

        long result = importController.rowQueue.size();
        Assert.assertEquals(0, result);

        result = importController.processedCount;
        Assert.assertEquals(2, result);

        result = importController.errorCount;
        Assert.assertEquals(0, result);

        result = importController.errorCategoryCount;
        Assert.assertEquals(0, result);
    }

    @Category({ UnitTest.class })
    @Test
    public void runFillsReadsQueueWithErrorsForTooManyFields() throws IOException {

        DatabaseImport databaseImport = Mockito.mock(DatabaseImport.class);

        File file = Mockito.mock(File.class);
        MainController mainController = Mockito.mock(MainController.class);
        AdminView adminView = Mockito.mock(AdminView.class);
        ProgressView progressView = Mockito.mock(ProgressView.class);
        DatabaseProxy databaseProxy = Mockito.mock(DatabaseProxy.class);
        ImportController importController = new ImportController(file, adminView, progressView, mainController, databaseProxy);

        // rows mit 3 werten dürfen nicht verarbeitet werden
        importController.rowQueue.add("abc,def,ghi");
        importController.rowQueue.add("123,456,789");
        importController.rowQueueCount = 2;

        CategoryConsumer categoryConsumer = new CategoryConsumer(importController, databaseImport, ",");
        categoryConsumer.run();

        long result = importController.rowQueue.size();
        Assert.assertEquals(0, result);

        result = importController.processedCount;
        Assert.assertEquals(0, result);

        result = importController.errorCount;
        Assert.assertEquals(2, result);

        result = importController.errorCategoryCount;
        Assert.assertEquals(0, result);
    }

    @Category({ UnitTest.class })
    @Test
    public void runFillsReadsQueueWithErrorsForEmptyRow() throws IOException {

        DatabaseImport databaseImport = Mockito.mock(DatabaseImport.class);

        File file = Mockito.mock(File.class);
        MainController mainController = Mockito.mock(MainController.class);
        AdminView adminView = Mockito.mock(AdminView.class);
        ProgressView progressView = Mockito.mock(ProgressView.class);
        DatabaseProxy databaseProxy = Mockito.mock(DatabaseProxy.class);
        ImportController importController = new ImportController(file, adminView, progressView, mainController, databaseProxy);

        // rows mit 3 werten dürfen nicht verarbeitet werden
        importController.rowQueue.add("abc,def");
        importController.rowQueue.add("");
        importController.rowQueueCount = 2;

        CategoryConsumer categoryConsumer = new CategoryConsumer(importController, databaseImport, ",");
        categoryConsumer.run();

        long result = importController.rowQueue.size();
        Assert.assertEquals(0, result);

        result = importController.processedCount;
        Assert.assertEquals(1, result);

        result = importController.errorCount;
        Assert.assertEquals(1, result);

        result = importController.errorCategoryCount;
        Assert.assertEquals(0, result);
    }

}