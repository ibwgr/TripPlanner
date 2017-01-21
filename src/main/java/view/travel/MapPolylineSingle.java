package view.travel;

import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.swing.MapView;
import controller.common.MainController;
import model.common.Pair;
import model.travel.Activity;
import sun.applet.Main;

import java.util.ArrayList;

public class MapPolylineSingle extends MapView {

    Map map;
    MainController mainController;
    ArrayList<Pair<Marker, Activity>> markerList = new ArrayList<>();
    ArrayList<InfoWindow> windowList = new ArrayList<>();

    public MapPolylineSingle(MapViewOptions options, MainController mainController) {
        super(options);
        this.mainController = mainController;
        initMap();
    }

    public void initMap() {
        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                if (status == MapStatus.MAP_STATUS_OK) {
                    map = getMap();
                    map.setZoom(10.0);
                    map.setCenter(new LatLng(46.8555150,9.5254066));
                    setMarkerList(Activity.searchByTrip(mainController.getTrip()));
                }
            }
        });
    }

    public void setMarkerList(ArrayList<Activity> activityList) {
        for (Pair<Marker, Activity> pair : markerList) {
            pair.getKey().remove();
        }
        markerList.clear();
        windowList.clear();
        LatLng[] path = new LatLng[activityList.size()];
        int i = -1;
        for (Activity activity : activityList) {
            // Marker erstellen
            Marker marker = new Marker(map);
            LatLng latLng = new LatLng(activity.getPoi().getLatitudeDouble(), activity.getPoi().getLongitudeDouble());
            marker.setPosition(latLng);
            markerList.add(new Pair<>(marker, activity));
            map.setCenter(latLng);

            // Adding event listener that intercepts clicking on marker
            marker.addEventListener("click", new MapMouseEvent() {
                @Override
                public void onEvent(MouseEvent mouseEvent) {
                    setWindow(activity);
                }
            });
            path[++i] = latLng;
        }
        // Creating a new polyline object
        Polyline polyline = new Polyline(map);
        // Initializing the polyline with the created path
        polyline.setPath(path);
        // Creating a polyline options object
        PolylineOptions options = new PolylineOptions();
        // Setting geodesic property value
        options.setGeodesic(true);
        // Setting stroke color value
        options.setStrokeColor("#FF0000");
        // Setting stroke opacity value
        options.setStrokeOpacity(1.0);
        // Setting stroke weight value
        options.setStrokeWeight(2.0);

        // TODO Reto, fertigmachen
        //https://developers.google.com/maps/documentation/javascript/examples/overlay-symbol-arrow?hl=de
        // Google Maps API (JavaScript) verlangt:
        // Define a symbol using a predefined path (an arrow)
        // supplied by the Google Maps JavaScript API.
        // var lineSymbol = {
        //        path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW
        //
        // new google.maps.Polyline({
        //icons: [{
        //   icon: lineSymbol,
        //            offset: '100%'
        //}],
        Symbol icon = new Symbol();
        icon.setPath("google.maps.SymbolPath.FORWARD_CLOSED_ARROW");

        IconSequence iconSequence = new IconSequence();
        iconSequence.setIcon(icon);
        iconSequence.setOffset("100%");

        options.setIcons(new IconSequence[]{iconSequence});

        // Applying ALL options to the polyline
        polyline.setOptions(options);
    }

    public void setWindow(Activity activity) {
        if (activity != null) {
            InfoWindow window = new InfoWindow(map);
            window.setContent("<b>" + activity.getCity() +": " +activity.getPoi().getName() + "</b><p>" + activity.getComment() + "</p>");

            // falls Fenster bereits offen ist, schliessen
            for (InfoWindow window2 : windowList) {
                if (window2.getContent().equals(window.getContent())) {
                    window2.close();
                }
            }

            Marker marker = null;
            for (Pair<Marker, Activity> pair : markerList) {
                if (pair.getValue().equals(activity)) {
                    marker = pair.getKey();
                }
            }

            window.open(map, marker);
            windowList.add(window);
        }
    }

}
