package model.travel;

import model.common.DatabaseProxy;
import model.common.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import testFramework.UnitTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.when;

/**
 * Created by user on 09.01.2017.
 */
public class TripTest {

    private static Long realerTripIdausDb;

    // Es besteht das Problem dass  aufgrund der Integrationstests zwingend ein "echter"
    // Trip ermittelt werden muss, der effektiv in der DB besteht, da sonst die Assertions fehlschlagen.
    // Deswegen die BEFORE Annoation. Wir suchen uns den erstbesten Trip aus der DB und verwenden
    // diesen fuer die weiteren Tests.
    @Before
    public void initializeTestCases() {
        User user = getTestUser();
        ArrayList<Trip> tripList = Trip.searchByUser(user);
        if (tripList.size() > 0) {
            realerTripIdausDb = tripList.get(1).getId();
        }
    }

    // Helper / Test Object (bestehend in DB, siehe \TripPlanner\resources\db_script.sql)
    private static Trip getTestTrip(){
        // hierbei
        Trip testTrip = new Trip();
        testTrip.setId(realerTripIdausDb);
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
        testUser.setId(1L); // fix!
        return testUser;
    }


//    // TEST MACHT VOELLIG KEINEN SINN! MUESSTE JA ALLES MOCKEN, KEINERLEI TEST!
//    @Category({ UnitTest.class })
//    @Test
//    public void fakeTestSearchByUserReturnsFakeTripList() throws Exception {
//        User user = getTestUser();
//        ArrayList<Trip> tripList = Mockito.mock(Trip.class).searchByUser(user);
//        ...
//    }


    // INTEGRATIONSTEST, wird nicht bei MVN TEST ausgefuehrt, aber bei allen IntelliJ Tests
    @Test
    public void integrationsTestSearchByUserWithRealDbAccessReturnsTripList() throws Exception {
        User user = getTestUser();
        ArrayList<Trip> tripList = Trip.searchByUser(user);
        // aus DB gelesener Wert vergleichen, aber wie wissen ja nicht genau
        // wieviele es sind!
        //Assert.assertEquals(3, tripList.size());
        if (tripList.size() > 1) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }

    }

    // INTEGRATIONSTEST, wird nicht bei MVN TEST ausgefuehrt, aber bei allen IntelliJ Tests
    @Test
    public void integrationsTestSearchByUserAndIdWithRealDbAccessReturnsTrip() throws Exception {
         User user = getTestUser();
         Trip trip = Trip.searchById(realerTripIdausDb);
         // aus DB gelesener Wert vergleichen
         Assert.assertEquals(realerTripIdausDb, trip.getId());
     }

    // INTEGRATIONSTEST, wird nicht bei MVN TEST ausgefuehrt, aber bei allen IntelliJ Tests
    @Test
    public void integrationsTestSaveWithoutIdInsertsNewTrip() throws Exception {
        User user = getTestUser();
        // dieses Trip Objekt hat noch keine ID
        Trip trip = getNewTestTripWithoutId();
        // in DB speichern, wird ein INSERT ergeben
        // nun bekommt das trip Objekt seine ID gesetzt
        trip.save();
        // wieder aus DB lesen
        Trip tripAusDb = Trip.searchById(trip.getId());
        // aus DB gelesener Wert vergleichen
        Assert.assertEquals(trip.getId(), tripAusDb.getId());
    }


    // INTEGRATIONSTEST, wird nicht bei MVN TEST ausgefuehrt, aber bei allen IntelliJ Tests
    @Test
    public void integrationsTestSaveIdUpdatesTrip() throws Exception {
        User user = getTestUser();
        Trip trip = getTestTrip();
        System.out.println("Trip ID: " +trip.getId());
        // in DB speichern, wird ein UPDATE ergeben
        trip.save();
        // wieder aus DB lesen
        Trip tripAusDb = Trip.searchById(trip.getId());
        // aus DB gelesener Wert vergleichen
        Assert.assertEquals(trip.getId(), tripAusDb.getId());
    }

    // TODO  @After : alle Testdaten wieder loeschen (also z.B. alle hier neu angelegten Trips)

}