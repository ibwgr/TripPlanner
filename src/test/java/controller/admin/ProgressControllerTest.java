package controller.admin;

import controller.common.MainController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import testFramework.UnitTest;
import view.admin.ProgressView;
import view.common.TripPlannerMain;

public class ProgressControllerTest {

    @Category({ UnitTest.class })
    @Test
    public void setImportControllerSetsCorrectImportController() {
        ProgressView progressView = Mockito.mock(ProgressView.class);
        TripPlannerMain tripPlannerMain = Mockito.mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        ProgressController progressController = new ProgressController(progressView, mainController);

        ImportController importController = Mockito.mock(ImportController.class);

        progressController.setImportController(importController);

        ImportController result = progressController.importController;
        Assert.assertEquals(importController, result);
    }

}