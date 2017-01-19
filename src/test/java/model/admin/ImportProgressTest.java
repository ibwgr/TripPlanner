package model.admin;

import controller.admin.ImportController;
import controller.common.MainController;
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
public class ImportProgressTest {

    @Test
    public void runCallsShowStatusAndImportIsFinished() throws IOException {

        ImportController importController = Mockito.mock(ImportController.class);

/*
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);
        ProgressView progressView = new ProgressView(mainController);
        ImportController importController = new ImportController(new File(""), adminView, progressView, mainController);
*/
        Mockito.when(importController.allRowsProcessed()).thenReturn(true);

        ImportProgress importProgress = new ImportProgress(importController);
        importProgress.run();

    }

}