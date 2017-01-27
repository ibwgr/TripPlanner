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

    private static Trip realerTripIdausDb;

    // Es besteht das Problem dass  aufgrund der Integrationstests zwingend ein "echter"
    // Trip ermittelt werden muss, der effektiv in der DB besteht, da sonst die Assertions fehlschlagen.
    // Deswegen die BEFORE Annoation. Wir suchen uns den erstbesten Trip aus der DB und verwenden
    // diesen fuer die weiteren Tests.
    @Before
    public void initializeTestCases() {
        User user = getTestUser();
        ArrayList<Trip> tripList = Trip.searchByUser(user);
        if (tripList.size() > 0) {
            this.realerTripIdausDb = tripList.get(1);
            this.realerTripIdausDb.setName("Test Ferien");
            System.out.println("*** Test Trip ID : " +realerTripIdausDb.getId());
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

    // INTEGRATIONSTEST, wird nicht bei MVN TEST ausgefuehrt, aber bei allen IntelliJ Tests
    @Test
    public void integrationsTestSearchByUserWithRealDbAccessReturnsTripList() throws Exception {
        User user = getTestUser();
        ArrayList<Trip> tripList = Trip.searchByUser(user);
        // aus DB gelesener Wert vergleichen, aber wie wissen ja nicht genau wieviele es sind!
        // Assert.assertEquals(3, tripList.size());
        if (tripList.size() > 1) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }
    }

    // INTEGRATIONSTEST, wird nicht bei MVN TEST ausgefuehrt, aber bei allen IntelliJ Tests
    @Test
    public void integrationsTestSearchByUserAndIdWithRealDbAccessReturnsTrip() throws Exception {
         Trip trip = Trip.searchById(realerTripIdausDb.getId());
         // aus DB gelesener Wert vergleichen
         Assert.assertEquals(realerTripIdausDb.getId(), trip.getId());
     }

    // INTEGRATIONSTEST, wird nicht bei MVN TEST ausgefuehrt, aber bei allen IntelliJ Tests
    @Test
    public void integrationsTestSaveWithoutIdInsertsNewTrip() throws Exception {
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
        Trip trip = realerTripIdausDb;
        System.out.println("Trip ID: " +trip.getId());
        // in DB speichern, wird ein UPDATE ergeben
        trip.save();
        // wieder aus DB lesen
        Trip tripAusDb = Trip.searchById(trip.getId());
        // aus DB gelesener Wert vergleichen
        Assert.assertEquals(trip.getId(), tripAusDb.getId());
    }

}