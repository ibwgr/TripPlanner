package controller.admin;

import com.sun.tools.internal.ws.wsdl.document.Import;
import controller.common.MainController;
import model.common.User;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import view.admin.ProgressView;
import view.common.TripPlannerMain;

import static org.junit.Assert.*;

public class ProgressControllerTest {

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