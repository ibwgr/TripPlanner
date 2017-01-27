package controller.travel;

import controller.common.MainController;
import model.common.DatabaseProxy;
import model.common.Poi;
import model.common.PoiCategory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import testFramework.UnitTest;
import view.common.TripPlannerMain;
import view.travel.SearchView;

import java.util.Date;

import static org.mockito.Mockito.*;

public class SearchControllerTest {

    @Category({ UnitTest.class })
    @Test
    public void searchCityShowsErrorForTooSmallSearchText() {
        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        SearchView searchView = mock(SearchView.class);
        SearchController searchController = new SearchController(searchView, mainController, databaseProxy);

        when(searchView.getSearchText()).thenReturn("AB");

        searchController.searchCity();

        verify(tripPlannerMain, times(1)).showErrorMessage("Search text is too short. Minimum length is 3 characters.");
    }

    @Category({ UnitTest.class })
    @Test
    public void searchPoiShowsErrorForEmptyCity() {
        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        SearchView searchView = mock(SearchView.class);
        SearchController searchController = new SearchController(searchView, mainController, databaseProxy);

        searchController.searchPoi();

        verify(tripPlannerMain, times(1)).showErrorMessage("City is mandatory to search for a poi.");
    }

    @Category({ UnitTest.class })
    @Test
    public void searchPoiShowsErrorForTooSmallSearchText() {
        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        SearchView searchView = mock(SearchView.class);
        SearchController searchController = new SearchController(searchView, mainController, databaseProxy);

        when(searchView.getSearchText()).thenReturn("AB");
        when(searchView.getCity()).thenReturn(new Poi("N17328659"
                        , "Wien"
                        , new PoiCategory("66", "City")
                        , "16.3725042"
                        , "48.2083537"
                )
        );

        searchController.searchPoi();

        verify(tripPlannerMain, times(1)).showErrorMessage("Search text minimum length is 3 characters.");
    }

    @Category({ UnitTest.class })
    @Test
    public void addActivityShowsErrorForMissingPoi() {
        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        SearchView searchView = mock(SearchView.class);
        SearchController searchController = new SearchController(searchView, mainController, databaseProxy);

        when(searchView.getDate()).thenReturn(new Date());
        when(searchView.getComment()).thenReturn("comment");

        searchController.addActivity();

        verify(tripPlannerMain, times(1)).showErrorMessage("City/Point of interest, Date or Comment is missing.");
    }

    @Category({ UnitTest.class })
    @Test
    public void addActivityShowsErrorForMissingDate() {
        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        SearchView searchView = mock(SearchView.class);
        SearchController searchController = new SearchController(searchView, mainController, databaseProxy);

        when(searchView.getPoi()).thenReturn(new Poi("N17328659"
                , "Wien"
                , new PoiCategory("66", "City")
                , "16.3725042"
                , "48.2083537"
        ));
        when(searchView.getComment()).thenReturn("comment");

        searchController.addActivity();

        verify(tripPlannerMain, times(1)).showErrorMessage("City/Point of interest, Date or Comment is missing.");
    }

    @Category({ UnitTest.class })
    @Test
    public void addActivityShowsErrorForMissingCommentNull() {
        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        SearchView searchView = mock(SearchView.class);
        SearchController searchController = new SearchController(searchView, mainController, databaseProxy);

        when(searchView.getPoi()).thenReturn(new Poi("N17328659"
                , "Wien"
                , new PoiCategory("66", "City")
                , "16.3725042"
                , "48.2083537"
        ));
        when(searchView.getDate()).thenReturn(new Date());

        searchController.addActivity();

        verify(tripPlannerMain, times(1)).showErrorMessage("City/Point of interest, Date or Comment is missing.");
    }

    @Category({ UnitTest.class })
    @Test
    public void addActivityShowsErrorForMissingCommentEmpty() {
        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        SearchView searchView = mock(SearchView.class);
        SearchController searchController = new SearchController(searchView, mainController, databaseProxy);

        when(searchView.getPoi()).thenReturn(new Poi("N17328659"
                , "Wien"
                , new PoiCategory("66", "City")
                , "16.3725042"
                , "48.2083537"
        ));
        when(searchView.getDate()).thenReturn(new Date());
        when(searchView.getComment()).thenReturn("");

        searchController.addActivity();

        verify(tripPlannerMain, times(1)).showErrorMessage("City/Point of interest, Date or Comment is missing.");
    }

    @Category({ UnitTest.class })
    @Test
    public void openPoiSearchShowsErrorForMissingPoi() {
        DatabaseProxy databaseProxy = mock(DatabaseProxy.class);
        TripPlannerMain tripPlannerMain = mock(TripPlannerMain.class);
        MainController mainController = new MainController(tripPlannerMain);
        SearchView searchView = mock(SearchView.class);
        SearchController searchController = new SearchController(searchView, mainController, databaseProxy);

        searchController.openPoiSearch();

        verify(tripPlannerMain, times(1)).showErrorMessage("Please select a city first.");
    }

}