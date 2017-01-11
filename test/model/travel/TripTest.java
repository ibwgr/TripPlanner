package model.travel;

import model.common.DatabaseProxy;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by user on 09.01.2017.
 */
public class TripTest {

    DatabaseProxy databaseProxy = new DatabaseProxy();

    // Helper / Fake Object
    private static Trip getFakeTrip(){
        Trip fakeTrip = new Trip();
        fakeTrip.setId(1L);
        fakeTrip.setName("Fake Ferien");
        fakeTrip.setUser_id(1L);
        return fakeTrip;
    }

    // Helper / Fake Object
    private static ArrayList<Trip> getFakeTripList() {
        ArrayList<Trip> fakeTripList = new ArrayList<Trip>();
        fakeTripList.add(getFakeTrip());
        fakeTripList.add(getFakeTrip());
        fakeTripList.add(getFakeTrip());
        return  fakeTripList;
    }

    // Helper / fake Object
    private static User getFakeUser() {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        User fakeUser = new User();
        fakeUser.setUsername("benutzer");
        fakeUser.setPassword("benutzer");
        fakeUser.setId(1L);
        return fakeUser;
    }

    // wird mittels Ignore bei MVN TEST ignoriert!
    @Ignore
    @Test
    public void integrationsTestSearchByUserWithRealDbAccessReturnsTripList() throws Exception {
        User user = getFakeUser();
        ArrayList<Trip> tripList = Trip.searchByUser(user);
        // aus DB gelesener Wert!
        Assert.assertEquals(3, tripList.size());
    }

    /* TEST MACHT VOELLIG KEINEN SINN! MUESSTE JA ALLES MOCKEN, KEINERLEI TEST!
    @Test
    public void fakeTestSearchByUserReturnsFakeTripList() throws Exception {
        User user = getFakeUser();
        ArrayList<Trip> tripList = Mockito.mock(Trip.class).searchByUser(user);
        when(searchUser.searchByCredentials()).thenReturn(fakeUser);
    }
    */

}