package model.travel;

import model.common.DatabaseProxy;
import model.common.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

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


}