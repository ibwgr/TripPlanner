package view.travel;

import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.swing.MapView;
import controller.common.MainController;
import model.travel.Activity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This example demonstrates how to draw polylines on the map.
 *
 * @author Vitaly Eremenko
 */
public class MapPolyline extends MapView {

    MainController mainController;

    public MapPolyline(MainController mainController) {
        this.mainController = mainController;
        initMap();
    }

    public void initMap() {
       setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                // Check if the map is loaded correctly
                if (status == MapStatus.MAP_STATUS_OK) {
                    // Getting the associated map object
                    final Map map = getMap();
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

                    // Creating a path (array of coordinates) that represents a polyline
                    ArrayList<Activity> activityList = Activity.searchByTrip(mainController.getTrip());
                    LatLng[] path = new LatLng[activityList.size()];
                    int i = -1;
                    for (Activity activity : activityList) {
                        LatLng latLng = new LatLng(activity.getPoi().getLatitudeDouble(), activity.getPoi().getLongitudeDouble());
                        path[++i] = latLng;
                        map.setCenter(latLng);
                    }
                    //Beispiel
                    //                    LatLng[] path = {new LatLng(37.772, -122.214),
                    //                            new LatLng(21.291, -157.821),
                    //                            new LatLng(-18.142, 178.431),
                    //                            new LatLng(-27.467, 153.027)};

                    // Creating a new polyline object
                    Polyline polyline = new Polyline(map);
                    // Initializing the polyline with created path
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
                    // Applying options to the polyline
                    polyline.setOptions(options);
                }
            }
        });
    }
}