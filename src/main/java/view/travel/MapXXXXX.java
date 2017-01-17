package view.travel;

import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.swing.MapView;
import model.common.Pair;
import model.travel.Activity;

import java.util.ArrayList;

public class MapXXXXX extends MapView {

    Map map;
    ArrayList<Pair<Marker, Activity>> markerList = new ArrayList<>();
    ArrayList<InfoWindow> windowList = new ArrayList<>();

    public MapXXXXX(MapViewOptions options) {
        super(options);
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
        for (Activity activity : activityList) {
            // Marker erstellen
            Marker marker = new Marker(map);
            marker.setPosition(new LatLng(activity.getPoi().getLatitudeDouble(), activity.getPoi().getLongitudeDouble()));
            markerList.add(new Pair<>(marker, activity));
            map.setCenter(new LatLng(activity.getPoi().getLatitudeDouble(), activity.getPoi().getLongitudeDouble()));

            // Adding event listener that intercepts clicking on marker
            marker.addEventListener("click", new MapMouseEvent() {
                @Override
                public void onEvent(MouseEvent mouseEvent) {
                    setWindow(activity);
                }
            });
        }
    }

    public void setMarker(Activity activity) {
        ArrayList<Activity> activityListWithOneEntry = new ArrayList<Activity>();
        activityListWithOneEntry.add(activity);
        setMarkerList(activityListWithOneEntry);
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
