package view.travel;

import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.swing.MapView;
import controller.common.MainController;
import model.common.Pair;
import model.travel.Activity;

import java.util.ArrayList;

public class MapPolyline extends MapView {

    Map map;
    MainController mainController;
    ActivityView activityView;
    ArrayList<Pair<Marker, Activity>> markerList = new ArrayList<>();
    ArrayList<InfoWindow> windowList = new ArrayList<>();
    Polyline polyline;

    public MapPolyline(MainController mainController, ActivityView activityView) {
        this.mainController = mainController;
        this.activityView = activityView;
        initMap();
    }

    public void initMap() {
       setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                // Check if the map is loaded correctly
                if (status == MapStatus.MAP_STATUS_OK) {
                    // Getting the associated map object
                    map = getMap();
                    // Creating a map options object
                    MapOptions mapOptions = new MapOptions();
                    // Creating a map type control options object
                    MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                    // Changing position of the map type control
                    controlOptions.setPosition(ControlPosition.TOP_RIGHT);
                    // Setting map type control options
                    mapOptions.setMapTypeControlOptions(controlOptions);
                    // Setting map options
                    map.setOptions(mapOptions);
                    // Setting initial zoom value
                    map.setZoom(5.0);
                    // unbenoetigte optische Google Map Elemente entfernen
                    MapHelper.removeGoogleMapElements(map);
                    //
                    refresh();
                }
            }
        });
    }

    public void refresh() {
        setMarkerList(Activity.searchByTrip(mainController.getTrip()));
    }

    public void setMarkerList(ArrayList<Activity> activityList) {
        for (Pair<Marker, Activity> pair : markerList) {
            pair.getKey().remove();
        }
        markerList.clear();
        windowList.clear();

/*
        LatLng[] path = new LatLng[activityList.size()];
        int i = -1;
        for (Activity activity : activityList) {
            LatLng latLng = new LatLng(activity.getPoi().getLatitudeDouble(), activity.getPoi().getLongitudeDouble());
            path[++i] = latLng;
            map.setCenter(latLng);
        }
*/
        //Beispiel
        //                    LatLng[] path = {new LatLng(37.772, -122.214),
        //                            new LatLng(21.291, -157.821),
        //                            new LatLng(-18.142, 178.431),
        //                            new LatLng(-27.467, 153.027)};

        LatLng[] path = new LatLng[activityList.size()];
        int i = -1;
        for (Activity activity : activityList) {
            System.out.println("Reihenfolge " +(i+1) +" --> "+activity.getCity());
            // Marker erstellen
            Marker marker = new Marker(map);
            LatLng latLng = new LatLng(activity.getPoi().getLatitudeDouble(), activity.getPoi().getLongitudeDouble());
            marker.setPosition(latLng);
            marker.setTitle(activity.getPoi().getName());
            markerList.add(new Pair<>(marker, activity));
            map.setCenter(latLng);
            // Adding event listener that intercepts clicking on marker
            marker.addEventListener("click", new MapMouseEvent() {
                @Override
                public void onEvent(MouseEvent mouseEvent) {
                    closeAllWindows();
                    activityView.setActivityInList(activity);
                    setWindow(activity);
                }
            });
            path[++i] = latLng;
        }
        // Creating a new polyline object
        // Falls aber schon eines existiert, bringt man es kaum mehr von der Map weg
        // sieht jetzt etwas umstaendlich aus, aber bis anhin hat nichts anderes funktioniert!
        if (polyline == null) {
            System.out.println("polyline NEU");
            polyline = new Polyline(map);
        } else {
            System.out.println("polyline BESTEHEND, loeschen");
            polyline.setVisible(false);
            polyline = null;
            polyline = new Polyline(map);
        }
        polyline.setPath(path);
        //
        // Creating a polyline options object
        PolylineOptions options = new PolylineOptions();
        // Setting geodesic property value
        options.setGeodesic(false);
        // Setting stroke color value
        //options.setStrokeColor("#F7584C");
        options.setStrokeWeight(3);
        // Setting stroke opacity value
        options.setStrokeOpacity(1.0);
        // Setting stroke weight value
        options.setClickable(false);
        // Die Polyline mit Pfeilen versehen
        Symbol lineSymbol = new Symbol();
        lineSymbol.setPath(StandardSymbol.FORWARD_CLOSED_ARROW);
        lineSymbol.setStrokeColor("#F7584C");
        lineSymbol.setFillColor("#F7584C");
        IconSequence iconSequence = new IconSequence();
        iconSequence.setIcon(lineSymbol );
        iconSequence.setRepeat("50px");
        options.setIcons(new IconSequence[]{iconSequence});
        //
        // Applying ALL options to the polyline
        polyline.setOptions(options);
    }

    public void closeAllWindows() {
        for (InfoWindow window : windowList) {
            window.close();
        }
    }

    public void setWindow(Activity activity) {
        closeAllWindows();
        if (activity != null) {
            InfoWindow window = new InfoWindow(map);
            window.setContent(activity.getCity() +": " +activity.getPoi().getName() );

            Marker marker = null;
            for (Pair<Marker, Activity> pair : markerList) {
                if (pair.getValue().getId().equals(activity.getId())) {
                    marker = pair.getKey();
                }
            }

            window.open(map, marker);
            windowList.add(window);
        }
    }

}