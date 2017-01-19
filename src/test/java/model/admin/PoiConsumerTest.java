package model.admin;

import controller.admin.ImportController;
import controller.common.MainController;
import model.common.DatabaseProxy;
import model.common.PoiCategory;
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

import static org.junit.Assert.*;

/**
 * Created by dieterbiedermann on 06.01.17.
 */
public class PoiConsumerTest {

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

        importController.poiCategories.add(new PoiCategory("1", "name1"));
        importController.poiCategories.add(new PoiCategory("2", "name2"));

        // Row muss zwei Werte haben
        importController.rowQueue.add("1,def,ghi,jkl,mno");
        importController.rowQueue.add("2,456,789,012,345");
        importController.rowQueueCount = 2;

        PoiConsumer poiConsumer = new PoiConsumer(importController, databaseImport, ",");
        poiConsumer.run();

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

        importController.poiCategories.add(new PoiCategory("1", "name1"));
        importController.poiCategories.add(new PoiCategory("2", "name2"));

        // rows mit 3 werten dürfen nicht verarbeitet werden
        importController.rowQueue.add("1,def,ghi,abc,def,ghi,111");
        importController.rowQueue.add("2,456,789,abc,def,ghi,222");
        importController.rowQueueCount = 2;

        PoiConsumer poiConsumer = new PoiConsumer(importController, databaseImport, ",");
        poiConsumer.run();

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

        // database proxy kann nicht gemockt werden, es muss DatabaseImport gemockt werden !!!
        //Mockito.when(databaseImport.insertMultiValueCategories(Mockito.any()));

        File file = Mockito.mock(File.class);
        MainController mainController = Mockito.mock(MainController.class);
        AdminView adminView = Mockito.mock(AdminView.class);
        ProgressView progressView = Mockito.mock(ProgressView.class);
        DatabaseProxy databaseProxy = Mockito.mock(DatabaseProxy.class);
        ImportController importController = new ImportController(file, adminView, progressView, mainController, databaseProxy);

        importController.poiCategories.add(new PoiCategory("1", "name1"));
        importController.poiCategories.add(new PoiCategory("2", "name2"));

        // rows mit 3 werten dürfen nicht verarbeitet werden
        importController.rowQueue.add("1,def,ghi,jkl,mno");
        importController.rowQueue.add("");
        importController.rowQueueCount = 2;

        PoiConsumer poiConsumer = new PoiConsumer(importController, databaseImport, ",");
        poiConsumer.run();

        long result = importController.rowQueue.size();
        Assert.assertEquals(0, result);

        result = importController.processedCount;
        Assert.assertEquals(1, result);

        result = importController.errorCount;
        Assert.assertEquals(1, result);

        result = importController.errorCategoryCount;
        Assert.assertEquals(0, result);
    }

    @Category({ UnitTest.class })
    @Test
    public void runFillsReadsQueueWithErrorsForMissingCategory() throws IOException {

        DatabaseImport databaseImport = Mockito.mock(DatabaseImport.class);

        // database proxy kann nicht gemockt werden, es muss DatabaseImport gemockt werden !!!
        //Mockito.when(databaseImport.insertMultiValueCategories(Mockito.any()));

        File file = Mockito.mock(File.class);
        MainController mainController = Mockito.mock(MainController.class);
        AdminView adminView = Mockito.mock(AdminView.class);
        ProgressView progressView = Mockito.mock(ProgressView.class);
        DatabaseProxy databaseProxy = Mockito.mock(DatabaseProxy.class);
        ImportController importController = new ImportController(file, adminView, progressView, mainController, databaseProxy);

        // rows mit 3 werten dürfen nicht verarbeitet werden
        importController.rowQueue.add("1,def,ghi,jkl,mno");
        importController.rowQueue.add("2,456,789,012,345");
        importController.rowQueueCount = 2;

        PoiConsumer poiConsumer = new PoiConsumer(importController, databaseImport, ",");
        poiConsumer.run();

        long result = importController.rowQueue.size();
        Assert.assertEquals(0, result);

        result = importController.processedCount;
        Assert.assertEquals(0, result);

        result = importController.errorCount;
        Assert.assertEquals(0, result);

        result = importController.errorCategoryCount;
        Assert.assertEquals(2, result);
    }

}