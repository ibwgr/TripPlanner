package controller.admin;

import controller.common.MainController;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import testFramework.UnitTest;
import view.admin.AdminView;
import view.common.TripPlannerMain;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by dieterbiedermann on 06.01.17.
 */
public class AdminControllerTest {

    @Category({ UnitTest.class })
    @Test
    public void importFileWithoutFileShowsError() {
        AdminView adminView = mock(AdminView.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);

        AdminController adminController = new AdminController(adminView, mainController);

        adminController.importFile();

        verify(tripPlannerMain, times(1)).showErrorMessage("Please choose a file first.");
    }

}