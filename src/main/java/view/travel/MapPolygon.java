package view.travel;

import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.swing.MapView;
import controller.common.MainController;
import model.common.Pair;
import model.travel.Activity;
import sun.applet.Main;

import java.util.ArrayList;

public class MapPolygon extends MapView {

    Map map;
    MainController mainController;
    ArrayList<Pair<Marker, Activity>> markerList = new ArrayList<>();
    ArrayList<InfoWindow> windowList = new ArrayList<>();

    public MapPolygon(MapViewOptions options, MainController mainController) {
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
        // Creating a new polygon object
        Polygon polygon = new Polygon(map);
        // Initializing the polygon with the created path
        polygon.setPath(path);
        // Creating a polyline options object
        PolygonOptions options = new PolygonOptions(map);
        // Setting fill color value
//        options.setFillColor("#FF0000");
        // Setting fill opacity value
//        options.setFillOpacity(0.35);
        // Setting stroke color value
        options.setStrokeColor("#FF0000");
        // Setting stroke opacity value
        options.setStrokeOpacity(0.8);
        // Setting stroke weight value
        options.setStrokeWeight(2.0);
        // Applying options to the polygon
        polygon.setOptions(options);
    }

    public void setWindow(Activity activity) {
        if (activity != null) {
            InfoWindow window = new InfoWindow(map);
            window.setContent(activity.getPoi().getName());

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
