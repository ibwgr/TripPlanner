package view.admin;

import controller.common.MainController;
import org.junit.Assert;
import org.junit.Test;
import view.common.TripPlannerMain;

/**
 * Created by dieterbiedermann on 25.01.17.
 */
public class ProgressViewTest {

    /**
     * progressView Konstruktor erstellt alle Komponenten
     */
    @Test
    public void progressViewCreatesAllComponentsInConstructor() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        ProgressView progressView = new ProgressView(mainController);

        Assert.assertNotNull(progressView.statusTotalRows);
        Assert.assertNotNull(progressView.statusRowsProcessed);
        Assert.assertNotNull(progressView.statusRowsError);
        Assert.assertNotNull(progressView.statusRowsCategoryError);
        Assert.assertNotNull(progressView.statusElapsedTime);
        Assert.assertNotNull(progressView.buttonNewUpload);
        Assert.assertNotNull(progressView.cancelButton);
        Assert.assertNotNull(progressView.progressBar);
    }

}