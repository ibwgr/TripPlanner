package controller.common;

import model.common.User;
import org.junit.Assert;
import org.junit.Test;
import view.common.TripPlannerMain;

public class MainControllerTest {

    @Test
    public void getUserReturnsCorrectUser() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);

        User testUser = new User();
        mainController.user = testUser;

        User result = mainController.getUser();
        Assert.assertEquals(testUser, result);
    }

    @Test
    public void setUserSetsCorrectUser() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);

        User testUser = new User();
//        mainController.user = null;
        Assert.assertNull(null, mainController.user);

        mainController.setUser(testUser);

        User result = mainController.user;
        Assert.assertEquals(testUser, result);
    }



}