package view.travel;

//import com.sun.tools.classfile.InnerClasses_attribute;

import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.swing.MapView;
import model.common.Poi;

import java.util.ArrayList;

public class MapWithPoi extends MapView {

    SearchView searchView;

    Map map;
    ArrayList<Pair> markerList = new ArrayList<>();
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
                    map.setZoom(8.0);
                    map.setCenter(new LatLng(46.8555150,9.5254066));
                }
            }
        });
    }

    /**
     * Hilfsklasse zum Speichern von zusammengehörenden Marker und Poi
     */
    class Pair {
        private Marker marker;
        private Poi poi;

        Pair(Marker marker, Poi poi) {
            this.marker = marker;
            this.poi = poi;
        }

        public Marker getMarker() {
            return marker;
        }
        public Poi getPoi() {
            return poi;
        }
    }

    public void closeAllWindows() {
        for (InfoWindow window : windowList) {
            window.close();
        }
    }

    public void setMarkerList(ArrayList<Poi> poiList) {
        closeAllWindows();
        for (Pair pair : markerList) {
            pair.getMarker().remove();
        }
        markerList.clear();
        windowList.clear();
        for (Poi poi : poiList) {
            // Marker erstellen
            Marker marker = new Marker(map);
            marker.setPosition(new LatLng(poi.getLatitudeDouble(), poi.getLongitudeDouble()));
            markerList.add(new Pair(marker, poi));
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
        InfoWindow window = new InfoWindow(map);
        window.setContent(poi.getName());

        Marker marker = null;
        for (Pair pair : markerList) {
            if (pair.getPoi().equals(poi)) {
                marker = pair.getMarker();
            }
        }

        window.open(map, marker);
        windowList.add(window);
    }

}
