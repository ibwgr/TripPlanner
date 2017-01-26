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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by dieterbiedermann on 06.01.17.
 */
public class ImportProgressTest {

    @Category({ UnitTest.class })
    @Test
    public void runCallsShowStatusAndImportIsFinished() throws IOException {

        ImportController importController = Mockito.mock(ImportController.class);

        Mockito.when(importController.allRowsProcessed()).thenReturn(false).thenReturn(true);

        ImportProgress importProgress = new ImportProgress(importController);
        importProgress.run();

        verify(importController, times(2)).showStatus();
        verify(importController, times(1)).importIsFinished();
    }

}