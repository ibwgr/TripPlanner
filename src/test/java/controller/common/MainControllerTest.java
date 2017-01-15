package controller.common;

import model.common.User;
import model.travel.Trip;
import org.junit.Assert;
import org.junit.Test;
import view.common.TripPlannerMain;

import static org.junit.Assert.*;

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

    @Test
    public void getTripReturnsCorrectTrip() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);

        Trip testTrip = new Trip();
        mainController.trip = testTrip;

        User result = mainController.getUser();
        Assert.assertEquals(testTrip, result);
    }

    @Test
    public void setTripSetsCorrectTrip() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);

        Trip testTrip = new Trip();
//        mainController.user = null;
        Assert.assertNull(null, mainController.trip);

        mainController.setTrip(testTrip);

        Trip result = mainController.trip;
        Assert.assertEquals(testTrip, result);
    }

}