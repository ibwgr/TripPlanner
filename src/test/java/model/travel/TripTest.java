package model.travel;

import model.common.DatabaseProxy;
import model.common.User;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 09.01.2017.
 */
public class TripTest {

    DatabaseProxy databaseProxy = new DatabaseProxy();

    // Helper / Test Object (bestehend in DB, siehe \TripPlanner\resources\db_script.sql)
    private static Trip getTestTrip(){
        Trip testTrip = new Trip();
        testTrip.setId(1L);
        testTrip.setName("Test Ferien");
        testTrip.setUser(getTestUser());
        return testTrip;
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
        testTripList.add(getTestTrip());
        testTripList.add(getTestTrip());
        testTripList.add(getTestTrip());
        return  testTripList;
    }

    // Helper / Test Object (bestehend in DB, siehe \TripPlanner\resources\db_script.sql)
    private static User getTestUser() {
        DatabaseProxy databaseProxy = new DatabaseProxy();
        User testUser = new User();
        testUser.setUsername("benutzer");
        testUser.setPassword("benutzer");
        testUser.setId(1L);
        return testUser;
    }




    // INTEGRATIONSTEST, wird nicht automatisch ausgefuehrt.
    // Test kann jedoch manuell bei Bedarf von Hand gestaret werden
    @Ignore
    @Test
    public void integrationsTestSearchByUserWithRealDbAccessReturnsTripList() throws Exception {
        User user = getTestUser();
        ArrayList<Trip> tripList = Trip.searchByUser(user);
        // aus DB gelesener Wert vergleichen
        Assert.assertEquals(3, tripList.size());
    }

    /* TEST MACHT VOELLIG KEINEN SINN! MUESSTE JA ALLES MOCKEN, KEINERLEI TEST!
    @Test
    public void fakeTestSearchByUserReturnsFakeTripList() throws Exception {
        User user = getTestUser();
        ArrayList<Trip> tripList = Mockito.mock(Trip.class).searchByUser(user);
        when(searchUser.searchByCredentials()).thenReturn(fakeUser);
    }
    */

    // INTEGRATIONSTEST, wird nicht automatisch ausgefuehrt.
    // Test kann jedoch manuell bei Bedarf von Hand gestaret werden
    @Ignore
    @Test
    public void integrationsTestSearchByUserAndIdWithRealDbAccessReturnsTrip() throws Exception {
        User user = getTestUser();
        Trip trip = Trip.searchByUserAndId(user,1L);
        // aus DB gelesener Wert vergleichen
        Assert.assertEquals(new Long(1), trip.getId());
    }


    // INTEGRATIONSTEST, wird nicht automatisch ausgefuehrt.
    // Test kann jedoch manuell bei Bedarf von Hand gestaret werden
    @Ignore
    @Test
    public void integrationsTestSaveWithoutIdInsertsNewTrip() throws Exception {
        User user = getTestUser();
        Trip trip = getNewTestTripWithoutId();
        // in DB speichern, wird ein INSERT ergeben
        // nun bekommt das trip Objekt seine ID gesetzt
        trip.save();
        // wieder aus DB lesen
        Trip tripAusDb = Trip.searchByUserAndId(user,trip.getId());
        // aus DB gelesener Wert vergleichen
        Assert.assertEquals(trip.getId(), tripAusDb.getId());
    }


    // INTEGRATIONSTEST, wird nicht automatisch ausgefuehrt.
    // Test kann jedoch manuell bei Bedarf von Hand gestaret werden
    @Ignore
    @Test
    public void integrationsTestSaveIdUpdatesTrip() throws Exception {
        User user = getTestUser();
        Trip trip = getTestTrip();
        // in DB speichern, wird ein UPDATE ergeben
        trip.save();
        // wieder aus DB lesen
        Trip tripAusDb = Trip.searchByUserAndId(user,trip.getId());
        // aus DB gelesener Wert vergleichen
        Assert.assertEquals(trip.getId(), tripAusDb.getId());
    }

}