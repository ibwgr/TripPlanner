package controller.common;

import model.common.Pair;
import model.common.User;
import model.travel.Trip;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import testFramework.UnitTest;
import view.common.TripPlannerMain;

import javax.swing.*;

import static org.junit.Assert.*;

public class MainControllerTest {

    @Category({ UnitTest.class })
    @Test
    public void getUserReturnsCorrectUser() {
        //TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        TripPlannerMain tripPlannerMain = Mockito.mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);

        User testUser = new User();
        mainController.user = testUser;

        User result = mainController.getUser();
        Assert.assertEquals(testUser, result);
    }

    @Category({ UnitTest.class })
    @Test
    public void setUserSetsCorrectUser() {
        //TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        TripPlannerMain tripPlannerMain = Mockito.mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);

        User testUser = new User();
//        mainController.user = null;
        Assert.assertNull(null, mainController.user);

        mainController.setUser(testUser);

        User result = mainController.user;
        Assert.assertEquals(testUser, result);
    }

    @Category({ UnitTest.class })
    @Test
    public void getTripReturnsCorrectTrip() {
        //TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        TripPlannerMain tripPlannerMain = Mockito.mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);

        Trip testTrip = new Trip();
        mainController.trip = testTrip;

        Trip result = mainController.getTrip();
        Assert.assertEquals(testTrip, result);
    }

    @Category({ UnitTest.class })
    @Test
    public void setTripSetsCorrectTrip() {
        //TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        TripPlannerMain tripPlannerMain = Mockito.mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);

        Trip testTrip = new Trip();
//        mainController.user = null;
        Assert.assertNull(null, mainController.trip);

        mainController.setTrip(testTrip);

        Trip result = mainController.trip;
        Assert.assertEquals(testTrip, result);
    }

    @Category({ UnitTest.class })
    @Test
    public void openLastViewReducesCurrentViewNo() {
        TripPlannerMain tripPlannerMain = Mockito.mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);

        JLabel jLabel = Mockito.mock(JLabel.class);

        mainController.viewList.add(new Pair<>("test1", jLabel));
        mainController.viewList.add(new Pair<>("test2", jLabel));

        mainController.currentViewNo = 2;

        mainController.openLastView();

        int result = mainController.currentViewNo;
        Assert.assertEquals(1, result);
    }

    @Category({ UnitTest.class })
    @Test
    public void openNextViewIncreasesCurrentViewNo() {
        TripPlannerMain tripPlannerMain = Mockito.mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);

        JLabel jLabel = Mockito.mock(JLabel.class);

        mainController.viewList.add(new Pair<>("test1", jLabel));
        mainController.viewList.add(new Pair<>("test2", jLabel));

        mainController.currentViewNo = 1;

        mainController.openNextView();

        int result = mainController.currentViewNo;
        Assert.assertEquals(2, result);
    }

    @Category({ UnitTest.class })
    @Test
    public void closeCurrentViewSetsCurrentViewNoTo1WhenFirstViewIsClosed() {
        TripPlannerMain tripPlannerMain = Mockito.mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);

        JLabel jLabel = Mockito.mock(JLabel.class);

        mainController.viewList.add(new Pair<>("test1", jLabel));
        mainController.viewList.add(new Pair<>("test2", jLabel));
        mainController.viewList.add(new Pair<>("test3", jLabel));

        mainController.currentViewNo = 1;

        mainController.closeCurrentView();

        int result = mainController.currentViewNo;
        Assert.assertEquals(1, result);
    }

    @Category({ UnitTest.class })
    @Test
    public void closeCurrentViewSetsCurrentViewNoToSecondLastViewWhenLastViewIsClosed() {
        TripPlannerMain tripPlannerMain = Mockito.mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);

        JLabel jLabel = Mockito.mock(JLabel.class);

        mainController.viewList.add(new Pair<>("test1", jLabel));
        mainController.viewList.add(new Pair<>("test2", jLabel));
        mainController.viewList.add(new Pair<>("test3", jLabel));

        mainController.currentViewNo = 3;

        mainController.closeCurrentView();

        int result = mainController.currentViewNo;
        Assert.assertEquals(2, result);
    }

    @Category({ UnitTest.class })
    @Test
    public void closeCurrentViewSetsCurrentViewNoTo2WhenAnotherViewFollows() {
        TripPlannerMain tripPlannerMain = Mockito.mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);

        JLabel jLabel = Mockito.mock(JLabel.class);

        mainController.viewList.add(new Pair<>("test1", jLabel));
        mainController.viewList.add(new Pair<>("test2", jLabel));
        mainController.viewList.add(new Pair<>("test3", jLabel));

        mainController.currentViewNo = 2;

        mainController.closeCurrentView();

        int result = mainController.currentViewNo;
        Assert.assertEquals(2, result);
    }

}