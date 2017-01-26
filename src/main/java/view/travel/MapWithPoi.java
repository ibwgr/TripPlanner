package view.travel;

import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.swing.MapView;
import model.common.Pair;
import model.common.Poi;

import java.util.ArrayList;

/**
 * Map nur mit Markern
 *
 * @author  Reto Kaufmann
 */
public class MapWithPoi extends MapView {

    SearchView searchView;

    Map map;
    ArrayList<Pair<Marker, Poi>> markerList = new ArrayList<>();
    ArrayList<InfoWindow> windowList = new ArrayList<>();

    public MapWithPoi(MapViewOptions options, SearchView searchView) {
        super(options);
        this.searchView = searchView;
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
                    // unbenoetigte optische Google Map Elemente entfernen
                    MapHelper.removeGoogleMapElements(map);
                }
            }
        });
    }

    public void closeAllWindows() {
        for (InfoWindow window : windowList) {
            window.close();
        }
    }

    public void setMarkerList(ArrayList<Poi> poiList) {
        closeAllWindows();
        for (Pair<Marker, Poi> pair : markerList) {
            pair.getKey().remove();
        }
        markerList.clear();
        windowList.clear();
        for (Poi poi : poiList) {
            // Marker erstellen
            Marker marker = new Marker(map);
            marker.setPosition(new LatLng(poi.getLatitudeDouble(), poi.getLongitudeDouble()));
            markerList.add(new Pair<>(marker, poi));
            map.setCenter(new LatLng(poi.getLatitudeDouble(), poi.getLongitudeDouble()));

            // Adding event listener that intercepts clicking on marker
            marker.addEventListener("click", new MapMouseEvent() {
                @Override
                public void onEvent(MouseEvent mouseEvent) {
                    closeAllWindows();
                    searchView.setPoiInList(poi);
                    setWindow(poi);
                }
            });

        }

    }

    public void setWindow(Poi poi) {
        closeAllWindows();
        if (poi != null) {
            InfoWindow window = new InfoWindow(map);
            window.setContent(poi.getName());

            Marker marker = null;
            for (Pair<Marker, Poi> pair : markerList) {
                if (pair.getValue().equals(poi)) {
                    marker = pair.getKey();
                }
            }

            window.open(map, marker);
            windowList.add(window);
        }
    }

}
