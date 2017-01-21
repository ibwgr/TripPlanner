package view.travel;

import com.teamdev.jxmaps.MapViewOptions;
import controller.common.MainController;
import controller.travel.CompleteTripController;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;

public class BigMapView extends JPanel {

    MainController mainController;
    CompleteTripController completeTripController;

    JList searchResult;
    JTextField searchText;
    JTextArea commentText;
    JXDatePicker datePicker;
    MapPolylineSingle mapView;

    public BigMapView(MainController mainController) {
        this.mainController = mainController;
        completeTripController = new CompleteTripController(this, mainController);

        this.setLayout(new BorderLayout());

        // NORTH: empty

        // CENTER: show big map
/*
        JPanel centerPanel = new JPanel(new GridLayout(1,1));
        centerPanel.add(new JScrollPane(searchResult = new JList<>()));
        searchResult.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                mapView.setWindow((Poi) searchResult.getSelectedValue());
            }
        });
*/

        MapViewOptions options = new MapViewOptions();
        options.importPlaces();
        mapView = new MapPolylineSingle(options, mainController);
//        centerPanel.add(mapView);

//        mapView.setMarkerList(Activity.searchByTrip(mainController.getTrip()));

        this.add(mapView, BorderLayout.CENTER);

        // SOUTH: empty


    }

}
