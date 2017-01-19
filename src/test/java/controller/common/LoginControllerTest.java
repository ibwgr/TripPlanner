package controller.common;

import model.common.DatabaseProxy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import testFramework.UnitTest;
import view.common.LoginView;
import view.common.TripPlannerMain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    @Category({ UnitTest.class })
    @Test
    public void doLoginShowsErrorForEmptyUser() {
        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        LoginView loginView = mock(LoginView.class);
        LoginController loginController = new LoginController(loginView, mainController, databaseProxy);

        loginController.doLogin("", "password");

        verify(tripPlannerMain, times(1)).showErrorMessage("Username and Password must be filled.");
    }

    @Category({ UnitTest.class })
    @Test
    public void doLoginShowsErrorForEmptyPassword() {
        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        LoginView loginView = mock(LoginView.class);
        LoginController loginController = new LoginController(loginView, mainController, databaseProxy);

        loginController.doLogin("username", "");

        verify(tripPlannerMain, times(1)).showErrorMessage("Username and Password must be filled.");
    }

    @Category({ UnitTest.class })
    @Test
    public void doLoginShowsErrorForUserNotFound() throws SQLException {
        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        LoginView loginView = mock(LoginView.class);
        LoginController loginController = new LoginController(loginView, mainController, databaseProxy);

        when(databaseProxy.prepareStatement(anyString())).thenReturn(preparedStatement);

        // return empty resultSet
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        loginController.doLogin("username", "password");

        verify(tripPlannerMain, times(1)).showErrorMessage("Username not found or Password incorrect.");
    }

//    @Category({ UnitTest.class })
    // ToDo: Nach dem Login wird eine View aufgerufen, deshalb kann dies nicht als UnitTest laufen gelassen werden.
    @Test
    public void doLoginLogsInWithCorrectUser() throws SQLException {
        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        LoginView loginView = mock(LoginView.class);
        LoginController loginController = new LoginController(loginView, mainController, databaseProxy);

        when(databaseProxy.prepareStatement(anyString())).thenReturn(preparedStatement);

        // return fake user in resultSet
        // first next() method call returns true, the second returns false
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(1)).thenReturn(1L);
        when(resultSet.getString(2)).thenReturn("username");
        when(resultSet.getString(3)).thenReturn("password");
        when(resultSet.getString(4)).thenReturn("mail@mail.mail");
        when(resultSet.getString(5)).thenReturn("Full name");
        when(resultSet.getLong(6)).thenReturn(1L);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        loginController.doLogin("username", "password");

        long resultId = mainController.getUser().getId();
        Assert.assertEquals(1L,resultId);
        String resultUsername = mainController.getUser().getUsername();
        Assert.assertEquals("username",resultUsername);
        String resultPassword = mainController.getUser().getPassword();
        Assert.assertEquals("password",resultPassword);
        String resultEmail = mainController.getUser().getEmail();
        Assert.assertEquals("mail@mail.mail",resultEmail);
        String resultName = mainController.getUser().getName();
        Assert.assertEquals("Full name",resultName);
        long resultType = mainController.getUser().getType();
        Assert.assertEquals(1L,resultType);
    }

}