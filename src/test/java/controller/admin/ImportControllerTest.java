package controller.admin;

import controller.common.MainController;
import org.junit.Assert;
import org.junit.Test;
import sun.applet.Main;
import view.admin.AdminView;
import view.admin.ProgressView;
import view.common.TripPlannerMain;

import java.io.File;

/**
 * Created by dieterbiedermann on 06.01.17.
 */
public class ImportControllerTest {

    /**
     * queueIsEmpty gibt true züruck wenn rowQueue leer ist
     */
    @Test
    public void queueIsEmptyReturnsTrueWhenRowQueueIsEmpty() {
        File file = new File("");
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);
        ProgressView progressView = new ProgressView(mainController);
        ImportController importController = new ImportController(file, adminView, progressView, mainController);

        importController.rowQueue.clear();

        Boolean result = importController.queueIsEmpty();
        Assert.assertEquals(true, result);
    }

    /**
     * queueIsEmpty gibt false züruck wenn rowQueue nicht leer ist
     */
    @Test
    public void queueIsEmptyReturnsTrueWhenRowQueueIsNotEmpty() {
        File file = new File("");
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);
        ProgressView progressView = new ProgressView(mainController);
        ImportController importController = new ImportController(file, adminView, progressView, mainController);

        importController.rowQueue.add("test");

        Boolean result = importController.queueIsEmpty();
        Assert.assertEquals(false, result);
    }

    /**
     * increaseRowQueueCount erhöht den rowQueueCount
     */
    @Test
    public void increaseRowQueueCountIncreasesRowQueueCount() {
        File file = new File("");
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);
        ProgressView progressView = new ProgressView(mainController);
        ImportController importController = new ImportController(file, adminView, progressView, mainController);

        importController.increaseRowQueueCount();
        long result = importController.rowQueueCount;
        Assert.assertEquals(1, result);

        importController.increaseRowQueueCount();
        result = importController.rowQueueCount;
        Assert.assertEquals(2, result);
    }

    /**
     * allRowsProcessed gibt True zurück wenn alle Rows verarbeitet sind
     */
    @Test
    public void allRowsProcessedReturnsTrueWhenAllRowsAreProcessed() {
        File file = new File("");
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);
        ProgressView progressView = new ProgressView(mainController);
        ImportController importController = new ImportController(file, adminView, progressView, mainController);

        importController.rowQueueCount = 5;
        importController.processedCount = 5;
        Boolean result = importController.allRowsProcessed();
        Assert.assertEquals(true, result);
    }

    /**
     * allRowsProcessed gibt False zurück wenn nicht alle Rows verarbeitet sind
     */
    @Test
    public void allRowsProcessedReturnsFalseWhenNotAllRowsAreProcessed() {
        File file = new File("");
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);
        ProgressView progressView = new ProgressView(mainController);
        ImportController importController = new ImportController(file, adminView, progressView, mainController);

        importController.rowQueueCount = 5;
        importController.processedCount = 2;
        Boolean result = importController.allRowsProcessed();
        Assert.assertEquals(false, result);
    }

    /**
     * increaseErrorCount erhöht den errorCount
     */
    @Test
    public void increaseErrorCountIncreasesErrorCount() {
        File file = new File("");
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);
        ProgressView progressView = new ProgressView(mainController);
        ImportController importController = new ImportController(file, adminView, progressView, mainController);

        importController.increaseErrorCount();
        long result = importController.errorCount;
        Assert.assertEquals(1, result);

        importController.increaseErrorCount();
        result = importController.errorCount;
        Assert.assertEquals(2, result);
    }

}