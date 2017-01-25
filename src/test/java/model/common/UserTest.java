package model.common;

import model.common.User;
import model.common.UserTypeEnum;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 *
 */
public class UserTest {

    @Test
    public void newUserWithAdminEnumerationShouldReturnWithType2() throws Exception {
        User a1 = new User();
        a1.setEmail("test@example.com");
        a1.setName("Hans");
        a1.setUsername("Hans");
        a1.setPassword("geheim");
        a1.setTypeEnum(UserTypeEnum.ADMIN);
        // Admin soll auf Wert 2 gemapped sein
        Assert.assertEquals(new Long(2),a1.getType());
    }

    @Test
    public void newUserWithUserEnumerationShouldReturnWithType1() throws Exception {
        User a1 = new User();
        a1.setEmail("test@example.com");
        a1.setName("Hans");
        a1.setUsername("Hans");
        a1.setPassword("geheim");
        a1.setTypeEnum(UserTypeEnum.USER);
        // User soll auf Wert 1 gemapped sein
        Assert.assertEquals(new Long(1),a1.getType());
    }

    @Test
    public void fakeTestSearchByCredentialsReturnsFakeUser() throws Exception {
        User searchUser = Mockito.mock(User.class);
        searchUser.setUsername("benutzer");
        searchUser.setPassword("benutzer");
        //
        // Mockito gibt diesen Fake User zurueck
        User fakeUser = new User();
        fakeUser.setEmail("benutzer@example.com");
        fakeUser.setUsername(searchUser.getUsername());
        //
        when(searchUser.searchByCredentials()).thenReturn(fakeUser);
        //
        User foundUser = searchUser.searchByCredentials();
        Assert.assertEquals("benutzer@example.com", foundUser.getEmail());
    }

// TODO mockito will so nicht richtig...
//    @Test
//    public void fakeLoginUserWithUsernameAndCorrectPasswordReturnsTrue() throws Exception {
//        User user = Mockito.mock(User.class);
//        user.setUsername("benutzer");
//        user.setPassword("benutzer");
//        //
//        // Mockito gibt diesen Fake User zurueck
//        User fakeUser = new User();
//        fakeUser.setEmail("fake-benutzer@example.com");
//        fakeUser.setUsername(user.getUsername());
//        fakeUser.setPassword(user.getPassword());
//        fakeUser.setId(new Long(23));
//        //
//        when(user.searchByCredentials()).thenReturn(fakeUser);
//        when(user.searchByCredentials(user.getUsername(),user.getPassword())).thenReturn(fakeUser);
//        //
//        // login erfolgreich
//        Assert.assertEquals(true, user.login());
//        Assert.assertEquals(true, user.isLoggedIn());
//    }

    @Test
    public void fakeLoginUserWithNoUsernameIsWrongAndShouldReturnFalse() throws Exception {
        // login darf so nie erfolgreich sein, da username fehlt
        User user = Mockito.mock(User.class);
        user.setPassword("geheim");
        //
        // Mockito gibt diesen Fake User zurueck
        User fakeUser = new User();
        fakeUser.setEmail("fake-benutzer@example.com");
        //
        when(user.searchByCredentials()).thenReturn(fakeUser);
        //
        Assert.assertEquals(false, user.login());
        Assert.assertEquals(false, user.isLoggedIn());
    }

// TODO: frage - wie machen wir die Integrationstests? Das hier funktioniert ja insoweit korrekt.
//    @Test
//    public void integrationsTestSearchByCredentialsWithRealDbAccessReturnsUser() throws Exception {
//        User a1 = new User(databaseProxy);
//        a1.setUsername("benutzer");
//        a1.setPassword("benutzer");
//        // search!
//        User a2 = a1.searchByCredentials(a1.getUsername(), a1.getPassword());
//        // aus DB gelesener Wert!
//        Assert.assertEquals("benutzer@example.com", a2.getEmail());
//    }



}