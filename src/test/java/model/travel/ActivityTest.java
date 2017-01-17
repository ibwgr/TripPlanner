package model.travel;

import model.common.DatabaseProxy;
import model.common.User;
import model.common.Util;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 13.01.2017.
 */
public class ActivityTest {

    // Helper / fake Object
    private static User getFakeUser() {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        User fakeUser = new User();
        fakeUser.setUsername("benutzer");
        fakeUser.setPassword("benutzer");
        fakeUser.setId(1L);
        return fakeUser;
    }

    // Helper / Fake Object
    private static Trip getFakeTrip(){
        Trip fakeTrip = new Trip();
        fakeTrip.setId(1L);
        fakeTrip.setName("Fake Ferien");
        fakeTrip.setUser(getFakeUser());
        return fakeTrip;
    }




    // INTEGRATIONSTEST
    @Test
    public void integrationsTestSearchByTripWithRealDbAccessReturnsActivityList() throws Exception {
        User user = getFakeUser();
        ArrayList<Activity> activityList = Activity.searchByTrip(getFakeTrip());
        // aus DB gelesener Wert!
        Assert.assertEquals(3, activityList.size());
    }

    // INTEGRATIONSTEST
    @Ignore  // TODO weil es die ID 1 allenfalls gar nicht gibt!
    @Test
    public void integrationsTestSearchByIdWithRealDbAccessReturnsActivity() throws Exception {
        User user = getFakeUser();
        Activity activity = Activity.searchById(5L);
        // aus DB gelesener Wert!
        Assert.assertEquals(new Long(5), activity.getId());
    }

    // INTEGRATIONSTEST
    @Test
    public void integrationsTestUpdateDatePlusOneDayReturnsActivityWithDatePlus1() throws Exception {
        User user = getFakeUser();
        ArrayList<Activity> activityList = Activity.searchByTrip(getFakeTrip());
        // aus DB gelesener Wert!
        for (Activity activity: activityList) {
            Date dateBefore = activity.getDate();
            System.out.println("before: " +dateBefore);
            activity.moveOneDayUp();
            System.out.println("after: " +activity.getDate());
            Assert.assertEquals(Util.addDays(dateBefore,1), activity.getDate());
        }
    }
    // INTEGRATIONSTEST
    @Test
    public void integrationsTestUpdateDateMinusOneDayReturnsActivityWithDateMinus1() throws Exception {
        User user = getFakeUser();
        ArrayList<Activity> activityList = Activity.searchByTrip(getFakeTrip());
        // aus DB gelesener Wert!
        for (Activity activity: activityList) {
            Date dateBefore = activity.getDate();
            System.out.println("before: " +dateBefore);
            activity.moveOneDayDown();
            System.out.println("after: " +activity.getDate());
            Assert.assertEquals(Util.addDays(dateBefore,-1), activity.getDate());
        }
    }

}