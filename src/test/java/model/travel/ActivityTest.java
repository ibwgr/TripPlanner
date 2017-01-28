package model.travel;

import model.common.DatabaseProxy;
import model.common.User;
import model.common.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 13.01.2017.
 */
public class ActivityTest {

    private static Trip realerTripIdausDb;

    // Es besteht das Problem dass  aufgrund der Integrationstests zwingend ein "echter"
    // Trip ermittelt werden muss, der effektiv in der DB besteht, da sonst die Assertions fehlschlagen.
    // Deswegen die BEFORE Annoation. Wir suchen uns den erstbesten Trip aus der DB und verwenden
    // diesen fuer die weiteren Tests.
    @Before
    public void initializeTestCases() {
        User user = getTestUser();
        ArrayList<Trip> tripList = Trip.searchByUser(user);
        for (Trip trip : tripList) {
            if (trip.getCountActivities() > 0) {
                this.realerTripIdausDb = trip;
                this.realerTripIdausDb.setName("Test Ferien");
                System.out.println("*** Test Trip ID : " + realerTripIdausDb.getId());
            }
        }
    }

    // Helper / Test Object (ohne ID, deshalb auch nicht in DB existent)
    private static Trip getNewTestTripWithoutId(){
        Trip testTrip = new Trip();
        testTrip.setName("Test Ferien "
                +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        testTrip.setUser(getTestUser());
        return testTrip;
    }

    // Helper / Test Object (bestehend in DB, siehe \TripPlanner\resources\db_script.sql)
    private static ArrayList<Trip> getTestTripList() {
        ArrayList<Trip> testTripList = new ArrayList<Trip>();
        testTripList.add(realerTripIdausDb);
        testTripList.add(realerTripIdausDb);
        testTripList.add(realerTripIdausDb);
        return  testTripList;
    }

    // Helper / Test Object (bestehend in DB, siehe \TripPlanner\resources\db_script.sql)
    private static User getTestUser() {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        User testUser = new User();
        testUser.setUsername("benutzer");
        testUser.setPassword("benutzer");
        testUser.setId(1L); // fix!
        return testUser;
    }

    //-------------------------------------------------------------------------------
    // Integrationstests
    //-------------------------------------------------------------------------------

    // INTEGRATIONSTEST
    @Test
    public void integrationsTestSearchByTripWithRealDbAccessReturnsActivityList() throws Exception {
        ArrayList<Activity> activityList = Activity.searchByTrip(realerTripIdausDb);
        // aus DB gelesener Wert!
        System.out.println(activityList.size());
        Assert.assertEquals(realerTripIdausDb.getCountActivities(), activityList.size());
    }

    // INTEGRATIONSTEST
    @Test
    public void integrationsTestSearchByIdWithRealDbAccessReturnsActivity() throws Exception {
        Activity activity = Activity.searchById(realerTripIdausDb.getId());
        // aus DB gelesener Wert!
        Assert.assertEquals(realerTripIdausDb.getId(), activity.getId());
    }

    // INTEGRATIONSTEST
    @Test
    public void integrationsTestUpdateDatePlusOneDayReturnsActivityWithDatePlus1() throws Exception {
        ArrayList<Activity> activityList = Activity.searchByTrip(realerTripIdausDb);
        // aus DB gelesener Wert!
        for (Activity activity: activityList) {
            Date dateBefore = activity.getDate();
            System.out.println("before: " +dateBefore);
            activity.setActivityDateBefore();
            System.out.println("after: " +activity.getDate());
            Assert.assertEquals(Util.addDays(dateBefore,1), activity.getDate());
        }
    }
    // INTEGRATIONSTEST
    @Test
    public void integrationsTestUpdateDateMinusOneDayReturnsActivityWithDateMinus1() throws Exception {
        ArrayList<Activity> activityList = Activity.searchByTrip(realerTripIdausDb);
        // aus DB gelesener Wert!
        for (Activity activity: activityList) {
            Date dateBefore = activity.getDate();
            System.out.println("before: " +dateBefore);
            activity.setActivityDateAfter();
            System.out.println("after: " +activity.getDate());
            Assert.assertEquals(Util.addDays(dateBefore,-1), activity.getDate());
        }
    }

}